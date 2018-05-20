package com.philsprojects.chart.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;

public class AxisX extends Axis 
{

    public AxisX()
    {
    }
    
    public AxisX(Stroke stroke, Color color) 
    {
	super(stroke, color);
    }

    public void draw(Graphics2D gr, Viewport view, Canvas destination) 
    {
	if (stroke != null)
	    gr.setStroke(stroke);
	
	if (color != null)
	    gr.setColor(color);

	double y = view.toScreenY(0.0);
	double bottom = destination.getCanvasHeight() - padding.bottom; 

	if (y < padding.top || y > bottom)
	    return;

	line.x1 = padding.left;
	line.y1 = line.y2 = y;
	line.x2 = destination.getCanvasWidth() - padding.right;
	
	gr.draw(line);
    }

}
