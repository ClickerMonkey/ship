package net.philsprojects.resource;

/**
 * A listener of resource allocation and deallocation in a resource pool.
 * 
 * @author Philip Diffenderfer
 *
 * @param <R>
 * 		The resource type.
 */
public interface ResourceListener<R extends Resource>
{
	
	/**
	 * Invoked when the pool has allocated a resource because a new resource is
	 * required. A new resource is required when the pool is queried via the 
	 * allocate or populate methods.
	 * 
	 * @param pool
	 * 		The pool which the resource was allocated to.
	 * @param resource
	 * 		The resource that has been allocated to the pool.
	 */
	public void onResourceAllocate(ResourcePool<R> pool, R resource);
	
	/**
	 * Invoked when the pool has deallocated a resource because the pool
	 * capacity exceeded the maximum or the resource wasn't needed anymore.
	 * 
	 * @param pool
	 * 		The pool which the resource was deallocated from.
	 * @param resource
	 * 		The resource that has been deallocated from the pool.
	 */
	public void onResourceDeallocate(ResourcePool<R> pool, R resource);
	
}
