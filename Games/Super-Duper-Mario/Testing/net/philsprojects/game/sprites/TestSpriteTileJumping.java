package net.philsprojects.game.sprites;

import static net.philsprojects.game.Constants.*;
import static org.junit.Assert.*;

import net.philsprojects.game.sprites.SpriteTileJumping;
import net.philsprojects.game.util.Vector;

import org.junit.Test;


public class TestSpriteTileJumping
{

	public final Vector v1 = new Vector(0, 0);
	public final Vector v2 = new Vector(20, 10);
	public final Vector v3 = new Vector(10, 60);
	public final Vector v4 = new Vector(40, 30);

	@Test
	public void testMethods()
	{
		SpriteTileJumping s = new SpriteTileJumping("Jumping", "Texture", LOOP_FORWARD, 30, 25, 0.2f, v1, v2, v3, v4);
		assertEquals("Jumping", s.getName());
		assertEquals("Texture", s.getTexture());
		assertEquals(LOOP_FORWARD, s.getAnimationType());
		assertEquals(FORWARD, s.getDirection());
		assertEquals(30, s.getSource().getWidth(), 0.000001);
		assertEquals(25, s.getSource().getHeight(), 0.000001);
		assertEquals(0.2f, s.getAnimationSpeed(), 0.000001);
		assertEquals(v1.toString(), s.getCurrent().toString());
	}

	@Test
	public void testONCE_FORWARD()
	{
		SpriteTileJumping s = new SpriteTileJumping("Jumping", "Texture", ONCE_FORWARD, 30, 25, 1f, v1, v2, v3, v4);
		s.update(0f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		s.update(1f);
		assertFalse(s.isEnabled());
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
	}

	@Test
	public void testLOOP_FORWARD()
	{
		SpriteTileJumping s = new SpriteTileJumping("Jumping", "Texture", LOOP_FORWARD, 30, 25, 1f, v1, v2, v3, v4);
		s.update(0f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		//Loop back around
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		//Loop book around again
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		assertTrue(s.isEnabled());
	}

	@Test
	public void testONCE_BACKWARD()
	{
		SpriteTileJumping s = new SpriteTileJumping("Jumping", "Texture", ONCE_BACKWARD, 30, 25, 1f, v1, v2, v3, v4);
		s.update(0f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		s.update(1f);
		assertFalse(s.isEnabled());
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
	}

	@Test
	public void testLOOP_BACKWARD()
	{
		SpriteTileJumping s = new SpriteTileJumping("Jumping", "Texture", LOOP_BACKWARD, 30, 25, 1f, v1, v2, v3, v4);
		s.update(0f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		//Loops back around to end
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		//Loops back around to end again
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		assertTrue(s.isEnabled());
	}

	public void testPINGPONG()
	{
		SpriteTileJumping s = new SpriteTileJumping("Jumping", "Texture", PINGPONG, 30, 25, 1f, v1, v2, v3, v4);
		s.update(0f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v4.toString(), s.getCurrent().toString());
		//Loops back around to end
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v1.toString(), s.getCurrent().toString());
		//Loops back around to end again
		s.update(1f);
		assertEquals(v2.toString(), s.getCurrent().toString());
		s.update(1f);
		assertEquals(v3.toString(), s.getCurrent().toString());
		assertTrue(s.isEnabled());
	}

}
