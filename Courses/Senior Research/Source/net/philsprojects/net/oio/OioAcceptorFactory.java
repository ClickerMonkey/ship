package net.philsprojects.net.oio;

import net.philsprojects.net.Acceptor;
import net.philsprojects.resource.ResourceFactory;

public class OioAcceptorFactory implements ResourceFactory<Acceptor> 
{

	@Override
	public Acceptor allocate() 
	{
		return new OioAcceptor();
	}

}
