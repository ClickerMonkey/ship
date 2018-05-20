package com.philsprojects.chart.icons;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class IconCircle extends Icon
{

    private final Ellipse2D.Double ellipse = new Ellipse2D.Double();

    public IconCircle(double size)
    {
	super(size);
	onResize();
    }

    public Shape getShape(double cx, double cy)
    {
	ellipse.x = cx - halfSize;
	ellipse.y = cy - halfSize;

	return ellipse;
    }

    protected void onResize()
    {
	ellipse.width = size;
	ellipse.height = size;
    }

}
