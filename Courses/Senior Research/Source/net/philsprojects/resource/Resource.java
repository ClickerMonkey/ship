package net.philsprojects.resource;

/**
 * A resource is typically expensive to allocate therefore its reused and pooled
 * as much as possible. 
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Resource 
{
	
	/**
	 * Whether this resource is reusable. A reusable resource can be returned
	 * by the pool even though it may be currently used. A reusable resource
	 * should be thread safe and should keep track of its holders in order to 
	 * know whether its in use.
	 * 
	 * @return
	 * 		True if the resource can be used by many holders.
	 */
	public boolean isReusable();
	
	/**
	 * Whether this resource is unused. An unused resource has no holders and
	 * is ready to be deallocated upon request.
	 * 
	 * @return
	 * 		True if the resource can be deallocated.
	 */
	public boolean isUnused();
	
	/**
	 * Permanently releases the resource from use.
	 */
	public void free();
	
}
