package shipgames.pongbasic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import shipgames.Vector;

/**
 * Represents a Ball or simply a centered circle with a radius, velocity, speed
 * in pixels per second, a maximum speed, and a color.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Ball 
{

	// The current position of the ball.
	protected Vector center;
	// The position of the ball in the last frame.  
	protected Vector previous;
	// The velocity as a unit vector to use as direction.
	protected Vector velocity;
	// The speed or magnitude of the velocity of the ball
	// in pixels/second.
	protected float speed = 0f;
	// The maximum speed of the ball in pixels/second.
	protected float maxSpeed = 0f;
	// The radius of this ball in pixels.
	protected float radius = 0f;
	// The color of the ball.
	protected Color color;

	
	/**
	 * Initializes a ball with its center position, the direction
	 * (in degrees) of the velocity, the magnitude of the velocity, 
	 * the maximum allowed magnitude of velocity, and the color.
	 * 
	 * @param x => The initial position of the center on the x-axis
	 * @param y => The initial position of the center on the y-axis
	 * @param radius => The radius of the ball in pixels
	 * @param angle => The velocities direction in degrees
	 * @param speed => The velocity of the ball in pixels/second
	 * @param maxSpeed => The maximum allowed velocity in pixels/second
	 * @param color => The color of the ball
	 */
	public Ball(float x, float y, float radius, float angle, float speed, float maxSpeed, Color color)
	{
		this.center = new Vector(x, y);
		this.previous = new Vector(x, y);
		this.velocity = new Vector(angle);
		this.radius = radius;
		this.speed = speed;
		this.maxSpeed = maxSpeed;
		this.color = color;
	}
	
	/**
	 * Draws this balls tail and then itself with gradients.
	 */
	public void draw(Graphics2D gr)
	{
		final Ellipse2D.Float shape = new Ellipse2D.Float();
		
		float diameter = getDiameter();
		float x = getLeft();
		float y = getTop();
		shape.setFrame(x, y, diameter, diameter);
		
		gr.setColor(color);
		gr.fill(shape);
	}
	
	/**
	 * Updates the balls position by the velocity.
	 */
	public void update(float deltatime)
	{
		// Cap the velocity
		if (speed > maxSpeed)
			speed = maxSpeed;

		// Update position
		previous.set(center);
		center.add(velocity, speed * deltatime);
	}
	
	/**
	 * Resets the position of the ball, its initial direction, and its initial
	 * speed in pixels-per-second.
	 * 
	 * @param x => The new center x coordinate.
	 * @param y => The new center y coordinate.
	 * @param angle => The angle in degrees of the direction of the ball.
	 * @param speed => The speed of the ball in pixels-per-second.
	 */
	public void reset(float x, float y, float angle, float speed)
	{
		this.center.set(x, y);
		this.previous.set(x, y);
		this.velocity.set(angle);
		this.speed = speed;
	}
	
	/**
	 * Add a speed in pixels-per-second to the current speed of the ball.
	 * 
	 * @param moreSpeed => The speed to add in pixels-per-second.
	 */
	public void addSpeed(float moreSpeed)
	{
		speed += moreSpeed;
	}
	
	/**
	 * Checks the ball with the top boundary wall
	 * and returns true if it collided as well as
	 * setting the new direction of the ball.
	 * 
	 * @param top => The top of the boundary.
	 */
	public boolean checkTop(int top)
	{
		if (getTop() > top)
			return false;
		
		velocity.mirrorX();
		setTop(top);
		return true;
	}

	/**
	 * Checks the ball with the bottom boundary 
	 * wall and returns true if it collided as well
	 * as setting the new direction of the ball.
	 * 
	 * @param bottom => The bottom of the boundary.
	 */
	public boolean checkBottom(int bottom)
	{
		if (getBottom() < bottom)
			return false;
		
		velocity.mirrorX();
		setBottom(bottom);
		return true;
	}

	/**
	 * Determines whether this ball is completely
	 * past the left boundary wall.
	 * 
	 * @param left => The left of the boundary.
	 */
	public boolean pastLeft(float left)
	{
		return (getRight() <= left);
	}
	
	/**
	 * Determines whether this ball is completelty
	 * past the right boundary wall.
	 * 
	 * @param right => The right of the boundary.
	 */
	public boolean pastRight(float right)
	{
		return (getLeft() >= right);
	}
		
	/**
	 * Gets the center x value.
	 */
	public float getX()
	{
		return center.x;
	}
	
	/**
	 * Gets the center y value.
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
	 * Gets the x value for the left side of the ball.
	 */
	public float getLeft()
	{
		return center.x - radius;
	}

	/**
	 * Gets the x value for the right side of the ball.
	 */
	public float getRight()
	{
		return center.x + radius;
	}

	/**
	 * Gets the y value for the top side of the ball.
	 */
	public float getTop()
	{
		return center.y - radius;
	}

	/**
	 * Gets the y value for the bottom side of the ball.
	 */
	public float getBottom()
	{
		return center.y + radius;
	}

	/**
	 * Gets the radius of the ball in pixels.
	 */
	public float getRadius()
	{
		return radius;
	}

	/**
	 * Gets the diameter of the ball in pixels.
	 */
	public float getDiameter()
	{
		return radius * 2f;
	}

	/**
	 * Gets the normalized velocity of the ball.
	 */
	public Vector getVelocity()
	{
		return velocity;
	}

	/**
	 * Gets the speed of the ball in pixels/second.
	 */
	public float getSpeed()
	{
		return speed;
	}
	
	/**
	 * Sets the speed of the ball.
	 * 
	 * @param speed => Speed of the ball in pixels/second.
	 */
	public void setSpeed(float speed)
	{
		this.speed = Math.min(speed, maxSpeed);
	}

	/**
	 * Sets the positions x value.
	 * 
	 * @param x => The new x value.
	 */
	public void setX(float x)
	{
		center.x = x;
	}
	
	/**
	 * Sets the positions y value.
	 * 
	 * @param y => The new y value.
	 */
	public void setY(float y)
	{
		center.y = y;
	}

	/**
	 * Sets the y value for the top of this ball.
	 * 
	 * @param top => The new top of the ball.
	 */
	public void setTop(float top)
	{
		center.y = top + radius;
	}
	
	/**
	 * Sets the y value for the bottom of this ball.
	 * 
	 * @param bottom => The new bottom of the ball.
	 */
	public void setBottom(float bottom)
	{
		center.y = bottom - radius;
	}
	
	/**
	 * Sets the x value for the left of this ball.
	 * 
	 * @param left => The new left of this ball.
	 */
	public void setLeft(float left)
	{
		center.x = left + radius;
	}
	
	/**
	 * Sets the x value for the right of this ball.
	 * 
	 * @param right => The new right of this ball.
	 */
	public void setRight(float right)
	{
		center.x = right - radius;
	}
	
}
