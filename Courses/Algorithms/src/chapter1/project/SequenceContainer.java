package chapter1.project;

/**
 * An interface to describe a data structure that holds
 * Sequences and keeps track or counts how many anagrams
 * match a given sequence.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface SequenceContainer 
{

	public int getOccurrences(Sequence s);
	
	public int getMemoryUsage();
	
}
