package com.philsprojects.chart.fills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class FillLinear implements Fill 
{
    public static final int NO_CYCLE = 0;
    public static final int REFLECT = 1;
    public static final int REPEAT = 2;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int DIAGONAL_LEFT = 2;
    public static final int DIAGONAL_RIGHT = 4;

    private static final CycleMethod[] CYCLES = {
	CycleMethod.NO_CYCLE, CycleMethod.REFLECT, CycleMethod.REPEAT
    };

    private Point2D point1 = new Point2D.Double();
    private Point2D point2 = new Point2D.Double();
    private final float[] fractions;
    private final Color[] colors;
    private final int cycle;
    private final int direction;
    private final double cycles;

    public FillLinear(Color inner, Color outer, int direction, int cycle, double cycles)
    {
	this.colors = new Color[] {inner, outer};
	this.fractions = new float[] {0, 1};
	this.cycle = cycle;
	this.direction = direction;
	this.cycles = cycles;
    }

    public FillLinear(Color inner, Color outer, int direction)
    {
	this(inner, outer, direction, NO_CYCLE, 1.0);
    }

    public FillLinear(Color[] colors, float[] fractions, int direction, int cycle, double cycles)
    {
	this.colors = colors;
	this.fractions = fractions;
	this.cycle = cycle;
	this.direction = direction;
	this.cycles = cycles;
    }

    public void select(Graphics2D gr) 
    {
	gr.setPaint(new LinearGradientPaint(point1, point2, fractions, colors, CYCLES[cycle]));
    }

    public void setShape(Shape shape)
    {
	setBounds(shape.getBounds2D());
    }

    private void setBounds(Rectangle2D rect) 
    {
	double left = rect.getX();
	double top = rect.getY();
	double right = left + rect.getWidth() / cycles;
	double bottom = top + rect.getHeight() / cycles;

	switch (direction)
	{
	case HORIZONTAL:
	    point1.setLocation(left, top);
	    point2.setLocation(right, top);
	    break;
	case VERTICAL:
	    point1.setLocation(left, top);
	    point2.setLocation(left, bottom);
	    break;
	case DIAGONAL_LEFT:
	    point1.setLocation(left, top);
	    point2.setLocation(right, bottom);
	    break;
	case DIAGONAL_RIGHT:
	    point1.setLocation(right, top);
	    point2.setLocation(left, bottom);
	    break;
	}
    }

}
