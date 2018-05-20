package com.philsprojects.chart.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import com.philsprojects.chart.view.ViewportEntity;

public abstract class Axis implements ViewportEntity 
{

    // The stroke use to draw the axis
    protected Stroke stroke = new BasicStroke(1f);

    // The color of the axis
    protected Color color = Color.black;

    // The padding for the axis.
    protected Padding padding = new Padding(0);
    
    // The line used for drawing an axis.
    protected Line2D.Double line = new Line2D.Double();

    
    protected Axis()
    {
    }
    
    protected Axis(Stroke stroke, Color color)
    {
	this.stroke = stroke;
	this.color = color;
    }

    /**
     * @return the padding
     */
    public Padding getPadding()
    {
        return padding;
    }

    /**
     * @param padding the padding to set
     */
    public void setPadding(Padding padding)
    {
        this.padding = padding;
    }

    public void setStroke(Stroke stroke)
    {
	this.stroke = stroke;
    }

    public void setColor(Color color)
    {
	this.color = color;
    }

    public Stroke getStroke()
    {
	return stroke;
    }

    public Color getColor()
    {
	return color;
    }

}
