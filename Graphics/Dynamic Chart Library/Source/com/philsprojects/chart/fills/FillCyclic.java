package com.philsprojects.chart.fills;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class FillCyclic implements Fill 
{

	private static final double RAD_45 = Math.PI * 0.25;
	private static final double RAD_90 = Math.PI * 0.50;
	private static final double RAD_135 = Math.PI * 0.75;
	private static final double RAD_180 = Math.PI * 1.00;
	private static final double RAD_225 = Math.PI * 1.25;
	private static final double RAD_270 = Math.PI * 1.50;
	private static final double RAD_315 = Math.PI * 1.75;
	
	private Point2D point1 = new Point2D.Double();
	private Point2D point2 = new Point2D.Double();
	private Color color1;
	private Color color2;
	private double radians;

	
	public FillCyclic(Color color1, Color color2)
	{
		this(color1, color2, 0.0);
	}
	
	public FillCyclic(Color color1, Color color2, double degrees)
	{
		this.color1 = color1;
		this.color2 = color2;
		this.radians = wrap(degrees) / 180.0 * Math.PI;
	}
	
	private double wrap(double degrees)
	{
		while (degrees >= 360) degrees -= 360;
		while (degrees < 0) degrees += 360;
		return degrees;
	}
	
	public void select(Graphics2D gr) 
	{
		gr.setPaint(new GradientPaint(point1, color1, point2, color2, true));
	}
	
	public void setShape(Shape shape)
	{
		if (shape instanceof Arc2D || shape instanceof Ellipse2D)
		{
			setCircularBounds(shape.getBounds2D());
		}
		else
		{
			setRectangularBounds(shape.getBounds2D());
		}
	}

	private void setCircularBounds(Rectangle2D rect)
	{
		double cx = rect.getCenterX();
		double cy = rect.getCenterY();
		double halfWidth = rect.getWidth() * 0.5;
		double halfHeight = rect.getHeight() * 0.5;
		double ox = Math.cos(radians) * halfWidth;
		double oy = -Math.sin(radians) * halfHeight;
		
		point1.setLocation(cx, cy);
		point2.setLocation(cx + ox, cy + oy);
	}
	
	private void setRectangularBounds(Rectangle2D rect) 
	{
		double cx = rect.getCenterX();
		double cy = rect.getCenterY();
		double halfWidth = rect.getWidth() * 0.5;
		double halfHeight = rect.getHeight() * 0.5;
		double ox = 0.0;
		double oy = 0.0;
		
		// Determine the quadrant and fix the offsets accordingly.
		if (radians <= RAD_45 || radians > RAD_315)
		{
			ox = halfWidth;
			oy = -halfWidth * Math.tan(radians);
		}
		else if (radians <= RAD_135)
		{
			oy = -halfHeight;
			ox = -halfHeight * Math.tan(radians - RAD_90);
		}
		else if (radians <= RAD_225)
		{
			ox = -halfWidth;
			oy = halfWidth * Math.tan(radians - RAD_180);
		}
		else if (radians <= RAD_315)
		{
			oy = halfHeight;
			ox = halfHeight * Math.tan(radians - RAD_270);
		}
		
		point1.setLocation(cx, cy);
		point2.setLocation(cx + ox, cy + oy);
	}

}
