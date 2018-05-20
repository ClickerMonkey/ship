package ship.weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Solves the Scrabble (2-1) problem. This algorithm solves it in O(2^m * logn)
 * where m is the number of characters in the tray and n is the number of
 * characters in the dictionary.
 * 
 * This solves the problem by building word groups (set of all words with same
 * length), hashing their words, then sorts them by hashing. Then a tray is
 * broken up into all subsets and for each subset its hashed to a value then
 * searched for in the appropriate word group.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Scrabble
{
	public static void main(String[] args) {
		new Scrabble();
	}

	// Counts of each letter in the bag [a-z], +1
	// Y = {10,3,3,5,13,3,4,3,10,2,2,5,3,7,9,3,2,7,5,7,5,3,3,2,3,2}
	// LETTER_MULT[0] = 1
	// LETTER_MULT[i] = LETTER_MULT[i-1] * Y[i-1]
	private static final long[] LETTER_MULTIPLIER = {
		1, 10, 30, 90, 450, 5850, 17550, 70200, 210600,				//A,B,C,D,E,F,G,H,I
		2106000, 4212000, 8424000, 42120000, 126360000, 			//J,K,L,M,N
		884520000L, 7960680000L, 23882040000L, 47764080000L,		//O,P,Q,R
		334348560000L, 1671742800000L, 11702199600000L,				//S,T,U
		58510998000000L, 175532994000000L, 526598982000000L,		//V,W,X
		1053197960000000L, 3159593890000000L							//Y,Z
	};
	
	// The value of each letter [a-z]
	private static final int[] LETTER_VALUE = {
		1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
	};

	// Number of letters in the alphabet of course.
	private static final int ALPHABET = 26;

	
	// The groups for each word length
	WordGroup groups[] = new WordGroup[13];

	/**
	 * Initializes and solves Scrabble problem.
	 */
	Scrabble() 
	{
		Scanner in = new Scanner(System.in);

		in.nextInt();	//Skip word count
		int groupCount = in.nextInt();

//		long startTime = System.nanoTime();
		// Read in every group that follows.
		while (--groupCount >= 0) {
			int groupSize = in.nextInt();
			int wordLength = in.nextInt();

			// Create the word group
			groups[wordLength] = new WordGroup(groupSize, wordLength);

			// Place the word in the group, order doesn't matter here.
			while (--groupSize >= 0) {
				groups[wordLength].words[groupSize] = new Word(in.next().toCharArray());
			}

			groups[wordLength].order();
		}
//		long endTime = System.nanoTime();
//		System.out.format("Loaded groups in: %dns\n", endTime - startTime);

		// Read in each tray
		int trayCount = in.nextInt();
		for (int i = 1; i <= trayCount; i++) 
		{
			char[] tray = in.next().toCharArray();
			ArrayList<WordResult> words = new ArrayList<WordResult>();

			// For every subset of the given tray...
			Subset set = new Subset(tray);
			while (set.hasNext()) {
				set.next();
				searchSubset(set, words);
			}

			// Sort returned words based on value, word length, then word letters
			Collections.sort(words);

			// Print out the tray given.
			System.out.format("Tray%d [%s]:\n", i, new String(tray));
			// Print out the sorted word results.
			for (WordResult result : words) {
				System.out.format("%s = %d\n", new String(result.word), result.value);
			}
		}
	}

	/**
	 * Returns the hash value for the given word. 
	 */
	private long hashWord(char[] word) 
	{
		int[] counts = new int[ALPHABET];
		for (char c : word) {
			counts[c - 'a']++;
		}
		
		long hash = 0;
		for (int i = 0; i < ALPHABET; i++) {
			hash += counts[i] * LETTER_MULTIPLIER[i];
		}
		return hash;
	}
	
	/**
	 * Returns the point value of the given word.
	 */
	private int wordValue(char[] word) 
	{
		int value = 0;
		for (char c : word) {
			value += LETTER_VALUE[c - 'a'];
		}
		return value;
	}

	/**
	 * Given a subset of tray characters this will search in the appropriate
	 * word group and return any words that match (are anagrams).
	 */
	private void searchSubset(Subset set, ArrayList<WordResult> words) 
	{
		// Get appropriate group
		WordGroup group = groups[set.size()];
		if (group == null) {
			return;
		}
		// Compute hash of current subset.
		long hash = set.hash();
		// Get the index of the first value in the group with this hash
		int start = group.indexOf(hash);
		if (start == -1) {
			return;
		}
		// For every word in the group that has the hash add it to the list
		int wordCount = group.words.length;
		while (start < wordCount && group.words[start].hash == hash) {
			words.add(new WordResult(group.words[start].chars));
			start++;
		}
	}

	/**
	 * A single word and its hash value.
	 */
	class Word implements Comparable<Word> 
	{
		final char[] chars;
		final long hash;
		Word(char[] word) {
			chars = word;
			hash = hashWord(word);
		}
		// Sort word by hash
		public int compareTo(Word w) {
			return Long.signum(hash - w.hash);
		}
	}

	/**
	 * A plausible word given a tray and its value.
	 */
	class WordResult implements Comparable<WordResult>
	{
		final char[] word;
		final int value;
		/**
		 * Initializes a new word result given its base word.
		 */
		WordResult(char[] word) {
			this.word = word;
			this.value = wordValue(word);
		}
		// Sort result by points then word
		public int compareTo(WordResult w) {
			// Compare value first
			int d = w.value - value;
			if (d != 0) {
				return d;
			}
			int same = Math.min(word.length, w.word.length);
			// Compare each letter (if value and word are equal)
			for (int i = 0; i < same; i++) {
				d = word[i] - w.word[i];
				if (d != 0) {
					return d;
				}
			}
			return 0;
		}
	}

	
	/**
	 * A group of same length words sorted by their hash values.
	 */
	class WordGroup 
	{
		final Word[] words;
		final int size;
		/**
		 * Initializes a new word group.
		 */
		WordGroup(int wordCount, int wordLength) 
		{
			words = new Word[wordCount];
			size = wordLength;
		}
		/**
		 * Hashes each word and sorts them based on the hash value
		 */
		public void order() 
		{
			Arrays.sort(words);
		}
		/**
		 * Returns the index of the first occurrence of the given hash. 
		 */
		public int indexOf(long key) 
		{
			// Binary search
			int min = 0;
			int max = words.length - 1;
			while (min <= max) {
				int mid = ((max + min) >> 1);
				if (words[mid].hash == key) {
					while (mid > 0 && words[mid - 1].hash == key) {
						mid--;
					}
					return mid;
				}
				if (key > words[mid].hash) {
					min = mid + 1;
				}
				else {
					max = mid - 1;
				}
			}
			return -1;
		}
	}

	/**
	 * Given a set of characters iterate through all subsets with a size > 1.
	 */
	class Subset 
	{
		final int[] max;
		final int[] subset;
		final char[] base;
		final int setSize;
		int subsetSize;
		boolean isReset;
		/**
		 * Initializes a new subset given the base word.
		 */
		Subset(char[] wordBase) {
			base = wordBase;
			setSize = wordBase.length;
			max = new int[setSize];
			subset = new int[setSize];
			subsetSize = setSize;
			reset();
		}
		/**
		 * Resets the subset based on the current subset size.
		 */
		void reset() {
			int padding = setSize - subsetSize;
			for (int i = 0; i < subsetSize; i++) {
				subset[i] = i;
				max[i] = i + padding;
			}
			isReset = true;
		}
		/**
		 * Returns true if a subset exist with a size > 1 that has not been hit.
		 */
		boolean hasNext() {
			return (subsetSize != 2 || subset[0] != max[0]);
		}
		/**
		 * Proceeds to the next subset. 
		 */
		void next() {
			if (isReset) {
				isReset = false;
				return;
			}
			// Index of the first not-maxed-out value.
			int k = subsetSize - 1;
			while (k >= 0 && subset[k] == max[k]) {
				k--;
			}
			// Current subset size exhausted, decrement and reset.
			if (k < 0) {
				subsetSize--;
				reset();
				isReset = false;
				return;
			}
			// From the first maxed out reset the subset.
			int j = subset[k];
			while (k < subsetSize) {
				subset[k++] = ++j;
			}
		}
		/**
		 * Return the length of the current word in the subset.
		 */
		int size() {
			return subsetSize;
		}
		/**
		 * Return the hash of the word represented by the subset
		 */
		long hash() {
			return hashWord(word());
		}
		/**
		 * Return the current word represented by the subset
		 */
		char[] word() {
			char[] word = new char[subsetSize];
			for (int i = 0; i < subsetSize; i++) {
				word[i] = base[subset[i]];
			}
			return word;
		}
	}

}
