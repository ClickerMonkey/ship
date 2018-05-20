package chapter2.sorting;

public class GeneratorInteger extends Generator<Integer> 
{

	/**
	 * Simple enough! 
	 */
	@Override
	protected Integer createElementAt(int i)
	{
		return new Integer(i);
	}

	@Override
	protected Class<Integer> getElementType() 
	{
		return Integer.class;
	}

}
