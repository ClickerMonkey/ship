package ship.hs2010;

import java.util.Scanner;


public class IsPrimeNumber
{

	public static void main(String[] args) {
		new IsPrimeNumber();
	}
	
	public IsPrimeNumber() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		
		while (--caseCount >= 0) {
			
			int number = input.nextInt();
			
			if (isPrime(number)) {
				System.out.println("is prime");
			}
			else {
				System.out.println("is not a prime");
			}
		}
	}
	
	// Primality test
	private boolean isPrime(int n) {
		// 1 is not prime
		if (n == 1) {
			return false;
		}
		// 2 is prime
		if (n == 2) {
			return true;
		}
		// Any other even is prime
		if (n % 2 == 0) {
			return false;
		}
		
		// Maximum possible factor for n (inclusive)
		int sqrt = (int)Math.sqrt(n);
		
		// Start at 3 and traverse every odd number until it hits its largest
		// possible factor.
		for (int i = 3; i <= sqrt; i += 2) {
			// If n is divisible by i it's not a prime
			if (n % i == 0) {
				return false;
			}
		}
		
		// It has no factors, prime number!
		return true;
	}
	
}
