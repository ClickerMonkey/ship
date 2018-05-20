package net.philsprojects.io.buffer;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import net.philsprojects.BaseTest;

public class TestBufferFactoryDirect extends BaseTest 
{

	@Test
	public void testFill()
	{
		BufferFactory bf = new BufferFactoryDirect();
		
		assertEquals( 0, bf.fill() );
	}
	
	@Test
	public void testClear()
	{
		BufferFactory bf = new BufferFactoryDirect();
		
		bf.free(ByteBuffer.allocateDirect(16));
		
		assertEquals( 0, bf.clear() );
	}
	
	@Test
	public void testFree()
	{
		BufferFactory bf = new BufferFactoryDirect();
		
		assertFalse( bf.free(ByteBuffer.allocateDirect(16)) );
	}
	
	@Test
	public void testAllocate()
	{
		BufferFactory bf = new BufferFactoryDirect();
		
		ByteBuffer b = bf.allocate(16);
		
		assertTrue( b.isDirect() );
	}
	
}
