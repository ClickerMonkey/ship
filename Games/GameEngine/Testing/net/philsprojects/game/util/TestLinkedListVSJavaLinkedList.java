package net.philsprojects.game.util;

import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;
import net.philsprojects.game.util.Timer;

import org.junit.Test;


public class TestLinkedListVSJavaLinkedList
{

	private Timer timer;
	private LinkedList<String> engine;
	private java.util.LinkedList<String> java;
	private float engineTime;
	private float javaTime;


	@Test
	public void testAdding()
	{
		startTesting();	
		startTiming();
		for (int i = 0; i < 100000; i++)
			engine.add("String#" + i);
		takeEngineTime();
		for (int i = 0; i < 100000; i++)
			java.add("String#" + i);
		takeJavaTime();
		printResults("[ ADDING ELEMENTS ]", "adding");
	}

	@Test
	public void testRemoveByIndex()
	{
		startTesting();
		fillLists();
		startTiming();
		for (int i = 0; i < 10000; i++)
			engine.remove((10000 - i) / 2);
		takeEngineTime();
		for (int i = 0; i < 10000; i++)
			java.remove((10000 - i) / 2);
		takeJavaTime();
		printResults("[ REMOVING ELEMENTS BY INDEX ]", "removing by index");
	}

	@Test
	public void testRemove()
	{
		startTesting();
		fillLists();	
		startTiming();

		LinkedList<String>.Node current = engine.getFirstNode(), next = null;
		int i = 0;
		while (current != null)
		{
			next = current._next;
			if (i % 25 == 0)
				engine.remove(current);
			i++;
			current = next;
		}

		takeEngineTime();

		for (i = java.size() - 1; i >= 0; i--)
			if (i % 25 == 0)
				java.remove(i);

		takeJavaTime();
		printResults("[ REMOVING ELEMENTS BY NODE AND INDEX ]", "removing by node");
	}

	@Test
	public void testGetByIndex()
	{
		startTesting();
		fillLists();
		startTiming();
		for (int i = 0; i < 10000; i++)
			engine.get((10000 - i) / 2);
		takeEngineTime();
		for (int i = 0; i < 10000; i++)
			java.get((10000 - i) / 2);
		takeJavaTime();
		printResults("[ GETTING ELEMENTS BY INDEX ]", "getting by index");
	}

	@Test
	public void testSetByIndex()
	{
		startTesting();
		fillLists();
		startTiming();
		for (int i = 0; i < 5000; i++)
			engine.set(i, "StringNew");
		takeEngineTime();
		for (int i = 0; i < 5000; i++)
			java.set(i, "StringNew");
		takeJavaTime();
		printResults("[ SETTING ELEMENTS BY INDEX ]", "setting by index");
	}

	@Test
	public void testClear()
	{
		startTesting();
		fillLists();
		startTiming();
		engine.clear();
		takeEngineTime();
		java.clear();
		takeJavaTime();
		printResults("[ CLEARING LIST ]", "clearing");
	}

	@Test
	public void testRemoveFirst()
	{
		startTesting();
		fillLists();
		startTiming();
		for (int i = 0; i < 100000; i++)
			engine.removeFirst();
		takeEngineTime();
		for (int i = 0; i < 100000; i++)
			java.removeFirst();
		takeJavaTime();
		printResults("[ REMOVE FIRST ]", "removing the first element");
	}

	@Test
	public void testRemoveLast()
	{
		startTesting();
		fillLists();
		startTiming();
		for (int i = 0; i < 100000; i++)
			engine.removeLast();
		takeEngineTime();
		for (int i = 0; i < 100000; i++)
			java.removeLast();
		takeJavaTime();
		printResults("[ REMOVE LAST ]", "removing the last element");
	}

	@Test
	public void testIterator()
	{
		startTesting();
		fillLists();
		startTiming();

		Iterator<String> engineIter = engine.getIterator();
		while (engineIter.hasNext())
		{
			engineIter.getNext();
		}

		takeEngineTime();

		java.util.Iterator<String> javaIter = java.iterator();
		while (javaIter.hasNext())
		{
			javaIter.next();
		}

		takeJavaTime();
		printResults("[ ITERATOR ]", "iterating");
	}




	public void printResults(String section, String test)
	{
		System.out.println(section);
		System.out.format("engine.util.LinkedList: %.9f secs\n", engineTime);
		System.out.format("java.util.LinkedList: %.9f secs\n", javaTime);
		if (engineTime < javaTime)
			System.out.format("The engine.util.LinkedList is faster %s by %.6f times.\n", test, javaTime / engineTime);
		else
			System.out.format("The java.util.LinkedList is faster %s by %.6f times.\n", test, engineTime / javaTime);
	}

	public void startTesting()
	{
		timer = new Timer(1);
		engine = new LinkedList<String>();
		java = new java.util.LinkedList<String>();
		javaTime = 0;
		engineTime = 0;
	}

	public void fillLists()
	{
		for (int i = 0; i < 100000; i++)
			engine.add("String#" + i);
		for (int i = 0; i < 100000; i++)
			java.add("String#" + i);
	}

	public void startTiming()
	{
		timer.start();
	}

	public void takeEngineTime()
	{
		timer.update();
		engineTime = timer.getDeltatime();
		timer.update();
	}

	public void takeJavaTime()
	{
		timer.update();
		javaTime = timer.getDeltatime();
		timer.update();
	}


}
