package com.philsprojects.chart.data;


import java.util.ArrayList;

/**
 *
 */
public class DatatableDynamic extends Datatable
{

    private ArrayList<Dataset> sets;

    /**
     * 
     * @param lists
     * @param dimensions
     */
    public DatatableDynamic(int lists, int dimensions)
    {
	super(lists, dimensions);

	sets = new ArrayList<Dataset>();
    }

    public void clear()
    {
	sets.clear();
	
	if (listener != null)
	    listener.datasetReset(this);
    }

    public boolean add(Dataset set)
    {
	if (!set.matches(lists, dimensions))
	    return false;
	
	sets.add(set);

	if (listener != null)
	    listener.dataAdded(this, set, sets.size() - 1);

	return true;
    }

    public Dataset get(int set)
    {
	return sets.get(set);
    }

    @Override
    public double getMax()
    {
	double max = -Double.MAX_VALUE;
	double localMax;
	
	for (int i = 0; i < sets.size(); i++)
	{
	    localMax = sets.get(i).getMax();
	    if (localMax > max)
		max = localMax;
	}
	
	return max;
    }

    @Override
    public int getSize()
    {
	return sets.size();
    }

}
