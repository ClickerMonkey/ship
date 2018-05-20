package net.philsprojects.io.bytes;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import net.philsprojects.io.bytes.ByteReader.ReadCallback;
import net.philsprojects.io.bytes.ByteWriter.WriteCallback;

/**
 * A callback for serializing and unserializing between bytes and Objects
 * 
 * @author Philip Diffenderfer
 *
 */
public class SerializerCallback implements ReadCallback<Object>, WriteCallback<Object>
{

	/**
	 * Unserializes an Object from the given ByteReader.
	 * 
	 * @param reader
	 * 		The ByteReader to read from.
	 */
	public Object read(ByteReader reader)
	{
		Object output = null;
		try {
			InputStream is = new ByteReaderStream(reader);
			ObjectInputStream ois = new ObjectInputStream(is);
			output = ois.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * Serializes the given Object to the given ByteWriter.
	 * 
	 * @param writer
	 * 		The ByteWriter to write to.
	 * @param input
	 * 		The object to write to the ByteWriter.
	 */
	public void write(ByteWriter writer, Object input)
	{
		try {
			OutputStream os = new ByteWriterStream(writer);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(input);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
