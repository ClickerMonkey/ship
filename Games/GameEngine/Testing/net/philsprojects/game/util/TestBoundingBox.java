package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Size;
import net.philsprojects.game.util.Vector;

import org.junit.Test;


public class TestBoundingBox
{

	@Test
	public void testConstructor()
	{
		BoundingBox b = new BoundingBox(10f, 30f, 40f, 5f);
		assertEquals(10f, b.getLeft(), 0.00001);
		assertEquals(30f, b.getTop(), 0.00001);
		assertEquals(40f, b.getRight(), 0.00001);
		assertEquals(5f, b.getBottom(), 0.00001);
		assertEquals(25f, b.getHeight(), 0.00001);
		assertEquals(30f, b.getWidth(), 0.00001);
		b = new BoundingBox(new Vector(5f, 10f), new Size(30f, 25f));
		assertEquals(5f, b.getLeft(), 0.00001);
		assertEquals(35f, b.getTop(), 0.00001);
		assertEquals(35f, b.getRight(), 0.00001);
		assertEquals(10f, b.getBottom(), 0.00001);
		assertEquals(25f, b.getHeight(), 0.00001);
		assertEquals(30f, b.getWidth(), 0.00001);
		b = new BoundingBox();
		assertEquals(0f, b.getLeft(), 0.00001);
		assertEquals(0f, b.getTop(), 0.00001);
		assertEquals(0f, b.getRight(), 0.00001);
		assertEquals(0f, b.getBottom(), 0.00001);
		assertEquals(0f, b.getHeight(), 0.00001);
		assertEquals(0f, b.getWidth(), 0.00001);
	}

	@Test
	public void testContains()
	{
		//Left Top Right Bottom
		BoundingBox b = new BoundingBox(10f, 30f, 40f, 5f);
		assertTrue(b.contains(12f, 20f)); //Somewhere inside
		assertFalse(b.contains(8f, 20f)); //Two away from the left
		assertFalse(b.contains(41f, 20f)); //One away from the right
		assertFalse(b.contains(40f, 20f)); //On the right edge
		assertFalse(b.contains(40f, 20f)); //On the left edge
		assertFalse(b.contains(40f, 20f)); //On the top edge
		assertFalse(b.contains(40f, 20f)); //On the bottom edge
	}


	@Test
	public void testInclude()
	{
		BoundingBox b = new BoundingBox();
		b.clear(5f, 10f);
		b.include(2f, 13f);
		//Include a point thats above and to the left
		assertEquals(2f, b.getLeft(), 0.00001);
		assertEquals(13f, b.getTop(), 0.00001);
		assertEquals(5f, b.getRight(), 0.00001);
		assertEquals(10f, b.getBottom(), 0.00001);
		b.include(7f, 8f);
		//Include a point thats below and to the right
		assertEquals(2f, b.getLeft(), 0.00001);
		assertEquals(13f, b.getTop(), 0.00001);
		assertEquals(7f, b.getRight(), 0.00001);
		assertEquals(8f, b.getBottom(), 0.00001);
		//Include a particle that overlaps the right side
		b.include(6f, 11f, 2f);
		assertEquals(2f, b.getLeft(), 0.00001);
		assertEquals(13f, b.getTop(), 0.00001);
		assertEquals(8f, b.getRight(), 0.00001);
		assertEquals(8f, b.getBottom(), 0.00001);
		//Include a box thats to the lower left
		b.include(1f, 6f, 5f, 9f);
		assertEquals(1f, b.getLeft(), 0.00001);
		assertEquals(15f, b.getTop(), 0.00001);
		assertEquals(8f, b.getRight(), 0.00001);
		assertEquals(6f, b.getBottom(), 0.00001);
		//Include a box thats to the middle right
		b.include(new BoundingBox(6f, 13f, 12f, 7f));
		assertEquals(1f, b.getLeft(), 0.00001);
		assertEquals(15f, b.getTop(), 0.00001);
		assertEquals(12f, b.getRight(), 0.00001);
		assertEquals(6f, b.getBottom(), 0.00001);
		//Include a box completely surrounding it
		b.include(new BoundingBox(0f, 20f, 14f, 2f));
		assertEquals(0f, b.getLeft(), 0.00001);
		assertEquals(20f, b.getTop(), 0.00001);
		assertEquals(14f, b.getRight(), 0.00001);
		assertEquals(2f, b.getBottom(), 0.00001);
	}

	@Test
	public void testIntersects()
	{
		BoundingBox b = new BoundingBox(2f, 15f, 11f, 3f);
		//Intersects with a bounding box
		assertTrue(b.intersects(new BoundingBox(5f, 20f, 13f, 6f))); //Upper Right
		assertFalse(b.intersects(new BoundingBox(13f, 20f, 18f, 6f))); //Far Right
		assertFalse(b.intersects(new BoundingBox(0f, 20f, 1.9999f, 6f))); //On Left Side
		assertTrue(b.intersects(new BoundingBox(5f, 10f, 9f, 6f))); //Inside
		assertTrue(b.intersects(new BoundingBox(1f, 20f, 16f, 2f))); //Outside
		//Intersects with a rectangle

		//Intersects with a quad

		//Intersects with a particle
	}


	@Test
	public void testIntersection()
	{
		BoundingBox m = new BoundingBox(10f, 35f, 25f, 15f); // Middle
		BoundingBox ur = new BoundingBox(15f, 45f, 40f, 20f); // Upper Right
		assertTrue(m.intersects(ur));
		assertEquals("{Left<15.0> Top<35.0> Right<25.0> Bottom<20.0>}", m.intersection(ur).toString());
		BoundingBox ul = new BoundingBox(5f, 45f, 15f, 30f); // Upper Left
		assertTrue(m.intersects(ul));
		assertEquals("{Left<10.0> Top<35.0> Right<15.0> Bottom<30.0>}", m.intersection(ul).toString());
		BoundingBox bl = new BoundingBox(8f, 30f, 16f, 10f); // Bottom Left
		assertTrue(m.intersects(bl));
		assertEquals("{Left<10.0> Top<30.0> Right<16.0> Bottom<15.0>}", m.intersection(bl).toString());
		BoundingBox br = new BoundingBox(15f, 30f, 27f, 10f); // Bottom Right
		assertTrue(m.intersects(br));
		assertEquals("{Left<15.0> Top<30.0> Right<25.0> Bottom<15.0>}", m.intersection(br).toString());
		BoundingBox i = new BoundingBox(15f, 30f, 20f, 20f); // Inside
		assertTrue(m.intersects(i));
		assertEquals(i.toString(), m.intersection(i).toString());
		assertEquals(i.toString(), i.intersection(m).toString());
	}

}
