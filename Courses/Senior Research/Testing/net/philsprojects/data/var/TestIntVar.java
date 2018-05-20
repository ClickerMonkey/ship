package net.philsprojects.data.var;

import org.junit.Test;

public class TestIntVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new IntVar(), 0, 12456);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new IntVar(), new IntVar(), 12456);
	}
	
}
