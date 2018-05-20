package pfxEditor;
public class HashTable<T> {
	protected HashEntry<T>[] _elements = null;
	protected int _maximumSize = 0;
	protected int _size = 0;

	@SuppressWarnings("unchecked")
	public HashTable(int maximumSize) {
		_maximumSize = maximumSize;
		_elements = new HashEntry[maximumSize];
		_size = 0;
	}

	public boolean add(String reference, T element) {
		if (_size == _maximumSize)
			return false;
		int index = getHash(reference);
		while (_elements[index] != null)
			index = (index + 1) % _maximumSize;
		_elements[index] = new HashEntry<T>(reference, element);
		_size++;
		return true;
	}

	public T remove(String reference) {
		int index = getIndex(reference);
		if (index != -1) {
			T element = _elements[index].getElement();
			_elements[index] = null;
			_size--;
			return element;
		} else {
			return null;
		}
	}

	public T get(String reference) {
		int index = getIndex(reference);
		return (index == -1 ? null : _elements[index].getElement());
	}

	public boolean clear() {
		if (_size != 0) {
			for (int i = 0; i < _maximumSize; i++) {
				_elements[i] = null;
			}
			_size = 0;
			return true;
		} else {
			return false;
		}
	}

	public int getSize() {
		return _size;
	}

	public int getMaximumSize() {
		return _maximumSize;
	}

	public boolean isFull() {
		return (_size == _maximumSize);
	}

	public boolean exists(String reference) {
		return (getIndex(reference) != -1);
	}

	protected int getIndex(String reference) {
		int index = getHash(reference);
		int checked = 0;
		while (checked < _maximumSize && _elements[index] != null
				&& !_elements[index].getReference().equals(reference)) {
			index = (index + 1) % _maximumSize;
			checked++;
		}
		if (_elements[index] == null)
			return -1;
		return index;
	}

	protected int getHash(String reference) {
		int total = 0;
		for (char c : reference.toCharArray())
			total += (int) c;
		return total % _maximumSize;
	}

	private class HashEntry<E> {
		private E _element = null;
		private String _reference;

		public HashEntry(String reference, E element) {
			_reference = reference;
			_element = element;
		}

		public E getElement() {
			return _element;
		}

		public String getReference() {
			return _reference;
		}

	}

}
