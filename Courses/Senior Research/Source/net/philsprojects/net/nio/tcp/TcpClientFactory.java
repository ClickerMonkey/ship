package net.philsprojects.net.nio.tcp;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientFactory;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.Worker;

/**
 * A factory for creating NIO TCP clients.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TcpClientFactory implements ClientFactory 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client newClient(Pipeline pipeline, Worker worker, Server server) 
	{
		return new TcpClient(pipeline, worker, server);
	}

}
