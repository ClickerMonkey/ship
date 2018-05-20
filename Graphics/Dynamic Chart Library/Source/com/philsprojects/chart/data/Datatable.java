package com.philsprojects.chart.data;

/**
 *        |Jun08|Jul08|Aug08|
 * -------+-----+-----+-----+
 * List#1 | 2,4 | 6,7 | 7,7 |
 * List#2 | 5,6 | 8,9 | 8,8 |
 * List#3 | 7,8 | 9,6 | 9,9 |
 * -------+-----+-----+-----+
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class Datatable
{
    
    // The number of values it takes to represent one piece of data on the chart.
    protected final int dimensions;

    // The number of lists (rows) maintained in this data set.
    protected final int lists;

    // The listener of data set changes.
    protected DatatableListener listener;
    
    
    public Datatable(int lists, int dimensions)
    {
	this.lists = lists;
	this.dimensions = dimensions;
    }
    
    public abstract void clear();
    public abstract boolean add(Dataset dataset);
    public abstract Dataset get(int set);
    public abstract int getSize();
    public abstract double getMax();

    public boolean add(String name, double[] ... data)
    {
	return add(new Dataset(name, data));
    }

    public void setListener(DatatableListener listener)
    {
	this.listener = listener;
    }

    public DatatableListener getListener()
    {
	return listener;
    }

    public final int getDimension()
    {
	return dimensions;
    }

    public final int getListCount()
    {
	return lists;
    }
    
}
