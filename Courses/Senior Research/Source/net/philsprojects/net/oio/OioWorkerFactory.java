package net.philsprojects.net.oio;

import net.philsprojects.net.Worker;
import net.philsprojects.resource.ResourceFactory;

public class OioWorkerFactory implements ResourceFactory<Worker> 
{

	@Override
	public Worker allocate() 
	{
		return new OioWorker();
	}

}
