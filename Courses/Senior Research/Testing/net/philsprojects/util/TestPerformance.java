package net.philsprojects.util;

import net.philsprojects.BaseTest;

import org.junit.Test;

public class TestPerformance extends BaseTest 
{

	@Test
	public void testSynchronize()
	{
		final int ITERATIONS = 1000000;
		
		watch.start("Without synchronized... ");
		int j = 0;
		for (int i = 0; i < ITERATIONS; i++) {
			j += (i + 23) * 25;
			j >>= 1;
			j |= (i << 3);
			j &= 0xFFFFFFF;
		}
		watch.stop("%.6f seconds.\n");

		long x = watch.nanos();
		
		final Object lock = new Object();
		
		watch.start("With synchronized... ");
		int k = 0;
		for (int i = 0; i < ITERATIONS; i++) {
			synchronized (lock) {
				k += (i + 23) * 25;
				k >>= 1;
				k |= (i << 3);
				k &= 0xFFFFFFF;	
			}
		}
		watch.stop("%.6f seconds.\n");
		
		long y = watch.nanos();
		
		double overhead = ((y - x) / (double)ITERATIONS);
		
		System.out.format("synchronized overhead: %.3f ns\n", overhead);
	}
	


	@Test
	public void testSynchronizeString()
	{
		final int ITERATIONS = 1000000;
		
		watch.start("Without synchronized... ");
		String j = "";
		for (int i = 0; i < ITERATIONS; i++) {
			j += i;
			if (i % 20 == 19) {
				j = "";
			}
		}
		watch.stop("%.6f seconds.\n");

		long x = watch.nanos();
		
		final Object lock = new Object();
		
		watch.start("With synchronized... ");
		String k = "";
		for (int i = 0; i < ITERATIONS; i++) {
			synchronized (lock) {
				k += i;
				if (i % 20 == 19) {
					k = "";
				}
			}
		}
		watch.stop("%.6f seconds.\n");
		
		long y = watch.nanos();
		
		double overhead = ((y - x) / (double)ITERATIONS);
		
		System.out.format("synchronized overhead: %.3f ns\n", overhead);
	}
	
}
