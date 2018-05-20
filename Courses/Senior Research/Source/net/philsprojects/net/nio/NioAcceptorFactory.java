package net.philsprojects.net.nio;

import java.io.IOException;

import net.philsprojects.net.Acceptor;
import net.philsprojects.resource.ResourceFactory;

public class NioAcceptorFactory implements ResourceFactory<Acceptor> 
{

	@Override
	public Acceptor allocate() 
	{
		Acceptor acceptor = null;
		try {
			acceptor = new NioWorker();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO
		}
		return acceptor;
	}

}
