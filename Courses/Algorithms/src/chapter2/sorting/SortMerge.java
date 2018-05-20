package chapter2.sorting;

public class SortMerge<E extends Comparable<E>> extends Sort<E>
{

	@Override
	@SuppressWarnings("unchecked")
	protected void doSort()
	{
		E[] space = (E[])new Comparable[data.length];
		mergesort(space, 0, data.length);
	}
	
	private void mergesort(E[] space, int low, int high)
	{
		if (high - low <= 1)
			return;
		
		int middle = low + ((high - low) >> 1);
		mergesort(space, low, middle);
		mergesort(space, middle, high);
		
		merge(space, low, middle, high);
	}
	
	private void merge(E[] space, int low, int middle, int high)
	{
		int i = low, j = middle;
		for (int k = low; k < high; k++) {
			if (i == middle)
				space[k] = data[j++];
			else if (j == high)
				space[k] = data[i++];
			else if (less(data[j], data[i]))
				space[k] = data[j++];
			else
				space[k] = data[i++];
			addSwap();
		}
		for (int k = low; k < high; k++)
		{
			data[k] = space[k];
			addSwap();
		}
	}

}
