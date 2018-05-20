package net.philsprojects.io.bytes;

import java.io.IOException;
import java.io.InputStream;

/**
 * A wrapper to disguise a ByteReader as an InputStream.
 *  
 * @author Philip Diffenderfer
 *
 */
public class ByteReaderStream extends InputStream 
{
	
	// The internal ByteReader.
	private final ByteReader reader;
	
	/**
	 * Instantiates a new ByteReaderStream.
	 * 
	 * @param reader
	 * 		The internal ByteReader.
	 */
	public ByteReaderStream(ByteReader reader) 
	{
		this.reader = reader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read() throws IOException 
	{
		if (reader.hasByte()) {
			return reader.getUbyte(); 
		}
		return -1;
	}

}
