package net.philsprojects.game.util;

import static net.philsprojects.game.util.Math.round;
import static org.junit.Assert.assertEquals;

import net.philsprojects.game.util.Curve;

import org.junit.Test;


public class TestCurve
{

	@Test
	public void testCurve()
	{
		float[] times = { 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f };
		float[] values = { 10.0f, 12.0f, 16.0f, 22.0f, 30.0f, 40.0f };
		Curve c = new Curve(11, times, values);
		assertEquals(10.0f, round(c.getValue(0.0f)), 0.00001);
		assertEquals(11.0f, round(c.getValue(0.1f)), 0.00001);
		assertEquals(12.0f, round(c.getValue(0.2f)), 0.00001);
		assertEquals(14.0f, round(c.getValue(0.3f)), 0.00001);
		assertEquals(16.0f, round(c.getValue(0.4f)), 0.00001);
		assertEquals(19.0f, round(c.getValue(0.5f)), 0.00001);
		assertEquals(22.0f, round(c.getValue(0.6f)), 0.00001);
		assertEquals(26.0f, round(c.getValue(0.7f)), 0.00001);
		assertEquals(30.0f, round(c.getValue(0.8f)), 0.00001);
		assertEquals(35.0f, round(c.getValue(0.9f)), 0.00001);
		assertEquals(40.0f, round(c.getValue(1.0f)), 0.00001);
	}

	@Test
	public void testCurveAnother()
	{
		float[] times = { 0.0f, 0.25f, 0.75f, 1.0f };
		float[] values = { 10.0f, 2.0f, 10.0f, 20.0f };
		Curve c = new Curve(9, times, values);
		assertEquals(10.0f, round(c.getValue(0.0f)), 0.00001);
		assertEquals(6.0f, round(c.getValue(0.125f)), 0.00001);
		assertEquals(2.0f, round(c.getValue(0.25f)), 0.00001);
		assertEquals(4.0f, round(c.getValue(0.375f)), 0.00001);
		assertEquals(6.0f, round(c.getValue(0.5f)), 0.00001);
		assertEquals(8.0f, round(c.getValue(0.625f)), 0.00001);
		assertEquals(10.0f, round(c.getValue(0.75f)), 0.00001);
		assertEquals(15.0f, round(c.getValue(0.875f)), 0.00001);
		assertEquals(20.0f, round(c.getValue(1.0f)), 0.00001);
	}

	@Test
	public void testCurveAnotherTime()
	{
		float[] times = { 0.0f, 0.25f, 0.75f, 1.0f };
		float[] values = { 10.0f, 2.0f, 10.0f, 20.0f };
		Curve c = new Curve(0.125f, times, values);
		assertEquals(10.0f, round(c.getValue(0.0f)), 0.00001);
		assertEquals(6.0f, round(c.getValue(0.125f)), 0.00001);
		assertEquals(2.0f, round(c.getValue(0.25f)), 0.00001);
		assertEquals(4.0f, round(c.getValue(0.375f)), 0.00001);
		assertEquals(6.0f, round(c.getValue(0.5f)), 0.00001);
		assertEquals(8.0f, round(c.getValue(0.625f)), 0.00001);
		assertEquals(10.0f, round(c.getValue(0.75f)), 0.00001);
		assertEquals(15.0f, round(c.getValue(0.875f)), 0.00001);
		assertEquals(20.0f, round(c.getValue(1.0f)), 0.00001);
	}

}
