package net.philsprojects.service;

import java.util.ArrayList;

import net.philsprojects.util.BlockableQueue;
import net.philsprojects.util.Notifier;
import net.philsprojects.util.State;

/**
 * A manager of services that handles events by delegating them to a pool of
 * services. The pool can be manipulated explicitly or its manipulated 
 * automatically adjusting its Services whenever certain things are triggered
 * by adding an event. Adding an event can trigger the allocation and
 * deallocation of the services, none of these will block the thread which added
 * the event which triggered it, but it will take a brief amount of time.
 * 
 * Pool properties are not synchronized with the pool, therefore changes while
 * the pool is started may not be immediate. The pool properties preferrably 
 * should be setup before the pool is started, or atleast only modified by one
 * thread at a time.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The event type.
 */
@Deprecated
public class ServicePool<E> implements EventHandler<E> 
{

	/**
	 * The state the pool is in when it is accepting events and it has a set
	 * of services it manages.
	 */
	public static final int Running = State.create(0);
	
	/**
	 * The state of the pool when it can't accept events and it has no services.
	 */
	public static final int Stopped = State.create(1);
	
	
	
	// The maximum number of services that can exist in the pool.
	protected int maxCapacity = 10;
	
	// The minimum number of services that can exist in the pool.
	protected int minCapacity = 2;
	
	// The number of services to allocate at once when they're required.
	protected int allocateSize = 2;

	// The number of services to deallocate at once.
	protected int deallocateSize = 2;
	
	// The minimum amount of time to wait since the last allocation before 
	// services can be deallocated.
	protected long allocateThreshold = 2000;
	
	// The last time services were allocated.
	protected long lastAllocateTime = Long.MAX_VALUE;
	
	// The number of queued events that trigger the allocation of additional
	// services.
	protected int eventThreshold = 2;
	
	// The max number of events before they must be rejected.
	protected int maxEvents = Integer.MAX_VALUE;

	// Whether this pool is currently accepting events. 
	private volatile boolean accepting = false;
	
	// The internal list of services.
	protected final ArrayList<Service<E>> services;
	
	// The notifier of listeners on the pools events.
	protected final Notifier<ServicePoolListener<E>> listeners;
	
	// The queue of events, where the services in the pool poll from.
	protected final BlockableQueue<E> events;
	
	// The current state of the pool.
	protected final State state;

	// The factory used to create services.
	protected ServiceFactory<E> factory;
	

	/**
	 * Instantiates a new ServicePool.
	 *
	 * @param factory
	 * 		The factory used to create new services.
	 */
	public ServicePool(ServiceFactory<E> factory) 
	{
		this.factory = factory;
		this.services = new ArrayList<Service<E>>();
		this.listeners = Notifier.create(ServicePoolListener.class);
		this.events = new BlockableQueue<E>();
		this.events.setBlocking(true);
		this.state = new State(Stopped);
	}
	
	/**
	 * Adds an event to the pool to be handled by a service. Adding events will 
	 * not cause contention among threads invoking this simultaneously unless 
	 * threads must be allocated or deallocated.
	 * 
	 * @param event
	 * 		The event to add to the pool to be handled by a service.
	 * @return
	 * 		True if the event could be added to the pool, false if this pool
	 * 		is not accepting or this pool cannot handle anymore events.
	 */
	public boolean addEvent(E event) 
	{
		// If the pool is not accepting or we cannot handle anymore events
		// then return immediately with a rejection.
		if (!accepting || events.size() >= maxEvents) {
			return false;
		}

		// Do a quick check on allocating, if this is true, invoke grow which 
		// will obtain the lock and double check it.
		if (isUnderflow()) {
			grow();
		}
		
		// Do a quick check on deallocating, if this is true invoke shrink which
		// will obtain the lock and double check it, while proceeding.
		if (isOverflow()) {
			shrink();	
		}
		
		// Add the event to the queue so services can grab it.
		return events.offer(event);
	}

	/**
	 * Grows the pool if more services are required.
	 */
	private void grow()
	{
		synchronized (state) 
		{
			if (isUnderflow()) {
				allocate(allocateSize);
			}
		}
	}
	
	/**
	 * Shrinks the pool if less services are required.
	 */
	private void shrink() 
	{
		synchronized (state) 
		{
			if (isOverflow()) {
				deallocate(deallocateSize);
			}
		}
	}
	
	/**
	 * Allocates the requested number of services. If the number of services
	 * requested exceeds the number of maximum possible services the pool will
	 * be filled to its maximum size.
	 * 
	 * @param desired
	 * 		The number of services to add to the pool.
	 * @return
	 * 		The number of services added to the pool.
	 */
	public int allocate(int desired)
	{
		synchronized (state) 
		{
			if (state.has(Stopped)) {
				return 0;
			}
			
			int serviceCount = StrictMath.min(maxCapacity - services.size(), desired);
			
			if (serviceCount > 0) 
			{
				listeners.proxy().onServiceCreateBatch(this, serviceCount);
				
				for (int i = 0; i < serviceCount; i++) {
					addService();	
				}
			}	
			
			return Math.max(0, serviceCount);
		}
	}

	/**
	 * Deallocates the requested number of services. If the number of services
	 * requested exceeds the number of minimum possible services the pool will
	 * be emptied to its minimum size.
	 * 
	 * @param desired
	 * 		The number of services to remove from the pool.
	 * @return
	 * 		The number of services removed from the pool.
	 */
	public int deallocate(int desired)
	{
		synchronized (state) 
		{
			if (state.has(Stopped)) {
				return 0;
			}
			
			int serviceCount = StrictMath.min(services.size() - minCapacity, desired);
			
			if (serviceCount > 0)
			{
				listeners.proxy().onServiceDestroyBatch(this, serviceCount);
				
				for (int i = 0; i < serviceCount; i++) {
					removeService(ServiceInterrupt.Immediate, false, Long.MAX_VALUE);	
				}
			}
			
			return Math.max(0, serviceCount);
		}
	}
	
	/**
	 * Adds a service to the pool and starts it (non-blocking).
	 */
	private void addService() 
	{
		Service<E> service = factory.newService(events);
		
		listeners.proxy().onServiceCreate(this, service);

		service.start(false);
		
		services.add(service);
		
		lastAllocateTime = System.currentTimeMillis();
	}

	/**
	 * Removes a service from the pool and stops it.
	 * 
	 * @param interrupt
	 * 		The interruption type for stopping the service.
	 * @param wait
	 * 		Whether this method should wait until the service has stopped.
	 * @param timeout
	 * 		The maximum amount of time to wait for the service to stop.
	 */
	private void removeService(ServiceInterrupt interrupt, boolean wait, long timeout) 
	{
		Service<E> service = services.remove(services.size() - 1);
		
		listeners.proxy().onServiceDestroy(this, service);
		
		service.stop(interrupt, wait, timeout);
	}

	/**
	 * Starts this pool by filling it with its initial set of services.
	 */
	public void start()
	{
		synchronized (state)
		{
			if (state.has(Stopped)) 
			{
				accepting = true;
				
				listeners.proxy().onServiceCreateBatch(this, minCapacity);
				
				for (int i = 0; i < minCapacity; i++) {
					addService();
				}	
				
				state.set(Running);
			}
		}
	}
	
	/**
	 * Stops this pool without waiting for all pending events to be performed
	 * but blocks until all services have stopped.
	 */
	public void stop() 
	{
		stop(ServiceInterrupt.Immediate, false, Long.MAX_VALUE);
	}
	
	/**
	 * Stops this pool without waiting for all pending events to be performed
	 * and optionally blocks until all services have stopped.
	 * 
	 * @param wait
	 * 		Whether this should wait for the services to stop.
	 */
	public void stop(boolean wait) 
	{
		stop(ServiceInterrupt.Immediate, wait, Long.MAX_VALUE);
	}
	
	/**
	 * Stops this pool without waiting for all pending events to be performed
	 * and optionally blocks a maximum amount of time for all services to stop. 
	 * 
	 * @param wait
	 * 		Whether this should wait for the services to stop.
	 * @param timeout
	 * 		The maximum amount of time to wait in milliseconds.
	 */
	public void stop(boolean wait, long timeout) 
	{
		stop(ServiceInterrupt.Immediate, wait, timeout);
	}
	
	/**
	 * Stops this pool and optionally waits for all events to be processed and
	 * optionally blocks a maximum amount of time for all services to stop.
	 * 
	 * @param interrupt
	 * 		The interruption type. This dictates whether the services must
	 * 		finish handling the events before they stop.
	 * @param wait
	 * 		Whether this should wait for the services to stop.
	 * @param timeout
	 * 		The maximum amount of time to wait in milliseconds.
	 */
	public void stop(ServiceInterrupt interrupt, boolean wait, long timeout)
	{
		synchronized (state) 
		{
			if (state.has(Running)) 
			{
				accepting = false;
				
				listeners.proxy().onServiceDestroyBatch(this, services.size());
				
				while (services.size() > 0) 
				{
					removeService(interrupt, wait, timeout);	
				}
				
				state.set(Stopped);
			}
		}
	}
	
	/**
	 * Returns whether this pool has more events that it can handle with its 
	 * current number of services.
	 * 
	 * @return
	 * 		True if this pool could use more services, otherwise false.
	 */
	public boolean isUnderflow()
	{
		return (events.size() > eventThreshold || services.size() < minCapacity);
	}
	
	/**
	 * Returns whether this pool has less events than it should for the number
	 * of services that exist in the pool. This also factors in the last time 
	 * services were allocated in order to avoid overactive deallocation.
	 * 
	 * @return
	 * 		True if this pool could do without servics, otherwise false.
	 */
	public boolean isOverflow()
	{
		long elapsed = System.currentTimeMillis() - lastAllocateTime;
		
		return (events.size() < eventThreshold && (elapsed > allocateThreshold || services.size() > maxCapacity));
	}
	
	/**
	 * Returns whether this pool is accepting events and has a set of services.
	 * 
	 * @return
	 * 		True if the pool is accepting, otherwise false.
	 */
	public boolean isRunning()
	{
		return state.has(Running);
	}
	
	/**
	 * Returns whether this pool has stopped accepting events and has no 
	 * services.
	 * 
	 * @return
	 * 		True if the pool is not accepting, otherwise false.
	 */
	public boolean isStopped()
	{
		return state.has(Stopped);
	}

	/**
	 * Returns the maximum number of services that can exist in the pool.  This 
	 * is not thread safe, therefore values returned may be out of date.
	 * @return
	 */
	public int getMaxCapacity() 
	{
		return maxCapacity;
	}

	/**
	 * Sets the maximum number of services that can exist in the pool. This is 
	 * not thread safe, therefore its affects are not guaranteed to happen
	 * immediately.
	 * 
	 * @param maxCapacity
	 * 		The maximum number of services.
	 */
	public void setMaxCapacity(int maxCapacity) 
	{
		this.maxCapacity = maxCapacity;
	}

	/**
	 * Returns the minimum number of services that can exist in the pool. This
	 * is not thread safe, therefore values returned may be out of date.
	 * @return
	 */
	public int getMinCapacity() 
	{
		return minCapacity;
	}

	/**
	 * Sets the minimum number of services that can exist in the pool. This is 
	 * not thread safe, therefore its affects are not guaranteed to happen
	 * immediately.
	 * 
	 * @param minCapacity
	 * 		The minimum number of services.
	 */
	public void setMinCapacity(int minCapacity) 
	{
		this.minCapacity = minCapacity;
	}

	/**
	 * Returns the number of services to allocate at once when they're required.
	 * This is not thread safe, therefore values returned may be out of date.
	 * @return
	 */
	public int getAllocateSize() 
	{
		return allocateSize;
	}

	/**
	 * Sets the number of services to allocate at once when they're required. 
	 * This is not thread safe, therefore its affects are not guaranteed to 
	 * happen immediately.
	 * 
	 * @param allocateSize
	 * 		The number of services to allocate at once.
	 */
	public void setAllocateSize(int allocateSize) 
	{
		this.allocateSize = allocateSize;
	}

	/**
	 * Returns the number of services to deallocate at once. This is not thread 
	 * safe, therefore values returned may be out of date.
	 * @return
	 */
	public int getDeallocateSize() 
	{
		return deallocateSize;
	}

	/**
	 * Sets the number of services to allocate at once when they're required. 
	 * This is not thread safe, therefore its affects are not guaranteed to 
	 * happen immediately.
	 * 
	 * @param deallocateSize
	 * 		The number of services to allocate at once.
	 */
	public void setDeallocateSize(int deallocateSize) 
	{
		this.deallocateSize = deallocateSize;
	}

	/**
	 * Returns the minimum amount of time to wait since the last allocation 
	 * before services can be deallocated. This is not thread safe, therefore 
	 * values returned may be out of date.
	 * @return
	 */
	public long getAllocateThreshold() 
	{
		return allocateThreshold;
	}

	/**
	 * Sets the minimum amount of time to wait since the last allocation before 
	 * services can be deallocated. This is not thread safe, therefore its 
	 * affects are not guaranteed to happen immediately.
	 * 
	 * @param allocateThreshold
	 * 		The minimum amount of time in milliseconds.
	 */
	public void setAllocateThreshold(long allocateThreshold) 
	{
		this.allocateThreshold = allocateThreshold;
	}

	/**
	 * Returns the minimum number of queued events that trigger the allocation 
	 * of additional services. This is not thread safe, therefore values 
	 * returned may be out of date.
	 * @return
	 */
	public int getEventThreshold() 
	{
		return eventThreshold;
	}

	/**
	 * Sets the minimum number of queued events that trigger the allocation of 
	 * additional services. This is not thread safe, therefore its affects are 
	 * not guaranteed to happen immediately.
	 * 
	 * @param eventThreshold
	 * 		The minimum number fo queued events the trigger service allocations.
	 */
	public void setEventThreshold(int eventThreshold) 
	{
		this.eventThreshold = eventThreshold;
	}

	/**
	 * Returns the maximum number of events before they must be rejected. This 
	 * is not thread safe, therefore values returned may be out of date.
	 * @return
	 */
	public int getMaxEvents() 
	{
		return maxEvents;
	}

	/**
	 * Sets the maximum number of events before they must be rejected. This is 
	 * not thread safe, therefore its affects are not guaranteed to happen 
	 * immediately.
	 * 
	 * @param maxEvents
	 * 		The maximum number of events before they must be rejected.
	 */
	public void setMaxEvents(int maxEvents) 
	{
		this.maxEvents = maxEvents;
	}

	/**
	 * Returns whether this pool is currently accepting events.
	 * 
	 * @return
	 * 		True if the pool is accepting events, otherwise false.
	 */
	public boolean isAccepting() 
	{
		return accepting;
	}

	/**
	 * Returns the factory used to create new services. This is not thread safe, 
	 * therefore values returned may be out of date.
	 * 
	 * @return
	 * 		The reference to the ServiceFactory.
	 */
	public ServiceFactory<E> getFactory() 
	{
		return factory;
	}

	/**
	 * Sets the factory used to create new services. This is not thread safe, 
	 * therefore its affects are not guaranteed to happen immediately.
	 * 
	 * @param factory
	 * 		The new service factory for the pool.
	 */
	public void setFactory(ServiceFactory<E> factory) 
	{
		this.factory = factory;
	}

	/**
	 * Returns the last time services were allocated for the pool.
	 * 
	 * @return
	 * 		The last allocation in milliseconds since the Unix Epoch.
	 */
	public long getLastAllocateTime() 
	{
		return lastAllocateTime;
	}
	
	/**
	 * Returns the approximate number of events currently waiting to be handled
	 * by one of the services in the pool. This is not thread safe, therefore 
	 * values returned may be out of date.
	 * 
	 * @return
	 * 		The number of events not handled yet.
	 */
	public int getWaitingEvents() 
	{
		return events.size();
	}
	
	/**
	 * Returns the number of services currently in this pool. This is not thread 
	 * safe, therefore values returned may be out of date.
	 * @return
	 */
	public int getServiceCount()
	{
		return services.size();
	}
	
	/**
	 * Returns the queue of events where the services in the pool poll from.
	 * This should not be directly manipulated, unexpected results will occur.
	 * A common use for accessing this queue is when the pool has stopped and
	 * there are remaining events that must be discarded or explicitly handled.
	 * 
	 * @return
	 * 		The reference to the queue of events.
	 */
	public BlockableQueue<E> getEventQueue()
	{
		return events;
	}

	/**
	 * Returns the notifier which manages the listeners to events in the pool. 
	 * ServicePoolListeners can be directly added and removed to the notifier.
	 * Avoid invoking the methods of the proxy object in the notifier since it
	 * may notify the listeners falsely when an event has not actually occurred 
	 * with the pool. 
	 * 
	 * @return
	 * 		The reference to the ServicePoolListener notifier.
	 */
	public Notifier<ServicePoolListener<E>> getListeners() 
	{
		return listeners;
	}
	
}