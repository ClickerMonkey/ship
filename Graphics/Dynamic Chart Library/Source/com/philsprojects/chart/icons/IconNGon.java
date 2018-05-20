package com.philsprojects.chart.icons;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class IconNGon extends Icon
{

    private double angle;
    private Point2D.Double[] points;

    public IconNGon(double size, int sides, double angle)
    {
	super(size);

	points = new Point2D.Double[sides];
	while (--sides >= 0)
	    points[sides] = new Point2D.Double();

	setAngle(angle);
    }

    public Shape getShape(double cx, double cy)
    {
	GeneralPath path = new GeneralPath();

	path.moveTo(points[0].x + cx, points[0].y + cy);
	for (int i = 1; i < points.length; i++)
	    path.lineTo(points[i].x + cx, points[i].y + cy);
	path.closePath();

	return path;
    }

    protected void onResize()
    {
	double theta = angle;
	double omega = (Math.PI * 2.0) / points.length;

	for (int i = 0; i < points.length; i++)
	{
	    points[i].x = Math.cos(theta) * halfSize;
	    points[i].y = -Math.sin(theta) * halfSize;

	    theta += omega;
	}
    }

    public void setAngle(double angle)
    {
	this.angle = Math.toRadians(angle);
	onResize();
    }

    public double getAngle()
    {
	return Math.toDegrees(angle);
    }

    public int getSides()
    {
	return points.length;
    }


}
