package ship.pacise09;

import java.util.Scanner;

public class program1
{

	public static void main(String[] args)
	{
		new program1();
	}
	
	public static final int MAX = 1000000;
	public int[] primes;
	public int total;
	
	public program1()
	{
		Scanner sc = new Scanner(System.in);
	
		primes = new int[MAX];
		primes[0] = 2;
		primes[1] = 3;
		total = 2;
		
		// Start at odd
		int start = sc.nextInt();
		// Number of palindromic primes after start
		int n = sc.nextInt();
		
		// Can't generate 0 primes
		if (n < 1)
			return;
		
		// If start is 1, 2, or 3
		if (start < 3)
		{
			if (n >= 1) System.out.println("2");
			if (n >= 2) System.out.println("3");
			n -= 2;
			start = 5;
		}
		
		start = (start | 0x1);
		
		while (n > 0)
		{
			while (!isPalindromic(start))
				start += 2;
			
			System.out.println(start);
				
			start += 2;
			n--;
		}
// 		System.out.println("END");
	}
	
	public boolean isPalindromic(int n)
	{
		String ns = String.valueOf(n);
		
		StringBuilder xs = new StringBuilder(ns.length());
		for (int i = 0; i < ns.length(); i++)
			xs.append(ns.charAt(ns.length() - i - 1));
			
		int x = Integer.parseInt(xs.toString());
		
		return (isPrime(n) && isPrime(x));
	}
	
	// Returns true if the given n is prime
	public boolean isPrime(int n)
	{
		int i = 0;
		while (primes[i] * primes[i] <= n)
		{
			if (n % primes[i] == 0)
				return false;
			
			i++;
			// If we need the next prime then generate it
			if (i >= total)
			{
				primes[total] = nextPrime(primes[total - 1] + 2);
				total++;
			}
		}
		return true;
	}

	// Gets the next prime after n
	public int nextPrime(int n)
	{
		while(!isPrime(n))
			n += 2;
		return n;
	}
	
}