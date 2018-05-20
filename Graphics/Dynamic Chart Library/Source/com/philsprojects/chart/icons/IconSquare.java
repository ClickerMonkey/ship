package com.philsprojects.chart.icons;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class IconSquare extends Icon
{

    private final Rectangle2D.Double rectangle = new Rectangle2D.Double();

    public IconSquare(double size)
    {
	super(size);
	onResize();
    }

    public Shape getShape(double cx, double cy)
    {
	rectangle.x = cx - halfSize;
	rectangle.y = cy - halfSize;

	return rectangle;
    }

    protected void onResize()
    {
	rectangle.height = size;
	rectangle.width = size;
    }


}
