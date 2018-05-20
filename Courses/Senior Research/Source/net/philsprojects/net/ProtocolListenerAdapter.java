package net.philsprojects.net;

import net.philsprojects.io.BufferStream;

/**
 * A basic ProtocolListener which prints all events to stdout.
 * 
 * @author Philip Diffenderfer
 *
 * @param <D>
 * 		The data encoded and decoded.
 */
public class ProtocolListenerAdapter<D> implements ProtocolListener<D> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDecodeError(Protocol<D> protocol, BufferStream in, Client client, Exception e) 
	{
		System.err.println("Decode Error: ");
		e.printStackTrace();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEncodeError(Protocol<D> protocol, BufferStream out, D data, Client client, Exception e) 
	{
		System.err.println("Encode Error: ");
		e.printStackTrace();
	}

}
