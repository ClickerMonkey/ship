package ship.fall2009;

import java.util.Scanner;


public class RomanPalindromes
{

	/* INPUT
VI
MCM
MCMLXXIV

	 */
	public static void main(String[] args) {
		new RomanPalindromes();
	}
	
	
	RomanPalindromes() {
		Scanner in = new Scanner(System.in);
		
		int cases = 1;
		// While the input file has lines remaining...
		while (in.hasNextLine()) {
			char[] line = in.nextLine().toCharArray();
			
			int length = line.length;
			
			// This adjusts the palindrome if the input is odd
			int newlength = (length << 1) - (length & 1);

			// Create and fill the palindrome
			char[] roman = new char[newlength];
			for (int i = 0; i < length; i++) {
				roman[i] = roman[newlength - i - 1] = line[length - i - 1];
			}
			
			// With the decimal values get the sum of the pairs of all the values
			int total = total(roman);
			
			System.out.format("Case %d: total = %d\n\n", cases, total);
			
			cases++;
		}
	}
	
	/**
	 * Computes the sum between each successive pair of roman numerals.
	 */
	int total(char[] roman) {
		// A single roman numeral
		if (roman.length == 1)
			return getDecimal(roman[0]);
		
		int total = 0;
		int a = getDecimal(roman[0]);
		for (int i = 1; i < roman.length; i++) {
			int b = getDecimal(roman[i]);
			if (a < b) {			// A<B: B-A
				total += (b - a);
			} else {					// A>=B: B+A
				total += (b + a);
			}
			a = b;
		}
		return total;
	}
	
	/**
	 * Returns the decimal value for the given roman numeral.
	 */
	int getDecimal(char roman) {
		if (roman == 'I')	return 1;
		if (roman == 'V')	return 5;
		if (roman == 'X')	return 10;
		if (roman == 'L')	return 50;
		if (roman == 'C')	return 100;
		if (roman == 'D')	return 500;
		if (roman == 'M')	return 1000;
		return 0;
	}
	
}
