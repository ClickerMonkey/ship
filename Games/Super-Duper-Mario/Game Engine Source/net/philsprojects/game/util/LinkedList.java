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
 *  Class: engine.util.LinkedList                 *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import net.philsprojects.game.IClone;

/**
 * A referenced based list of elements that grows dynamically.
 * 
 * @author Philip Diffenderfer
 */
public class LinkedList<E>
{

	// The number of elements in the list.
	private int _size = 0;

	// The first and last node of the list.
	private Node _first = null;

	private Node _last = null;

	/**
	 * Initializes an empty LinkedList.
	 */
	public LinkedList()
	{

	}

	/**
	 * Adds an element to the end of this list.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param element =>
	 *           The element to add.
	 */
	public void add(E element)
	{
		if (_size == 0)
		{
			_last = _first = new Node(element);
			_size = 1;
		}
		else
		{
			Node n = new Node(element);
			n._previous = _last;
			_last._next = n;
			_last = n;
			_size++;
		}
	}

	/**
	 * Removes the element at the given index and returns it.<br>
	 * <br>
	 * Magnitude: O(n)<br>
	 * 
	 * @param index =>
	 *           The position of the element to remove in the list. Must be
	 *           between 0 and the list size - 1.
	 * @return The element at index if one exists, if not then it returns null.
	 */
	public E remove(int index)
	{
		if (index == 0)
			return removeFirst();
		if (index == _size - 1)
			return removeLast();
		if (index >= _size || index < 0)
			return null;
		Node n = _first;
		while (index > 0)
		{
			index--;
			n = n._next;
		}
		E element = n._element;
		Node previous = n._previous;
		Node next = n._next;
		n = null;
		previous._next = next;
		next._previous = previous;
		_size--;
		return element;
	}

	/**
	 * Removes the element and returns it.<br>
	 * <br>
	 * Magnitude: O(n)<br>
	 * 
	 * @param element =>
	 *           The element to remove.
	 * @return The reference to the element removed, or null if it wasn't
	 *         removed.
	 */
	public E remove(E element)
	{
		Node n = _first;
		while (n != null)
		{
			if (n._element.equals(element))
			{
				Node previous = n._previous;
				Node next = n._next;
				n = null;
				previous._next = next;
				next._previous = previous;
				_size--;
				return element;
			}
			n = n._next;
		}
		return null;
	}

	/**
	 * Gets the element at the given index.<br>
	 * <br>
	 * Magnitude: O(n)<br>
	 * 
	 * @param index =>
	 *           The position of the element to get in the list. Must be between
	 *           0 and the list size - 1.
	 * @return The element at index if one exists, if not then it returns null.
	 */
	public E get(int index)
	{
		if (index >= _size || index < 0)
			return null;
		Node n = _first;
		while (index > 0)
		{
			index--;
			n = n._next;
		}
		return n._element;
	}

	/**
	 * Sets the element at the given index.<br>
	 * <br>
	 * Magnitude: O(n)<br>
	 * 
	 * @param index =>
	 *           The position of the element to set in the list. Must be between
	 *           0 and the list size - 1.
	 * @param element =>
	 *           The element to set at the index.
	 * @return True if the element at that index could be set, false if the index
	 *         is outside the bounds.
	 */
	public boolean set(int index, E element)
	{
		if (index >= _size || index < 0)
			return false;
		Node n = _first;
		while (index > 0)
		{
			index--;
			n = n._next;
		}
		n._element = element;
		return true;
	}

	/**
	 * Returns and removes the first element in the list.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @return The first element.
	 */
	public E removeFirst()
	{
		if (_first != null)
		{
			E element = _first._element;
			if (_size > 1)
			{
				Node next = _first._next;
				next._previous = null;
				_first = next;
			}
			else
			{
				_first = _last = null;
			}
			_size--;
			return element;
		}
		return null;
	}

	/**
	 * Returns and removes the last element in the list.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @return The last element.
	 */
	public E removeLast()
	{
		if (_last == null)
			return null;
		E element = _last._element;
		if (_size > 1)
		{
			Node previous = _last._previous;
			previous._next = null;
			_last = previous;
		}
		else
		{
			_first = _last = null;
		}
		_size--;
		return element;
	}

	/**
	 * Clears the LinkedList of all data.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 */
	public void clear()
	{
		_first = null;
		_last = null;
		_size = 0;
	}

	/**
	 * The first element in the list.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @return The first element in the list if any, otherwise null.
	 */
	public E getFirst()
	{
		return (_first == null ? null : _first._element);
	}

	/**
	 * The last element in the list.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @return The last element in the list if any, otherwise null.
	 */
	public E getLast()
	{
		return (_last == null ? null : _last._element);
	}

	/**
	 * The number of elements in this LinkedList.
	 * 
	 * @return The number of elements.
	 */
	public int getSize()
	{
		return _size;
	}

	/**
	 * An Iterator to iterate through all the elements in this LinkedList.
	 * 
	 * @return The Iterator.
	 */
	public Iterator<E> getIterator()
	{
		return new LinkedListIterator(this);
	}

	/**
	 * The first node in the list.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @return The first node in the list if any, otherwise null.
	 */
	public Node getFirstNode()
	{
		return _first;
	}

	/**
	 * The last node in the list.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @return The last node in the list if any, otherwise null.
	 */
	public Node getLastNode()
	{
		return _last;
	}

	/**
	 * Inserts an element after the specified Node and before the node after the
	 * previous. <br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param previous =>
	 *           The node to insert the element after.
	 * @param element =>
	 *           The element to insert.
	 */
	public void insertAfter(Node previous, E element)
	{
		if (previous == null || element == null)
			return;
		// If previous is the last node in the list
		if (previous._next == null)
		{
			Node n = new Node(element, previous);
			previous._next = n;
			_last = n;
		}
		else
		{
			Node next = previous._next;
			Node n = new Node(element, previous, next);
			next._previous = n;
			previous._next = n;
		}
		_size++;
	}

	/**
	 * Inserts an element before the specified Node and after the node before the
	 * next.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param next =>
	 *           The node to insert the element before.
	 * @param element =>
	 *           The element to instert.
	 */
	public void insertBefore(Node next, E element)
	{
		if (next == null || element == null)
			return;
		// If next is the first node in the list
		if (next._previous == null)
		{
			Node n = new Node(element);
			n._next = next;
			next._previous = n;
			_first = n;
		}
		else
		{
			Node previous = next._previous;
			Node n = new Node(element, previous, next);
			next._previous = n;
			previous._next = n;
		}
		_size++;
	}

	/**
	 * Removes this node from the list closing it up.<br>
	 * <br>
	 * Magnitude: O(1)<br>
	 * 
	 * @param n =>
	 *           The node to remove.
	 */
	public void remove(Node n)
	{
		if (n == null)
			return;
		// If the node is the only node in the list.
		if (n._next == null && n._previous == null)
		{
			_size = 0;
			_first = null;
			_last = null;
			n = null;
			return;
		}
		// If this is the first node of the list
		if (n._previous == null)
		{
			_first = n._next;
			n._next._previous = null;
			n._next = null;
			n = null;
		}
		// If this is the last node of the list
		else if (n._next == null)
		{
			_last = n._previous;
			n._previous._next = null;
			n._previous = null;
			n = null;
		}
		// If it's somewhere in between
		else
		{
			Node previous = n._previous;
			Node next = n._next;
			n = null;
			previous._next = next;
			next._previous = previous;
		}
		_size--;
	}

	/**
	 * Determines whether the element is inside the LinkedList.<br>
	 * <br>
	 * Magnitude: O(n)<br>
	 * 
	 * @param element =>
	 *           The element to search for.
	 * @return True if the element is in this list, otherwise false.
	 */
	public boolean contains(E element)
	{
		Node start = _first;
		while (start != null)
		{
			if (start._element.equals(element))
				return true;
			start = start._next;
		}
		return false;
	}

	public LinkedList<E> getReferencedClone()
	{
		LinkedList<E> clone = new LinkedList<E>();
		Iterator<E> i = getIterator();
		while (i.hasNext())
			clone.add(i.getNext());
		return clone;
	}

	@SuppressWarnings("unchecked")
	public LinkedList<E> getClone()
	{
		if (_size == 0)
			return new LinkedList<E>();

		LinkedList<E> clone = new LinkedList<E>();
		Iterator<E> i = getIterator();
		while (i.hasNext())
		{
			E element = i.getNext();
			if (element instanceof IClone)
				clone.add(((IClone<E>)element).getClone());
			else
				clone.add(element);
		}
		return clone;
	}

	/**
	 * The Node class that holds an element and a reference to the Nodes before
	 * and after this one.
	 * 
	 * @author Philip Diffenderfer
	 */
	public class Node
	{

		public Node _previous;

		public Node _next;

		public E _element;

		/**
		 * Initializes a node based on an element.
		 * 
		 * @param nodeElement =>
		 *           The element for this node.
		 */
		public Node(E element)
		{
			this(element, null, null);
		}

		/**
		 * Initializes a node based on its element and the previous node.
		 * 
		 * @param nodeElement =>
		 *           The element for this node.
		 * @param previousNode =>
		 *           The previous node to this node.
		 */
		public Node(E element, Node previous)
		{
			this(element, previous, null);
		}

		/**
		 * Initializes a node based on its element and next and previous nodes.
		 * 
		 * @param nodeElement =>
		 *           The element for this node.
		 * @param previousNode =>
		 *           The previous node to this node.
		 * @param nextNode =>
		 *           The next node to this node.
		 */
		public Node(E element, Node previous, Node next)
		{
			_element = element;
			_previous = previous;
			_next = next;
		}

	}

	/**
	 * An Iterator that iterates through each element of this LinkedList.
	 * 
	 * @author Philip Diffenderfer
	 */
	private class LinkedListIterator implements Iterator<E>
	{

		// A reference of the source LinkedList.
		private LinkedList<E> _source;

		// The current node.
		private Node _current;

		/**
		 * Initializes a LinkedList Iterator with its source LinkedList.
		 * 
		 * @param source =>
		 *           The LinkedList to iterate through.
		 */
		public LinkedListIterator(LinkedList<E> source)
		{
			_source = source;
			_current = _source._first;
		}

		/**
		 * Returns true if there is still another element left in the LinkedList
		 * to iterate over.
		 */
		public boolean hasNext()
		{
			return (_current != null);
		}

		/**
		 * Returns the next element in the LinkedList and goes to the next.
		 */
		public E getNext()
		{
			if (_current == null)
				return null;
			E element = _current._element;
			_current = _current._next;
			return element;
		}

		/**
		 * Steps over the next element in the LinkedList and goes to the next.
		 */
		public void remove()
		{
			if (_current != null)
				_current = _current._next;
		}

		/**
		 * Resets the Iterator back to the beginning.
		 */
		public void reset()
		{
			_current = _source._first;
		}

	}

}
