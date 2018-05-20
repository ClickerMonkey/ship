package chapter2.sorting;

public class SortBubble<E extends Comparable<E>> extends Sort<E>
{

	@Override
	protected void doSort() 
	{
		for (int i = 1; i < data.length; i++)
			for (int j = 0; j < data.length - i; j++)
				if (greater(data[j], data[j + 1]))
					swap(j, j + 1);
	}

}
