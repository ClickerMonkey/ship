/**************************************************\ 
 *            ______ ___    __   _    _____       * 
 *           / ____//   |  /  | / |  / ___/       *
 *          / /___ / /| | /   |/  | / __/         *
 *         / /_| // __  |/ /|  /| |/ /__          *
 *        /_____//_/  |_|_/ |_/ |_|\___/          *
 *     _____ __   _  ______ ______ __   _  _____  *
 *    / ___//  | / // ____//_  __//  | / // ___/  *
 *   / __/ / | |/ // /___   / /  / | |/ // __/    *
 *  / /__ / /|   // /_| /__/ /_ / /|   // /__     *
 *  \___//_/ |__//_____//_____//_/ |__/ \___/     *
 *                                                *
 * Author: Philip Diffenderfer                    *
 *  Class: engine.util.Iterator                   *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

/**
 * An Interface used to iterate through a collection of elements.
 * 
 * @author Philip Diffenderfer
 */
public interface Iterator<T>
{

	/**
	 * Whether the collection being iterated through has another element.
	 * 
	 * @return True if the iterator has another element, false otherwise.
	 */
	public boolean hasNext();

	/**
	 * Returns the current element and then proceeds to the next.
	 * 
	 * @return The current element.
	 */
	public T getNext();

	/**
	 * Removes the current element and proceeds to the next.
	 */
	public void remove();

	/**
	 * Resets the Iterator back to the beginning of the collection.
	 */
	public void reset();

}
