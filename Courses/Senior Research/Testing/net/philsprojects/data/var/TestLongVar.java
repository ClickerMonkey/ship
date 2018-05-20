package net.philsprojects.data.var;

import org.junit.Test;

public class TestLongVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new LongVar(), 0L, 9876543210L);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new LongVar(), new LongVar(), 9876543210L);
	}
	
}
