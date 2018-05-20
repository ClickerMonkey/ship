package chapter2.sorting;

public class SortBinaryInsertion<E extends Comparable<E>> extends Sort<E>
{

	/**
	 * Perform Binary Insertion Sort.
	 */
	@Override
	protected void doSort()
	{
		int insert;
		E temp;
	
		for (int i = 1; i < data.length; i++)
		{
			// Perform a binary search on the sorted part
			// of the data array against the current item.
			insert = binarySearch(0, i, data[i]);
		
			if (insert < i)
			{
				// Adjust everything in the sorted part for the 
				// new data to insert.
				temp = data[i];

				// Shift everything between i and the place to insert
				// ontop of i to open up the insert space.
				for (int j = i - 1; j >= insert; j--)
				{
					data[j + 1] = data[j];
					addSwap();
				}
				
				// Insert the data in the now free space.
				data[insert] = temp;
			}
		}
	}
	
	/**
	 * Performs a binary search between low and high for
	 * the right place to put value.
	 */
	protected int binarySearch(int low, int high, E value)
	{
		int middle =  low + ((high - low) / 2);
		
		do 
		{
			if (greater(value, data[middle]))
				low = middle + 1;
            else if (less(value, data[middle]))
                high = middle;
            else
                break;

            middle = low + ((high - low) / 2);
        } while (low < high);
		
		return middle;
	}

}
