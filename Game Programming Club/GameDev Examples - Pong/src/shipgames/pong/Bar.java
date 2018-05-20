package shipgames.pong;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Represents a Bar or simply a centered box with dimensions, color and transparency.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Bar extends shipgames.pongbasic.Bar
{

	// The transparency of this bar.
	private float alpha = 1f;
	
	/**
	 * Initializes a bar centered with a certain size.
	 * 
	 * @param centerX => The center x coordinate of the bar.
	 * @param centerY => The center y coordinate of the bar.
	 * @param width => The width in pixels of the bar.
	 * @param height => The height in pixels of the bar.
	 * @param shade => The color of the bar.
	 */
	public Bar(float centerX, float centerY, float width, float height, Color shade)
	{
		super(centerX, centerY, width, height, shade);
	}
	
	/**
	 * Draws the Bar with a color and several gradients.
	 */
	public void draw(Graphics2D gr)
	{
		if (alpha == 0f)
			return;
		
		Shape s = new Rectangle2D.Double(getLeft(), getTop(), getWidth(), getHeight());
		
		int alpha = (int)(this.alpha * 255.0) % 256;
		Color shade = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
		Color transparent = new Color(0, 0, 0, 0);
		Color black = new Color(0, 0, 0, alpha);
		Color white = new Color(255, 255, 255, alpha);
		
		gr.setColor(shade);
		gr.fill(s);
		s = new Rectangle2D.Double(getLeft(), getTop(), getHalfWidth(), getHeight());
		gr.setPaint(new GradientPaint(
				(center.x - halfWidth), 
				center.y, transparent, 
				center.x, 
				center.y, black));
		gr.fill(s);
		s = new Rectangle2D.Double(getX(), getTop(), getHalfWidth(), getHeight());
		gr.setPaint(new GradientPaint(
				center.x, 
				center.y, black, 
				(center.x + halfWidth), 
				center.y, transparent));
		gr.fill(s);
		
		s = new Rectangle2D.Double(getLeft() + 3, getTop(), getWidth() - 6, getHeight());	
		gr.setPaint(new GradientPaint(
				center.x, 
				(center.y - halfHeight), white, 
				center.x, 
				center.y, transparent));
		gr.fill(s);		
		gr.setPaint(new GradientPaint(
				center.x, 
				(center.y + halfHeight), white, 
				center.x, 
				center.y, transparent));
		gr.fill(s);
	}
	
	public void setVisible(boolean visible)
	{
		alpha = (!visible ? 0f : 1f);
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	
	public boolean getVisible()
	{
		return (alpha != 0f);
	}
	
}
