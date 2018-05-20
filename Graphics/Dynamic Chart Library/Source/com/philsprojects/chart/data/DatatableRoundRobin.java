package com.philsprojects.chart.data;

public class DatatableRoundRobin extends Datatable
{
    
    private int size;
    private int pointer;
    private Dataset[] sets;
    
    
    public DatatableRoundRobin(int lists, int dimensions, int capacity)
    {
	super(lists, dimensions);
	
	sets = new Dataset[capacity];
    }

    @Override
    public boolean add(Dataset dataset)
    {
	if (size < sets.length)
	{
	    sets[size++] = dataset;
	}
	else
	{
	    sets[pointer] = dataset;
	    pointer = index(1);
	}
	
	return true;
    }
    
    private int index(int i)
    {
	return (pointer + i) % sets.length;
    }

    @Override
    public void clear()
    {
	size = 0;
	pointer = 0;
	
	for (int i = 0; i < sets.length; i++)
	    sets[i] = null;
    }

    @Override
    public Dataset get(int set)
    {
	if (set < 0 || set >= size)
	    return null;
	
	return sets[index(set)];
    }

    @Override
    public double getMax()
    {
	double max = -Double.MAX_VALUE;
	double localMax;
	int p = pointer;
	
	for (int i = 0; i < size; i++)
	{
	    localMax = sets[p].getMax();
	    if (localMax > max)
		max = localMax;
	    
	    p = (p + 1) % sets.length;
	}
	
	return max;
    }

    @Override
    public int getSize()
    {
	return size;
    }

}
