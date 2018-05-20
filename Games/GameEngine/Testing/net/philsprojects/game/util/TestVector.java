package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;

import net.philsprojects.game.util.Vector;

import org.junit.Test;


public class TestVector
{

	@Test
	public void testConstructor()
	{
		Vector v = new Vector(23.4f, 45.6f);
		assertEquals(23.4f, v.x, 0.00001);
		assertEquals(45.6f, v.y, 0.00001);
	}

	@Test
	public void testAccessors()
	{
		Vector v = new Vector();
		v.x = 23.4f;
		v.y = 45.6f;
		assertEquals(23.4f, v.x, 0.00001);
		assertEquals(45.6f, v.y, 0.00001);
		v.set(12.3f, 24.5f);
		assertEquals(12.3f, v.x, 0.00001);
		assertEquals(24.5f, v.y, 0.00001);
		v.set(new Vector(76.8f, 89.5f));
		assertEquals(76.8f, v.x, 0.00001);
		assertEquals(89.5f, v.y, 0.00001);
	}

	@Test
	public void testStaticMethods()
	{
		Vector v1 = new Vector(2f, 5f);
		Vector v2 = new Vector(8f, 10f);
		Vector v3;
		v3 = Vector.add(v1, v2);
		assertEquals(10f, v3.x, 0.00001);
		assertEquals(15f, v3.y, 0.00001);
		v3 = Vector.subtract(v1, v2);
		assertEquals(-6f, v3.x, 0.00001);
		assertEquals(-5f, v3.y, 0.00001);
		v3 = Vector.multiply(v1, v2);
		assertEquals(16f, v3.x, 0.00001);
		assertEquals(50f, v3.y, 0.00001);
		v3 = Vector.divide(v1, v2);
		assertEquals(0.25f, v3.x, 0.00001);
		assertEquals(0.5f, v3.y, 0.00001);
		v3 = Vector.zero();
		assertEquals(0.0f, v3.x, 0.00001);
		assertEquals(0.0f, v3.y, 0.00001);
		v3 = Vector.one();
		assertEquals(1.0f, v3.x, 0.00001);
		assertEquals(1.0f, v3.y, 0.00001);
		assertEquals(7.81025f, Vector.distance(v1, v2), 0.00001);
	}

}
