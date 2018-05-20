package net.philsprojects.net;

import net.philsprojects.resource.ResourceFactory;
import net.philsprojects.resource.ResourcePool;

/**
 * An abstract implementation of a selector.
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class AbstractSelector implements Selector 
{

	// The pool of acceptors.
	protected final ResourcePool<Acceptor> acceptorPool;
	
	// The pool of workers.
	protected final ResourcePool<Worker> workerPool;
	
	
	/**
	 * Instantiates an AbstractSelector.
	 * 
	 * @param acceptors
	 * 		The pool of acceptors.
	 * @param workers
	 * 		The pool of workers.
	 */
	public AbstractSelector(ResourceFactory<Acceptor> acceptors, ResourceFactory<Worker> workers) 
	{ 
		this.acceptorPool = new ResourcePool<Acceptor>(acceptors);
		this.workerPool = new ResourcePool<Worker>(workers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourcePool<Acceptor> getAcceptors() 
	{
		return acceptorPool;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourcePool<Worker> getWorkers() 
	{
		return workerPool;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Worker getWorker() 
	{
		return workerPool.allocate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Acceptor getAcceptor() 
	{
		return acceptorPool.allocate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() 
	{
		acceptorPool.populate();
		workerPool.populate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() 
	{
		acceptorPool.empty();
		workerPool.empty();
	}

}
