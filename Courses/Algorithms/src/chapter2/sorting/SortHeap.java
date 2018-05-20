package chapter2.sorting;

public class SortHeap<E extends Comparable<E>> extends Sort<E>
{

	@Override
	protected void doSort()
	{
		buildHeap();
		int n = data.length;
		
		while (n > 1)
		{
			n--;
			swap(0, n);
			downheap(0, n);
		}
	}
	
	private void buildHeap()
	{
		int start = (data.length >> 1) - 1;
		for (int v = start; v >= 0; v--)
			downheap(v, data.length);
	}
	
	private void downheap(int v, int n)
	{
		int w = (v << 1) + 1;
		while (w < n)
		{
			if (w + 1 < n && greater(data[w + 1], data[w]))
				w++;
			
			if (greaterOrEqual(data[v], data[w]))
				return;
			
			swap(v, w);
			
			w = ((v = w) << 1) + 1;
		}
	}
//	
//	private int left(int i)
//	{
//		return (i >> 1) + 1;
//	}
//	
//	private int right(int i)
//	{
//		return (i << 1) + 2;
//	}
//
//	private void buildheap()
//	{
//		int n = (data.length >> 1) + 1;
//		for (int i = n; i >= 0; i++)
//			heapify(i);
//	}
//	
//	private void heapify(int i)
//	{
//		int largest;
//		int left = left (i);
//		int right = right(i);
//		
//		if (greater(data[i], data[left]))
//			largest = left;
//		else
//			largest = i;
//		
//		if (greater(data[right], data[largest]))
//			largest = right;
//		
//		if (largest != i)
//		{
//			swap(i, largest);
//			heapify(largest);
//		}
//	}

}
