package chapter2.sorting;

public class SortQuick<E extends Comparable<E>> extends Sort<E>
{

	@Override
	protected void doSort()
	{
		quicksort(0, data.length - 1);
	}
	
	private void quicksort(int left, int right)
	{
		if (right <= left) return;
		int i = partition(left, right);
		quicksort(left, i - 1);
		quicksort(i + 1, right);
	}
	
	private int partition(int left, int right)
	{
		int i = left - 1;
		int j = right;
		while (true) 
		{
		    while (less(data[++i], data[right]));
		    while (less(data[right], data[--j]))  
		        if (j == left) break;
		    if (i >= j) break;
		    swap(i, j);
		}
		swap(i, right);
		return i;
	}

}
