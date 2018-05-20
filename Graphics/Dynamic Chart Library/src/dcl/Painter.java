package dcl;

import java.awt.Graphics2D;

public abstract class Painter 
{

	public abstract void draw(Graphics2D gr);
	
	public abstract Settings getSettings();
	
	public abstract Series getSeries(int index);
	
}
