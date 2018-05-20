package ship.practice.unfinished;

import java.util.Scanner;


public class Primes
{
	public static void main(String[] args)
	{
		new Primes();
	}
	
	public long[] primes;
	public long[] squares;
	
	public Primes()
	{
		Scanner sc = new Scanner(System.in);
		
		int total = sc.nextInt();
		
		if (total < 3)
			return;
		
		primes = new long[total];
		squares = new long[total];
		
		primes[0] = 2; squares[0] = 4;
		primes[1] = 3; squares[1] = 9;
		
		System.out.println("2\n3");
		
		for (int i = 2; i < total; i++)
		{
			primes[i] = nextPrime(primes[i - 1] + 2);
			squares[i] = primes[i] * primes[i];
			
			System.out.println(primes[i]);
		}

	}
	

	public long nextPrime(long n)
	{
		while (!isPrime(n))
			n += 2;
		
		return n;
	}

	public boolean isPrime(long n)
	{
		int index = 0;
		
		while (n >= squares[index])
		{
			if(n % primes[index] == 0)
				return false;
			index++;
		}
		
		return true;
	}
	
}
