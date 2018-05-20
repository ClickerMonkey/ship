package net.philsprojects.net.nio;

import java.io.IOException;

import net.philsprojects.net.Worker;
import net.philsprojects.resource.ResourceFactory;

public class NioWorkerFactory implements ResourceFactory<Worker> 
{

	@Override
	public Worker allocate() 
	{
		Worker worker = null;
		try {
			worker = new NioWorker();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO
		}
		return worker;
	}

}
