package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;

import net.philsprojects.game.util.Size;

import org.junit.Test;


public class TestSize
{

	@Test
	public void testConstructor()
	{
		Size s = new Size(23.4f, 45.6f);
		assertEquals(23.4f, s.width, 0.00001);
		assertEquals(45.6f, s.height, 0.00001);
	}

	@Test
	public void testAccessors()
	{
		Size s = new Size();
		s.width = 23.4f;
		s.height = 45.6f;
		assertEquals(23.4f, s.width, 0.00001);
		assertEquals(45.6f, s.height, 0.00001);
		s.set(12.3f, 24.5f);
		assertEquals(12.3f, s.width, 0.00001);
		assertEquals(24.5f, s.height, 0.00001);
	}

	@Test
	public void testStaticMethods()
	{
		Size s = Size.zero();
		assertEquals(0.0f, s.width, 0.00001);
		assertEquals(0.0f, s.height, 0.00001);
		s = Size.one();
		assertEquals(1.0f, s.width, 0.00001);
		assertEquals(1.0f, s.height, 0.00001);
	}

}
