package shipgames.snake;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import shipgames.GraphicsUtil;
import shipgames.Vector;

/**
 * A link on a snake. This keeps track of the snake link behind (link towards
 * the tail of the snake) and before (link towards the head of the snake).
 * 
 * @author Philip Diffenderfer
 *
 */
public class SnakeLink
{
	
	// The location of this snake link.
	private Vector location;
	// The point this link is moving away from
	private Vector previous;
	// Gets the direction of the link last computed when getDirection was called.
	private Vector direction;
	// The snake link behind this one (link towards the tail of the snake).
	private SnakeLink behind;
	// The snake link before this one (link towards the head of the snake).
	private SnakeLink before;
	
	/**
	 * Initializes the head link 
	 */
	public SnakeLink(float x, float y)
	{
		location = new Vector(x, y);
		previous = new Vector(location);
		direction = new Vector();
		
		behind = before = null;
	}
	
	/**
	 * Creates a snake link after the tail and set its location to the tail.
	 * 
	 * @param tail => The tail of the snake
	 */
	public SnakeLink(SnakeLink tail) 
	{
		// Set this link as the link after the tail
		tail.behind = this;
		before = tail;
		behind = null;
		// Calculate this links location to be behind the tails
		location = new Vector(tail.location);
		previous = new Vector(location);
		direction = new Vector();
	}

	/**
	 * Draws this snake link.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void draw(Graphics2D gr, BufferedImage tilesheet, Rectangle source, float radius)
	{
		// Recursively call draw on the links before so this link overlaps all
		// of the preceeding links (towards the tail).
		if (behind != null)
			behind.draw(gr, tilesheet, source, radius);
		
		GraphicsUtil.drawSprite(gr, tilesheet, source, location.x, location.y, 
				radius * 2, radius * 2, getDirection().angleRadians());
	}
	
	/**
	 * This should be called by the tail of the snake when its read to move up
	 * to the next links path.
	 */
	public void moveUp()
	{
		if (before != null)
		{
			previous.set(before.previous);
			before.moveUp();
		}
	}
	
	/**
	 * Updates the location of this snake link based on the destination or the
	 * location of the next snake link.
	 * 
	 * @param direction => The destination to be separation away from.
	 * @param speed => The separation in pixels to maintain.
	 */
	public void update(Vector target, float delta)
	{
		// If this is not the head of the snake then update its position on the
		// line between this links previous and the link before previous.
		float dx = target.x - previous.x;
		float dy = target.y - previous.y;
		location.x = dx * delta + previous.x;
		location.y = dy * delta + previous.y;
		
		// Update the link behind this one
		if (behind != null)
			behind.update(previous, delta);
		
	}

	/**
	 * Returns the direction of this link as a unit vector.
	 */
	public Vector getDirection()
	{
		// Dont calculate the direction if the location and previous position is
		// the same
		if (location.x == previous.x && location.y == previous.y)
			return direction;
		
		direction.set(location);
		direction.subtract(previous);
		direction.normalize();
		return direction;
	}
	
	/**
	 * Gets the location of this Snake link.
	 */
	public Vector getLocation()
	{
		return location;
	}

	/**
	 * Gets the previous location of this Snake link.
	 */
	public Vector getPrevious()
	{
		return previous;
	}
	
	/**
	 * Gets the previous snake link (link towards the tail of the snake).
	 */
	public SnakeLink getBehind()
	{
		return behind;
	}
	
}
