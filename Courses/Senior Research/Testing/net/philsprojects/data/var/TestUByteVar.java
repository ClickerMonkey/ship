package net.philsprojects.data.var;

import org.junit.Test;

public class TestUByteVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new UByteVar(), (short)0, (short)23);
		testAccessors(new UByteVar(), (short)0, (short)0);
		testAccessors(new UByteVar(), (short)0, (short)255);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new UByteVar(), new UByteVar(), (short)0);
		testPersist(new UByteVar(), new UByteVar(), (short)23);
		testPersist(new UByteVar(), new UByteVar(), (short)255);
	}
	
}
