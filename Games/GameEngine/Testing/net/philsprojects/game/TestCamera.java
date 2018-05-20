package net.philsprojects.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.philsprojects.game.Camera;
import net.philsprojects.game.ICameraObserver;

import org.junit.Test;

public class TestCamera
{

	@Test
	public void testOnlyInstance()
	{
		Camera.initialize(1, 2, 3, 4);
		Camera c1 = Camera.getInstance();
		Camera c2 = Camera.getInstance();
		assertTrue(c1 == c2);
		assertEquals(c1, c2);
	}

	@Test
	public void testAddObserver()
	{
		Camera.initialize(128, 64, 512, 384);
		Observer o1 = new Observer();
		Observer o2 = new Observer();
		assertEquals(2, Camera.getInstance().getObserverCount());
		Camera.getInstance().setLocation(10, 10);
		assertEquals(10, o1.cameraX);
		assertEquals(10, o1.cameraY);
		assertEquals(512, o1.cameraWidth);
		assertEquals(384, o1.cameraHeight);
		assertEquals(10, o2.cameraX);
		assertEquals(10, o2.cameraY);
		assertEquals(512, o2.cameraWidth);
		assertEquals(384, o2.cameraHeight);
	}

	@Test
	public void testAccessors()
	{
		Camera.initialize(128, 64, 512, 384);
		Camera c = Camera.getInstance();
		assertEquals(128, c.getX());
		assertEquals(64, c.getY());
		assertEquals(512, c.getWidth());
		assertEquals(384, c.getHeight());
		assertEquals(448, c.getTop());
		assertEquals(640, c.getRight());
	}

	@Test
	public void testContains()
	{
		Camera.initialize(128, 64, 512, 384);
		Camera c = Camera.getInstance();
		// Test Vector
		assertTrue(c.contains(129, 256)); // Left Edge
		assertTrue(c.contains(639, 256)); // Right Edge
		assertTrue(c.contains(384, 65)); // Top Edge
		assertTrue(c.contains(384, 447)); // Bottom Edge
		assertTrue(c.contains(384, 256)); // Middle
		assertFalse(c.contains(384, 63)); // Above Top
		assertFalse(c.contains(384, 449)); // Below Bottom
		assertFalse(c.contains(641, 256)); // Far Right
		assertFalse(c.contains(127, 256)); // Far Left
		// Test Rectangle/Quad
		assertTrue(c.contains(130, 240, 70, 200)); // Inside
		assertFalse(c.contains(120, 240, 70, 200)); // On Edge
		assertFalse(c.contains(0, 127, 100, 300)); // Outside
	}

	@Test
	public void testIntersects()
	{
		Camera.initialize(128, 64, 512, 384);
		Camera c = Camera.getInstance();
		// Test Rectangle/Quad
		assertTrue(c.intersects(130, 240, 70, 200)); // Inside
		assertTrue(c.intersects(120, 240, 70, 200)); // On Edge
		assertFalse(c.intersects(0, 127, 100, 300)); // Outside
	}

	@Test
	public void testTranslate()
	{
		Camera.initialize(128, 64, 512, 384);
		Camera c = Camera.getInstance();
		Observer o = new Observer();
		c.update();
		assertEquals(128, o.cameraX);
		assertEquals(64, o.cameraY);
		assertEquals(512, o.cameraWidth);
		assertEquals(384, o.cameraHeight);
		c.translate(64, 32);
		assertEquals(192, o.cameraX);
		assertEquals(96, o.cameraY);
		assertEquals(512, o.cameraWidth);
		assertEquals(384, o.cameraHeight);
	}

	private class Observer implements ICameraObserver
	{
		public int cameraX = 0;
		public int cameraY = 0;
		public int cameraWidth = 0;
		public int cameraHeight = 0;

		public Observer()
		{
			Camera.getInstance().addObserver(this);
		}

		public void cameraChanged(int newX, int newY, int newWidth, int newHeight)
		{
			cameraX = newX;
			cameraY = newY;
			cameraWidth = newWidth;
			cameraHeight = newHeight;
		}

	}

}
