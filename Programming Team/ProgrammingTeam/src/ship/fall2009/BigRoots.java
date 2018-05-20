package ship.fall2009;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Solve k = n-root(p) aka [k ^ n = p] where n and p are given and k must be found.
 * 1 <= n <= 200
 * 1 <= p <= 10^101
 * 1 <= k <= 2^31
 * 
 * Strategy:
 * 	If n is 1 then return p.
 * 	If n is 2 and p has a perfect square then return sqrt(p).
 * 	If n > 2
 * 		Start m at 1
 * 		while trying to find value...
 * 			If m^n == p, Return m
 * 			If	m^n > p, No Solution!
 * 			If m^n < p, Increment m
 * 
 * @author Philip Diffenderfer
 *
 */
public class BigRoots
{
	/* INPUT
2
16
3
27
7
4357186184021382204544
7
4357186184021382204545
0

	 */
	public static void main(String[] args) {
		new BigRoots();
	}
	
	BigRoots() {
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		int cases = 1;
		while (n != 0) {
			// Take the entire number as a string and parse it into a BigInteger
			// since it can be <= 10^101
			BigInteger p = new BigInteger(in.next());
			
			// Start off with no solution
			int k = -1;
			
			// Where (n = 1) k^1 = p for any k.
			if (n == 1) {
				k = p.intValue();
			}
			// Where (n = 2) k^2 = sqrt(p) for any k.
			else if (n == 2) {
				// Get the floating point sqrt
				double sqrt = Math.sqrt(p.doubleValue());
				// Get the integer part
				int floor = (int)Math.floor(sqrt);
				// If the floating and integer parts are equal then k = floor.
				if (equal(sqrt, floor)) {
					k = floor;
				}
			} 
			// Where (n > 2) k^n = p for any k.
			else {
				// Start at 1^n
				int m = 1;
				BigInteger x = BigInteger.valueOf(m).pow(n);
				// Loop until we've passed p or we found an exact match.
				while (true) {
					int cmp = x.compareTo(p); // cmp<0 when x<p, etc.
					// If x and p are equal then m^n = p
					if (cmp == 0) {
						k = m;
						break;
					}
					// If x > p then there is no solution for k^n = p
					else if (cmp > 0) { 
						break;
					}
					// If x < p then we can increase m and keep trying.
					else {
						x = BigInteger.valueOf(++m).pow(n);
					}
				}
			}

			// No solution exists!
			if (k == -1) {
				System.out.format("Case %d: No solution\n\n", cases);
			} else {
				System.out.format("Case %d: %d\n\n", cases, k);
			}
			
			n = in.nextInt();
			cases++;
		}
	}
	
	boolean equal(double a, double b) {
		return Math.abs(a - b) < 0.0000001;
	}
	
}
