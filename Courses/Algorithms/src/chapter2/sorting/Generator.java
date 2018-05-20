package chapter2.sorting;

import java.lang.reflect.Array;

/**
 * 
 * @author Philip Diffenderfer
 *
 * @param <E> The type to generate.
 */
public abstract class Generator<E extends Comparable<E>> 
{
	
	// The generated data.
	private E[] data;
	
	/**
	 * Returns a type unique to the index i. Elements
	 * returned should be ordered where 0 is the first 
	 * element and N is the last element in ascending order.
	 * 
	 * @param i => The index to create the element at.
	 */
	protected abstract E createElementAt(int i);
	
	/**
	 * Returns the class type for the element type E. This
	 * is required to generate generic arrays.
	 */
	protected abstract Class<E> getElementType();
	
	/**
	 * Generates a specific number of elements using the 
	 * generation method specified.
	 * 
	 * @param elements => The number of elements.
	 * @param method => The method of generation.
	 */
	@SuppressWarnings("unchecked")
	public void generate(int elements, GenerateMethod method)
	{
		data = (E[])Array.newInstance(getElementType(), elements);
		
		for (int i = 0; i < elements; i++)
			data[i] = createElementAt(i);
		
		// If its random shuffle around the values
		if (method == GenerateMethod.Random)
		{
			for (int i = 0; i < elements; i++)
			{
				swap(i, i + (int)(Math.random() * (elements - i)));
			}
		}
		
		// If its reverse then just reverse the order.
		else if (method == GenerateMethod.Reverse)
		{
			for (int i = 0; i < elements; i++)
			{
				data[i] = data[elements - i - 1];
			}
		}
	}
	
	/**
	 * Swap the elements at i and j.
	 */
	private void swap(int i, int j)
	{
		E temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	/**
	 * Get the generated data.
	 */
	public E[] getData()
	{
		return data;
	}
}
