package com.philsprojects.chart.settings;

import com.philsprojects.chart.view.Viewport;

public abstract class Settings 
{

    // The underlying view which determines the visual properties.
    protected Viewport view;

    protected Settings()
    {
    }

    /**
     * 
     * @param view => The underlying view which determines the visual properties.
     * @param listener => The associated listener which reacts to setting change events.
     */
    protected Settings(Viewport view)
    {
	this.view = view;
    }

    /**
     * Returns the width of a data set on the view port in pixels.
     * 
     * @param lists => The number of lists on the chart.
     */
    public abstract double getDatasetWidth(int lists);

    /**
     * Returns the spacing between data sets (included in the data set width)
     * 
     * @param lists => The number of lists on the chart.
     */
    public abstract double getDatasetSpacing();
    
    /**
     * Returns the x-coordinate of the first data set in screen space, in pixels.
     */
    public abstract double getDatasetOffset();
    
    /**
     * Returns the index of the first visible data set on the view port.
     * 
     * @param lists => The number of lists on the chart.
     */
    public abstract int getFirstVisibleDataset(int lists);
    
    /**
     * Returns the index of the last visible data set on the view port.
     * 
     * @param lists => The number of lists on the chart.
     */
    public abstract int getLastVisibleDataset(int lists);
    
    /**
     * Returns the view port associated with the settings.
     */
    public Viewport getViewport()
    {
	return view;
    }

    /**
     * Sets the view port associated with the settings.
     */
    public void setViewport(Viewport view)
    {
	this.view = view;
    }

}
