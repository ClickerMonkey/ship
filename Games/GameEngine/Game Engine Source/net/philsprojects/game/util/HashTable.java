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
 *  Class: engine.util.HashTable                  *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

/**
 * A Hash Table that hashes elements to an array based on a String used to store
 * it. Provides an Iterator to iterate through all of the elements on the table.
 * 
 * @author Philip Diffenderfer
 */
public class HashTable<T>
{

	// The elements in the table.
	protected HashEntry<T>[] _elements = null;

	// How many elements are currently in the table.
	protected int _size = 0;

	/**
	 * Initializes a HashTable with a maximum size.
	 * 
	 * @param maximumSize =>
	 *           The maximum number of elements this table can hold.
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int maximumSize)
	{
		_elements = new HashEntry[maximumSize];
		_size = 0;
	}

	/**
	 * Adds an element to the HashTable based on a String thats used to access
	 * the element.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param reference =>
	 *           The string used to hash the element and later return it.
	 * @param element =>
	 *           The element to add.
	 * @return True if the item was added, false if the HashTable is full.
	 */
	public boolean add(String reference, T element)
	{
		if (_size == _elements.length)
			return false;
		int index = getHash(reference);
		while (_elements[index] != null)
			index = (index + 1) % _elements.length;
		_elements[index] = new HashEntry<T>(reference, element);
		_size++;
		return true;
	}

	/**
	 * Removes an element based on the reference string given and returns it.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param reference =>
	 *           The reference string used to store the element.
	 * @return The element removed if any. If no element was found it returns
	 *         null.
	 */
	public T remove(String reference)
	{
		int index = getIndex(reference);
		if (index == -1)
			return null;
		T element = _elements[index].getElement();
		_elements[index] = null;
		_size--;
		return element;
	}

	/**
	 * Gets an element based on the reference string given.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param reference =>
	 *           The reference string used to store the element.
	 * @return The element found if any. If no element was found it returns null.
	 */
	public T get(String reference)
	{
		int index = getIndex(reference);
		return (index == -1 ? null : _elements[index].getElement());
	}

	/**
	 * Clears the HashTable of elements.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @return True if the elements were cleard, false if there were no elements
	 *         to clear.
	 */
	public boolean clear()
	{
		if (_size == 0)
			return false;
		// Set each element in the table to null
		for (int i = 0; i < _elements.length; i++)
			_elements[i] = null;
		// The size is now 0 and it was a success.
		_size = 0;
		return true;
	}

	/**
	 * The number of elements added to the HashTable.
	 * 
	 * @return The number of elements in the HashTable.
	 */
	public int getSize()
	{
		return _size;
	}

	/**
	 * The maximum number of elements this HashTable can hold.
	 * 
	 * @return The maximum number of elements this HashTable can hold.
	 */
	public int getMaximumSize()
	{
		return _elements.length;
	}

	/**
	 * Determines whether this HashTable is full of elements.
	 * 
	 * @return True if this HashTable cannot have anymore elements, false if it
	 *         still has space.
	 */
	public boolean isFull()
	{
		return (_size == _elements.length);
	}

	/**
	 * Determines whether an element with the given reference name exists in the
	 * HashTable.
	 * 
	 * @param reference =>
	 *           The reference string used to store the element.
	 * @return True if an element exists in the HashTable with the same reference
	 *         name, otherwise false.
	 */
	public boolean exists(String reference)
	{
		return (getIndex(reference) != -1);
	}

	/**
	 * An Iterator that iterates through all the non-null elements in the
	 * HashTable.
	 * 
	 * @return An Iterator for this HashTable.
	 */
	public Iterator<T> getIterator()
	{
		return new HashTableIterator(this);
	}

	/**
	 * The index on the array of the element with a matching reference name.
	 * 
	 * @param reference =>
	 *           The reference string used to store the element.
	 * @return The index of the element in the array if it exists, if it doesn't
	 *         exist it returns -1.
	 */
	protected int getIndex(String reference)
	{
		int index = getHash(reference);
		int checked = 0;
		while (checked < _elements.length && _elements[index] != null && !_elements[index].getReference().equals(reference))
		{
			index = (index + 1) % _elements.length;
			checked++;
		}
		if (_elements[index] == null)
			return -1;
		return index;
	}

	/**
	 * Hashes a string into an index on the Table where this element should tried
	 * to be placed.
	 * 
	 * @param reference =>
	 *           The reference string used to store the element.
	 * @return A number from 0 to the maximum size - 1 of this HashTable.
	 */
	protected int getHash(String reference)
	{
		int total = 0;
		for (char c : reference.toCharArray())
			total += c;
		return total % _elements.length;
	}

	/**
	 * An Entry in the HashTable with an element and its reference name.
	 * 
	 * @author Philip Diffenderfer
	 */
	private class HashEntry<E>
	{

		// The element and its reference name.
		private E _element = null;

		private String _reference;

		/**
		 * Initializes a HashEntry with its element and reference name.
		 * 
		 * @param reference =>
		 *           The reference string used to store the element.
		 * @param element =>
		 *           The element being stored.
		 */
		public HashEntry(String reference, E element)
		{
			_reference = reference;
			_element = element;
		}

		/**
		 * The element being stored.
		 * 
		 * @return The element being stored.
		 */
		public E getElement()
		{
			return _element;
		}

		/**
		 * The reference string used to store the element.
		 * 
		 * @return The reference string used to store the element.
		 */
		public String getReference()
		{
			return _reference;
		}

	}

	/**
	 * An Iterator that iterates through all the non-null elements in the
	 * HashTable.
	 * 
	 * @author Philip Diffenderfer
	 */
	private class HashTableIterator implements Iterator<T>
	{

		// The HashTable its iterating through
		private HashTable<T> _source = null;

		// The current element its looking at.
		private int _index = 0;;

		/**
		 * Initializes a HashTable Iterator with its source HashTable.
		 * 
		 * @param table =>
		 *           The HashTable to iterate through.
		 */
		public HashTableIterator(HashTable<T> table)
		{
			_source = table;
			findNext();
		}

		/**
		 * Returns the next element in the HashTable and goes to the next.
		 */
		public T getNext()
		{
			if (_index >= _source._elements.length)
				return null;
			T element = _source._elements[_index]._element;
			_index++;
			findNext();
			return element;
		}

		/**
		 * Returns true if there is still another element left in the HashTable to
		 * iterate over.
		 */
		public boolean hasNext()
		{
			return _index < _source._elements.length;
		}

		/**
		 * Steps over the next element in the HashTable and goes to the next.
		 */
		public void remove()
		{
			_index++;
			findNext();
		}

		/**
		 * Resets the Iterator back to the beginning.
		 */
		public void reset()
		{
			_index = 0;
			findNext();
		}

		/**
		 * Finds the next non-null element in the HashTable.
		 */
		private void findNext()
		{
			while (_index < _source._elements.length && _source._elements[_index] == null)
				_index++;
		}

	}

}
