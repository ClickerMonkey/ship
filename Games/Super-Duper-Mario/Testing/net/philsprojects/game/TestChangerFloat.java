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

import net.philsprojects.game.ChangerFloat;

import org.junit.Test;

public class TestChangerFloat
{

	@Test
	public void testConstructor1()
	{
		ChangerFloat c = new ChangerFloat("Name", LOOP_FORWARD);
		assertEquals("Name", c.getName(), 0.00001);
		assertEquals(LOOP_FORWARD, c.getAnimationType(), 0.00001);
		assertEquals(FORWARD, c.getDirection(), 0.00001);
	}

	@Test
	public void testConstructor2()
	{
		float[] values = { 4f, 2f, 8f, 6f };
		ChangerFloat c = new ChangerFloat("Name", LOOP_FORWARD, 1.0f, values);
		assertEquals("Name", c.getName(), 0.00001);
		assertEquals(LOOP_FORWARD, c.getAnimationType(), 0.00001);
		assertEquals(FORWARD, c.getDirection(), 0.00001);
		assertEquals(4f, c.getValue(), 0.00001);
	}

	@Test
	public void testConstructor3()
	{
		float[] values = { 4f, 2f, 8f, 6f };
		float[] times = { 0f, 0.3f, 0.6f, 1f };
		ChangerFloat c = new ChangerFloat("Name", LOOP_FORWARD, 97, times, values);
		assertEquals("Name", c.getName(), 0.00001);
		assertEquals(LOOP_FORWARD, c.getAnimationType(), 0.00001);
		assertEquals(FORWARD, c.getDirection(), 0.00001);
		assertEquals(4f, c.getValue(), 0.00001);
	}

	@Test
	public void testONCE_FORWARD()
	{
		float[] values = { 4f, 2f, 8f, 6f };
		ChangerFloat c = new ChangerFloat("Name", ONCE_FORWARD, 6f, values);
		c.start();
		c.update(0f);
		assertEquals(4f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(5f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(8f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(7f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(6f, c.getValue(), 0.00001);
		// Stops at last value
		c.update(1f);
		assertEquals(6f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(6f, c.getValue(), 0.00001);
		assertFalse(c.isEnabled());
	}

	@Test
	public void testLOOP_FORWARD()
	{
		float[] values = { 4f, 2f, 8f, 6f };
		ChangerFloat c = new ChangerFloat("Name", LOOP_FORWARD, 6f, values);
		c.start();
		c.update(0f);
		assertEquals(4f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(5f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(8f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(7f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(6f, c.getValue(), 0.00001);
		// Loops back around
		c.update(1f);
		assertEquals(4f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		assertTrue(c.isEnabled());
	}

	@Test
	public void testONCE_BACKWARD()
	{
		float[] values = { 4f, 2f, 8f, 6f };
		ChangerFloat c = new ChangerFloat("Name", ONCE_BACKWARD, 6f, values);
		c.start();
		c.update(0f);
		assertEquals(6f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(7f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(8f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(5f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(4f, c.getValue(), 0.00001);
		// Stops at last value
		c.update(1f);
		assertEquals(4f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(4f, c.getValue(), 0.00001);
		assertFalse(c.isEnabled());
	}

	@Test
	public void testLOOP_BACKWARD()
	{
		float[] values = { 4f, 2f, 8f, 6f };
		ChangerFloat c = new ChangerFloat("Name", LOOP_BACKWARD, 6f, values);
		c.start();
		c.update(0f);
		assertEquals(6f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(7f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(8f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(5f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(4f, c.getValue(), 0.00001);
		// Loops back around
		c.update(1f);
		assertEquals(6f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(7f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(8f, c.getValue(), 0.00001);
		assertTrue(c.isEnabled());
	}

	@Test
	public void testPINGPONG()
	{
		float[] values = { 4f, 2f, 8f, 6f };
		ChangerFloat c = new ChangerFloat("Name", PINGPONG, 6f, values);
		c.start();
		c.update(0f);
		assertEquals(FORWARD, c.getDirection(), 0.00001);
		assertEquals(4f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(5f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(8f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(7f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(6f, c.getValue(), 0.00001);
		assertEquals(FORWARD, c.getDirection(), 0.00001);
		// Goes backwards now
		c.update(1f);
		assertEquals(BACKWARD, c.getDirection(), 0.00001);
		assertEquals(7f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(8f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(5f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(4f, c.getValue(), 0.00001);
		assertEquals(BACKWARD, c.getDirection(), 0.00001);
		// Goes forwards now
		c.update(1f);
		assertEquals(FORWARD, c.getDirection(), 0.00001);
		assertEquals(3f, c.getValue(), 0.00001);
		c.update(1f);
		assertEquals(2f, c.getValue(), 0.00001);
		assertTrue(c.isEnabled());
	}

}
