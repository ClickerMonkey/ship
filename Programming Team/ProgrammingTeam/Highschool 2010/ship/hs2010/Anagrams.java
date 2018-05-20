package ship.hs2010;

import java.util.Scanner;


public class Anagrams
{

	private static final int LETTERS = 26;
	
	public static void main(String[] args) {
		new Anagrams();
	}
	
	public Anagrams() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = Integer.parseInt(input.nextLine());
	
		while (--caseCount >= 0) {
			
			String line = input.nextLine();
			String parts[] = line.split("=");
			String first = parts[0].toLowerCase();
			String second = parts[1].toLowerCase();

			int[] firstCount = getLetterCounts(first);
			int[] secondCount = getLetterCounts(second);
			
			// Count how many letter counts match...
			int matches = 0;
			for (int i = 0; i < LETTERS; i++) {
				if (firstCount[i] == secondCount[i]) {
					matches++;
				}
			}
			
			// Anagram!
			if (matches == LETTERS) {
				System.out.println("match");
			}
			else {
				System.out.println("not a match");
			}
		}
		
	}
	
	private int[] getLetterCounts(String phrase) {
		int[] counts = new int[LETTERS];
		for (int i = 0; i < phrase.length(); i++) {
			char letter = phrase.charAt(i);
			if (letter >= 'a' && letter <= 'z') {
				int index = letter - 'a';
				counts[index]++;
			}
		}
		return counts;
	}
	
}
