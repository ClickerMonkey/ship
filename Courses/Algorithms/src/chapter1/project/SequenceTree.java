package chapter1.project;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Node to a sequence tree. This node keeps track of how many
 * sequences that were anagrams to this one were added. For nodes
 * that only have an occurrence of 1 there was no matching anagram.
 * 
 * @author Philip Diffenderfer
 *
 */
public class SequenceTree implements SequenceContainer
{
	
	private SequenceTree left;
	private SequenceTree right;
	private Sequence sequence;
	private int occurrences;
	private int children;

	/**
	 * Initializes a Tree building all of its elements based on
	 * a linked list of Sequences.
	 */
	public SequenceTree(LinkedList<Sequence> sequences)
	{
		Iterator<Sequence> iter = sequences.iterator();
		
		if (!iter.hasNext())
			return;
		
		sequence = iter.next();
		occurrences = 1;
		
		// Add each sequence down the tree starting at the root.
		while (iter.hasNext())
			add(iter.next());
	}
	
	/**
	 * Initializes a SequenceTree based on a sequence.
	 */
	public SequenceTree(Sequence s)
	{
		sequence = s;
		occurrences = 1;
	}

	/**
	 * Attempts to add a Sequence to this tree/subtree. If the sequence
	 * to add is an anagram to this one then it isnt added but the occurences
	 * is incremented.<br>
	 * <br>
	 * Best case O(logn)<br>
	 * Worst case O(n) where:<br>
	 * 		n = The number of sequences in this tree.
	 */
	private void add(Sequence s)
	{
		children++;
		// If they have the same hash they are anagrams
		// so add the number of times this anagram occurs.
		if (s.getHash() == sequence.getHash())
		{
			occurrences++;
		}
		// If the hashed value is less then add it to the left
		else if (s.getHash() < sequence.getHash())
		{
			if (left == null)
				left = new SequenceTree(s);
			else
				left.add(s);
		}
		// If the hashed value is more then add it to the right
		else if (s.getHash() > sequence.getHash())
		{
			if (right == null)
				right = new SequenceTree(s);
			else
				right.add(s);
		}
	}
	
	/**
	 * Returns the number of sequences that are an anagram
	 * to the given Sequence. <br>
	 * <br>
	 * Best Case O(logn) <br>
	 * Average Case O(logn) <br>
	 * Worst Case O(n) where: <br>
	 * 		n = The number of sequences in this tree.
	 */
	public int getOccurrences(Sequence s)
	{
		// If they are an anagram....
		if (s.getHash() == sequence.getHash())
		{
			return occurrences;
		}
		else if (s.getHash() < sequence.getHash() && left != null)
		{
			return left.getOccurrences(s);
		}
		else if (s.getHash() > sequence.getHash() && right != null)
		{
			return right.getOccurrences(s);
		}
		// No occurrence found.
		return 0;
	}
	
	/**
	 * Gets the memory usage in bytes for this array.
	 */
	public int getMemoryUsage()
	{
		return ((children + 1) * 20);
	}
	
}
