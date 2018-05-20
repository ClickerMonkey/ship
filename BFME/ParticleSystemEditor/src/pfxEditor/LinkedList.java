package pfxEditor;

public class LinkedList<E> {

	private int _size = 0;
	private Node _first = null;
	private Node _last = null;

	public LinkedList<E> add(E element) {
		if (_size == 0) {
			_last = new Node(element);
			_first = _last;
			_last.previous = _first;
			_first.next = _last;
			_first.previous = null;
			_last.next = null;
			_size = 1;
		} else {
			Node n = new Node(element);
			n.previous = _last;
			_last.next = n;
			_last = n;
			_size++;
		}
		return this;
	}

	public E remove(int index) {
		if (index == 0)
			return removeFirst();
		if (index == _size - 1)
			return removeLast();
		if (index >= _size || index < 0)
			return null;
		Node n = _first;
		while (index > 0) {
			index--;
			n = n.next;
		}
		E element = n.element;
		Node previous = n.previous;
		Node next = n.next;
		n = null;
		previous.next = next;
		next.previous = previous;
		_size--;
		return element;
	}

	public E get(int index) {
		if (index >= _size || index < 0)
			return null;
		Node n = _first;
		while (index > 0) {
			index--;
			n = n.next;
		}
		return n.element;
	}

	public void set(int index, E element) {
		if (index >= _size || index < 0)
			return;
		Node n = _first;
		while (index > 0) {
			index--;
			n = n.next;
		}
		n.element = element;
	}

	public E removeFirst() {
		if (_first != null) {
			E element = _first.element;
			Node next = _first.next;
			next.previous = null;
			_first = next;
			_size--;
			return element;
		}
		return null;
	}

	public E removeLast() {
		if (_last != null) {
			E element = _last.element;
			Node previous = _last.previous;
			previous.next = null;
			_last = previous;
			_size--;
			return element;
		}
		return null;
	}

	public LinkedList<E> clear() {
		_first = null;
		_last = null;
		_size = 0;
		return this;
	}

	public E getFirst() {
		if (_first != null) {
			return _first.element;
		} else {
			return null;
		}
	}

	public E getLast() {
		if (_last != null) {
			return _last.element;
		} else {
			return null;
		}
	}

	public int getSize() {
		return _size;
	}

	public Iterator<E> getIterator() {
		return new LinkedListIterator(this);
	}

	public LinkedList<E> clone() {
		LinkedList<E> clone = new LinkedList<E>();
		Node current = _first;
		while (current != null) {
			clone.add(current.element);
			current = current.next;
		}
		return clone;
	}

	public Node getFirstNode() {
		return _first;
	}

	public Node getLastNode() {
		return _last;
	}

	public void insertAfter(Node previous, E element) {
		if (previous == null || element == null)
			return;
		// If previous is the last node in the list
		if (previous.next == null) {
			Node n = new Node(element, previous);
			previous.next = n;
			_last = n;
		} else {
			Node next = previous.next;
			Node n = new Node(element, previous, next);
			next.previous = n;
			previous.next = n;
		}
		_size++;
	}

	public void insertBefore(Node next, E element) {
		if (next == null || element == null)
			return;
		// If next is the first node in the list
		if (next.previous == null) {
			Node n = new Node(element);
			n.next = next;
			next.previous = n;
			_first = n;
		} else {
			Node previous = next.previous;
			Node n = new Node(element, previous, next);
			next.previous = n;
			previous.next = n;
		}
		_size++;
	}

	public void remove(Node n) {
		if (n == null || (n.next == null && n.previous == null))
			return;
		// If this is the first node of the list
		if (n.previous == null) {
			_first = n.next;
			n.next.previous = null;
			n.next = null;
			n = null;
		}
		// If this is the last node of the list
		else if (n.next == null) {
			_last = n.previous;
			n.previous.next = null;
			n.previous = null;
			n = null;
		}
		// If it's somewhere in between
		else {
			Node previous = n.previous;
			Node next = n.next;
			n = null;
			previous.next = next;
			next.previous = previous;
		}
		_size--;
	}

	public class Node {
		public Node previous;
		public Node next;
		public E element;

		public Node(E nodeElement) {
			this(nodeElement, null, null);
		}

		public Node(E nodeElement, Node previousNode) {
			this(nodeElement, previousNode, null);
		}

		public Node(E nodeElement, Node previousNode, Node nextNode) {
			element = nodeElement;
			previous = previousNode;
			next = nextNode;
		}
	}

	private class LinkedListIterator implements Iterator<E> {

		private LinkedList<E> list;
		private Node current;

		public LinkedListIterator(LinkedList<E> baseList) {
			list = baseList;
			current = list._first;
		}

		public boolean hasNext() {
			return (current != null);
		}

		public E getNext() {
			if (current != null) {
				E element = current.element;
				current = current.next;
				return element;
			} else {
				return null;
			}
		}

		public void remove() {
			if (current != null) {
				current = current.next;
			}
		}

		public void reset() {
			current = list._first;
		}

	}

}
