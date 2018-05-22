package net.philsprojects.game.sprites;

import static net.philsprojects.game.Constants.FORWARD;
import static net.philsprojects.game.Constants.LOOP_BACKWARD;
import static net.philsprojects.game.Constants.LOOP_FORWARD;
import static net.philsprojects.game.Constants.ONCE_BACKWARD;
import static net.philsprojects.game.Constants.ONCE_FORWARD;
import static net.philsprojects.game.Constants.PINGPONG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import net.philsprojects.game.sprites.SpriteTileFramed;

import org.junit.Test;


public class TestSpriteTileFramed
{
	@Test
	public void testMethods()
	{
		SpriteTileFramed tile = new SpriteTileFramed("Name", "Texture", LOOP_FORWARD, 3, 32, 64, 0.5f, new int[] {0, 1, 2, 1, 0 ,3});
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

	@Test
	public void testONCE_FORWARD()
	{
		SpriteTileFramed tile = new SpriteTileFramed("OF", "Texture", ONCE_FORWARD, 3, 32, 64, 1f, new int[] {0, 1, 2, 1, 0, 1});
		tile.update(0f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertFalse(tile.isEnabled());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
	}

	@Test
	public void testLOOP_FORWARD()
	{
		SpriteTileFramed tile = new SpriteTileFramed("OF", "Texture", LOOP_FORWARD, 3, 32, 64, 1f, new int[] {0, 1, 2, 1, 0, 1});
		tile.update(0f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
	}

	@Test
	public void testONCE_BACKWARD()
	{
		SpriteTileFramed tile = new SpriteTileFramed("OF", "Texture", ONCE_BACKWARD, 3, 32, 64, 1f, new int[] {0, 1, 2, 1, 0, 1});
		tile.update(0f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
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
		SpriteTileFramed tile = new SpriteTileFramed("OF", "Texture", LOOP_BACKWARD, 3, 32, 64, 1f, new int[] {0, 1, 2, 1, 0, 1});
		tile.update(0f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
	}

	public void testPINGPONG()
	{
		SpriteTileFramed tile = new SpriteTileFramed("OF", "Texture", PINGPONG, 3, 32, 64, 1f, new int[] {0, 1, 2, 1, 0, 1});
		tile.update(0f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source2, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source0, tile.getSource().toString());
		tile.update(1f);
		assertEquals(source1, tile.getSource().toString());	
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
		assertEquals(source1, tile.getSource().toString());
	}
}
