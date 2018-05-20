package com.philsprojects.chart.icons;

import java.awt.Shape;

public abstract class Icon 
{

    protected double size;
    protected double halfSize;

    protected Icon(double size)
    {
	this.size = size;
	this.halfSize = size * 0.5;
    }

    public abstract Shape getShape(double cx, double cy);

    protected abstract void onResize();

    public void setSize(double size)
    {
	this.size = size;
	this.halfSize = size * 0.5;

	onResize();
    }

    public double getSize()
    {
	return size;
    }

    public double getHalfSize()
    {
	return halfSize;
    }

}
