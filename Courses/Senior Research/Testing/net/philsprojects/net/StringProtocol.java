package net.philsprojects.net;


import net.philsprojects.io.bytes.ByteReader;
import net.philsprojects.io.bytes.ByteWriter;

/**
 * A simple protocol that uses Strings as the medium for communication.
 * 
 * @author Philip Diffenderfer
 *
 */
public class StringProtocol extends AbstractProtocol<String> 
{
	/**
	 * Decodes a string from the given input.
	 */
	@Override
	public String decode(ByteReader in, Client client) 
	{
		// Attempt to read in a string. The length of the string is read in
		// with this method.
		String msg = in.getString();
		// If there was enough data to read the string in...
		if (in.isValid()) {
			// Discard the read bytes.
			in.sync();
		}
		// Return the read string. This will be null if it was invalid.
		return msg;
	}

	/**
	 * Encodes a string to the given output.
	 */
	@Override
	public void encode(ByteWriter out, String data, Client client) 
	{
		// Writes the string and its length
		out.putString(data);
	}
}