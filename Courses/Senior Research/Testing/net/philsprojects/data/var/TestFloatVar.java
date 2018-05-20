package net.philsprojects.data.var;

import org.junit.Test;

public class TestFloatVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new FloatVar(), 0.0f, 12.456f);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new FloatVar(), new FloatVar(), 12.456f);
	}
	
}
