package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;

import net.philsprojects.game.util.SmoothCurve;

import org.junit.Test;


public class TestSmoothCurve
{

	@Test
	public void testMethods()
	{
		float[] v = new float[] { 2.0f, 4.0f, 5.0f, 2.0f, 8.0f };
		SmoothCurve s = new SmoothCurve(1.0f, v);
		assertEquals(0.25f, s.getTimeInterval(), 0.00001);
		assertEquals(1.0f, s.getDuration(), 0.00001);
		assertEquals(v, s.getValues());
	}

	@Test
	public void setGetValue()
	{
		float[] v = new float[] { 2.0f, 4.0f, 5.0f, 2.0f, 8.0f };
		SmoothCurve s = new SmoothCurve(1.0f, v);
		assertEquals(2.0f, s.getValue(0.0f), 0.00001);
		assertEquals(3.0f, s.getValue(0.125f), 0.00001);
		assertEquals(4.0f, s.getValue(0.25f), 0.00001);
		assertEquals(4.5f, s.getValue(0.375f), 0.00001);
		assertEquals(5.0f, s.getValue(0.5f), 0.00001);
		assertEquals(3.5f, s.getValue(0.625f), 0.00001);
		assertEquals(2.0f, s.getValue(0.75f), 0.00001);
		assertEquals(5.0f, s.getValue(0.875f), 0.00001);
		assertEquals(6.5f, s.getValue(0.9375f), 0.00001); // Odd ball
		assertEquals(8.0f, s.getValue(1.0f), 0.00001);
	}

	@Test
	public void setGetValueAnother()
	{
		float[] v = new float[] { 0.0f, 256.0f };
		SmoothCurve s = new SmoothCurve(8.0f, v);
		assertEquals(0f, s.getValue(-1f), 0.00001);
		assertEquals(0f, s.getValue(0f), 0.00001);
		assertEquals(32f, s.getValue(1f), 0.00001);
		assertEquals(64f, s.getValue(2f), 0.00001);
		assertEquals(80f, s.getValue(2.5f), 0.00001); // Odd ball
		assertEquals(96f, s.getValue(3f), 0.00001);
		assertEquals(128f, s.getValue(4f), 0.00001);
		assertEquals(160f, s.getValue(5f), 0.00001);
		assertEquals(192f, s.getValue(6f), 0.00001);
		assertEquals(224f, s.getValue(7f), 0.00001);
		assertEquals(256f, s.getValue(8f), 0.00001);
		assertEquals(256f, s.getValue(9f), 0.00001);
	}
}
