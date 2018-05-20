package ship.fall2009;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Strategy:
 * 	Determine all positive factors of the value and return it as a Set.
 * 	Convert all integers in the set to a string and then add up all the digits.
 * 	The sum of all the digits is used as the next value, add it to a hashset
 * 		to make sure the value is not repeated, stop the loop when the value is
 * 		repeated.
 * 
 * @author Philip Diffenderfer
 *
 */
public class LastTerm
{
	
	/* INPUT
17
31
68
1448
0

	 */
	public static void main(String[] args) {
		new LastTerm();
	}
	
	LastTerm() {
		Scanner in = new Scanner(System.in);
		
		int x = in.nextInt();
		int cases = 1;
		while (x != 0) {
			
			// The set of integers already visited
			HashSet<Integer> visited = new HashSet<Integer>();
			int next = x;
			
			// Avoid repeating the same number
			while (!visited.contains(next)) {
				visited.add(next);
				
				// Get all factors of next
				HashSet<Integer> factors = allFactors(next);
				
				// Build char array of each factors string representation
				StringBuffer sb = new StringBuffer();
				for (Integer f : factors) {
					sb.append(f.toString());
				}
				
				// Add up each digit from the buffer
				next = 0;
				for (int i = 0; i < sb.length(); i++) {
					next += sb.charAt(i) - '0';
				}
			}
			
			// The number of values in visited is how many ddf terms in x.
			System.out.format("Case %d: %d terms\n\n", cases, visited.size());
			
			x = in.nextInt();
			cases++;
		}
	}
	
	/**
	 * Returns a set of integers which make up all the factors of n.
	 */
	HashSet<Integer> allFactors(int n) {
		
		HashSet<Integer> set = new HashSet<Integer>();
		
		// 1 and itself are always factors of n.
		set.add(1);
		set.add(n);

		// Check every number between 2 and half of n as a factor.
		for (int i = 2; i * i <= n; i++) {
			// If n is divisble by i AND i is not already in the set, its a new factor
			if (n % i == 0) {
				set.add(i);
				set.add(n / i);
			}
		}
		
		return set;
	}
	                                 

}
