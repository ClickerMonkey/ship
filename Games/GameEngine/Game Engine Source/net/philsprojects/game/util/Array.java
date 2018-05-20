/************************************************\ 
 *                   [Array]                    * 
 *            ______ ___    __   _    _____     * 
 *           / ____//   |  /  | / |  / ___/     *
 *          / /___ / /| | /   |/  | / __/       *
 *         / /_| // __  |/ /|  /| |/ /__        *
 *        /_____//_/  |_|_/ |_/ |_|\___/        *
 *    _____ __   _  ______ ______ __   _  _____ *
 *   / ___//  | / // ____//_  __//  | / // ___/ *
 *  / __/ / | |/ // /___   / /  / | |/ // __/   *
 * / /__ / /|   // /_| /__/ /_ / /|   // /__    *
 * \___//_/ |__//_____//_____//_/ |__/ \___/    *
 *                                              *
 \************************************************/

package net.philsprojects.game.util;

/**
 * A wrapper around an array of any type and provides an iterator to iterate
 * through the elements.
 * 
 * @author Philip Diffenderfer
 */
public class Array<T>
{

	// Generic array of elements.
	private T[] _elements;

	/**
	 * Initializes an Array with a fixed amount of elements.
	 * 
	 * @param elements =>
	 *           The initial elements of this array.
	 */
	public Array(T[] elements)
	{
		_elements = elements;
	}

	/**
	 * Initializes an Array with a fixed amount of elements.
	 * 
	 * @param elementCount =>
	 *           The new size of the array.
	 */
	public Array(int elementCount)
	{
		reset(elementCount);
	}

	/**
	 * Resets the array to a new size clearing all elements.
	 * 
	 * @param elementCount =>
	 *           The new size of the array.
	 */
	@SuppressWarnings("unchecked")
	public void reset(int elementCount)
	{
		_elements = (T[])(new Object[elementCount]);
	}

	/**
	 * Returns the element at this index.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param index =>
	 *           The index of the element. Greater then or equal to 0 and less
	 *           then the size.
	 */
	public T get(int index)
	{
		return _elements[index];
	}

	/**
	 * Sets the element at this index.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param index =>
	 *           The index of the element. Greater then or equal to 0 and less
	 *           then the size.
	 * @param element =>
	 *           The element to set at the index in the array.
	 */
	public void set(int index, T element)
	{
		_elements[index] = element;
	}

	/**
	 * Returns an Iterator to iterate through the elements.
	 */
	public Iterator<T> getIterator()
	{
		return new ArrayIterator(this);
	}

	/**
	 * Returns the size of the array.
	 */
	public int getSize()
	{
		return _elements.length;
	}

	/**
	 * Returns the elements in this array.
	 */
	public T[] getElements()
	{
		return _elements;
	}

	/**
	 * Class that implements the Iterator interface and iterates through the
	 * values of this Array.
	 */
	private class ArrayIterator implements Iterator<T>
	{

		private T[] _elements = null;

		private int _current = 0;

		/**
		 * Initializes an Iterator based on the Array passed in.
		 * 
		 * @param a =>
		 *           The Array<T> object to iterate through.
		 */
		public ArrayIterator(Array<T> a)
		{
			_elements = a.getElements();
		}

		/**
		 * Returns true if this Iterator has another available element.
		 */
		public boolean hasNext()
		{
			return (_current < _elements.length);
		}

		/**
		 * Returns the next element if any.
		 */
		public T getNext()
		{
			if (_current < _elements.length)
			{
				T element = _elements[_current];
				_current++;
				return element;
			}
			return null;
		}

		/**
		 * Removes the next element if any.
		 */
		public void remove()
		{
			_current++;
		}

		/**
		 * Resets the iterator so it can be iterated through again.
		 */
		public void reset()
		{
			_current = 0;
		}

	}

}
