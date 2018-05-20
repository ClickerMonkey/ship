package net.philsprojects.service;

/**
 * A listener to the events of a ServicePool. Events for a ServicePool consist
 * of notifications when Services are created or stopped and deallocated.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The event type.
 */
@Deprecated
public interface ServicePoolListener<E> 
{
	
	/**
	 * Occurs when a ServicePool has been told or has decided to create a
	 * Service. Any listener to this event should not perform any intensive
	 * or blocking operations, the Thread which has caused the service to be
	 * created is the one notifying listeners.
	 * 
	 * @param pool
	 * 		The pool which created the service.
	 * @param service
	 * 		The service which has started or is in the process.
	 */
	public void onServiceCreate(ServicePool<E> pool, Service<E> service);

	/**
	 * Occurs when a ServicePool has been told or has decided to create a set
	 * of Services. Any listener to this event should not perform any intensive
	 * or blocking operations, the Thread which has caused the services to be
	 * created is the one notifying listeners.
	 * 
	 * @param pool
	 * 		The pool which is creating services.
	 * @param serviceCount
	 * 		The number of services that are going to be created.
	 */
	public void onServiceCreateBatch(ServicePool<E> pool, int serviceCount);
	
	/**
	 * Occurs when a ServicePool has been told or has decided to destroy a
	 * Service. Any listener to this event should not perform any intensive
	 * or blocking operations, the Thread which has caused the service to be
	 * destroyed is the one notifying listeners.
	 * 
	 * @param pool
	 * 		The pool which destroyed the service.
	 * @param service
	 * 		The service which has stopped or is in the process.
	 */
	public void onServiceDestroy(ServicePool<E> pool, Service<E> service);
	
	/**
	 * Occurs when a ServicePool has been told or has decided to release a set
	 * of Services. Any listener to this event should not perform any intensive
	 * or blocking operations, the Thread which has caused the services to be
	 * destroyed is the one notifying listeners.
	 * 
	 * @param pool
	 * 		The pool which is destroying services.
	 * @param serviceCount
	 * 		The number of services that are going to be destroyed.
	 */
	public void onServiceDestroyBatch(ServicePool<E> pool, int serviceCount);
	
}
