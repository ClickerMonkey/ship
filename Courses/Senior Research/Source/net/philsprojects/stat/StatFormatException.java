package net.philsprojects.stat;

/**
 * A problem occurred parsing an existing Store with a given StatFormat.
 * 
 * @author Philip Diffenderfer
 *
 */
public class StatFormatException extends RuntimeException 
{
	
	/**
	 * The format which thrown the exception.
	 */
	private StatFormat format;
	
	
	/**
	 * Instantiates a new StatFormatException.
	 * 
	 * @param format
	 * 		The format thats throwing the exception.
	 * @param message
	 * 		The message describing the reason for throwing the exception.
	 */
	public StatFormatException(StatFormat format, String message) 
	{
		super(message);
		this.format = format;
	}
	
	/**
	 * Returns the format that threw this exception.
	 */
	public StatFormat getFormat() 
	{
		return format;
	}
	
}
