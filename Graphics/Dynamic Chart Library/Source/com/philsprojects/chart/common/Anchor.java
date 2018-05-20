package com.philsprojects.chart.common;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Anchor
{

    public static final Anchor Default = new Anchor(0.0f, 0.0f);
    public static final Anchor TopLeft = new Anchor(0.0f, 0.0f);
    public static final Anchor TopCenter = new Anchor(0.5f, 0.0f);
    public static final Anchor TopRight = new Anchor(1.0f, 0.0f);
    public static final Anchor CenterLeft = new Anchor(0.0f, 0.5f);
    public static final Anchor Center = new Anchor(0.5f, 0.5f);
    public static final Anchor CenterRight = new Anchor(1.0f, 0.5f);
    public static final Anchor BottomLeft = new Anchor(0.0f, 1.0f);
    public static final Anchor BottomCenter = new Anchor(0.5f, 1.0f);
    public static final Anchor BottomRight = new Anchor(1.0f, 1.0f);

    public final float x;
    public final float y;

    public Anchor(float x, float y)
    {
	this.x = x;
	this.y = y;
    }

    public void clip(Rectangle2D bounds, double width, double height)
    {
	double extentX = bounds.getWidth() - width;
	double extentY = bounds.getHeight() - height;
	double rx = (extentX * x) + bounds.getX();
	double ry = (extentY * y) + bounds.getY();
	
	bounds.setFrame(rx, ry, width, height);
    }

    public void clip(Point2D point, double width, double height)
    {
	double rx = (width * x) + point.getX();
	double ry = (height * y) + point.getY();
	
	point.setLocation(rx, ry);
    }

    public void clipX(Rectangle2D bounds, double width, double height)
    {
	double extentX = bounds.getWidth() - width;
	double rx = (extentX * x) + bounds.getX();
	
	bounds.setFrame(rx, bounds.getY(), width, height);
    }
    
    public double clipX(Rectangle2D bounds, double width)
    {
	return ((bounds.getWidth() - width) * x) + bounds.getX();
    }
    
    public double clipY(Rectangle2D bounds, double height)
    {
	return ((bounds.getHeight() - height) * y) + bounds.getY();
    }
    
    public Rectangle2D getClip(Rectangle2D bounds, double width, double height)
    {
	Rectangle2D.Double clipped = new Rectangle2D.Double();
	clipped.setFrame(bounds);
	
	clip(clipped, width, height);
	
	return clipped;
    }

}
