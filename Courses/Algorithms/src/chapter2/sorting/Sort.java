package chapter2.sorting;


public abstract class Sort<E extends Comparable<E>>
{
	
	private long swaps;
	private long comparisons;
	private long duration;
	
	private E temp;
	protected E[] data;
	
	public void sort(E[] array)
	{
		data = array;
		swaps = 0;
		comparisons = 0;
		
		long startTime = System.nanoTime();
		
		doSort();
		
		duration = (System.nanoTime() - startTime);
	}
	
	protected abstract void doSort();
	
	protected final void swap(int i, int j)
	{
		temp = data[i];
		data[i] = data[j];
		data[j] = temp;
		swaps++;
	}
	
	protected final boolean less(E a, E b)
	{
		comparisons++;
		return (a.compareTo(b) < 0);
	}
	
	protected final boolean lessOrEqual(E a, E b)
	{
		comparisons++;
		return (a.compareTo(b) <= 0);
	}
	
	protected final boolean greater(E a, E b)
	{
		comparisons++;
		return (a.compareTo(b) > 0);
	}
	
	protected final boolean greaterOrEqual(E a, E b)
	{
		comparisons++;
		return (a.compareTo(b) >= 0);
	}
	
	protected final boolean equal(E a, E b)
	{
		comparisons++;
		return (a.compareTo(b) == 0);
	}
	
	protected void addSwap()
	{
		swaps++;
	}
	
	protected void addComparison()
	{
		comparisons++;
	}
	
	public long getDuration()
	{
		return duration;
	}
	
	public E[] getArray()
	{
		return data;
	}
	
	public long getSwaps()
	{
		return swaps;
	}
	
	public long getComparisons()
	{
		return comparisons;
	}
	
}
