package net.philsprojects.service;

import net.philsprojects.util.BlockableQueue;

/**
 * A factory for creating services. This is used by the ServicePool to 
 * instantiate new services to do its bidding.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The event type.
 */
public interface ServiceFactory<E> 
{
	
	/**
	 * Creates a new Service with a default constructor.
	 * 
	 * @return
	 * 		The reference to a newly instantiated service.
	 */
	public Service<E> newService();
	
	/**
	 * Creates a new Service which uses the given queue to take events from.
	 * 
	 * @param queue
	 * 		The queue which the service should take events from.
	 * @return
	 * 		The reference to a newly instantiated service.
	 */
	public Service<E> newService(BlockableQueue<E> queue);
	
}
