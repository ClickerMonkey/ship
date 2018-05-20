package dbms.formatter;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.sql.CLOB;

import dbms.common.OutputFormatter;

/**
 * A formatter for the CLOB data type.
 * 
 * @author Philip Diffenderfer
 *
 */
public class ClobFormatter implements OutputFormatter<CLOB>
{

	// The maximum length of the string to return (appox) from the CLOB.
	private static final int CLOB_MAX = 50;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(CLOB item) 
	{
		// Get the stream of the CLOB, on error return null.
		InputStream stream = null;
		try {
			stream = item.getAsciiStream();
		} 
		catch (SQLException e) {
			return null;
		}
		// Parse the clob one word at a time (good/bad idea)
		Scanner input = new Scanner(stream);
		StringBuilder builder = new StringBuilder(CLOB_MAX);
		
		// While words exist in the clob and the max threshold is untouched...
		while (input.hasNext() && builder.length() < CLOB_MAX) {
			builder.append(input.next());
			builder.append(" ");
		}
		
		// If the clob still has data then append '...'
		if (input.hasNext()) {
			builder.append("...");	
		}

		return builder.toString();
	}

}
