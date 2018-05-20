package com.philsprojects.chart.common;

import java.awt.geom.Rectangle2D;

public class Padding
{

    public static final Padding Default = new Padding(10.0f);
    
    public float left;
    public float top;
    public float right;
    public float bottom;
    
    public Padding(float left, float top, float right, float bottom)
    {
	this.left = left;
	this.top = top;
	this.right = right;
	this.bottom = bottom;
    }
    
    public Padding(float padding)
    {
	this(padding, padding, padding, padding);
    }
    
    public void set(Padding padding)
    {
	left = padding.left;
	top = padding.top;
	right = padding.right;
	bottom = padding.bottom;
    }

    public void clip(Rectangle2D bounds)
    {
	double width = bounds.getWidth() - (left + right);
	double height = bounds.getHeight() - (top + bottom);
	bounds.setFrame(bounds.getX() + left, bounds.getY() + top, width, height);
    }
    
    public Rectangle2D getClip(Rectangle2D bounds)
    {
	Rectangle2D.Double clipped = new Rectangle2D.Double();
	clipped.setFrame(bounds);
	
	clip(clipped);
	
	return clipped;
    }
    
}
