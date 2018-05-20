package net.philsprojects.net.oio.tcp;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientFactory;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.Worker;

public class TcpClientFactory implements ClientFactory 
{

	@Override
	public Client newClient(Pipeline pipeline, Worker worker, Server server) 
	{
		return new TcpClient(pipeline, worker, server);
	}

}
