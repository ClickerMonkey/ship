package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;

import net.philsprojects.game.util.Color;

import org.junit.Test;


public class TestColor
{

	@Test
	public void testConstructor()
	{
		Color c = new Color(243, 200, 250, 29);
		assertEquals(243 / 255f, c.getR(), 0.00001);
		assertEquals(200 / 255f, c.getG(), 0.00001);
		assertEquals(250 / 255f, c.getB(), 0.00001);
		assertEquals(29 / 255f, c.getA(), 0.00001);
		assertEquals(243, c.getByteR());
		assertEquals(200, c.getByteG());
		assertEquals(250, c.getByteB());
		assertEquals(29, c.getByteA());

		c = new Color(0.8f, 0.25f, 0.1f, 1.0f);
		assertEquals(0.8f, c.getR(), 0.00001);
		assertEquals(0.25f, c.getG(), 0.00001);
		assertEquals(0.1f, c.getB(), 0.00001);
		assertEquals(1.0f, c.getA(), 0.00001);
		assertEquals(0.8f * 255, c.getByteR(), 0.00001);
		assertEquals(0.25f * 255, c.getByteG(), 0.00001);
		assertEquals(0.1f * 255, c.getByteB(), 0.00001);
		assertEquals(1.0f * 255, c.getByteA(), 0.00001);
	}

	@Test
	public void testInverse()
	{
		assertEquals(Color.black().toString(), Color.white().getInverse().toString());
		assertEquals(Color.white().toString(), Color.black().getInverse().toString());
		assertEquals(Color.magenta().toString(), Color.green().getInverse().toString());
		assertEquals(Color.green().toString(), Color.magenta().getInverse().toString());
	}

}
