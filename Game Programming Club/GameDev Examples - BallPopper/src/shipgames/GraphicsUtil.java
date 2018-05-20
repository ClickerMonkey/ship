package shipgames;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * A utility class to provide methods of drawing sprites.
 * 
 * @author Philip Diffenderfer
 *
 */
public class GraphicsUtil
{

	/**
	 * Draws a sprite to the given graphics object given the source tilesheet of
	 * the sprites image, the rectangle on the image to grab the tile from, and
	 * the destination dimensions of the sprite.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param tilesheet => The image containing sprite tiles.
	 * @param source => The source rectangle on the tilesheet of the sprites tile.
	 * @param x => The center x-coordinate of the sprite.
	 * @param y => The center y-coordinate of the sprite.
	 * @param width => The width of the sprite in pixels.
	 * @param height => The height of the sprite in pixels.
	 */
	public static void drawSprite(Graphics2D gr, BufferedImage tilesheet, Rectangle source, float x, float y, float width, float height)
	{
		int halfWidth = (int)(width * 0.5);
		int halfHeight = (int)(height * 0.5);

		AffineTransform state = gr.getTransform();
		
		gr.translate(x, y);
		gr.drawImage(tilesheet, -halfWidth, -halfHeight, halfWidth, halfHeight, 
				source.x, source.y, source.x + source.width, source.y + source.height, null);
		
		// Restore the previous transform.
		gr.setTransform(state);
	}
	
	/**
	 * Draws a rotated sprite to the given graphics object given the source 
	 * tilesheet of the sprites image, the rectangle on the image to grab the 
	 * tile from, and the destination dimensions of the sprite.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param tilesheet => The image containing sprite tiles.
	 * @param source => The source rectangle on the tilesheet of the sprites tile.
	 * @param x => The center x-coordinate of the sprite.
	 * @param y => The center y-coordinate of the sprite.
	 * @param width => The width of the sprite in pixels.
	 * @param height => The height of the sprite in pixels.
	 * @param radians => The angle of the sprite in radians.
	 */
	public static void drawSprite(Graphics2D gr, BufferedImage tilesheet, Rectangle source, float x, float y, float width, float height, float radians)
	{
		int halfWidth = (int)(width * 0.5);
		int halfHeight = (int)(height * 0.5);

		AffineTransform state = gr.getTransform();
		
		gr.translate(x, y);
		gr.rotate(radians);
		gr.drawImage(tilesheet, -halfWidth, -halfHeight, halfWidth, halfHeight, 
				source.x, source.y, source.x + source.width, source.y + source.height, null);
		
		// Restore the previous transform.
		gr.setTransform(state);
	}
	
}
