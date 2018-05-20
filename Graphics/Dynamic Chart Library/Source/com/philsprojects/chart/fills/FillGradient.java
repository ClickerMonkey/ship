package com.philsprojects.chart.fills;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class FillGradient implements Fill 
{

	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	public static final int DIAGONAL_LEFT = 2;
	public static final int DIAGONAL_RIGHT = 4;
	
	private Point2D point1 = new Point2D.Double();
	private Point2D point2 = new Point2D.Double();
	private Color color1;
	private Color color2;
	private int direction;
	
	public FillGradient(Color color1, Color color2, int direction)
	{
		this.color1 = color1;
		this.color2 = color2;
		this.direction = direction;
	}
	
	public void select(Graphics2D gr) 
	{
		gr.setPaint(new GradientPaint(point1, color1, point2, color2));
	}
	
	public void setShape(Shape shape)
	{
		setBounds(shape.getBounds2D());
	}

	private void setBounds(Rectangle2D rect) 
	{
		double left = rect.getX();
		double top = rect.getY();
		double right = left + rect.getWidth();
		double bottom = top + rect.getHeight();
		
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
