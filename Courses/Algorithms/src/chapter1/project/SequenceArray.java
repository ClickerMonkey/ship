package chapter1.project;

import java.util.LinkedList;

/**
 * A straight array of the sequences.
 * 
 * @author Philip Diffenderfer
 *
 */
public class SequenceArray implements SequenceContainer
{

	private Sequence[] array;
	
	/**
	 * Initializes a SequenceArray based on a linked list
	 * of Sequences.
	 */
	public SequenceArray(LinkedList<Sequence> sequences)
	{
		array = new Sequence[sequences.size()];
		array = sequences.toArray(array);
	}
	
	/**
	 * Returns how many anagrams there are for this Sequence.
	 * 
	 * Executes in O(n)
	 */
	public int getOccurrences(Sequence s)
	{
		// The total number of occurrences.
		int total = 0;
		// Check against every sequence
		for (int i = 0; i < array.length; i++)
			if (s.isAnagram(array[i]))
				total++;
		
		return total;
	}
	
	/**
	 * Gets the memory usage in bytes for this array.
	 */
	public int getMemoryUsage()
	{
		return (array.length * 4);
	}

}
