package net.philsprojects.data.var;

import org.junit.Test;

public class TestShortVar extends TestVar 
{

	@Test
	public void testAccessors()
	{
		testAccessors(new ShortVar(), (short)0, (short)5543);
	}
	
	@Test
	public void testPersist()
	{
		testPersist(new ShortVar(), new ShortVar(), (short)5543);
	}
	
}
