package com.philsprojects.chart.fills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class FillRadial implements Fill 
{

	private Point2D center = new Point2D.Double();
	private float radius;
	private float radiusScale;
	private final float[] fractions;
	private final Color[] colors;
	
	public FillRadial(Color inner, Color outer)
	{
		this.colors = new Color[] {inner, outer};
		this.fractions = new float[] {0, 1};
		this.radiusScale = 1f;
	}
	
	public FillRadial(Color inner, Color outer, float radiusScale)
	{
		this.colors = new Color[] {inner, outer};
		this.fractions = new float[] {0, 1};
		this.radiusScale = radiusScale;
	}
	
	public FillRadial(Color[] colors, float[] fractions, float radiusScale)
	{
		this.colors = colors;
		this.fractions = fractions;
		this.radiusScale = radiusScale;
	}
	
	public void select(Graphics2D gr) 
	{
		gr.setPaint(new RadialGradientPaint(center, radius, fractions, colors));
	}
	
	public void setShape(Shape shape)
	{
		setBounds(shape.getBounds2D());
	}

	private void setBounds(Rectangle2D rect) 
	{
		double cx = rect.getCenterX();
		double cy = rect.getCenterY();
		double halfWidth = rect.getWidth() * 0.5;
		double halfHeight = rect.getHeight() * 0.5;
		
		center.setLocation(cx, cy);
		radius = (float)Math.max(halfWidth, halfHeight) * radiusScale;
	}

}
