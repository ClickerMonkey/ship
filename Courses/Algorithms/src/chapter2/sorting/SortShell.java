package chapter2.sorting;

public class SortShell<E extends Comparable<E>> extends Sort<E>
{

	@Override
	protected void doSort()
	{
		int i, j, k, half = (data.length) >> 1;
		double inv = 1.0 / 2.2;
		E temp;
		
		i = half;
		while (i > 0)
		{
			for (k = i; k < data.length; k++)
			{
				temp = data[k];
				for (j = k; j >= i && greater(data[j - i], temp); j -= i)
				{
					data[j] = data[j - i];
					addSwap();
				}
				data[j] = temp;
			}
			
			if (i == 2)
				i = 1;
			else
				i = (int)(i * inv);
		}
		
	}

}
