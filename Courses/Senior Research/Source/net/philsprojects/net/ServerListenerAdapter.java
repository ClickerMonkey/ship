package net.philsprojects.net;

import java.net.SocketAddress;

/**
 * A basic ServerListener which prints all events to stdout.
 * 
 * @author Philip Diffenderfer
 *
 */
public class ServerListenerAdapter implements ServerListener 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerAccept(Server server, Client client) 
	{
		System.out.format("Server %s accepted client %s\n", server.getAddress(), client.getAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerError(Server server, Exception e) 
	{
		System.err.print("Server Error: ");
		e.printStackTrace();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerBind(Server server, SocketAddress address) 
	{
		System.out.format("Server bound to %s\n", address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerStart(Server server) 
	{
		System.out.format("Server started on %s\n", server.getAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerStop(Server server) 
	{
		System.out.format("Server stopped on %s\n", server.getAddress());
	}

}
