package net.philsprojects.io;

/**
 * A listener to the flush events of a BufferStream.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface BufferStreamListener 
{
	
	/**
	 * Flush has been invoked on the given BufferStream.
	 * 
	 * @param stream
	 * 		The stream to which flush was invoked.
	 */
	public void onBufferFlush(BufferStream stream);
	
}
