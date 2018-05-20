package net.philsprojects.data.var;

import org.junit.Test;

public class TestUIntVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new UIntVar(), 0L, (1L << 32) - 1);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new UIntVar(), new UIntVar(), (1L << 32) - 1);
	}
	
}
