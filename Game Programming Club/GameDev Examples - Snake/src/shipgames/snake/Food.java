package shipgames.snake;

import java.awt.Graphics2D;

import shipgames.Vector;

/**
 * Any type of food the snake can eat on the game field.
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class Food 
{

	private Vector location;
	private float radius;
	
	/**
	 * Occurs when the snake eats this food.
	 * 
	 * @param s => The snake that ate this food.
	 */
	public abstract void eaten(Snake s);
	
	/**
	 * Draws this food on the field.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public abstract void draw(Graphics2D gr);
	
	
	public boolean isEaten(Snake s)
	{
		return true;
	}
	
	/**
	 * Returns the center of this food. 
	 */
	public Vector getLocation()
	{
		return location;
	}
	
	/**
	 * Returns the bounding radius of this food in pixels.
	 */
	public float getRadius()
	{
		return radius;
	}
	
}
