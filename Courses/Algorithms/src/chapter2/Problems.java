package chapter2;


public class Problems 
{

	public static void main(String[] args)
	{
		System.out.println(maxFibonacciInt());
		System.out.println(maxFibonacciLong());
		
		System.out.println(maxDivisionsEuclid(1, 100));
	}
	
	// Tries all possible combinations for min through max
	// for computing the gcd and computes the maximum number
	// of divisions one gcd caclulation uses.
	public static int maxDivisionsEuclid(int min, int max)
	{
		int maxDivisions = 0;
		int divisions;
		for (int m = min; m <= max; m++)
		{
			for (int n = m + 1; n <= max; n++)
			{
				divisions = getGcdDivisions(m, n);
				if (divisions > maxDivisions) 
					maxDivisions = divisions;
			}
		}
		return maxDivisions;
	}

	// Where n > m
	public static int getGcdDivisions(int m, int n)
	{
		int divisions = 0;
		while (m != 0)
		{
			m = n % (n = m);
			divisions++;
		}
		return divisions;
	}
	
	// This computes the nth Fibonacci number iteratively
	// and uses O(1) space.
	public static int fib(int n)
	{
		int a = 0;
		int b = 1;
		int fib = 0;

		if (n == 0 || n == 1) 
			return n;

		for(int i = 2; i <= n; i++)
		{
			fib = b + a;
			a = b;
			b = fib;
		}

		return fib;
	}

	// Returns the n at which the n'th fibonacci
	// number is the first in the sequence that
	// cannot fit in the memory allocated for an int.
	public static int maxFibonacciInt()
	{
		int a = 0;
		int b = 1;
		int fib = 0;
		int n = 2;
		
		while (true)
		{
			fib = b + a;
			n++;
			// fib should always be larger then b
			// unless fib overflowed
			if (fib < b) break;
			a = b;
			b = fib;
		}
		
		return n;
	}
	

	// Returns the n at which the n'th fibonacci
	// number is the first in the sequence that
	// cannot fit in the memory allocated for a long.
	public static int maxFibonacciLong()
	{
		long a = 0;
		long b = 1;
		long fib = 0;
		int n = 2;

		while (true)
		{
			fib = b + a;
			n++;
			// fib should always be larger then b
			// unless fib overflowed
			if (fib < b) break;
			a = b;
			b = fib;
		}
		return n;
	}

	
}
