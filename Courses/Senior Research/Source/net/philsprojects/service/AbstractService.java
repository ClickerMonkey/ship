package net.philsprojects.service;

import java.util.Queue;

import net.philsprojects.util.BlockableQueue;

/**
 * An abstract implementation of a Service which also listens to its own events.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The event added to the service. Events added to the service
 * 		are handled in the service's thread by invoking the onServiceEvent
 * 		method in all attached listeners.
 */
public abstract class AbstractService<E> extends Service<E> implements ServiceListener<E> 
{

	/**
	 * Instantiates a new AbstractService.
	 */
	public AbstractService() 
	{
		getListeners().add(this);
	}
	
	/**
	 * Instantiates a new AbstractService.
	 * 
	 * @param blocking
	 * 		Whether the event queue for this service blocks when it polls for
	 * 		events or whether it returns immediately when empty.
	 */
	public AbstractService(boolean blocking) 
	{ 
		getListeners().add(this);
		getEventQueue().setBlocking(blocking);
	}
	
	/**
	 * Instantiates a new AbstractService.
	 * 
	 * @param sourceQueue
	 * 		The queue implementation to use internally as an event queue.
	 */
	public AbstractService(Queue<E> sourceQueue) 
	{
		super(new BlockableQueue<E>(sourceQueue));
		getListeners().add(this);
	}
	
	/**
	 * Instantiates a new AbstractService.
	 * 
	 * @param eventQueue
	 * 		The queue of events to poll from.
	 */
	public AbstractService(BlockableQueue<E> eventQueue) 
	{
		super(eventQueue);
		getListeners().add(this);
	}
	
	/**
	 * Invoked when the service handles an event.
	 * 
	 * @param event
	 * 		The event handled by the service.
	 */
	protected abstract void onEvent(E event);
	
	/**
	 * Invoked when the service performs an execution.
	 */
	protected abstract void onExecute();
	
	/**
	 * Invoked when the service has paused.
	 */
	protected abstract void onPause();
	
	/**
	 * Invoked when the service is resumed.
	 */
	protected abstract void onResume();
	
	/**
	 * Invoked when the service has started.
	 */
	protected abstract void onStart();
	
	/**
	 * Invoked when the service has stopped.
	 */
	protected abstract void onStop();
	
	
	/**
	 * {@inheritDoc}
	 */
	public final void onServiceEvent(Service<E> service, E event) 
	{
		this.onEvent(event);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public final void onServiceExecute(Service<E> service) 
	{
		this.onExecute();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onServicePause(Service<E> service, ServiceInterrupt interrupt) 
	{
		this.onPause();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onServiceResume(Service<E> service, ServiceInterrupt interrupt) 
	{
		this.onResume();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onServiceStart(Service<E> service) 
	{
		this.onStart();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onServiceStop(Service<E> service, ServiceInterrupt interrupt) 
	{
		this.onStop();
	}

}
