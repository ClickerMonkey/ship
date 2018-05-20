package net.philsprojects.io.buffer;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import net.philsprojects.BaseTest;

public class TestBufferFactoryHeap extends BaseTest 
{

	@Test
	public void testFill()
	{
		BufferFactory bf = new BufferFactoryHeap();
		
		assertEquals( 0, bf.fill() );
	}
	
	@Test
	public void testClear()
	{
		BufferFactory bf = new BufferFactoryHeap();
		
		bf.free(ByteBuffer.allocate(16));
		
		assertEquals( 0, bf.clear() );
	}
	
	@Test
	public void testFree()
	{
		BufferFactory bf = new BufferFactoryHeap();
		
		assertFalse( bf.free(ByteBuffer.allocate(16)) );
	}
	
	@Test
	public void testAllocate()
	{
		BufferFactory bf = new BufferFactoryHeap();
		
		ByteBuffer b = bf.allocate(16);
		
		assertFalse( b.isDirect() );
	}
	
}
