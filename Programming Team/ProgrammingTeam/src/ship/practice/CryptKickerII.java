package ship.practice;

import java.util.ArrayList;
import java.util.Scanner;

/* INPUT *
2

vtz ud xnm xugm itr pyy jttk gmv xt otgm xt xnm puk ti xnm fprxq
kld lhdsf sdhhe hsd shdhe shdh nwi hmwp nvn
xnm ceuob lrtzv ita hegfd tsmr xnm ypwq ktj
frtjrpgguvj otvxmdxd prm iev prmvx xnmq

vtz ud xnm xugm itr pyy jttk gmv xt otgm xt xnm puk ti xnm fprxq
xnm ceuob lrtzv ita hegfd tsmr xnm ypwq ktj
frtjrpgguvj otvxmdxd prm iev prmvx xnmq


 */

public class CryptKickerII
{

	// Entry point into application.
	public static void main(String[] args) {
		new CryptKickerII();
	}

	// Execute the CryptKicker problem.
	public CryptKickerII()
	{
		Scanner input = new Scanner(System.in);

		int caseCount = Integer.parseInt(input.nextLine());
		input.nextLine(); // Empty Line

		// Handle every case
		for (int X = 0; X < caseCount; X++) {

			// 1 or more lines, terminated by empty line.
			ArrayList<String> lines = new ArrayList<String>();
			String line = null;
			while ((line = input.nextLine()).length() > 0) {
				lines.add(line);
			}

			// Decrypt
			solve(lines);
		}
	}

	// Solves the CryptKickerII problem.
	private void solve(ArrayList<String> lines) 
	{
		// Determine which line is "the quick brown fox jumps over the lazy dog"
		String lock = "the quick brown fox jumps over the lazy dog";
		String key = getKey(lines, lock);
		if (key == null) {
			System.out.println("No Solution.");
			return;
		}

		// Create the mapping between each character and its decryption
		char[] mapping = new char[26];
		for (int i = 0; i < lock.length(); i++) {
			mapping[lock.charAt(i) - 'a'] = key.charAt(i);
		}

		// Translate every line with the decryption mapping
		for (String line : lines) {
			char[] letters = line.toCharArray();
			for (int j = 0; j < letters.length; j++) {
				letters[j] = mapping[letters[j] - 'a'];
			}
			System.out.println(letters);
		}
	}

	// Returns the key from the lines in the paragraph
	private String getKey(ArrayList<String> lines, String lock) 
	{
		for (String line : lines) {
			if (match(line.toCharArray(), lock.toCharArray())) {
				return line;
			}
		}
		return null;
	}

	// Returns true if the given two strings match. Two strings are considered a
	// match if there's some possible way to map one string to another.
	private boolean match(char[] a, char[] b) 
	{
		// First check length
		if (a.length != b.length) {
			return false;
		}

		// For every letter, find what it maps to. aka map[3] corresponds to the
		// letter 'd' in string A mapping to whatever letter is in map[3].
		char[] map = new char[26];

		// For every char in A and B, either map it, or confirm its a match
		for (int i = 0; i < a.length; i++) {
			// The index of a[i] in the alphabet, where a = 0 and z = 25.
			int index = a[i] - 'a';
			// If its a letter...
			if (index >= 0 && index < 26) {
				// Check if this has been mapped yet...
				if (map[index] != 0) {
					// Check that the mapping is correct
					if (a[i] != map[index]) {
						return false;
					}
				}
				// Map it, basically the letter in the alphabet at the given index
				// should be tranlsated to 'b[i]'.
				else {
					map[index] = b[i];
				}
			}
			// If its not compare it normally. This is for characters like 
			// punctuation, white space, numbers, etc.
			else if (a[i] != b[i]) {
				return false;
			}
		}
		
		// They are a match!
		return true;
	}


}
