package com.philsprojects.chart.common;

import java.awt.geom.Line2D;

import com.philsprojects.chart.outlines.OutlineDelta;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;

public abstract class Grid extends ChartVisual 
{
	
    // The outline used to draw the lines of the grid based on their precision.
    protected OutlineDelta outline;
    
    // The line object used for all line drawing.
    protected Line2D.Double line = new Line2D.Double();



	public Grid()
	{

	}

	public Grid(Viewport view, Canvas destination)
	{
		super(view, destination);
	}


    /**
     * @return the outline
     */
    public OutlineDelta getOutline()
    {
        return outline;
    }

    /**
     * @param outline the outline to set
     */
    public void setOutline(OutlineDelta outline)
    {
        this.outline = outline;
    }
	
}
