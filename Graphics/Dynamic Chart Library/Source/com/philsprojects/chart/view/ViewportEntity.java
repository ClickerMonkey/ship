package com.philsprojects.chart.view;

import java.awt.Graphics2D;


public interface ViewportEntity 
{

	public void draw(Graphics2D gr, Viewport view, Canvas destination);
	
}
