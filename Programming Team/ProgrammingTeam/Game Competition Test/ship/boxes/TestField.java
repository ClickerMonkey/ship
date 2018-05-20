package ship.boxes;

import static org.junit.Assert.*;

import org.junit.Test;



public class TestField
{

	@Test
	public void testImpl()
	{
		Field field = new FieldImpl(4, 5);
		
		assertEquals(4, field.getWidth());
		assertEquals(5, field.getHeight());

		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				Box box = field.getBox(x, y);
				assertNotNull(box);
				assertEquals(0, box.lines());
			}
		}
	}
	
	@Test
	public void testHasLine()
	{
		FieldImpl field = new FieldImpl(4, 4);
		Line line = new Line(1, 1, Side.Left);
		
		assertEquals(0, field.place(line, 1));

		assertTrue(field.hasLine(1, 1, Side.Left));
		assertTrue(field.hasLine(0, 1, Side.Right));
		
		assertFalse(field.hasLine(1, 1, Side.Right));
		assertFalse(field.hasLine(0, 1, Side.Left));
	}

	@Test
	public void testBox()
	{
		// +-+ +
		// | 
		// + +-+
		// |   |
		// +-+ +
		FieldImpl field = new FieldImpl(2, 2);
		assertEquals(0, field.place(new Line(0, 0, Side.Top), 1));
		assertEquals(0, field.place(new Line(0, 0, Side.Left), 1));

		assertEquals(0, field.place(new Line(0, 1, Side.Left), 1));
		assertEquals(0, field.place(new Line(0, 1, Side.Bottom), 1));
		
		assertEquals(0, field.place(new Line(1, 1, Side.Top), 1));
		assertEquals(0, field.place(new Line(1, 1, Side.Right), 1));
		
		Box b00 = field.getBox(0, 0);
		assertTrue(b00.left);
		assertTrue(b00.top);
		assertFalse(b00.right);
		assertFalse(b00.bottom);
		
		Box b10 = field.getBox(1, 0);
		assertFalse(b10.left);
		assertFalse(b10.top);
		assertFalse(b10.right);
		assertTrue(b10.bottom);
		
		Box b01 = field.getBox(0, 1);
		assertTrue(b01.left);
		assertFalse(b01.top);
		assertFalse(b01.right);
		assertTrue(b01.bottom);
		
		Box b11 = field.getBox(1, 1);
		assertFalse(b11.left);
		assertTrue(b11.top);
		assertTrue(b11.right);
		assertFalse(b11.bottom);
	}

	@Test
	public void testBeforeAfter()
	{
		// +-+ +
		// | 
		// + +-+
		// |   |
		// +-+ +
		FieldImpl field = new FieldImpl(2, 2);
		assertEquals(0, field.place(new Line(0, 0, Side.Top), 1));
		assertEquals(0, field.place(new Line(0, 0, Side.Left), 1));

		assertEquals(0, field.place(new Line(0, 1, Side.Left), 1));
		assertEquals(0, field.place(new Line(0, 1, Side.Bottom), 1));
		
		assertEquals(0, field.place(new Line(1, 1, Side.Top), 1));
		assertEquals(0, field.place(new Line(1, 1, Side.Right), 1));

		Line l1 = new Line(0, 0, Side.Right);
		Box b1 = field.getBefore(l1);
		Box a1 = field.getAfter(l1);
		assertTrue(b1.left && b1.top && !b1.right && !b1.bottom);
		assertTrue(!a1.left && !a1.top && !a1.right && a1.bottom);
		
		Line l2 = new Line(1, 1, Side.Bottom);
		Box b2 = field.getBefore(l2);
		Box a2 = field.getAfter(l2);
		assertTrue(!b2.left && b2.top && b2.right && !b2.bottom);
		assertNull(a2);
	}
	
	@Test
	public void testPlaceSplit()
	{
		// +-+-+
		// | x |
		// +-+-+
		FieldImpl field = new FieldImpl(2, 1);

		assertEquals(0, field.place(new Line(0, 0, Side.Bottom), 1));
		assertEquals(-1, field.place(new Line(0, 0, Side.Bottom), 1));
		assertEquals(0, field.place(new Line(0, 0, Side.Left), 1));
		assertEquals(0, field.place(new Line(0, 0, Side.Top), 1));
		
		assertEquals(0, field.place(new Line(1, 0, Side.Top), 1));
		assertEquals(0, field.place(new Line(1, 0, Side.Right), 1));
		assertEquals(0, field.place(new Line(1, 0, Side.Bottom), 1));
		
		assertEquals(2, field.place(new Line(0, 0, Side.Right), 534));

		assertEquals(534, field.getBoxId(0, 0));
		assertEquals(534, field.getBoxId(1, 0));
	}
	
	@Test
	public void testPlaceHallway()
	{
		// +-+-+-+-+-+-+
		// | x         |
		// +-+-+-+-+-+-+
		FieldImpl field = new FieldImpl(6, 1);
		assertEquals(0, field.place(new Line(0, 0, Side.Left), -1));
		assertEquals(0, field.place(new Line(0, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(1, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(2, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(3, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(4, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(5, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(5, 0, Side.Right), -1));
		assertEquals(0, field.place(new Line(5, 0, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(4, 0, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(3, 0, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(2, 0, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(1, 0, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(0, 0, Side.Bottom), -1));
		
		assertEquals(6, field.place(new Line(0, 0, Side.Right), 534));
	}
	
	@Test
	public void testPlacePath()
	{	
		//	+-+-+-+-+-+
		// | | | 2   |
		//	+1+ +-+-+ +
		// | |       |
		//	+ +-+-+-+-+
		// |   |   3 |
		// +-+-+-+-+-+
		FieldImpl field = new FieldImpl(5, 3);
		assertEquals(0, field.place(new Line(0, 0, Side.Left), -1));
		assertEquals(0, field.place(new Line(0, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(0, 0, Side.Right), -1));
		
		assertEquals(0, field.place(new Line(1, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(1, 0, Side.Right), -1));
		
		assertEquals(0, field.place(new Line(2, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(2, 0, Side.Bottom), -1));

		assertEquals(0, field.place(new Line(3, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(3, 0, Side.Bottom), -1));

		assertEquals(0, field.place(new Line(4, 0, Side.Top), -1));
		assertEquals(0, field.place(new Line(4, 0, Side.Right), -1));
		
		assertEquals(0, field.place(new Line(0, 1, Side.Left), -1));
		assertEquals(0, field.place(new Line(0, 1, Side.Right), -1));
		
		assertEquals(0, field.place(new Line(1, 1, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(2, 1, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(3, 1, Side.Bottom), -1));
		
		assertEquals(0, field.place(new Line(4, 1, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(4, 1, Side.Right), -1));

		assertEquals(0, field.place(new Line(0, 2, Side.Left), -1));
		assertEquals(0, field.place(new Line(0, 2, Side.Bottom), -1));
		
		assertEquals(0, field.place(new Line(1, 2, Side.Right), -1));
		assertEquals(0, field.place(new Line(1, 2, Side.Bottom), -1));
		
		assertEquals(0, field.place(new Line(2, 2, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(3, 2, Side.Bottom), -1));
		
		assertEquals(0, field.place(new Line(4, 2, Side.Bottom), -1));
		assertEquals(0, field.place(new Line(4, 2, Side.Right), -1));

		assertEquals(4, field.place(new Line(0, 0, Side.Bottom), 543));
		
		assertEquals(8, field.place(new Line(2, 0, Side.Right), 543));
		
		assertEquals(3, field.place(new Line(3, 2, Side.Right), 543));
	}
	
	
}
