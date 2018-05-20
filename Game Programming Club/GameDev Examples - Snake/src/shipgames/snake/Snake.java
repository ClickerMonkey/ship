package shipgames.snake;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import shipgames.Util;
import shipgames.Vector;

public class Snake
{

	// The radius of the snake links.
	private float radius;
	// The amount of separation in pixels between each link.
	private float separation;
	// The speed of the snake in pixels-per-second.
	private float speed;
	// The speed for the snake to turn in radians-per-second.
	private float turnSpeed;
	// The length of the snake in links.
	private int length;
	// The target location on the map
	private Vector target;
	// The unit vector pointing towards the target
	private Vector direction;

	// The source rectangle of the snake's body from the tilesheet
	private Rectangle source;
	// The image containing the snake's body.
	private BufferedImage tilesheet;
	
	// The head of the snake. Used for updating link locations.
	private SnakeLink head;
	// The tail of the snake. Used for adding more links.
	private SnakeLink tail;
	
	
	/**
	 * Initializes a snake at its initial position for the first game and sets 
	 * its speed in pixels-per-second and the amount of separation between the
	 * links in pixels and the radius of each link.
	 * 
	 * @param x => The starting x value of the snake's head.
	 * @param y => The starting y value of the snake's head.
	 * @param speed => The speed of the snake in pixels-per-second.
	 * @param separation => The amount of separation to maintain between every 
	 * 		link in pixels.
	 * @param radius => The radius of each link in pixels.
	 * @param maxTurnSpeed => The maxmimum number of radians the head of the snake
	 * 		can rotate in a second.
	 */
	public Snake(BufferedImage tilesheet, Rectangle source, float x, float y, float speed, float separation, float radius, float maxTurnSpeed)
	{
		this.reset(x, y);
		this.tilesheet = tilesheet;
		this.source = source;
		this.speed = speed;
		this.separation = separation;
		this.radius = radius;
		this.turnSpeed = maxTurnSpeed;
		this.target = new Vector();
		this.direction = new Vector();
	}
	
	/**
	 * Resets the snake to a new position and with a length of 1.
	 * 
	 * @param x => The new x value of the snake's head.
	 * @param y => The new y value of the snakes. head.
	 */
	public void reset(float x, float y)
	{
		head = new SnakeLink(x, y);
		tail = head;
		length = 1;
	}

	/**
	 * Draws all the links of the snake.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	void draw(Graphics2D gr)
	{
		head.draw(gr, tilesheet, source, radius);
		
		// Draw the direction line and heading line
//		gr.setStroke(new BasicStroke(3f));
//		gr.setColor(Color.black);
//		gr.draw(new Line2D.Float(head.getLocation().x, head.getLocation().y, head.getLocation().x + direction.x * 40, head.getLocation().y + direction.y * 40));
//		Vector heading = head.getDirection();
//		gr.setColor(Color.magenta);
//		gr.draw(new Line2D.Float(head.getLocation().x, head.getLocation().y, head.getLocation().x + heading.x * 40, head.getLocation().y + heading.y * 40));
//		gr.setStroke(new BasicStroke(1f));
	}
	
	/**
	 * Updates the snakes position based on the destination direction.
	 *  
	 * @param deltatime => The time in seconds since the las update.
	 */
	public void update(float deltatime)
	{	
		// Compute the direction based on the target
		direction.set(target);
		direction.subtract(head.getLocation());
		direction.normalize();
		
		// Compute the direction of the snakes head based on the target direction
		// and the turning speed of the snake.
		Vector heading = getHeading(turnSpeed * deltatime);
		// Put the heading separation in front of the snakes previous position.
		heading.multiply(separation);
		heading.add(head.getPrevious());
		
		// The amount to move in pixels this frame
		float move = speed * deltatime;
		// The distance the head is from its previous position.
		float distance = head.getLocation().distance(head.getPrevious());
		// The percent between the new location of the head is between the previous
		// location and the location separation away.
		float delta = (move + distance) / separation;
		
		// If its over 1 then each link is going to shift up on the next line.
		if (delta >= 1f)
		{
			// Change each links previous
			tail.moveUp();
			head.getPrevious().set(heading);
			// Make each link the full distance away from its previous
			head.update(heading, 1f);
			// Correct the delta
			delta -= 1f;
		}
		
		// Update the link to move towards the one before it.
		head.update(heading, delta);
	}
	
	/**
	 * Adds a certain number of links to the snake.
	 * 
	 * @param links => The number of links to add.
	 */
	public void addLinks(int links)
	{
		// For all the links to add
		for (int l = 0; l < links; l++)
		{
			// Set the tails previous to the new link then reset the tail
			// as the new link added. 
			tail = new SnakeLink(tail);
		}
		
		length += links;
	}
	
	/**
	 * Adds speed in pixels-per-second to this Snake.
	 * 
	 * @param amount => The amount to add in pixels-per-second.
	 */
	public void addSpeed(float amount)
	{
		speed += amount;
	}
	
	public void addSeparation(float amount)
	{
		separation += amount;
	}
	
	/**
	 * Gets the next location for the snakes head based on the target direction
	 * and the max turning speed in radians-per-second.
	 */
	public Vector getHeading(float maxTurnRate)
	{
		// If its just the snake head then follow the direction towards the
		// target exactly
		if (head.getBehind() == null)
			return direction;
		
		Vector heading = head.getBehind().getDirection();
		// If the direction has no length then use the target (this occurs when
		// the head of the snake is still at its starting position).
		if (heading.distanceSq() == 0f)
			return direction;
		
		// This must be checked for over 1 and below -1, or acos will return NaN
		float dot = Util.clamp(heading.dot(direction), -1, 1);
		// The angle in radians between the direction of the head and target
		float angle = (float)Math.acos(dot);
		// If the angle is small just return the heading
		if (angle < 0.00001)
			return heading;
		// Clamp the turn amount
		if (angle > maxTurnRate)
			angle = maxTurnRate;
		// Rotate direction by angle
		int sign = heading.sign(direction);
		heading.rotateRadians(angle * sign);
		
		return heading;
	}
	
	/**
	 * Sets the target to move towards that determines the direction of the snake.
	 * 
	 * @param x => The x value of the target position.
	 * @param y => The y value to the target position.
	 */
	public void setTarget(int x, int y)
	{
		target.set(x, y);
	}
	
	/**
	 * Returns the head of the snake.
	 */
	public SnakeLink getHead()
	{
		return head;
	}

	/**
	 * Returns the tail of the snake.
	 */
	public SnakeLink getTail()
	{
		return tail;
	}

	/**
	 * Returns the number of links in this snake.
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * Returns the amount of separation between each links center in pixels.
	 */
	public float getSeparation()
	{
		return separation;
	}

	/**
	 * Returns the radius of each link in pixels.
	 */
	public float getRadius()
	{
		return radius;
	}

	/**
	 * Returns the speed of the snake in pixels-per-second.
	 */
	public float getSpeed()
	{
		return speed;
	}

	/**
	 * Returns the maximum turning speed of the snake in radians-per-second.
	 */
	public float getTurnSpeed()
	{
		return turnSpeed;
	}

	/**
	 * Returns a unit vector pointing towards where the snake wants to go (mouse).
	 */
	public Vector getTarget()
	{
		return target;
	}
	
}
