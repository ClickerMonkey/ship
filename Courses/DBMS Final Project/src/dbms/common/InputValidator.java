package dbms.common;

/**
 * Parses a string for a given data type. 
 * 
 * @author Philip Diffenderfer
 *
 * @param <T> The data type to parse.
 */
public interface InputValidator<T> 
{

	/**
	 * Parses a string to the datatype T. If the data cannot be parsed from the
	 * string then null will be returned.
	 * 
	 * @param s => The string to parse.
	 * @return => The data parsed, or null if none could be parsed.
	 */
	public T parse(String s);
	
}
