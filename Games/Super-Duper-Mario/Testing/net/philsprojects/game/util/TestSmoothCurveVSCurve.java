package net.philsprojects.game.util;

import net.philsprojects.game.util.Curve;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.SmoothCurve;

import org.junit.Test;


public class TestSmoothCurveVSCurve
{
	@Test
	public void printDifferences()
	{
		float[] v = { 0f, 10f, 5f, 25f, 30f };
		float[] t = { 0f, 0.25f, 0.5f, 0.75f, 1.0f };
		SmoothCurve s = new SmoothCurve(1.0f, v);
		Curve c = new Curve(97, t, v);
		// Size in memory
		int smoothSize = s.getValues().length * 4 + 8;
		int normalSize = c.getDepth() * 4 + 8;
		System.out.println("Smooth Curve Size: " + smoothSize + " bytes");
		System.out.println("Normal Curve Size: " + normalSize + " bytes");
		System.out.println("Smooth Curve getValue Memory Allocation: 8 bytes");
		System.out.println("Normal Curve getValue Memory Allocation: 0 bytes");
		System.out.println("Smooth Curve getValue Operations: 13");
		System.out.println("Normal Curve getValue Operations: 6");
	}

	int runTimes = 0;

	@Test
	public void testAccuracyAndTime()
	{
		float[] v = { 0f, 10f, 5f, 25f, 30f };
		float[] t = { 0f, 0.25f, 0.5f, 0.75f, 1.0f };
		long smooth = 0L, normal = 0L, start = 0L, end = 0L;

		SmoothCurve s = new SmoothCurve(1f, v);
		Curve c = new Curve(97, t, v);
		// Accuracy (10 Samples from each Curve)
		// 0, .1, .2, .25, .4, .5, .6, .75, .9, 1
		// 0, 4, 8, 10, 7, 5, 13, 25, 28, 30
		float[] accurTimes = { 0f, 0.1f, 0.2f, 0.25f, 0.4f, 0.5f, 0.6f, 0.75f, 0.9f, 1.0f };
		float[] accurValue = { 0f, 4f, 8f, 10f, 7f, 5f, 13f, 25f, 28f, 30f };
		double smoothAccur = 0.0;
		double normalAccur = 0.0;
		for (int i = 0; i < 10; i++)
		{
			smoothAccur += (1.0 - Math.abs(accurValue[i] - s.getValue(accurTimes[i]))) * 100;
			normalAccur += (1.0 - Math.abs(accurValue[i] - c.getValue(accurTimes[i]))) * 100;
		}
		System.out.println("Smooth Curve Accuracy: " + (smoothAccur / 10.0) + "%");
		System.out.println("Normal Curve Accuracy: " + (normalAccur / 10.0) + "%");
		// SmoothCurve Timing
		start = System.nanoTime();
		for (int i = 0; i < 100; i++)
		{
			s.getValue(i / 100f);
		}
		end = System.nanoTime();
		smooth = (end - start);
		System.out.println("Smooth Curve Time: " + smooth + "ns");
		// Curve Timing
		start = System.nanoTime();
		for (int i = 0; i < 100; i++)
		{
			c.getValue(i / 100f);
		}
		end = System.nanoTime();
		normal = (end - start);
		System.out.println("Normal Curve Time: " + normal + "ns");
		// WINNER?
		if (smooth < normal)
		{
			System.out.println("The Smooth Curve is faster by " + (double)normal / (double)smooth + " times.");
		}
		else
		{
			System.out.println("The Normal Curve is faster by " + (double)smooth / (double)normal + " times.");
		}
		runTimes++;
		if (runTimes <= 10)
			testAccuracyAndTime();
	}

}
