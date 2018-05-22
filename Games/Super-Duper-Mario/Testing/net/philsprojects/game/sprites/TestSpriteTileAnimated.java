package net.philsprojects.game.sprites;

import static net.philsprojects.game.Constants.*;
import static org.junit.Assert.*;

import net.philsprojects.game.sprites.SpriteTileAnimated;

import org.junit.Test;


public class TestSpriteTileAnimated
{
	@Test
	public void testMethods()
	{
		SpriteTileAnimated tile = new SpriteTileAnimated("Name", "Texture", LOOP_FORWARD, 3, 6, 32, 64, 0.5f);
		assertEquals("Name", tile.getName());
		assertEquals("Texture", tile.getTexture());
		assertEquals(LOOP_FORWARD, tile.getAnimationType());
		assertEquals(FORWARD, tile.getDirection());
		assertEquals(3, tile.getColumns());
		assertEquals(6, tile.getTotalFrames());
		assertEquals(32, tile.getFrameWidth());
		assertEquals(64, tile.getFrameHeight());
		assertEquals(0.5f, tile.getAnimationSpeed(), 0.000001);
	}

	public static final String source0 = "{X<0.0> Y<0.0> Width<32.0> Height<64.0>}";
	public static final String source1 = "{X<32.0> Y<0.0> Width<32.0> Height<64.0>}";
	public static final String source2 = "{X<64.0> Y<0.0> Width<32.0> Height<64.0>}";
	public static final String source3 = "{X<0.0> Y<64.0> Width<32.0> Height<64.0>}";
	public static final String source4 = "{X<32.0> Y<64.0> Width<32.0> Height<64.0>}";
	public static final String source5 = "{X<64.0> Y<64.0> Width<32.0> Height<64.0>}";

	@Test
	public void testONCE_FORWARD()
	{
		SpriteTileAnimated tile = new SpriteTileAnimated("OF", "Texture", ONCE_FORWARD, 3, 6, 32, 64, 1f);
		tile.update(0f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source3, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source4, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source5, tile.getSource().toString());
		tile.update(1f);
		assertFalse(tile.isEnabled());
		tile.update(1f);
		assertEquals(source5, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source5, tile.getSource().toString());
	}

	@Test
	public void testLOOP_FORWARD()
	{
		SpriteTileAnimated tile = new SpriteTileAnimated("LF", "Texture", LOOP_FORWARD, 3, 6, 32, 64, 1f);
		tile.update(0f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source3, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source4, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source5, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
	}

	@Test
	public void testONCE_BACKWARD()
	{
		SpriteTileAnimated tile = new SpriteTileAnimated("OF", "Texture", ONCE_BACKWARD, 3, 6, 32, 64, 1f);
		tile.update(0f);
		assertEquals(source5, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source4, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source3, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertFalse(tile.isEnabled());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
	}

	@Test
	public void testLOOP_BACKWARD()
	{
		SpriteTileAnimated tile = new SpriteTileAnimated("LF", "Texture", LOOP_BACKWARD, 3, 6, 32, 64, 1f);
		tile.update(0f);
		assertEquals(source5, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source4, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source3, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source5, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source4, tile.getSource().toString());
	}

	public void testPINGPONG()
	{
		SpriteTileAnimated tile = new SpriteTileAnimated("LF", "Texture", PINGPONG, 3, 6, 32, 64, 1f);
		tile.update(0f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source3, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source4, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source5, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source4, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source3, tile.getSource().toString());	
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source3, tile.getSource().toString());
	}

}
