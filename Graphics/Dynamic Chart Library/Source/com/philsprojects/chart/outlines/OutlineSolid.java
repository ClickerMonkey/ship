package com.philsprojects.chart.outlines;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

public class OutlineSolid implements Outline
{

    private Color color;
    private Stroke stroke;
    
    public OutlineSolid(float width)
    {
	this(null, width, Cap.Round, Join.Round);
    }
    
    public OutlineSolid(float width, Cap cap, Join join)
    {
	this(null, width, cap, join);
    }
    
    public OutlineSolid(Color color, float width)
    {
	this(color, width, Cap.Round, Join.Round);
    }
    
    public OutlineSolid(Color color, float width, Cap cap, Join join)
    {
	this.color = color;
	this.stroke = new BasicStroke(width, cap.value, join.value);
    }
    
    public void select(Graphics2D gr)
    {
	if (color != null)
	    gr.setColor(color);
	
	gr.setStroke(stroke);
    }

    public void setShape(Shape shape)
    {
    }

}
