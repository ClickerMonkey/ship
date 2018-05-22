package net.philsprojects.game.depreciated;

import static org.junit.Assert.*;
import net.philsprojects.game.deprecated.IRecycle;
import net.philsprojects.game.deprecated.Recycler;
import net.philsprojects.game.util.Timer;
import net.philsprojects.game.util.Vector;

//import org.junit.Test;


public class TestRecycler
{
	public void test()
	{
		Recycler.clear();
		RVector v;
		v = new RVector(2, 3);
		System.out.println(v);
		assertEquals(0, Recycler.getTotalRecycled());
		assertEquals(0, Recycler.getTotalRequested());
		assertEquals(0, Recycler.getTotalThrown());
		Recycler.recycle(v);
		assertEquals(0, Recycler.getTotalRecycled());
		assertEquals(0, Recycler.getTotalRequested());
		assertEquals(1, Recycler.getTotalThrown());
		v = Recycler.<RVector>get(Vector.class, 4f, 6f);
		System.out.println(v);
		assertEquals(1, Recycler.getTotalRecycled());
		assertEquals(1, Recycler.getTotalRequested());
		assertEquals(1, Recycler.getTotalThrown());
		v = (RVector)Recycler.get(Vector.class, 2f, 1f);
		System.out.println(v);
		assertEquals(1, Recycler.getTotalRecycled());
		assertEquals(2, Recycler.getTotalRequested());
		assertEquals(1, Recycler.getTotalThrown());
		Recycler.recycle(v);
		v = (RVector)Recycler.get(Vector.class, 4f, 5f);
		System.out.println(v);
		assertEquals(2, Recycler.getTotalRecycled());
		assertEquals(3, Recycler.getTotalRequested());
		assertEquals(2, Recycler.getTotalThrown());
		Recycler.recycle(v);
		assertEquals(2, Recycler.getTotalRecycled());
		assertEquals(3, Recycler.getTotalRequested());
		assertEquals(3, Recycler.getTotalThrown());
	}

	/**
	 * Create 10-thousand objects with the Recycler and then without.
	 */
	public void testEfficiency()
	{
		Recycler.clear();
		final java.text.DecimalFormat f = new java.text.DecimalFormat("0.000");
		final Timer t = new Timer(1);
		RVector v = null;
		float with = 0f, without = 0f;
		//With
		t.start();
		for (float i = 0f; i < 10000f; i+=1f)
		{
			Recycler.recycle(v);
			v = Recycler.<RVector>get(RVector.class, i * 0.15f, i);
		}
		t.update();
		System.out.println(with = t.getDeltatime());
		for (float i = 0f; i < 10000f; i+=1f)
		{
			v = null;
			v = new RVector(i * 0.15f, i);
		}
		t.update();
		System.out.println(without = t.getDeltatime());
		if (with < without)
			System.out.println("With the Recycler it was " + f.format(without / with * 100.0) + "% faster.");
		else
			System.out.println("Without the Recycler it was " + f.format(with / without * 100.0) + "% faster.");
	}

	private class RVector extends Vector implements IRecycle
	{

		public RVector(float x, float y)
		{
			super(x, y);
		}

		public Object set(Object[] args)
		{
			x = (Float)args[0];
			y = (Float)args[1];
			return this;
		}

	}


}
