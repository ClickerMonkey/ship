package net.philsprojects.net;


import net.philsprojects.io.bytes.ByteReader;
import net.philsprojects.io.bytes.ByteWriter;

/**
 * A simple protocol that uses Strings as the medium for communication.
 * 
 * @author Philip Diffenderfer
 *
 */
public class ByteProtocol extends AbstractProtocol<byte[]> 
{
	/**
	 * Decodes a string from the given input.
	 */
	@Override
	public byte[] decode(ByteReader in, Client client) 
	{
		String magic = in.getString(4);
		
		if (in.isValid() && !magic.equals("Wo0t")) {
			client.disconnect();
			return null;
		}
		
		byte[] bytes = in.getByteArray();

		if (in.isValid()) {
			in.sync();
		}
		
		return bytes;
	}

	/**
	 * Encodes a string to the given output.
	 */
	@Override
	public void encode(ByteWriter out, byte[] data, Client client) 
	{
		out.putBytes("Wo0t".getBytes());
		out.putByteArray(data);
	}
}