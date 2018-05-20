package com.philsprojects.chart.common;

import java.awt.Graphics2D;

import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;

public abstract class ChartVisual
{

    // The offset of the lines in the grid from the axis.
    protected float offset;

    // The spacing ratio between the lines in the grid in the same precision.
    protected float frequency;

    // The number of lines between the larger precision on lines.
    protected int interval;

    // The frequency at which grid lines are 'most' visible.
    protected float startFrequency;

    // The frequency at which grid lines are 'least' visible. The visibility 
    // threshold of lines in pixels. Lines separated by this value or less are
    // not drawn.
    protected float endFrequency;


    // The padding around the grid
    protected Padding padding = new Padding(0f);

    // The view port that's associated with the grid.
    protected Viewport view;

    // The destination canvas to draw the grid on.
    protected Canvas destination;


    public ChartVisual()
    {
    }

    /**
     * Initializes a grid given its view port.
     * 
     * @param view => The view port of the grid.
     */
    public ChartVisual(Viewport view, Canvas destination)
    {
	this.view = view;
	this.destination = destination;
    }


    /**
     * Draws the implemented grid to the graphics object.
     * 
     * @param gr => The graphics object to draw on.
     */
    public abstract void draw(Graphics2D gr);


    /**
     * @return the offset
     */
    public float getOffset()
    {
	return offset;
    }


    /**
     * @return the frequency
     */
    public float getFrequency()
    {
	return frequency;
    }


    /**
     * @return the interval
     */
    public int getInterval()
    {
	return interval;
    }

    /**
     * @return the startFrequency
     */
    public float getStartFrequency()
    {
	return startFrequency;
    }


    /**
     * @return the endFrequency
     */
    public float getEndFrequency()
    {
	return endFrequency;
    }


    /**
     * @return the view
     */
    public Viewport getView()
    {
	return view;
    }


    /**
     * @param offset the offset to set
     */
    public void setOffset(float offset)
    {
	this.offset = offset;
    }


    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(float frequency)
    {
	this.frequency = frequency;
    }


    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval)
    {
	this.interval = interval;
    }


    /**
     * @param startFrequency the startFrequency to set
     */
    public void setStartFrequency(float startFrequency)
    {
	this.startFrequency = startFrequency;
    }


    /**
     * @param endFrequency the endFrequency to set
     */
    public void setEndFrequency(float endFrequency)
    {
	this.endFrequency = endFrequency;
    }


    /**
     * @return the padding
     */
    public Padding getPadding()
    {
	return padding;
    }

    /**
     * @return the destination
     */
    public Canvas getDestination()
    {
	return destination;
    }

    /**
     * @param padding the padding to set
     */
    public void setPadding(Padding padding)
    {
	this.padding = padding;
    }

    /**
     * @param view the view to set
     */
    public void setView(Viewport view)
    {
	this.view = view;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(Canvas destination)
    {
	this.destination = destination;
    }


}
