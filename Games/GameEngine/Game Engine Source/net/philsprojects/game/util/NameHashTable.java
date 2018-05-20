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
 *  Class: engine.util.NameHashTable              *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import net.philsprojects.game.IName;

/**
 * A HashTable where the elements all implement the <code>IName</code>
 * interface. The elements are hashed in based on their name.
 * 
 * @author Philip Diffenderfer
 */
public class NameHashTable<T extends IName> extends HashTable<T>
{

	/**
	 * Initializes a HashTable with a maximum size.
	 * 
	 * @param maximumSize =>
	 *           The maximum number of elements this table can hold.
	 */
	public NameHashTable(int maximumSize)
	{
		super(maximumSize);
	}

	/**
	 * Adds an element that implements the <code>IName</code> interface to the
	 * HashTable.
	 * 
	 * @param element =>
	 *           The element to add.
	 * @return True if the item was added, false if the HashTable is full.
	 */
	public boolean add(T element)
	{
		return add(element.getName(), element);
	}

}
