package net.philsprojects.task;

import java.util.Queue;

import net.philsprojects.service.AbstractService;
import net.philsprojects.util.BlockableQueue;

/**
 * A service which can process any type of task.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TaskService extends AbstractService<Task<?>> implements TaskEventHandler
{
	
	/**
	 * Instantiates a new TaskService.
	 */
	public TaskService() 
	{
		super();
	}

	/**
	 * Instantiates a new TaskService.
	 * 
	 * @param eventQueue
	 * 		The queue of events to poll from.
	 */
	public TaskService(BlockableQueue<Task<?>> eventQueue) 
	{
		super(eventQueue);
	}
	
	/**
	 * Instantiates a new TaskService.
	 * 
	 * @param blocking
	 * 		Whether the event queue for this service blocks when it polls for
	 * 		events or whether it returns immediately when empty.
	 */
	public TaskService(boolean blocking) 
	{
		super(blocking);
	}

	/**
	 * Instantiates a new TaskService.
	 * 
	 * @param sourceQueue
	 * 		The queue implementation to use internally as an event queue.
	 */
	public TaskService(Queue<Task<?>> sourceQueue) 
	{
		super(sourceQueue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onEvent(Task<?> event) {
		event.run();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onExecute() {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPause() 
	{
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume() 
	{
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStart() 
	{
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStop() 
	{
		
	}

}
