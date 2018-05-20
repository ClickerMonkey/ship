package net.philsprojects.net;

import java.util.Set;

import net.philsprojects.resource.Resource;
import net.philsprojects.util.ConcurrentSet;

/**
 * An acceptor is a service which handles a set of servers. The acceptor will
 * process the accepting of clients by its servers. An acceptor can either 
 * handle a single server or an infinite number of servers depending on the
 * underlying I/O.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Acceptor extends Resource
{
	
	/**
	 * Returns the set of servers this Acceptor is handling. 
	 * 
	 * @return
	 * 		The reference to the set of servers handled by this Acceptor.
	 * @see ConcurrentSet
	 */
	public Set<Server> getServers();
	
}
