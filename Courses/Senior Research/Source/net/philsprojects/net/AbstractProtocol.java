package net.philsprojects.net;

/**
 * An abstract implementation of a Protocol that handles basic getters and 
 * setters.
 * 
 * @author Philip Diffenderfer
 *
 * @param <D>
 * 		The type of data thats being encoded and decoded.
 */
public abstract class AbstractProtocol<D> implements Protocol<D> 
{
	
	// The listener to the events of the protocol.
	protected ProtocolListener<D> listener;

	/**
	 * Instantiates an AbstractProtocol with a default listener.
	 */
	public AbstractProtocol()
	{
		listener = new ProtocolListenerAdapter<D>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ProtocolListener<D> getListener() 
	{
		return listener;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setListener(ProtocolListener<D> listener) 
	{
		this.listener = listener;
	}
	
}
