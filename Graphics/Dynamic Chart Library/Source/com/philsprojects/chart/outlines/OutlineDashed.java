package com.philsprojects.chart.outlines;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import com.philsprojects.chart.fills.Fill;

public class OutlineDashed implements Outline
{

    private Fill fill;
    private Stroke stroke;
    
    public OutlineDashed(Fill fill, float width, Cap cap, Join join, float miterLimit, float offset, float ... dashes)
    {
	this.fill = fill;
	this.stroke = new BasicStroke(width, cap.value, join.value, miterLimit, dashes, offset);
    }
    
    public void select(Graphics2D gr)
    {
	fill.select(gr);
	gr.setStroke(stroke);
    }

    public void setShape(Shape shape)
    {
	fill.setShape(shape);
    }

}
