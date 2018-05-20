package shipgames;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GraphicsUtil
{

	public static final AffineTransform IDENTITY = new AffineTransform();
	
	
	
	public static void drawSprite(Graphics2D gr, BufferedImage tilesheet, Rectangle source, float x, float y, float width, float height)
	{
		int halfWidth = (int)(width * 0.5);
		int halfHeight = (int)(height * 0.5);
		
		gr.translate(x, y);
		gr.drawImage(tilesheet, -halfWidth, -halfHeight, halfWidth, halfHeight, 
				source.x, source.y, source.x + source.width, source.y + source.height, null);
		gr.setTransform(IDENTITY);
	}
	
	public static void drawSprite(Graphics2D gr, BufferedImage tilesheet, Rectangle source, float x, float y, float width, float height, float radians)
	{
		int halfWidth = (int)(width * 0.5);
		int halfHeight = (int)(height * 0.5);

		gr.translate(x, y);
		gr.rotate(radians);
		gr.drawImage(tilesheet, -halfWidth, -halfHeight, halfWidth, halfHeight, 
				source.x, source.y, source.x + source.width, source.y + source.height, null);
		gr.setTransform(IDENTITY);
	}
	
}
