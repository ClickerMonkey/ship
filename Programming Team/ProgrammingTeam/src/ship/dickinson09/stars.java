package ship.dickinson09;

import java.util.HashMap;
import java.util.Scanner;


public class stars 
{

	public static void main(String[] args)
	{
		new stars();
	}
	
	public HashMap<Integer, Integer> primes = new HashMap<Integer, Integer>();
	public int totalPrimes;
	
	public stars()
	{
		Scanner sc = new Scanner(System.in);
		
		int total = sc.nextInt();
		long stars[] = new long[total];
		
		for (int s = 0; s < total; s++)
			stars[s] = sc.nextInt();
		
		primes.put(0, 2);
		primes.put(1, 3);
		totalPrimes = 2;
		
		long n1 = 2;
		long n2 = 3;
		
		while (true)
		{
			n1 = n2;
			n2 = nextPrime(n2 + 2);
			// Add the prime found to the hash table of primes
			primes.put(totalPrimes++, (int)n2);
			
			if (noCollisions(stars, n1, n2))
				break;
			
			if (n2 > 1000000000)
				break;
		}
		
//		for (int i = 0; i < primes.size(); i++)
//			System.out.println(primes.get(i));

		System.out.println(primes.get(totalPrimes - 2));
		System.out.println(primes.get(totalPrimes - 1));
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
		
		while (index < totalPrimes)
		{
			if(n % primes.get(index) == 0)
				return false;
			else
				index++;
		}
		
		return true;
	}
	
	public boolean noCollisions(long[] stars, long n1, long n2)
	{
		int max = (int)Math.max(n1, n2);
		long table[] = new long[max];
		long s;
		int h1, h2;
		
		for (int i = 0; i < stars.length; i++)
		{
			s = stars[i];
			h1 = (int)(s % n1);
			h2 = (int)(s % n2);
			
			if (table[h1] == 0)
				table[h1] = s;
			else if (table[h2] == 0)
				table[h2] = s;
			else
				return false;
		}
		
		return true;
	}
	
//	public LinkedList<Integer> primes = new LinkedList<Integer>();
//	public int lastPrime;
//	public boolean isPrime(int num)
//	{
//		int index = 0;
//		int newPrime;
//		while (primes.get(index) <= num / primes.get(index)){
//			if(num % primes.get(index) == 0){
//				return false;
//			} else {
//				if(index + 1 > lastPrime){
//					newPrime = primes.get(index) + 2;
//					while(!isPrime(newPrime)){
//						newPrime += 2;
//					}
//					primes.add(newPrime);
//					this.lastPrime++;
//				}
//				index++;
//			}
//		}
//		return true;
//	}
	
}
