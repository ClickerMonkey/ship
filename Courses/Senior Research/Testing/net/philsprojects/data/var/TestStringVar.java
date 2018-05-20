package net.philsprojects.data.var;

import org.junit.Test;

public class TestStringVar extends TestVar
{	

	@Test
	public void testAccessors()
	{
		testAccessors(new StringVar(12), "", "Hello World!");
		testAccessors(new StringVar(12), "", "Hi");
		testAccessors(new StringVar(12), "", "This is much longer than 12 characters", "This is much");
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new StringVar(12), new StringVar(12), "Hi");
		testPersist(new StringVar(12), new StringVar(12), "Hello World!");
		testPersist(new StringVar(12), new StringVar(12), "This is much longer than 12 characters", "This is much");
	}
	
}
