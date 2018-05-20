package net.philsprojects.net;

import java.util.Set;

import net.philsprojects.resource.Resource;
import net.philsprojects.util.ConcurrentSet;

/**
 * A worker is a service which handles a set of clients. The worker will process
 * the connecting, reading, and writing of its clients. A worker can either 
 * handle a single client or an infinite number of clients depending on the
 * underlying I/O.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Worker extends Resource
{
	
	/**
	 * Returns the set of clients this Worker is handling.
	 * 
	 * @return
	 * 		The reference to the set of clients handled by this Worker.
	 * @see ConcurrentSet
	 */
	public Set<Client> getClients();
	
}
