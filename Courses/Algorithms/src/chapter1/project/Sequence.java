package chapter1.project;

/**
 * A sequence stores a tally of how many times each letter occurs in a String.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Sequence
{

	/**
	 * Determines whether any Sequence will be able to be hashed based on the
	 * highest possible occurrences per letter.
	 * 
	 * @param hashKeys => The hash keys array to check.
	 */
	public static boolean canUseHashKeys(int[] hashKeys)
	{
		long total = 0;
		long prevTotal = 0;
		for (int i = 0; i < LETTERS_IN_ALPHABET; i++)
		{
			if (hashKeys[i] == 0)
				continue;
			
			total += ((prevTotal = total) + 1) * hashKeys[i];
			
			// Check for overflow
			if (prevTotal > total)
				return false;
		}
		// No overflow, we can use this method!
		return true;
	}
	
	/**
	 * The total number of letters in the alphabet, duh! 
	 */
	public static final int LETTERS_IN_ALPHABET = 26;
	

	// The original string.
	private String originalString;
	// An array for each letter where the number of times the letter
	// occurs in a given string is tallied.
	private int sequence[]; 
	// The total number of letters in this Sequence.
	private int totalLetters = 0;
	// The computed hash. Any sequence that is an anagram to this one
	// has the exact same hashed value.
	private long anagramHash = 0;
	
	/**
	 * Initializes a Sequence with a String. Each occurence of a character
	 * in the string is tallied up in an array. Any non-letters in the string
	 * are ignored.<br>
	 * <br>
	 * Executes in O(n) where:
	 * 		n = length of the string.
	 */
	public Sequence(String s)
	{
		originalString = s;
		
		sequence = new int[LETTERS_IN_ALPHABET];
		// Send it to a lower case char array
		char[] c = s.toLowerCase().toCharArray();
		
		// Go through and only look at letters tallying how many
		// times each letter occurs in the anagram
		for (int i = 0; i < c.length; i++)
		{
			if (Character.isLetter(c[i]))
			{
				sequence[c[i] - 'a']++;
				totalLetters++;
			}
		}
	}
	 
	/**
	 * Given an array used for hashing this will correct
	 * the hash key by making sure that all tallies in the
	 * hash key are greater then or equal to the tallies in
	 * this sequence for each letter.
	 * 
	 * @param hashKeys => The hash keys to correct.
	 */
	public void correctHashKeys(int[] hashKeys)
	{
		for (int i = 0; i < LETTERS_IN_ALPHABET; i++)
			hashKeys[i] = Math.max(hashKeys[i], sequence[i]);
	}
	
	/**
	 * Computes the hash used for quicker checking.<br>
	 * <br>
	 * Executes in O(1)
	 * 
	 * @param hashKeys => An array that keeps track of the 
	 * 		maximum occurences of each letter in each sequence.
	 */
	public void computeHash(int[] hashKeys)
	{
		anagramHash = 0;
		long total = 0;

		for (int i = 0; i < LETTERS_IN_ALPHABET; i++)
		{
			if (hashKeys[i] == 0)
				continue;
			
			anagramHash += (total + 1) * sequence[i];
			total += (total + 1) * hashKeys[i];
		}
	}
	
	/**
	 * Determines if there is an anagram between these two sequences
	 * by comparing the tally array for the letters.<br>
	 * <br>
	 * Executes in O(1)
	 * 
	 * @param a => The sequence to Compare to.
	 */
	public boolean isAnagram(Sequence a)
	{
		// They must have the same amount of letters
		if (a.getLetters() != getLetters())
			return false;
		
		// If they dont have the same amount for each
		// letter then these are not anagrams.
		for (int i = 0; i < LETTERS_IN_ALPHABET; i++)
			if (a.sequence[i] != sequence[i])
				return false;
		
		return true;
	}
	
	/**
	 * Returns how many letters are in this occurence.
	 */
	public int getLetters()
	{
		return totalLetters;
	}

	/**
	 * Returns the hash for this Sequence. If two given sequences hashes
	 * are the same then they are anagrams.
	 */
	public long getHash()
	{
		return anagramHash;
	}
	
	/**
	 * Returns the original string used to initialize this Sequence.
	 */
	public String getString()
	{
		return originalString;
	}
	
}
