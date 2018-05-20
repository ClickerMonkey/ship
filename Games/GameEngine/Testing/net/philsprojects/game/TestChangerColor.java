package net.philsprojects.game;

import static net.philsprojects.game.Constants.BACKWARD;
import static net.philsprojects.game.Constants.FORWARD;
import static net.philsprojects.game.Constants.LOOP_BACKWARD;
import static net.philsprojects.game.Constants.LOOP_FORWARD;
import static net.philsprojects.game.Constants.ONCE_BACKWARD;
import static net.philsprojects.game.Constants.ONCE_FORWARD;
import static net.philsprojects.game.Constants.PINGPONG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.philsprojects.game.ChangerColor;
import net.philsprojects.game.util.Color;

import org.junit.Test;


public class TestChangerColor
{

	@Test
	public void testConstructor1()
	{
		ChangerColor c = new ChangerColor("Name", LOOP_FORWARD);
		assertEquals("Name", c.getName());
		assertEquals(LOOP_FORWARD, c.getAnimationType());
		assertEquals(FORWARD, c.getDirection());
	}

	@Test
	public void testConstructor2()
	{
		Color[] values = { Color.white(), Color.red(), Color.blue() };
		ChangerColor c = new ChangerColor("Name", LOOP_FORWARD, 1.0f, values);
		assertEquals("Name", c.getName());
		assertEquals(LOOP_FORWARD, c.getAnimationType());
		assertEquals(FORWARD, c.getDirection());
		assertEquals("{R<1.000> G<1.000> B<1.000> A<1.000>}", c.getColor().toString());
	}

	@Test
	public void testConstructor3()
	{
		Color[] values = { Color.white(), Color.red(), Color.blue() };
		float[] times = { 0f, 0.5f, 1f };
		ChangerColor c = new ChangerColor("Name", LOOP_FORWARD, 97, times, values);
		assertEquals("Name", c.getName());
		assertEquals(LOOP_FORWARD, c.getAnimationType());
		assertEquals(FORWARD, c.getDirection());
		assertEquals("{R<1.000> G<1.000> B<1.000> A<1.000>}", c.getColor().toString());
	}

	public static final String white = "{R<1.000> G<1.000> B<1.000> A<1.000>}";
	public static final String pink = "{R<1.000> G<0.500> B<0.500> A<1.000>}";
	public static final String red = "{R<1.000> G<0.000> B<0.000> A<1.000>}";
	public static final String purple = "{R<0.500> G<0.000> B<0.500> A<1.000>}";
	public static final String blue = "{R<0.000> G<0.000> B<1.000> A<1.000>}";

	@Test
	public void testONCE_FORWARD()
	{
		Color[] values = { Color.white(), Color.red(), Color.blue() };
		ChangerColor c = new ChangerColor("Name", ONCE_FORWARD, 4f, values);
		c.start();
		c.update(0f);
		assertEquals(white, c.getColor().toString());
		c.update(1f);
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		c.update(1f);
		assertEquals(purple, c.getColor().toString());
		c.update(1f);
		assertEquals(blue, c.getColor().toString());
		// Stops at last value
		c.update(1f);
		assertEquals(blue, c.getColor().toString());
		c.update(1f);
		assertEquals(blue, c.getColor().toString());
		assertFalse(c.isEnabled());
	}

	@Test
	public void testLOOP_FORWARD()
	{
		Color[] values = { Color.white(), Color.red(), Color.blue() };
		ChangerColor c = new ChangerColor("Name", LOOP_FORWARD, 4f, values);
		c.start();
		c.update(0f);
		assertEquals(white, c.getColor().toString());
		c.update(1f);
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		c.update(1f);
		assertEquals(purple, c.getColor().toString());
		c.update(1f);
		assertEquals(blue, c.getColor().toString());
		// Loops back around
		c.update(1f);
		assertEquals(white, c.getColor().toString());
		c.update(1f);
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		assertTrue(c.isEnabled());
	}

	@Test
	public void testONCE_BACKWARD()
	{
		Color[] values = { Color.white(), Color.red(), Color.blue() };
		ChangerColor c = new ChangerColor("Name", ONCE_BACKWARD, 4f, values);
		c.start();
		c.update(0f);
		assertEquals(blue, c.getColor().toString());
		c.update(1f);
		assertEquals(purple, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		c.update(1f);
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(white, c.getColor().toString());
		// Stops at last value
		c.update(1f);
		assertEquals(white, c.getColor().toString());
		c.update(1f);
		assertEquals(white, c.getColor().toString());
		assertFalse(c.isEnabled());
	}

	@Test
	public void testLOOP_BACKWARD()
	{
		Color[] values = { Color.white(), Color.red(), Color.blue() };
		ChangerColor c = new ChangerColor("Name", LOOP_BACKWARD, 4f, values);
		c.start();
		c.update(0f);
		assertEquals(blue, c.getColor().toString());
		c.update(1f);
		assertEquals(purple, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		c.update(1f);
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(white, c.getColor().toString());
		// Loops back around
		c.update(1f);
		assertEquals(blue, c.getColor().toString());
		c.update(1f);
		assertEquals(purple, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		assertTrue(c.isEnabled());
	}

	@Test
	public void testPINGPONG()
	{
		Color[] values = { Color.white(), Color.red(), Color.blue() };
		ChangerColor c = new ChangerColor("Name", PINGPONG, 4f, values);
		c.start();
		c.update(0f);
		assertEquals(FORWARD, c.getDirection());
		assertEquals(white, c.getColor().toString());
		c.update(1f);
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		c.update(1f);
		assertEquals(purple, c.getColor().toString());
		c.update(1f);
		assertEquals(blue, c.getColor().toString());
		assertEquals(FORWARD, c.getDirection());
		// Goes backwards now
		c.update(1f);
		assertEquals(BACKWARD, c.getDirection());
		assertEquals(purple, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		c.update(1f);
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(white, c.getColor().toString());
		assertEquals(BACKWARD, c.getDirection());
		// Goes forwards now
		c.update(1f);
		assertEquals(FORWARD, c.getDirection());
		assertEquals(pink, c.getColor().toString());
		c.update(1f);
		assertEquals(red, c.getColor().toString());
		assertTrue(c.isEnabled());
	}

}
