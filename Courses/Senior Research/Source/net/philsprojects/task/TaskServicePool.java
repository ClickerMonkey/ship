package net.philsprojects.task;

import net.philsprojects.service.ServicePool;

/**
 * A pool of TaskServices.
 * 
 * @author Philip Diffenderfer
 *
 */
@Deprecated
public class TaskServicePool extends ServicePool<Task<?>> implements TaskEventHandler
{

	/**
	 * Instantiates a new TaskServicePool.
	 */
	public TaskServicePool() 
	{
		super(new TaskServiceFactory());
	}

}
