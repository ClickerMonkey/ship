package chapter1.project;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Problem:
 * <p> Write a program that will read a list of words from standard input, 
 * determine if it is an anagram to another word in the input list, if it is, 
 * display it. After all words in the input have been processed, display 
 * a total count of the anagrams found so far, and display the longest 
 * anagram found. If there is a tie, display the last one in the input.</p>
 * 
 * Solution:
 * <p>For each line of input create a sequence class which simply tallies the 
 * number of times each letter occurs in a string. This makes checking if two
 * strings are anagrams to O(1) based on the length of the strings. There are
 * three methods for finding anagrams in a list:<br>
 * <br>
 * <b>#1</b> If the sequences can be hashed then hash them, add them to a 
 * hashtable where they are hashed again based on the size of the table. And
 * then for each Sequence check the number of occurrences in that hashtable
 * to determine if it has an anagram. <br>
 * <br>
 * <b>#2</b> If the sequences can be hashed then hash them, build a tree of
 * sequences based on the computed hashes, and then for each Sequence check
 * if there is another in the tree that has the same hash (Sequences with the
 * same hash are anagrams). <br>
 * <br>
 * <b>#3</b> If the sequences cannot be hashed then for each sequence check
 * if it equals any other sequence in the list.<br>
 * <br>
 * The first algorithm is usually solved in O(n) based on the complexity of
 * the sequences and the max capacity of the hash table. The second algorithm
 * is usually solved in O(nlogn) and in worst case O(n^2). The third algorithm
 * will always be O(n^2). The size of the sequences has no affect on comparing
 * if two sequences are an anagram.
 * </p>
 * 
 * @author Philip Diffenderfer
 *
 */
public class Anagrams
{

	/**
	 * The desired solving method for the problem.
	 */
	private enum SolvingMethod
	{
		// The quickest algorithm, but for most affectiveness
		// it takes up the most memory. Average: O(n)
		Hashtable,
		// The second quickest algorithm, it is the second
		// quickest and doesnt take up as much memory as
		// the hashtable method. Average: O(nlogn)
		BinaryTree,
		// The slowest but uses the least memory. Executes in O(n^2)
		BruteForce
	}
	
	/**
	 * Get the source for input and create the solver class. Run the desired algorithm.
	 */
	public static void main(String[] args) 
	{
		try 
		{
			String path = new File(".").getCanonicalPath();
			String fullpath = path + "/src/chapter1/project/BigInput.txt";
			File input = new File(fullpath);
			
			Anagrams a = new Anagrams();
			a.getInput(new Scanner(input));
			a.solve(SolvingMethod.Hashtable);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// A linked list of the sequences. A linked list is used
	// because adding and traversing is O(1).
	private LinkedList<Sequence> sequences;
	// An array that keeps track of the maximum occurences
	// of each letter in each sequence.
	private int hashKeys[];
	
	/**
	 * Gets the input from the source by line.
	 */
	public void getInput(Scanner sc)
	{
		sequences = new LinkedList<Sequence>();
		hashKeys = new int[Sequence.LETTERS_IN_ALPHABET];
		
		// Grab each line and turn it into a sequence. Compute the max 
		// amount of times a letter has re-occured.
		while (sc.hasNextLine())
		{
			Sequence s = new Sequence(sc.nextLine());
			// Make sure the keys used for hashing each sequence is updated
			// with maximum occurrences per letter for each sequence.
			s.correctHashKeys(hashKeys);
			
			sequences.add(s);
		}
	}
	
	/**
	 * Tries to solve the anagram problem with the given method.
	 * If it cannot use the desired method the brute force O(n^2)
	 * algorithm will be used.
	 */
	public void solve(SolvingMethod solving)
	{
		// Determines whether hashing can be used for the given input.
		boolean hashable = Sequence.canUseHashKeys(hashKeys);
		
		// If the sequences are hashable compute each sequences
		// hash with the hashkeys
		if (hashable)
		{
			Iterator<Sequence> iter = sequences.iterator();
			while (iter.hasNext())
				iter.next().computeHash(hashKeys);
		}
		
		// If you want to solve using the BinaryTree and the Sequences are hashable...
		if (solving == SolvingMethod.BinaryTree && hashable)
			execute(new SequenceTree(sequences));
		
		// If you want to solve using the Hashtable and the Sequences are hashable...
		else if (solving == SolvingMethod.Hashtable && hashable)
			execute(new SequenceTable(sequences));

		// If you want to do bruteforce or there is no other option...
		else
			execute(new SequenceArray(sequences));
	}
	
	/**
	 * Executes the algorithm given the container for the Sequences.
	 */
	public void execute(SequenceContainer container)
	{
//		System.out.println("MemoryUsage: " + holder.getMemoryUsage());
//		if (container instanceof SequenceTable)
//		{
//			SequenceTable table = (SequenceTable)container;
//			System.out.println("Collisions: " + table.getCollisions());
//		}

		// Iterator through each sequence and use the
		// SequenceHolder to determine how many anagrams
		// to the current Sequence there are.
		Iterator<Sequence> iter = sequences.iterator();
		// The last longest anagram.
		String longest = "";
		// Current sequence.
		Sequence s;
		// Total number of anagrams found.
		int totalAnagrams = 0;
		
		while (iter.hasNext())
		{
			s = iter.next();
			// If the container has at least 2 sequences
			// that are anagrams then print it out.
			if (container.getOccurrences(s) > 1)
			{
				System.out.println(s.getString());
				totalAnagrams++;
				
				if (s.getString().length() >= longest.length())
					longest = s.getString();
			}
		}
		// Print out the total and the last longest.
		System.out.println(totalAnagrams);
		System.out.println(longest);
	}
	
}
