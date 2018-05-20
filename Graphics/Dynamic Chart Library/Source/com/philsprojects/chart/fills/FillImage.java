package com.philsprojects.chart.fills;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FillImage implements Fill 
{

	public TexturePaint texture;
	
	public FillImage(BufferedImage image, Rectangle2D source)
	{
		texture = new TexturePaint(image, source);
	}
	
	public void select(Graphics2D gr) 
	{
		gr.setPaint(texture);
	}

	public void setShape(Shape shape)
	{
	}

}
