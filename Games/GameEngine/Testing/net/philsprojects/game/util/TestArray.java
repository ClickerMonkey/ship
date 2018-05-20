package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import net.philsprojects.game.util.Array;
import net.philsprojects.game.util.Iterator;

import org.junit.Test;



public class TestArray
{

	@Test
	public void testConstructor()
	{
		Array<String> a = new Array<String>(10);
		assertEquals(10, a.getSize());
	}

	@Test
	public void testAccessors()
	{
		Array<String> a = new Array<String>(10);
		assertEquals(10, a.getSize());
		a.set(0, "Hello");
		a.set(1, "how");
		a.set(2, "are");
		a.set(3, "you");
		a.set(4, "today?");
		assertEquals("Hello", a.get(0));
		assertEquals("how", a.get(1));
		assertEquals("are", a.get(2));
		assertEquals("you", a.get(3));
		assertEquals("today?", a.get(4));
	}

	@Test
	public void testReset()
	{
		Array<String> a = new Array<String>(10);
		assertEquals(10, a.getSize());
		a.set(0, "Meow");
		a.reset(5);
		assertEquals(5, a.getSize());
		for (int i = 0; i < 5; i++)
			assertNull(a.get(i));
	}

	@Test
	public void testIterator()
	{
		Array<String> a = new Array<String>(5);
		assertEquals(5, a.getSize());
		a.set(0, "Hello");
		a.set(1, " how");
		a.set(2, " are");
		a.set(3, " you");
		a.set(4, " today?");
		Iterator<String> i = a.getIterator();
		String sentence = "";
		while (i.hasNext())
			sentence += i.getNext();
		assertEquals("Hello how are you today?", sentence);
		assertFalse(i.hasNext());
		//Reset
		i.reset();
		sentence = "";
		while (i.hasNext())
			sentence += i.getNext();
		assertEquals("Hello how are you today?", sentence);
		assertFalse(i.hasNext());
	}

}
