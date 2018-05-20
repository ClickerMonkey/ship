package gerry;

import java.lang.reflect.Array;

public class Ring<E> 
{

	public static void main(String[] args) {
		Ring<Integer> r = Ring.create(Integer.class, 10);
		for (int i = 0; i <= 40; i++) {
			System.out.print(r.add(i));
			System.out.print(' ');
		}
		System.out.println();
		for (int i = 0; i < r.size(); i++) {
			System.out.print(r.elements[i]);
			System.out.print(' ');
		}
	}
	
	private int next = 0;
	private int size = 0;
	private final int capacity;
	private final E[] elements;
	
	public static <T> Ring<T> create(Class<T> type, int size) {
		return new Ring<T>(type, size);
	}

	@SuppressWarnings("unchecked")
	private Ring(Class<E> type, int size) {
		elements = (E[])Array.newInstance(type, size);
		capacity = size;
	}
	
	public E add(E e) {
		E removed = null;
		if (size == capacity) removed = elements[next];
		else size++;
		elements[next] = e;
		next = (next + 1) % capacity;
		return removed;
	}
	
	public boolean isFull() {
		return (size == capacity);
	}
	
	public int size() {
		return size;
	}
	
	public int capacity() {
		return capacity;
	}
	
}
