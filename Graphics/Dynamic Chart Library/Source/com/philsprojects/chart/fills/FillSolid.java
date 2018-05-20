package com.philsprojects.chart.fills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public class FillSolid implements Fill 
{

	public Color color;
	
	public FillSolid(Color color)
	{
		this.color = color;
	}
	
	public void select(Graphics2D gr) 
	{
		gr.setPaint(color);
	}

	public void setShape(Shape shape)
	{
		
	}

}
