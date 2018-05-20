package net.philsprojects.io;

import java.util.List;

/**
 * A Transferable class is able to dump its collection of information into a
 * list (by release) and then pass it to another Transferable which can take
 * any acceptable pieces of that information and return a list of denied 
 * information (by transfer).
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The transferable object.
 */
public interface Transferable<E> 
{
	
	/**
	 * Releases all elements from this class. This is the first step in 
	 * transfering the elements from this class into another Transferable.
	 * 
	 * @return
	 * 		A list of transferable elements.
	 */
	public List<E> release();
	
	/**
	 * Transfers all given elements into this class. This is the second step
	 * in transfering the elements from another Transferable into this class.
	 *  
	 * @param elements
	 * 		The list of transferable elements released from another class.
	 * @return
	 * 		The list of denied elements that could be transfered.
	 */
	public List<E> transfer(List<E> elements);
	
}