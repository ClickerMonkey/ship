package gerry.draw;

import gerry.Viewport;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface Drawer 
{
	
	public Viewport getView();
	public void setViewport(Viewport view);
	
	public BufferedImage create();
	public BufferedImage create(Viewport view);
	
	public void save(File location) throws IOException;
	
	public Color getColor(int x);
	public void setColor(int x, Color c);
	public boolean hasColor(int x);
	
}

