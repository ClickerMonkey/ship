package net.philsprojects.service;

/**
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 */
@Deprecated
public class ServicePoolListenerAdapter<E> implements ServicePoolListener<E> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServiceCreate(ServicePool<E> pool, Service<E> service) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServiceCreateBatch(ServicePool<E> pool, int serviceCount) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServiceDestroy(ServicePool<E> pool, Service<E> service) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServiceDestroyBatch(ServicePool<E> pool, int serviceCount) 
	{
	}

}
