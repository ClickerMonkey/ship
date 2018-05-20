package ship;

import java.awt.Graphics2D;


public interface ScreenListener
{

	public void draw(Graphics2D gr);
	
	public void update(TimeUnit elapsed);
	
}
