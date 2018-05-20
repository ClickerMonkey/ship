package com.philsprojects.chart.outlines;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public class OutlineDelta implements Outline
{


    private int sr, sg, sb, sa;
    private int er, eg, eb, ea;
    private int r, g, b, a;
    private float startWidth, endWidth, width;
    private Cap cap;
    private Join join;
    private float milterLimit;
    private float offset;
    private float[] dashes;
    
    public OutlineDelta(Color start, Color end, float startWidth, float endWidth)
    {
	this(start, end, startWidth, endWidth, Cap.Round, Join.Round, 1f, null);
    }
    
    public OutlineDelta(Color start, Color end, float startWidth, float endWidth, Cap cap, Join join)
    {
	this(start, end, startWidth, endWidth, cap, join, 1f, null);
    }
    
    public OutlineDelta(Color start, Color end, float startWidth, float endWidth, Cap cap, Join join, float milterLimit, float ... dashes)
    {
	this.setStart(start);
	this.setEnd(end);
	this.startWidth = startWidth;
	this.endWidth = endWidth;
	this.cap = cap;
	this.join = join;
	this.milterLimit = milterLimit;
	this.dashes = dashes;
    }
    
    
    public void select(Graphics2D gr)
    {
	gr.setStroke(new BasicStroke(width, cap.value, join.value, milterLimit, dashes, offset));
	gr.setColor(new Color(r, g, b, a));
    }

    public void setShape(Shape shape)
    {
    }
    
    public void setStart(Color start)
    {
	sr = start.getRed();
	sg = start.getGreen();
	sb = start.getBlue();
	sa = start.getAlpha();
    }
    
    public void setEnd(Color end)
    {
	er = end.getRed();
	eg = end.getGreen();
	eb = end.getBlue();
	ea = end.getAlpha();
    }
    
    public void setDelta(double delta, double offsetScale)
    {
	if (delta > 1f)
	    delta = 1f;
	if (delta < 0f)
	    delta = 0f;
	
	r = (int)((er - sr) * delta + sr) & 0xFF;
	g = (int)((eg - sg) * delta + sg) & 0xFF;
	b = (int)((eb - sb) * delta + sb) & 0xFF;
	a = (int)((ea - sa) * delta + sa) & 0xFF;
	width = (float)((endWidth - startWidth) * delta + startWidth);
	offset = (float)(delta * offsetScale);
    }

    
}
