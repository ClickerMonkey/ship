package finalproject;

/**
 * An Array-based Queue used when you know exactly what the maximum number of
 * elements can be on the Queue at any time. The actual size of the queue is
 * always a power of 2 larger then the capacity given at initialization. Using
 * a queue with the maximum size of a power of 2 avoids using the modulus 
 * operator and instead uses the bit & operator. This method is always 10 times
 * quicker then the modulus operator.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T> => The type of class to enqueue and dequeue.
 */
public class Queue<T> 
{

	// The number of elements in the Queue
	private int size;
	
	// The index of the element in the front of the Queue
	private int front;
	
	// The index of the element in the back of the Queue
	private int back;
	
	// The array of elements (size is power of 2) in the Queue.
	private T[] elements;
	
	// The cached value for the max number of elements minus 1.
	private int mod;
	
	/**
	 * Initializes an Array-based Queue with a minimum size for this Queue. The
	 * actual max size will be the next power-of-2 number.
	 * 
	 * @param capacity => The minimum number of elements allowable for this queue.
	 */
	@SuppressWarnings("unchecked")
	public Queue(int capacity)
	{
		size = front = 0;
		back = -1;
		
		// Set the actual size to the next number that is power of 2
		int actual = 1;
		while (actual < capacity)
			actual <<= 1;
		
		elements = (T[])new Object[actual];
		mod = actual - 1;
	}
	
	/**
	 * Puts this element on the end of the queue if there is space left.
	 * 
	 * @param element => The element to add.
	 * @return True if the element was added, False if there is no space.
	 */
	public boolean offer(T element)
	{
		if (size == elements.length)
			return false;
		
		// Calculate the index on the back of the queue and add the element
		back = (back + 1) & mod;
		elements[back] = element;
		size++;
		
		return true;
	}
	
	/**
	 * Returns the element on the front of the queue if one exists.
	 */
	public T peek()
	{
		return elements[front];
	}
	
	/**
	 * Returns the element at the specified index in the queue.
	 * 
	 * @param index => The index of the element in the queue.
	 */
	public T peek(int index)
	{
		int actual = (index + front) & mod;
		return elements[actual];
	}

	/**
	 * Returns and removes the element on the front of the queue if one exists.
	 */
	public T poll()
	{
		if (size == 0)
			return null;
		
		// Grab the element at the front of the queue, empty the space, and
		// move the front of the queue through the array
		T element = elements[front];
		elements[front] = null;
		front = (front + 1) & mod;
		size--;
		
		return element;
	}

	/**
	 * Removes all elements from the queue.
	 */
	public void clear()
	{
		for (int i = 0; i < elements.length; i++)
			elements[i] = null;
		
		front = size = 0;
		back = -1;
	}
	
	/**
	 * Returns whether this queue has any elements in it or not.
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
