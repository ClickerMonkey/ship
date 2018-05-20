package net.philsprojects.net.oio;

import net.philsprojects.net.AbstractSelector;


public class OioSelector extends AbstractSelector
{

	public OioSelector() 
	{
		super(new OioAcceptorFactory(), new OioWorkerFactory());
		
		acceptorPool.setMinCapacity(0);
		acceptorPool.setMaxCapacity(10);
		acceptorPool.setAllocateSize(1);
		acceptorPool.setDeallocateSize(1);
		
		workerPool.setMinCapacity(1);
		workerPool.setMaxCapacity(100);
		workerPool.setAllocateSize(4);
		workerPool.setDeallocateSize(1);
	}
	
}
