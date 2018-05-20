package net.philsprojects.data.error;

import java.io.IOException;

/**
 * An IOException has occurred, this is a RuntimeException wrapping IO errors.
 * 
 * @author Philip Diffenderfer
 *
 */
public class StoreIOException extends RuntimeException 
{

	// The IOException that was thrown.
	private final IOException source;
	
	
	/**
	 * Instantiates a new StoreIOException.
	 * 
	 * @param e
	 * 		The IOException that was thrown.
	 */
	public StoreIOException(IOException e) 
	{ 
		super(e.getMessage());
		this.source = e;
	}
	
	
	/**
	 * The IOException that was thrown.
	 * 
	 * @return
	 * 		The reference to the exception.
	 */
	public IOException getSource() 
	{
		return source;
	}
	
}
