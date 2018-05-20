package finalproject;

/**
 * An Array-based Stack used when you know exactly what the maximum number of
 * elements can be on the Stack at any time.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T> => The type of class to push and pop.
 */
public class Stack<T> 
{

	// The number of elements in the Stack.
	private int size;
	
	// The array of elements in the Stack.
	private T[] elements;
	
	/**
	 * Initializes an Array-based Stack with the maximum number of elements that
	 * can be pushed onto the stack.
	 * 
	 * @param capacity => The maximum number of elements that can be pushed onto the stack.
	 */
	@SuppressWarnings("unchecked")
	public Stack(int capacity)
	{
		elements = (T[])new Object[capacity];
		size = 0;
	}
	
	/**
	 * Pushes this element to the top of the stack.
	 * 
	 * @param element => The element to add to the stack.
	 * @return True if the element was added, False if there is no space.
	 */
	public boolean push(T element)
	{
		if (size == elements.length)
			return false;
		
		elements[size++] = element;
		
		return true;
	}
	
	/**
	 * Returns the element on the front of the queue if one exists.
	 */
	public T peek()
	{
		return elements[size - 1];
	}

	/**
	 * Returns and removes the element on the front of the queue if one exists.
	 */
	public T pop()
	{
		if (size == 0)
			return null;
	
		size--;
		T top = elements[size];
		elements[size] = null;
		
		return top;
	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() 
	{
		for (int i = 0; i < size; i++)
			elements[i] = null;
		
		size = 0;
	}
	
	/**
	 * Returns whether this stack has any elements or not.
	 */
	public boolean hasElements()
	{
		return (size != 0);
	}
	
	/**
	 * Returns the number of elements in this queue.
	 */
	public int getSize()
	{
		return size;
	}

}
