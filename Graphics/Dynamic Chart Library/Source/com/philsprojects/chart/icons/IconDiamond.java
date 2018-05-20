package com.philsprojects.chart.icons;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class IconDiamond extends Icon
{

    private final Point2D.Double[] points = {
	    new Point2D.Double(), new Point2D.Double(),
	    new Point2D.Double(), new Point2D.Double(), 
    };

    public IconDiamond(double size)
    {
	super(size);
	onResize();
    }

    public Shape getShape(double cx, double cy)
    {
	GeneralPath path = new GeneralPath();

	path.moveTo(points[0].x + cx, points[0].y + cy);
	path.lineTo(points[1].x + cx, points[1].y + cy);
	path.lineTo(points[2].x + cx, points[2].y + cy);
	path.lineTo(points[3].x + cx, points[3].y + cy);
	path.closePath();

	return path;
    }

    protected void onResize()
    {
	// Top
	points[0].x = 0;
	points[0].y = -halfSize;
	// Right
	points[1].x = halfSize;
	points[1].y = 0;
	// Bottom
	points[2].x = 0;
	points[2].y = halfSize;
	// Left
	points[3].x = -halfSize;
	points[3].y = 0;
    }


}
