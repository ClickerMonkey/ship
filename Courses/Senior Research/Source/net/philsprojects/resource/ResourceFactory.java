package net.philsprojects.resource;

/**
 * A factory for allocating new resources.
 * 
 * @author Philip Diffenderfer
 *
 * @param <R>
 * 		The resource type.
 */
public interface ResourceFactory<R extends Resource> 
{
	
	/**
	 * Returns a newly allocated resource. This resource should return reusable
	 * and unused.
	 * 
	 * @return
	 * 		The reference to a resource.
	 */
	public R allocate();
	
}
