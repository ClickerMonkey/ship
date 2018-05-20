package net.philsprojects.data.var;

import org.junit.Test;

public class TestBooleanVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new BooleanVar(), false, true);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new BooleanVar(), new BooleanVar(), true);
	}
	
}
