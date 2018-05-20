package net.philsprojects.net.nio.udp;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientFactory;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.Worker;

public class UdpClientFactory implements ClientFactory 
{

	@Override
	public Client newClient(Pipeline pipeline, Worker worker, Server server) 
	{
		return new UdpClient(pipeline, worker, server);
	}

}
