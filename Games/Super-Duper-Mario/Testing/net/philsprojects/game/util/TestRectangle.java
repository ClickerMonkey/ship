package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;

import net.philsprojects.game.util.Rectangle;

import org.junit.Test;


public class TestRectangle
{

	@Test
	public void testConstructor()
	{
		Rectangle r = new Rectangle(1f, 2f, 3f, 4f);
		assertEquals(1f, r.getX(), 0.00001);
		assertEquals(2f, r.getY(), 0.00001);
		assertEquals(3f, r.getWidth(), 0.00001);
		assertEquals(4f, r.getHeight(), 0.00001);
		r.setX(5f);
		assertEquals(5f, r.getX(), 0.00001);
		r.setY(6f);
		assertEquals(6f, r.getY(), 0.00001);
		r.setWidth(7f);
		assertEquals(7f, r.getWidth(), 0.00001);
		r.setHeight(8f);
		assertEquals(8f, r.getHeight(), 0.00001);
	}

	@Test
	public void testStaticMethods()
	{
		Rectangle r = Rectangle.zero();
		assertEquals(0f, r.getX(), 0.00001);
		assertEquals(0f, r.getY(), 0.00001);
		assertEquals(0f, r.getWidth(), 0.00001);
		assertEquals(0f, r.getHeight(), 0.00001);
		r = Rectangle.one();
		assertEquals(1f, r.getX(), 0.00001);
		assertEquals(1f, r.getY(), 0.00001);
		assertEquals(1f, r.getWidth(), 0.00001);
		assertEquals(1f, r.getHeight(), 0.00001);
	}

}
