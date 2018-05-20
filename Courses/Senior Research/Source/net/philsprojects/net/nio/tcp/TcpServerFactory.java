package net.philsprojects.net.nio.tcp;

import java.net.SocketAddress;

import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Selector;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerFactory;

/**
 * A factory for creating NIO TCP servers.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TcpServerFactory implements ServerFactory 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Server newServer(Selector selector, Pipeline pipeline, Acceptor acceptor, SocketAddress address) 
	{
		return new TcpServer(selector, pipeline, acceptor, address);
	}

}
