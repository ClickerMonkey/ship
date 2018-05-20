package net.philsprojects.io.buffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A BufferFactory which does not cache and uses DirectByteBuffers.
 * 
 * @author Philip Diffenderfer
 *
 */
public class BufferFactoryDirect extends AbstractBufferFactory 
{

	private static List<ByteBuffer> EMPTY = new ArrayList<ByteBuffer>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ByteBuffer onAllocate(int size, AtomicBoolean cache) 
	{
		try {
			return ByteBuffer.allocateDirect(size);
		}
		catch (OutOfMemoryError e) {
			System.err.format("Cannot allocate a ByteBuffer of size %d; out of memory.\n", size);
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean onCache(ByteBuffer buffer) 
	{
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected long onFill() 
	{
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<ByteBuffer> onRelease() 
	{
		return EMPTY;
	}

}
