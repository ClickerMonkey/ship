package net.philsprojects.task;

import net.philsprojects.service.Service;
import net.philsprojects.service.ServiceFactory;
import net.philsprojects.util.BlockableQueue;

/**
 * A factory that creates TaskServers.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TaskServiceFactory implements ServiceFactory<Task<?>> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Service<Task<?>> newService() 
	{
		return new TaskService();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Service<Task<?>> newService(BlockableQueue<Task<?>> queue) 
	{
		return new TaskService(queue);
	}

}
