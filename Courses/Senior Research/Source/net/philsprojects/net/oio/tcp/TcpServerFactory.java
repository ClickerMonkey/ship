package net.philsprojects.net.oio.tcp;

import java.net.SocketAddress;

import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Selector;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerFactory;

public class TcpServerFactory implements ServerFactory 
{

	@Override
	public Server newServer(Selector selector,
			Pipeline pipeline, Acceptor acceptor, SocketAddress address) 
	{
		return new TcpServer(selector, pipeline, acceptor, address);
	}


}
