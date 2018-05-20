package com.philsprojects.chart.outlines;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;

public interface Outline
{

    public enum Cap
    {
        Round(BasicStroke.CAP_ROUND),
        Square(BasicStroke.CAP_SQUARE),
        Butt(BasicStroke.CAP_BUTT);
        
        public final int value;
        
        private Cap(int value)
        {
            this.value = value;
        }
    }

    public enum Join
    {
        Bevel(BasicStroke.JOIN_BEVEL),
        Miter(BasicStroke.JOIN_MITER),
        Round(BasicStroke.JOIN_ROUND);
        
        public final int value;
        
        private Join(int value)
        {
            this.value = value;
        }
    }
    
    public void setShape(Shape shape);

    public void select(Graphics2D gr);
    
}
