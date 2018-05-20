package net.philsprojects.game;

import static org.junit.Assert.assertEquals;
import net.philsprojects.game.Camera;
import net.philsprojects.game.Landscape;
import net.philsprojects.game.sprites.SpriteTileStatic;

import org.junit.Test;


public class TestLandscape
{

	@Test
	public void testConstuctor()
	{
		Camera.initialize(0, 0, 800, 600);
		Landscape l = new Landscape(new SpriteTileStatic("", "Landscape", 0, 0, 0, 0), 512, 256, 0, 0.8f, 0.5f);
		assertEquals("Landscape", l.getTexture());
		assertEquals(512, l.getWidth());
		assertEquals(256, l.getHeight());
		assertEquals(0, l.getY());
		assertEquals(0.8f, l.getXDamping(), 0.00001);
		assertEquals(0.5f, l.getYDamping(), 0.00001);
		assertEquals(1, Camera.getInstance().getObserverCount());
	}

	@Test
	public void testNormalLandscape()
	{
		Camera.initialize(0, 0, 800, 600);
		Camera c = Camera.getInstance();
		Landscape l = new Landscape(new SpriteTileStatic("", "Landscape", 0, 0, 0, 0), 512, 256, 0, 0.5f, 1f);
		assertEquals(1, c.getObserverCount());
		assertEquals(0, l.getActualXStart());
		assertEquals(0, l.getActualY());
		c.setLocation(200, 0); c.update();
		assertEquals(100, l.getActualXStart());
		assertEquals(0, l.getActualY());
		c.setLocation(513, 0); c.update();
		assertEquals(256, l.getActualXStart());
		assertEquals(0, l.getActualY());
	}

}
