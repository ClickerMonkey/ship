package net.philsprojects.stat;

import net.philsprojects.service.AbstractService;

/**
 * The service responsible for handling statistic events. A statistic event
 * contains a list of databases and the statistic to add to the databases. This
 * functionality is in its own service to ensure the application can continue
 * execution without blocking due to I/O operations. Essentially this service
 * exists to perform all I/O operations involved statistic databases.
 * 
 * @author Philip Diffenderfer
 *
 */
public final class StatService extends AbstractService<StatEvent> 
{

	// The single instance of the service. As soon as the class is loaded the
	// single instance is created and the service is started.
	private static final StatService instance = new StatService();
	
	
	/**
	 * Returns the single instance of this service.
	 * 
	 * @return
	 * 		The reference to the StatService instance.
	 */
	public static StatService get() 
	{
		return instance;
	}
	
	
	/**
	 * Instantiates a new StatService.
	 */
	private StatService() 
	{
		// Using a blocking queue
		super(true);
		
		// Start but do not wait for it to finish.
		start(false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onEvent(StatEvent event) 
	{
		event.execute();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onExecute() 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPause() 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume() 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStart() 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStop() 
	{
	}
	
}
