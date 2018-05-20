package net.philsprojects.data.var;

import org.junit.Test;

public class TestByteVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new ByteVar(), (byte)0, (byte)5);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new ByteVar(), new ByteVar(), (byte)45);
	}
	
}
