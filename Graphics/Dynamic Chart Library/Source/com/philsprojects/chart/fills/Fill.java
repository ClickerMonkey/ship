package com.philsprojects.chart.fills;

import java.awt.Graphics2D;
import java.awt.Shape;

public interface Fill 
{

    public void setShape(Shape shape);

    public void select(Graphics2D gr);

}
