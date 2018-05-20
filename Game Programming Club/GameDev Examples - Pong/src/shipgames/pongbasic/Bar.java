package shipgames.pongbasic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import shipgames.Vector;

/**
 * Represents a Bar or simply a centered box with dimensions and color.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Bar
{

	// The center point of the bar.
	protected Vector center;
	// The last center point of the bar.
	protected Vector previous;
	// Half of the width of this bar.
	protected float halfWidth;
	// Half of the height of this bar.
	protected float halfHeight;
	// The color of this bar
	protected Color color;
	
	/**
	 * Initializes a bar centered with a certain size.
	 * 
	 * @param centerX => The center x coordinate of the bar.
	 * @param centerY => The center y coordinate of the bar.
	 * @param width => The width in pixels of the bar.
	 * @param height => The height in pixels of the bar.
	 */
	public Bar(float centerX, float centerY, float width, float height, Color shade)
	{
		center = new Vector(centerX, centerY);
		previous = new Vector(centerX, centerY);
		halfWidth = width * 0.5f;
		halfHeight = height * 0.5f;
		color = shade;
	}
	
	/**
	 * Draws the Bar with a color and several gradients.
	 */
	public void draw(Graphics2D gr)
	{
		gr.setColor(color);
		gr.fill(new Rectangle2D.Float(getLeft(), getTop(), getWidth(), getHeight()));
	}
	
	/**
	 * This will 'clip' the top of this bar. If the top of this bar goes pass
	 * 'top' then the bar will be pushed back so it doesn't pass 'top'.
	 */
	public void clipTop(float top)
	{
		if (getTop() < top)
			setTop(top);
	}

	/**
	 * This will 'clip' the bottom of this bar. If the bottom of this bar goes pass
	 * 'bottom' then the bar will be pushed back so it doesn't pass 'bottom'.
	 */
	public void clipBottom(float bottom)
	{
		if (getBottom() > bottom)
			setBottom(bottom);
	}

	/**
	 * This will 'clip' the left of this bar. If the left of this bar goes pass
	 * 'left' then the bar will be pushed back so it doesn't pass 'left'.
	 */
	public void clipLeft(float left)
	{
		if (getLeft() < left)
			setLeft(left);
	}

	/**
	 * This will 'clip' the right of this bar. If the right of this bar goes pass
	 * 'right' then the bar will be pushed back so it doesn't pass 'right'.
	 */
	public void clipRight(float right)
	{
		if (getRight() > right)
			setRight(right);
	}
	
	/**
	 * This will return true if this bar intersects with bounds specified as the
	 * four components of left, right, top, and bottom.
	 * 
	 * @param left => A value on the x-axis less then right.
	 * @param right => A value on the x-axis greater then left.
	 * @param top => A value on the y-axis less then bottom.
	 * @param bottom => A value on the y-axis greater then top.
	 */
	public boolean intersects(double left, double right, double top, double bottom)
	{
		return !( left > getRight() || right < getLeft() ||
		          top > getBottom() || bottom < getTop());
	}
	
	/**
	 * Sets the top side of this bar.
	 */
	public void setTop(float top)
	{
		previous.y = center.y;
		center.y = top + halfHeight;
	}
	
	/**
	 * Sets the bottom side of this bar.
	 */
	public void setBottom(float bottom)
	{
		previous.y = center.y;
		center.y = bottom - halfHeight;
	}
	
	/**
	 * Sets the left side of this bar.
	 */
	public void setLeft(float left)
	{
		previous.x = center.x;
		center.x = left + halfWidth;
	}
	
	/**
	 * Sets the right side of this bar.
	 */
	public void setRight(float right)
	{
		previous.x = center.x;
		center.x = right - halfWidth;
	}
	
	/**
	 * Sets the center x position of this bar.
	 */
	public void setX(float x)
	{
		previous.x = center.x;
		center.x = x;
	}

	/**
	 * Sets the center y position of this bar.
	 */
	public void setY(float y)
	{
		previous.y = center.y;
		center.y = y;
	}
	
	/**
	 * Sets the width of this bar in pixels.
	 */
	public void setWidth(float width)
	{
		halfWidth = width * 0.5f;
	}
	
	/**
	 * Sets the height of this bar in pixels.
	 */
	public void setHeight(float height)
	{
		halfHeight = height * 0.5f;
	}
	
	/**
	 * Gets the top of this bar (pixels on y-axis from origin).
	 */
	public float getTop()
	{
		return center.y - halfHeight;
	}
	
	/**
	 * Gets the bottom of this bar (pixels on y-axis from origin).
	 */
	public float getBottom()
	{
		return center.y + halfHeight;
	}
	
	/**
	 * Gets the left of this bar (pixels on x-axis from origin).
	 */
	public float getLeft()
	{
		return center.x - halfWidth;
	}
	
	/**
	 * Gets the right of this bar (pixels on x-axis from origin).
	 */
	public float getRight()
	{
		return center.x + halfWidth;
	}

	/**
	 * Gets the center x value of this bar (pixels on x-axis from origin).
	 */
	public float getX()
	{
		return center.x;
	}

	/**
	 * Gets the center y value of this bar (pixels on y-axis from origin).
	 */
	public float getY()
	{
		return center.y;
	}
	
	/**
	 * Gets the last center x value before the last bar move.
	 */
	public float getLastX()
	{
		return previous.x;
	}
	
	/**
	 * Gets the last center y value before the last bar move.
	 */
	public float getLastY()
	{
		return previous.y;
	}
	
	/**
	 * Gets the width of this bar in pixels.
	 */
	public float getWidth()
	{
		return halfWidth * 2;
	}
	
	/**
	 * Gets the height of this bar in pixels. 
	 */
	public float getHeight()
	{
		return halfHeight * 2;
	}
	
	/**
	 * Gets half the width of this bar in pixels.
	 */
	public float getHalfWidth()
	{
		return halfWidth;
	}
	
	/**
	 * Gets half the height of this bar in pixels.
	 */
	public float getHalfHeight()
	{
		return halfHeight;
	}	

	/**
	 * Gets the color of this bar.
	 */
	public Color getColor()
	{
		return color;
	}
	
	
}
