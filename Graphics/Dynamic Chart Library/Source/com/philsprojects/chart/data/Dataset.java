package com.philsprojects.chart.data;

public class Dataset
{

    private final String name;
    private final double[][] data;
    private double max = -Double.MAX_VALUE;
    
    public Dataset(String name, int lists, int dimensions)
    {
	this(name, new double[lists][dimensions]);
    }   
    
    public Dataset(String name, double[][] data)
    {
	this.name = name;
	this.data = data;
	
	this.updateMax();
    }
    
    protected boolean matches(int lists, int dimensions)
    {
	if (data.length != lists)
	    return false;
	
	for (double[] d : data)
	    if (d.length != dimensions)
		return false;
	
	return true;
    }
    
    private void updateMax()
    {
	max = -Double.MAX_VALUE;
	for (int i = 0; i < data.length; i++)
	{
	    for (int j = 0; j < data[i].length; j++)
	    {
		if (data[i][j] > max)
		    max = data[i][j];
	    }
	}
    }
    
    public double getTotal(int dimension)
    {
	double total = 0.0;
	
	for (int i = 0; i < data.length; i++)
	    total += data[i][dimension];
	
	return total;
    }
    
    public double getData(int list, int dimension)
    {
	return data[list][dimension];
    }
    
    public void setData(int list, double[] data)
    {
	this.data[list] = data;

	this.updateMax();
    }

    public void setData(int list, double data)
    {
	this.data[list][0] = data;
	
	if (data > max)
	    max = data;
    }
    
    public void setData(int list, int dimension, double data)
    {
	this.data[list][dimension] = data;
	
	if (data > max)
	    max = data;
    }
    
    public double getMax()
    {
	return max;
    }
    
    public String getName()
    {
        return name;
    }
    
}
