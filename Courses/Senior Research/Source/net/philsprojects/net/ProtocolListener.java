package net.philsprojects.net;

import net.philsprojects.io.BufferStream;

/**
 * A listener of encoding and decoding errors of a protocol. 
 * 
 * @author Philip Diffenderfer
 *
 * @param <D>
 * 		The data encoded and decoded.
 */
public interface ProtocolListener<D> 
{
	
	/**
	 * Invoked when an error has occurred decoding an input stream from the
	 * given client.
	 * 
	 * @param protocol
	 * 		The protocol which threw an exception while decoding.
	 * @param in
	 * 		The input stream that may have caused the error.
	 * @param client
	 * 		The client that was being decoded and may have caused the error.
	 * @param e
	 * 		The error that occurred decoding an input stream.
	 */
	public void onDecodeError(Protocol<D> protocol, BufferStream in, Client client, Exception e);
	
	/**
	 * Invoked when an error has occurred encoding data to an output stream from
	 * the given client.
	 * 
	 * @param protocol
	 * 		The protocol which threw an exception while decoding.
	 * @param out
	 * 		The output stream that may have caused the error.
	 * @param data
	 * 		The data to encode that may have caused the error.
	 * @param client
	 * 		The client that was being encoded and may have caused the error.
	 * @param e
	 * 		The error that occurred encoding an object to an output stream.
	 */
	public void onEncodeError(Protocol<D> protocol, BufferStream out, D data, Client client, Exception e);
	
}