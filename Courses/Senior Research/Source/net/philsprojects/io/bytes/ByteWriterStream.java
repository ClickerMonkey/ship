package net.philsprojects.io.bytes;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A wrapper to disguise a ByteWriter as an OutputStream.
 *  
 * @author Philip Diffenderfer
 *
 */
public class ByteWriterStream extends OutputStream 
{

	// The internal ByteWriter.
	private final ByteWriter writer;
	
	/**
	 * Instantiates a new ByteWriterStream.
	 * 
	 * @param writer
	 * 		The internal ByteWriter.
	 */
	public ByteWriterStream(ByteWriter writer) 
	{
		this.writer = writer;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(int b) throws IOException 
	{
		writer.putByte((byte)b);
	}

}
