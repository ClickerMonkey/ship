package net.philsprojects.data.var;

import org.junit.Test;

public class TestDoubleVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new DoubleVar(), 0.0, 12.456);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new DoubleVar(), new DoubleVar(), 12.456);
	}
	
}
