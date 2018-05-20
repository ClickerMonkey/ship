package net.philsprojects.data.var;

import org.junit.Test;

public class TestUShortVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new UShortVar(), 0, 12456);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new UShortVar(), new UShortVar(), 12456);
	}
	
}
