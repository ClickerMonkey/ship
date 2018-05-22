package net.philsprojects.game.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;

import org.junit.Test;



public class TestLinkedList
{

	@Test
	public void testAdd()
	{
		LinkedList<String> l = new LinkedList<String>();
		l.add("Hello");
		l.add("World");
		assertEquals(2, l.getSize());
		assertEquals("Hello", l.getFirst());
		assertEquals("World", l.getLast());
	}

	@Test
	public void testGet()
	{
		LinkedList<String> l = new LinkedList<String>();
		l.add("Hello");
		l.add("how");
		l.add("are");
		l.add("you");
		l.add("today?");
		assertEquals(5, l.getSize());
		assertEquals("Hello", l.get(0));
		assertEquals("how", l.get(1));
		assertEquals("are", l.get(2));
		assertEquals("you", l.get(3));
		assertEquals("today?", l.get(4));
	}

	@Test
	public void testRemove()
	{
		LinkedList<String> l = new LinkedList<String>();
		// Add words
		l.add("Hello");
		l.add("how");
		l.add("are");
		l.add("you");
		l.add("today?");
		// remove 'are'
		l.remove(2);
		// double check new size
		assertEquals(4, l.getSize());
		// check all words
		assertEquals("Hello", l.get(0));
		assertEquals("how", l.get(1));
		assertEquals("you", l.get(2));
		assertEquals("today?", l.get(3));
		assertNull(l.get(4));
		assertNull(l.get(-1));
	}

	@Test
	public void testIterator()
	{
		LinkedList<String> l = new LinkedList<String>();
		// Add words
		l.add("Hello");
		l.add(" how");
		l.add(" are");
		l.add(" you");
		l.add(" today?");
		Iterator<String> i = l.getIterator();
		String sentence = "";
		while (i.hasNext())
			sentence += i.getNext();
		assertEquals("Hello how are you today?", sentence);
		assertFalse(i.hasNext());
		//Reset it
		i.reset();
		sentence = "";
		while (i.hasNext())
			sentence += i.getNext();
		assertEquals("Hello how are you today?", sentence);
		assertFalse(i.hasNext());
	}


	@Test
	public void testGetNode()
	{
		LinkedList<String> l = new LinkedList<String>();
		// Add words
		l.add("Hello");
		l.add(" how");
		l.add(" are");
		l.add(" you");
		l.add(" today?");
		assertNull(l.getFirstNode()._previous);
		assertEquals("Hello", l.getFirstNode()._element);
		assertEquals(" how", l.getFirstNode()._next._element);
		assertEquals(" are", l.getFirstNode()._next._next._element);
		assertEquals(" you", l.getLastNode()._previous._element);
		assertEquals(" today?", l.getLastNode()._element);
		assertNull(l.getLastNode()._next);
	}

	@Test
	public void testNodeRemove()
	{
		LinkedList<String> l = new LinkedList<String>();
		// Add words
		l.add("Hello");
		l.add(" how");
		l.add(" are");
		l.add(" you");
		l.add(" today?");
		//                                 Hello -> how -> are
		LinkedList<String>.Node n = l.getFirstNode()._next._next;
		assertEquals(" are", n._element);
		l.remove(n);
		//Iterate over remaining
		Iterator<String> i = l.getIterator();
		String sentence = "";
		while (i.hasNext())
			sentence += i.getNext();
		assertEquals("Hello how you today?", sentence);
	}

	@Test
	public void testNodeInsertAfter()
	{
		LinkedList<String> l = new LinkedList<String>();
		// Add words
		l.add("Hello");
		l.add(" how");
		l.add(" you");
		l.add(" today?");
		//                                  Hello -> how
		LinkedList<String>.Node n = l.getFirstNode()._next;
		assertEquals(" how", n._element);
		l.insertAfter(n, " are");
		//Iterate over remaining
		Iterator<String> i = l.getIterator();
		String sentence = "";
		while (i.hasNext())
			sentence += i.getNext();
		assertEquals("Hello how are you today?", sentence);
	}

	@Test
	public void testNodeInsertBefore()
	{
		LinkedList<String> l = new LinkedList<String>();
		// Add words
		l.add("Hello");
		l.add(" how");
		l.add(" you");
		l.add(" today?");
		//                                  Hello -> how -> you
		LinkedList<String>.Node n = l.getFirstNode()._next._next;
		assertEquals(" you", n._element);
		l.insertBefore(n, " are");
		//Iterate over remaining
		Iterator<String> i = l.getIterator();
		String sentence = "";
		while (i.hasNext())
			sentence += i.getNext();
		assertEquals("Hello how are you today?", sentence);
	}

}
