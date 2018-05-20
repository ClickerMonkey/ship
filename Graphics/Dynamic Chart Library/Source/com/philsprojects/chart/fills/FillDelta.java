package com.philsprojects.chart.fills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public class FillDelta implements Fill
{
    
    private int sr, sg, sb, sa;
    private int er, eg, eb, ea;
    private int r, g, b, a;
    
    public FillDelta(Color start, Color end)
    {
	setStartColor(start);
	setEndColor(end);
    }
    
    public void select(Graphics2D gr)
    {
	gr.setColor(new Color(r, g, b, a));
    }

    public void setShape(Shape shape)
    {
    }
    
    public void setDelta(double delta)
    {
	if (delta > 1f)
	    delta = 1f;
	if (delta < 0f)
	    delta = 0f;
	
	r = (int)((er - sr) * delta + sr) & 0xFF;
	g = (int)((eg - sg) * delta + sg) & 0xFF;
	b = (int)((eb - sb) * delta + sb) & 0xFF;
	a = (int)((ea - sa) * delta + sa) & 0xFF;
    }
    
    public void setStartColor(Color start)
    {
	sr = start.getRed();
	sg = start.getGreen();
	sb = start.getBlue();
	sa = start.getAlpha();
    }
    
    public void setEndColor(Color end)
    {
	er = end.getRed();
	eg = end.getGreen();
	eb = end.getBlue();
	ea = end.getAlpha();
    }

}
