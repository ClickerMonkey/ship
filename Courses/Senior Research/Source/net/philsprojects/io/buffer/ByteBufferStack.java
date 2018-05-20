package net.philsprojects.io.buffer;

import java.nio.ByteBuffer;

import net.philsprojects.util.AtomicStack;

/**
 * A lock-free thread-safe stack of ByteBuffers.
 * 
 * @author Philip Diffenderfer
 *
 */
public class ByteBufferStack extends AtomicStack<ByteBuffer> 
{
	// This may seem silly, but it makes code more readable.
}
