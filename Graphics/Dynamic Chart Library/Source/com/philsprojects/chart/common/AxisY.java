package com.philsprojects.chart.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;

public class AxisY extends Axis 
{

    public AxisY()
    {
    }

    public AxisY(Stroke stroke, Color color) 
    {
	super(stroke, color);
    }

    public void draw(Graphics2D gr, Viewport view, Canvas destination) 
    {
	if (stroke != null)
	    gr.setStroke(stroke);
	
	if (color != null)
	    gr.setColor(color);
	
	double x = view.toScreenX(0.0);
	double right = destination.getCanvasWidth() - padding.right;

	if (x < padding.left || x > right)
	    
	    return;

	line.x1 = line.x2 = x;
	line.y1 = padding.top;
	line.y2 = destination.getCanvasHeight() - padding.bottom;
	
	gr.draw(line);
    }

}
