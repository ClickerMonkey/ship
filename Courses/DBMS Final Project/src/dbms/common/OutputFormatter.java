package dbms.common;

/**
 * Converts an object of a given Type to its String representation.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T> The type of object to convert to a string.
 */
public interface OutputFormatter<T> 
{

	/**
	 * Converts the given item to a string. If the given item could not be
	 * converted to a string then the implementation should return null.
	 * 
	 * @param item => The item to convert to a string.
	 * @return The string representation of item.
	 */
	public String toString(T item);
	
}
