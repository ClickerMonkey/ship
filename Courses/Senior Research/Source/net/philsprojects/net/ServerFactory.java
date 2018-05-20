package net.philsprojects.net;

import java.net.SocketAddress;

/**
 * A factory which creates a server handled by the given accepter, which uses
 * the given selector and pipeline, and has binded to the given address.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface ServerFactory 
{
	
	/**
	 * Creates a new Server.
	 * 
	 * @param selector
	 * 		The selector which can create workers for connecting clients.
	 * @param pipeline
	 * 		The pipeline the server and its clients should use.
	 * @param address
	 * 		The address the server is bounded to.
	 * @return
	 * 		A newly instantiated server with the given parameters.
	 */
	public Server newServer(Selector selector, Pipeline pipeline, Acceptor acceptor, SocketAddress address);
	
}