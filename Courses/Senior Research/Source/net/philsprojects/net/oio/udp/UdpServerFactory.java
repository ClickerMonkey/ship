package net.philsprojects.net.oio.udp;

import java.net.SocketAddress;

import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Selector;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerFactory;

public class UdpServerFactory implements ServerFactory 
{

	@Override
	public Server newServer(Selector selector,
			Pipeline pipeline, Acceptor acceptor, SocketAddress address) 
	{
		return new UdpServer(selector, pipeline, acceptor, address);
	}


}
