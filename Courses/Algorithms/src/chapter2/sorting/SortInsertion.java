package chapter2.sorting;

public class SortInsertion<E extends Comparable<E>> extends Sort<E>
{

	@Override
	protected void doSort()
	{
		E insert;
		for (int i = 1; i < data.length; i++)
		{
			insert = data[i];
			int moveItem = i;
			
			while (moveItem > 0 && greater(data[moveItem - 1], insert))
			{
				data[moveItem] = data[moveItem - 1];
				moveItem--;
				addSwap();
			}
			
			data[moveItem] = insert;
		}
	}

}
