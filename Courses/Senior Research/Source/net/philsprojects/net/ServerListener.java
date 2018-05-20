package net.philsprojects.net;

import java.net.SocketAddress;

/**
 * 
 * @author Philip Diffenderfer
 *
 */
public interface ServerListener 
{

	/**
	 * 
	 * @param server
	 */
	public void onServerStart(Server server);
	
	/**
	 * 
	 * @param server
	 * @param address
	 */
	public void onServerBind(Server server, SocketAddress address);
	
	/**
	 * 
	 * @param server
	 * @param client
	 */
	public void onServerAccept(Server server, Client client);
	
	/**
	 * 
	 * @param server
	 * @param e
	 */
	public void onServerError(Server server, Exception e);
	
	/**
	 * 
	 * @param server
	 */
	public void onServerStop(Server server);
	
}