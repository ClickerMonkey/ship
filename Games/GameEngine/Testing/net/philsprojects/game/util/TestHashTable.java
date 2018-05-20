package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import net.philsprojects.game.util.HashTable;
import net.philsprojects.game.util.Iterator;

import org.junit.Test;


public class TestHashTable
{

	@Test
	public void testOverflow()
	{
		HashTable<String> h = new HashTable<String>(3);
		// Add the maximum amount of entries
		assertTrue(h.add("Hello", "World"));
		assertTrue(h.add("Kitty", "Cat"));
		assertTrue(h.add("Donkey", "Punch"));
		// Check the size
		assertEquals(3, h.getSize());
		// Try to add another
		assertFalse(h.add("Banana", "Split"));
		// The size should still be the same
		assertEquals(3, h.getSize());
		// make sure all original 3 are still there
		assertTrue(h.exists("Hello"));
		assertTrue(h.exists("Kitty"));
		assertTrue(h.exists("Donkey"));
	}

	@Test
	public void testExists()
	{
		HashTable<String> h = new HashTable<String>(11);
		// Add 2 entries
		h.add("Hello", "World");
		h.add("Kitty", "Cat");
		// double check size
		assertEquals(2, h.getSize());
		// make sure that they exist
		assertTrue(h.exists("Hello"));
		assertTrue(h.exists("Kitty"));
		// make sure that it returns false if it doesn't exist
		assertFalse(h.exists("Hellp"));
	}

	@Test
	public void testClear()
	{
		HashTable<String> h = new HashTable<String>(3);
		// Add 3 entries
		assertTrue(h.add("Hello", "World"));
		assertTrue(h.add("Kitty", "Cat"));
		assertTrue(h.add("Donkey", "Punch"));
		// Make sure the size is now 3
		assertEquals(3, h.getSize());
		// Clear all entries
		assertTrue(h.clear());
		// Make sure the size is now 0
		assertEquals(0, h.getSize());
		// Make sure none of the entries exist
		assertFalse(h.exists("Hello"));
		assertFalse(h.exists("Kitty"));
		assertFalse(h.exists("Donkey"));
		// Your not able to clear it now
		assertFalse(h.clear());
	}

	@Test
	public void testIsFull()
	{
		HashTable<String> h = new HashTable<String>(3);
		// Double check its not full
		assertFalse(h.isFull());
		// Add 3 entries
		assertTrue(h.add("Hello", "World"));
		assertTrue(h.add("Kitty", "Cat"));
		// Double check its not full
		assertFalse(h.isFull());
		assertTrue(h.add("Donkey", "Punch"));
		// Make sure the size is now 3
		assertEquals(3, h.getSize());
		// Make sure its full
		assertTrue(h.isFull());
	}

	@Test
	public void testGet()
	{
		HashTable<String> h = new HashTable<String>(5);
		// Add 3 entries
		assertTrue(h.add("Hello", "World"));
		assertTrue(h.add("Kitty", "Cat"));
		assertTrue(h.add("Donkey", "Punch"));
		// double check size
		assertEquals(3, h.getSize());
		// test for correct values
		assertEquals("World", h.get("Hello"));
		assertEquals("Cat", h.get("Kitty"));
		assertEquals("Punch", h.get("Donkey"));
		// Make sure that it returns null of not found
		assertNull(h.get("Blah"));
	}

	@Test
	public void testRemove()
	{
		HashTable<String> h = new HashTable<String>(5);
		// Add 3 entries
		assertTrue(h.add("Hello", "World"));
		assertTrue(h.add("Kitty", "Cat"));
		assertTrue(h.add("Donkey", "Punch"));
		// double check size
		assertEquals(3, h.getSize());
		// Remove one, check size, check existence
		assertEquals("World", h.remove("Hello"));
		assertEquals(2, h.getSize());
		assertFalse(h.exists("Hello"));
		// Try to remove non-existent
		assertNull(h.remove("Blah"));
	}

	@Test
	public void testIterator()
	{
		HashTable<String> h = new HashTable<String>(11);
		assertTrue(h.add("Hello", "World"));
		assertTrue(h.add("Donkey", "Punch"));
		assertTrue(h.add("Kitty", "Cat"));
		Iterator<String> i = h.getIterator();
		assertEquals(3, h.getSize());
		String[] words = new String[3];
		int j = 0;
		while (i.hasNext())
			words[j++] = i.getNext();
		i.reset();
		assertEquals(words[0], i.getNext());
		assertEquals(words[1], i.getNext());
		assertEquals(words[2], i.getNext());
		System.out.println(words[0] + ", " + words[1] + ", " + words[2]);
	}

}
