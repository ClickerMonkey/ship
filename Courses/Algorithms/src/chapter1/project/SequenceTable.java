package chapter1.project;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Builds a hash table with a size based on the size of the 
 * input linked list multiplied by the HASH_SCALE. On average
 * this can count how many anagrams per sequence in O(1).
 * 
 * @author Philip Diffenderfer
 *
 */
public class SequenceTable implements SequenceContainer
{

	public static double HASH_SCALE = 2.7;
	
	private int capacity;
	private int collisions;
	private long[] hashes;
	private int[] occurrences;

	/**
	 * Initializes a SequenceTable based on a linked list of
	 * Sequences.
	 */
	public SequenceTable(LinkedList<Sequence> sequences)
	{
		// Determine the size of the hashtable
		capacity = (int)(sequences.size() * HASH_SCALE);
		
		// Initialize the array of hashes and how many
		// times that hash has been added (occurred).
		hashes = new long[capacity];
		occurrences = new int[capacity];
		
		// Add each sequence to the table.
		Iterator<Sequence> iter = sequences.iterator();
		while (iter.hasNext())
			add(iter.next());
	}
	
	/**
	 * Returns the index in the table that is best for this
	 * Sequence given based on its hashed value.
	 */
	private int getHash(Sequence s)
	{
		return (int)(s.getHash() % capacity);
	}
	
	/**
	 * Adds a sequence to this table. This has the same complexity
	 * as the getOccurrences algorithm. This tallies up an collisions
	 * that occur when trying to add a Sequence where its desired
	 * index is already occupied by another Sequence.
	 * 
	 * Best case O(1)
	 * Average case O(1)
	 * Worst case O(n)
	 */
	private void add(Sequence s)
	{
		int index = getHash(s);
		int original = index;
		int count = 0;
		
		while (hashes[index] != 0 && s.getHash() != hashes[index])
			index = ((++count) + original) % capacity;
		
		collisions += count;
		
		hashes[index] = s.getHash();
		occurrences[index]++;
	}
	
	/**
	 * Returns how many anagrams there are for this Sequence. This
	 * has the same complexity as the add algorithm.
	 * 
	 * Best case O(1)
	 * Average case O(1)
	 * Worst case O(n)
	 */
	public int getOccurrences(Sequence s)
	{
		int index = getHash(s);
		
		while (hashes[index] != s.getHash())
			index = (index + 1) % capacity;

		return occurrences[index];
	}
	
	/**
	 * Returns how many collisions occured while adding all 
	 * the current Sequences to this table.
	 */
	public int getCollisions()
	{
		return collisions;
	}
	
	/**
	 * Returns the max capacity of Sequences for this table.
	 */
	public int getCapacity()
	{
		return capacity;
	}
	
	/**
	 * 
	 */
	public int getMemoryUsage()
	{
		return (capacity * 12) + 8;
	}
	
}
