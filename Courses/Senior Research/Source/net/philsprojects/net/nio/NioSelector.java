package net.philsprojects.net.nio;

import net.philsprojects.net.AbstractSelector;

public class NioSelector extends AbstractSelector
{

	public NioSelector() 
	{
		super(new NioAcceptorFactory(), new NioWorkerFactory());
		
		acceptorPool.setMinCapacity(0);
		acceptorPool.setMaxCapacity(1);
		
		workerPool.setCapacity(1);
	}
	
}