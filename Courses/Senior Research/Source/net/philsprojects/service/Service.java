package net.philsprojects.service;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import net.philsprojects.util.BlockableQueue;
import net.philsprojects.util.Notifier;
import net.philsprojects.util.Release;
import net.philsprojects.util.Sleepable;
import net.philsprojects.util.State;

/**
 * A service is a more friendly version of java.lang.Thread. A service can
 * have several states and can be restarted (opposed to a once and done Thread).
 * A service can automatically handle events passed to the service. A service
 * also has a set of listeners which will be notified when a change in state
 * occurs (started, stopped, paused, resumed, etc). All listeners will also
 * be notified of the events recieved in the context of the service's thread.
 * A service is built for optimum performance when state changes are infrequent.
 * Frequent state changes will cause a lot of contention among all threads
 * modifying or viewing the state of the service.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The event added to the service. Events added to the service
 * 		are handled in the service's thread by invoking the onServiceEvent
 * 		method in all attached listeners.
 */
public abstract class Service<E> implements EventHandler<E>, Runnable, Sleepable
{

	/**
	 * The state of the service when its completely paused.
	 */
	public static final int Paused = State.create(0);

	/**
	 * The state of the service when its in the process of pausing.
	 */
	public static final int Pausing = State.create(1);

	/**
	 * The state of the service when its operating normally.
	 */
	public static final int Running = State.create(2);

	/**
	 * The state of the service when its completely stopped.
	 */
	public static final int Stopped = State.create(3);

	/**
	 * The state of the service when its in the process of stopping.
	 */
	public static final int Stopping = State.create(4);


	// The state of this Service.
	private final State state = new State();

	
	// Whether this service is paused or trying to be paused.
	private volatile boolean paused = false;
	
	// Whether this service is stopped or trying to be stopped.
	private volatile boolean stopped = false;
	

	// An interrupt object which dictates what things (events, executes) can be 
	// done during an interrupting state (pausing or stopping).
	private volatile ServiceInterrupt interrupt = ServiceInterrupt.None;

	// The set of listeners to this services events.
	protected final Notifier<ServiceListener<E>> notifier;
	
	// The set of blockers that need to be awakened when this service is in the
	// process of being paused or stopped.
	protected final Release release;
	
	// The thread executing this service.  
	private Thread thread;

	// The queue events are added to. If this queue is being shared then
	// events can be added externally. If this queue is shared and events are
	// added to this service, this service won't be guarunteed to get the event,
	// another service may take it if this Service is busy.
	private final BlockableQueue<E> eventQueue;

	
	
	// The remaining number of events this service can handle before it
	// automatically stops.
	private final AtomicInteger remainingEvents = new AtomicInteger(-1);

	// The remaining number of iterations this service can process before it
	// automatically stops.
	private final AtomicInteger remainingIterations = new AtomicInteger(-1);

	// The remaining number of executes invokable before this service 
	// automatically stops.
	private final AtomicInteger remainingExecutes = new AtomicInteger(-1);

	// Whether this service will accept adding events through the addEvent
	// method. Events can still be given to the event queue if one was passed
	// to this service in the constructor.
	private final AtomicBoolean acceptsEvents = new AtomicBoolean(true);

	// Whether processing events will occur in this service. If true, added 
	// events will be processed as soon as the last execution finishes. If false
	// events will continue to be queued and not processed until this is set to
	// true.
	private final AtomicBoolean activeEvents = new AtomicBoolean(true);

	// Whether invoking execute will occur in this service. If true, every 
	// iteration all listeners will be notified. If false, no listeners during
	// any iterations will be notified.
	private final AtomicBoolean activeExecute = new AtomicBoolean(true);


	/**
	 * Instantiates a new Service.
	 */
	public Service() 
	{
		this(new BlockableQueue<E>());
	}
	
	/**
	 * Instantiates a new Service.
	 * 
	 * @param sourceQueue
	 * 		The queue implementation to use internally as an event queue.
	 */
	public Service(Queue<E> sourceQueue) 
	{
		this(new BlockableQueue<E>(sourceQueue));
	}
	
	/**
	 * Instantiates a new Service given a queue to poll and offer events to.
	 * 
	 * @param eventQueue
	 * 		The queue of events.
	 */
	public Service(BlockableQueue<E> eventQueue) 
	{
		this.eventQueue = eventQueue;
		this.notifier = Notifier.create(ServiceListener.class);
		this.release = new Release();
		this.release.getBlockers().add(this);
		this.state.set(Stopped);
	}
	
	
	/**
	 * The queue of events which have been added to the service. Events should
	 * not be directly added to this queue unless you mean to purposely bypass
	 * all logic imposed by several properties of this Service.
	 * 
	 * @return
	 * 		The reference to the event queue.
	 */
	public BlockableQueue<E> getEventQueue() 
	{
		return eventQueue;
	}
	
	
	/**
	 * Starts this service and waits for it to finish starting. If this service
	 * is in the Stopping state, this will wait indefinitely until the service
	 * has stopped. If the service is in the Stopped state then this service
	 * will be started, else this will wait indefinitely until the service is
	 * in a started state (Paused, Pausing, Running).
	 *  
	 * @return
	 * 		True if the service was started, otherwise false.
	 */
	public boolean start() 
	{
		return start(true, Long.MAX_VALUE);
	}
	
	/**
	 * Starts this service and optionally waits for it to finish starting. If 
	 * this service is in the Stopping state, this will wait indefinitely until 
	 * the service has stopped. If the service is in the Stopped state then this
	 * service will be started, else this will wait indefinitely until the 
	 * service is in a started state (Paused, Pausing, Running).
	 * 
	 * @param wait
	 * 		True if the invoking thread should wait until this service is in a
	 * 		started state, false if this method should return immediately.
	 * @return
	 * 		True if the service was started, otherwise false.
	 */
	public boolean start(boolean wait) 
	{
		return start(wait, Long.MAX_VALUE);
	}
	
	/**
	 * Starts this service and optionally waits for a maximum amount of time for
	 * it to finish starting. If this service is in the Stopping state, this 
	 * will wait the maximum time until the service has stopped. If the service
	 * is in the Stopped state then this service will be started, else this will
	 * wait the maximum time until the service is in a started state (Paused, 
	 * Pausing, Running). 
	 * 
	 * @param wait
	 * 		True if the invoking thread should wait until this service is in a
	 * 		started state, false if this method should return immediately.
	 * @param timeout
	 * 		The maximum amount of time in milliseconds to wait for the service 
	 * 		to start or wait for the service to stop if it is Stopping. 
	 * @return
	 * 		True if the service was started, otherwise false.
	 */
	public boolean start(boolean wait, long timeout) 
	{
		synchronized (state) 
		{
			// Acceptable states to be considered "started"
			final int STARTED_STATE = Running | Paused | Pausing;
			
			// If its stopping, wait for it to stop.
			if (state.has(Stopping)) {
				state.waitFor(Stopped, timeout);
			}
			// Only start if it's in the stopped state.
			if (state.has(Stopped)) {
				thread = new Thread(this);
				
				try {
					thread.start();	
				}
				// Don't have enough memory to allocate another thread.
				catch (java.lang.OutOfMemoryError e) {
					System.err.println("Cannot allocate a Thread; out of memory.");
					return false;
				}
				
				// Mark as running, even though the thread may have not started.
				state.set(Running);		
			}
			// Wait for started state if specified.
			if (wait) {
				state.waitFor(STARTED_STATE, timeout);
			}
			
			// Are we in a started state?
			return state.has(STARTED_STATE);
		}
	}

	/**
	 * Pauses this service and waits indefinitely for it to be in a resting 
	 * state (Paused, Stopped). If the service is not started, already paused, 
	 * or is stopped this will return immediately. If this service is in the 
	 * Pausing or Stopping state this method will simply wait indefinitely for 
	 * it to reach its resting state. This pause will not wait for all events
	 * to be processed, it will interrupt all processing immediately. 
	 *
	 * @return
	 * 		True if this service is now in a resting state (Paused, Stopped).
	 */
	public boolean pause() 
	{
		return pause(ServiceInterrupt.Immediate, true, Long.MAX_VALUE);
	}
	
	/**
	 * Pauses this service and optionally waits indefinitely for it to be in a 
	 * resting state (Paused, Stopped). If the service is not started, already 
	 * paused, or is stopped this will return immediately. If this service is in
	 * the Pausing or Stopping state this method will simply wait indefinitely 
	 * for it to reach its resting state. This pause will not wait for all 
	 * events to be processed, it will interrupt all processing immediately.
	 * 
	 * @param wait
	 * 		True if the invoking thread should wait until this service is in a
	 * 		resting state, false if this method should return immediately.
	 * @return
	 * 		True if this service is now in a resting state (Paused, Stopped).
	 */
	public boolean pause(boolean wait) 
	{
		return pause(ServiceInterrupt.Immediate, wait, Long.MAX_VALUE);
	}
	
	/**
	 * Pauses this service and optionally waits a maximum time for it to be in a 
	 * resting state (Paused, Stopped). If the service is not started, already 
	 * paused, or is stopped this will return immediately. If this service is in
	 * the Pausing or Stopping state this method will simply wait a maximum time 
	 * for it to reach its resting state. This pause will not wait for all 
	 * events to be processed, it will interrupt all processing immediately.
	 * 
	 * @param wait
	 * 		True if the invoking thread should wait until this service is in a
	 * 		resting state, false if this method should return immediately.
	 * @param timeout
	 * 		The maximum amount of time in milliseconds to wait for the service 
	 * 		to be in a resting state. 
	 * @return
	 * 		True if this service is now in a resting state (Paused, Stopped).
	 */
	public boolean pause(boolean wait, long timeout) 
	{
		return pause(ServiceInterrupt.Immediate, wait, timeout);
	}
	
	/**
	 * Pauses this service and optionally waits a maximum time for it to be in a 
	 * resting state (Paused, Stopped). If the service is not started, already 
	 * paused, or is stopped this will return immediately. If this service is in
	 * the Pausing or Stopping state this method will simply wait a maximum time 
	 * for it to reach its resting state. This pause can choose to wait for all 
	 * events to finish processing, and choose to invoke execute before the
	 * service comes to a resting state.
	 * 
	 * @param serviceInterrupt
	 * 		The type of interruption. This can specify whether to finish 
	 * 		processing events, or whether to skip execution. This is typically
	 *		used to ensure that all events that have been added to the service
	 *		are processed before resting the service.
	 * @param wait
	 * 		True if the invoking thread should wait until this service is in a
	 * 		resting state, false if this method should return immediately.
	 * @param timeout
	 * 		The maximum amount of time in milliseconds to wait for the service 
	 * 		to be in a resting state. 
	 * @return
	 * 		True if this service is now in a resting state (Paused, Stopped).
	 */
	public boolean pause(ServiceInterrupt serviceInterrupt, boolean wait, long timeout) 
	{
		synchronized (state) 
		{
			// Acceptable states to be considered "resting"
			final int RESTING_STATE = Paused | Stopped;
			
			// If the service is currently running...
			if (state.equals(Running)) 
			{
				// Acquire the lock on the releaser
				release.lock(); 
				try {
					// Mark this as true, this will set off the double checked
					// locking of the service, which is more efficient than
					// having the service constantly checking its state for each
					// event end execution.
					paused = true;
					
					// Set the requested interruption type.
					interrupt = serviceInterrupt;
					
					// Now in the pausing state.
					state.set(Pausing);
					
					// Awake any blockers.
					release.awake();
					
					// Wait for the resting state?
					if (wait) {
						state.waitFor(RESTING_STATE, timeout);
					}
				}
				finally {
					// Always unlock, even if an error occurs above.
					release.unlock();
				}
			}
			// Not running, wait for the resting state?
			else if (wait) {
				state.waitFor(RESTING_STATE, timeout);
			}
			
			// Are we in a resting state?
			return state.has(RESTING_STATE);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void awake() 
	{
		eventQueue.wakeup();
	}

	
	
	public void resume() {
		this.resume(true, Long.MAX_VALUE);
	}
	public void resume(boolean wait) {
		this.resume(wait, Long.MAX_VALUE);
	}
	public void resume(boolean wait, long timeout) {
		synchronized (state) {
			if (state.has(Pausing)) {
				state.waitFor(Paused, timeout);
			}
			if (state.has(Paused)) {
				paused = false;
				state.set(Running);
			}
		}
	}

	public void stop() {
		this.stop(ServiceInterrupt.Immediate, true, Long.MAX_VALUE);
	}
	public void stop(boolean wait) {
		this.stop(ServiceInterrupt.Immediate, wait, Long.MAX_VALUE);
	}
	public void stop(boolean wait, long timeout) {
		this.stop(ServiceInterrupt.Immediate, wait, timeout);
	}
	public void stop(ServiceInterrupt serviceInterrupt, boolean wait, long timeout) {
		synchronized (state) {
			if (state.has(Pausing)) {
				state.waitFor(Paused, timeout);
			}
			if (state.has(Running | Paused)) {
				release.lock();
				try {
					stopped = true;
					interrupt = serviceInterrupt;
					state.set(Stopping);
					release.awake();
					
					if (wait) {
						state.waitFor(Stopped, timeout);
					}
				}
				finally {
					release.unlock();
				}
			}
			else if (wait){
				state.waitFor(Stopped, timeout);
			}
		}
	}

	/**
	 * 
	 */
	public void clear() 
	{
		eventQueue.clear();
	}

	/**
	 * 
	 * @param event
	 */
	public boolean addEvent(E event) 
	{
		if (!acceptsEvents.get()/* || !state.has(Running | Paused | Pausing)*/) {
			return false;
		}
		return eventQueue.offer(event);
	}

	/**
	 * 
	 * @param accept
	 */
	public void setEventAccept(boolean accept) 
	{
		this.acceptsEvents.set(accept);
	}
	
	/**
	 * 
	 * @param active
	 */
	public void setEventActive(boolean active) 
	{
		this.activeEvents.set(active);
	}

	/**
	 * 
	 * @param active
	 */
	public void setExecuteActive(boolean active) 
	{
		this.activeExecute.set(active);
	}

	/**
	 * 
	 * @return
	 */
	public Thread getThread() 
	{
		synchronized (state) 
		{
			return thread;
		}
	}

	/**
	 * 
	 */
	public void run() 
	{
		// Applies the running state, and notifies all listeners that the
		// service has started.
		notifier.proxy().onServiceStart(this);

		// While the service is in a runnable state...
		while (!stopped) 
		{
			boolean valid = true;
			// If event processing is active...
			if (activeEvents.get()) 
			{
				E event = null;
				// Take all events from the event queue and process them.
				for (;;) 
				{
					// Clear event, very important!
					event = null;
					
					// Enter blocking section with caution!
					if (release.enter()) {
						try {
							event = eventQueue.poll();	
						}
						finally {
							release.exit();	
						}
					}
					
					// If event was null (queue is empty and it returned
					// immediately or it was awoken by an attempt to pause or
					// stop the server) then stop processing events.
					if (event == null) {
						canEvent();
						break;	
					}
					
					// If events can be processed right now...
					if (canEvent()) 
					{
						// Adjust validation based on remaining events.
						valid &= (remainingEvents.getAndDecrement() < 0);

						// If the service state is valid, notify listeners.
						if (valid) {
							// Quicker then calling the proxy, and this could
							// be called VERY frequently.
							// notifier.proxy().onServiceEvent(this, event);
							for (ServiceListener<E> listener : notifier) {
								listener.onServiceEvent(this, event);
							}
						}
					}
				}	
			}
			// If execution is active...
			if (activeExecute.get()) {
				// If execution can occur right now...
				if (canExecute()) {
					// Adjust validation based on remaining executions
					valid &= (remainingExecutes.getAndDecrement() < 0);

					// If the service state is valid, notify listeners.
					if (valid) {
						// Quicker then calling the proxy, and this could
						// be called VERY frequently.
						// notifier.proxy().onServiceExecute(this);
						for (ServiceListener<E> listener : notifier) {
							listener.onServiceExecute(this);
						}
					}
				}
			}

			// Adjust validation based on remaining iterations
			valid &= (remainingIterations.getAndDecrement() <= 0);

			// If the service state is not valid, stop service.
			if (!valid) {
				state.set(Stopping);
			}
		}

		// Applies the stopped state, and notifies all listeners that the
		// service has stopped.
		state.set(Stopped);
		
		notifier.proxy().onServiceStop(this, interrupt);
	}

	/**
	 * 
	 * @param desiredState
	 * @return
	 */
	public boolean waitFor(int desiredState) 
	{
		return waitFor(desiredState, Long.MAX_VALUE);
	}

	/**
	 * 
	 * @param desiredState
	 * @param timeout
	 * @return
	 */
	public boolean waitFor(int desiredState, long timeout) 
	{
		return state.waitFor(desiredState, timeout);
	}

	/**
	 * 
	 * @param desiredState
	 * @return
	 */
	public boolean hasState(int desiredState) 
	{
		return state.has(desiredState);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRunning() 
	{
		return state.has(Running | Paused | Pausing);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isResting() 
	{
		return state.has(Stopped | Paused);
	}
	
	/**
	 * 
	 * @return
	 */
	public Notifier<ServiceListener<E>> getListeners() 
	{
		return notifier;
	}

	/**
	 * 
	 * @return
	 */
	public Release getRelease() 
	{
		return release;
	}
	
	//==========================================================================
	
	/**
	 * 
	 */
	private boolean canEvent() 
	{
		checkPause();
		return interrupt.runEvents;
	}

	/**
	 * 
	 * @return
	 */
	private boolean canExecute() 
	{
		checkPause();
		return interrupt.runExecute;
	}
	
	/**
	 * 
	 */
	private void checkPause() 
	{
		if (paused) {
			synchronized (state) {
				if (state.has(Pausing)) {
					state.set(Paused);
					notifier.proxy().onServicePause(this, interrupt);
					state.waitFor(Running | Stopping);
					notifier.proxy().onServiceResume(this, interrupt);
				}
			}
		}
	}

}