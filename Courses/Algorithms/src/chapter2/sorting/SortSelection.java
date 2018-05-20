package chapter2.sorting;

public class SortSelection<E extends Comparable<E>> extends Sort<E>
{

	@Override
	protected void doSort()
	{
		int min;
		for (int i = 0; i < data.length - 1; i++) 
		{
			min = i;
			for (int j = i + 1; j < data.length; j++)
				if (less(data[j], data[min]))
					min = j;
			swap(i, min);
		}
	}

}
