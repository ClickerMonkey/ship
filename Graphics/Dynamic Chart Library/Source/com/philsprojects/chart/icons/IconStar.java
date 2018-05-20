package com.philsprojects.chart.icons;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class IconStar extends Icon
{

    private double angle;
    private double insetFactor;

    private Point2D.Double[] points;

    public IconStar(double size, int points, double insetFactor, double angle)
    {
	super(size);

	int sides = points << 1;

	this.points = new Point2D.Double[sides];
	while (--sides >= 0)
	    this.points[sides] = new Point2D.Double();

	setInsetFactor(insetFactor);
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
	double inset = halfSize * insetFactor;
	double length;
	double theta = angle;
	double omega = (Math.PI * 2.0) / points.length;

	for (int i = 0; i < points.length; i++)
	{
	    length = ((i & 1) == 0 ? halfSize : inset);

	    points[i].x = Math.cos(theta) * length;
	    points[i].y = -Math.sin(theta) * length;

	    theta += omega;
	}
    }

    public void setAngle(double angle)
    {
	this.angle = Math.toRadians(angle);
	onResize();
    }

    public void setInsetFactor(double factor)
    {
	this.insetFactor = Math.max(0.0, Math.min(factor, 1.0));
	onResize();
    }


}
