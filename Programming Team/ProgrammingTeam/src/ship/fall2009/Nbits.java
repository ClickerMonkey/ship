package ship.fall2009;

import java.util.Scanner;

/**
 * How many different integers between A and B (inclusive) have exactly N bits
 * of 1 in the two's complement representation?
 * 
 * Strategy:
 * 	For every number between A and B...
 * 		If it has exactly N bits then increment counter by 1.
 * 	Print out how many numbers have exactly N bits.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Nbits
{

	/* INPUT
5 14 2
5 14 3
-1 1 1
0 0 0

	 */
	
	public static void main(String[] args) {
		new Nbits();
	}
	
	public Nbits() {
		Scanner in = new Scanner(System.in);
		
		int start = in.nextInt();
		int end = in.nextInt();
		int bits = in.nextInt();
		int cases = 1;
		
		// While start, end, and bits are all not zero...
		while (!(start == 0 && end == 0 && bits == 0)) {
			
			// Start and end might not be given where start < end
			int first = Math.min(start, end);
			int last = Math.max(start, end);
			int numbers = 0;
			
			// Include the first and last numbers, add 1 for each number with n bits
			while (first <= last) {
				numbers += hasNBits(first++, bits);
			}
			
			// Print case solution
			System.out.format("Case %d: %d numbers\n\n", cases, numbers);
			
			start = in.nextInt();
			end = in.nextInt();
			bits = in.nextInt();
			cases++;
		}
	}
	
	/**
	 * Returns 1 if x has n bits, 0 otherwise. Uses bit-chopping method
	 */
	int hasNBits(long x, int n) {
		while (x > 0) {							// While 1 bits exist in x
			if (((x & 1) == 1) && (--n < 0))	// If the last is a one AND the number of 1's found is greater then expected
				return 0;							// Return 0 since to many 1's were found
			x >>= 1;									// Chop off the last bit
		}
		return ((n == 0) ? 1 : 0);				// Return 1 if exactly n bits were found in x.
	}
	
}
