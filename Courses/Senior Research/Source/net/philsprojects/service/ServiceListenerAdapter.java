package net.philsprojects.service;

/**
 * An adapter which implements all ServiceListener methods. This can be used
 * to only listen to specific service events. i.e.
 * 
 * <h1>Example Usage</h1>
 * <pre>
 * service.addListener(new ServiceListenerAdapter() {
 * 	public void onServiceEvent(Service service, String event) {
 * 		
 * 	}
 * });
 * </pre>
 * 
 * @author Philip Diffenderfer
 *
 * @param <E> The event added to the service. Events added to the service
 * 		are handled in the service's thread by invoking the onServiceEvent 
 * 		method in all attached listeners.
 */
public class ServiceListenerAdapter<E> implements ServiceListener<E>
{
	
	/**
	 * {@inheritDoc}
	 */
	public void onServiceEvent(Service<E> service, E event)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	public void onServiceExecute(Service<E> service)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	public void onServiceStart(Service<E> service)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	public void onServicePause(Service<E> service, ServiceInterrupt interrupt)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	public void onServiceResume(Service<E> service, ServiceInterrupt interrupt)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	public void onServiceStop(Service<E> service, ServiceInterrupt interrupt)
	{
	}
	
}