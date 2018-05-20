package com.philsprojects.chart.icons;

import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

public class IconPie extends Icon
{

    private double left, right, top, bottom;

    private final boolean clip;
    private final Arc2D.Double arc = new Arc2D.Double(Arc2D.PIE);

    public IconPie(double size, double centralAngle, double sweep, boolean clip)
    {
	super(size);
	onResize();
	setSweep(sweep);
	setAngle(centralAngle);

	this.clip = clip;
    }

    public Shape getShape(double cx, double cy)
    {
	arc.x = cx - halfSize;
	arc.y = cy - halfSize;

	arc.width = size;
	arc.height = size;
	if (clip)
	{
	    arc.x -= left;
	    arc.width += left + right;
	    arc.y -= top;
	    arc.height += top + bottom;
	}

	return arc;
    }

    protected void onResize()
    {
	arc.width = size;
	arc.height = size;
	updateClipping();
    }

    private void updateClipping()
    {
	Rectangle2D.Double bounds = (Rectangle2D.Double)arc.getBounds2D();
	left = bounds.x - arc.x;
	right = (arc.x + arc.width) - (bounds.x + bounds.width);
	top = bounds.y - arc.y;
	bottom = (arc.y + arc.height) - (bounds.y + bounds.height);
    }

    public void setSweep(double sweep)
    {
	arc.extent = sweep;
	updateClipping();
    }

    public void setAngle(double angle)
    {
	arc.start = angle - (arc.extent * 0.5);
	updateClipping();
    }

    public double getSweep()
    {
	return arc.extent;
    }

    public double getAngle()
    {
	return arc.start + (arc.extent * 0.5);
    }

    public double getStartAngle()
    {
	return arc.start;
    }

    public double getEndAngle()
    {
	return arc.start + arc.extent;
    }

}
