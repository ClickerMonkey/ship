package ship.practice;

import java.util.Scanner;


public class FourPrimeSummation
{

	private static long[] primes;

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		System.out.print("Check up until x primes: ");
		int max = sc.nextInt();

		System.out.println("Building primes...");
		buildPrimes(max);
		System.out.println("Primes Built.");

		//		// Test which number?
		//		System.out.print("Test which?: ");
		//		int n = sc.nextInt();
		//		
		//		// Find prime less then this
		//		int i = 0;
		//		while (i < primes.length && primes[i] < n)
		//			i++;
		//
		//		for (int i1 = 0; i1 < i; i1++)
		//		{
		//			for (int i2 = 0; i2 < i; i2++)
		//			{
		//				for (int i3 = 0; i3 < i; i3++)
		//				{
		//					for (int i4 = 0; i4 < i; i4++)
		//					{
		//						if (primes[i1] + primes[i2] + primes[i3] + primes[i4] == n)
		//						{
		//							System.out.format("%d %d %d %d\n", primes[i1], primes[i2], primes[i3], primes[i4]);
		//							return;
		//						}
		//					}	
		//				}	
		//			}	
		//		}
		//		
		long maxPrime = primes[max - 1];

		System.out.println("Test Range: 8 => " + maxPrime);

		int[] solution = new int[4];
		for (int i = 8; i < maxPrime; i++)
		{
			findAddends(i, solution);
			if (solution[0] + solution[1] + solution[2] + solution[3] != i)
				System.out.format("wrong! (%d) => %d %d %d %d\n", i, 
						solution[0], solution[1], solution[2], solution[3]);
		}
		System.out.println("done");
	}

	public static void findAddends(int number, int[] result) 
	{ 
		//		result[0] = getLastPrimeUnder(number - 6);
		//		number -= result[0];
		//		result[1] = getLastPrimeUnder(number - 4);
		//		number -= result[1];
		//		result[2] = getLastPrimeUnder(number - 2);
		//		number -= result[2];
		//		result[3] = getLastPrimeUnder(number - 0);
		//		number -= result[3];

		//If number != 0 then this is an impossible solution!

		//				int[] res = new int[4];
		//				for (int i = 4; i >= 1; i--)
		//				{
		int maxNumber, thePrime;

		maxNumber = number - 6;
		thePrime = getLastPrimeUnder(maxNumber + 1);
		result[0] = thePrime;
		number -= thePrime;
		
		maxNumber = number - 4;
		thePrime = getLastPrimeUnder(maxNumber + 1);
		result[1] = thePrime;
		number -= thePrime;
		
		maxNumber = number - 2;
		thePrime = getLastPrimeUnder(maxNumber + 1);
		result[2] = thePrime;
		number -= thePrime;
		
		maxNumber = number;
		thePrime = getLastPrimeUnder(maxNumber + 1);
		result[3] = thePrime;
		number -= thePrime;
		//				}
	//				return res;
	}

	public static int getLastPrimeUnder(int number) 
	{
		for (int i=(number-1); i>1; i--)
			if (isPrime(i))
				return i;
		//		for (number = (number | 1) - 2; number > 1; number -= 2)
		//			if (isPrime(number))
		//				return number;

		return 2;
	}

	public static boolean isPrime(long n)
	{
		int lowest = (int)Math.sqrt(n);
		int i = 0;

		while (i < primes.length && primes[i] <= lowest)
		{
			if (n % primes[i] == 0)
				return false;
			i++;
		}

		return true;
	}

	public static long nextPrime(long n)
	{
		do {
			n += 2;
		} while (!isPrime(n));

		return n;
	}

	public static void buildPrimes(int n)
	{
		primes = new long[n];

		primes[0] = 2;
		primes[1] = 3;

		for (int i = 2; i < n; i++)
			primes[i] = nextPrime(primes[i - 1]);
	}



}
