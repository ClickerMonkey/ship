package shipgames.tron;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bike 
{

	// The fastest way to update the position based on direction is taking 
	// advantage of binary. The first 2 bits are used for x direction and the
	// second 2 bits are used for y direction where:
	// '00' - 1 = -1   --> Go in the negative direction on this axis
	// '01' - 1 =  0   --> Do not move on this axis
	// '10' - 1 =  1   --> Go in the positive direction on this axis
	//
	public static final int WEST  = 1; // 0001  x = 00  y = 01 
	public static final int NORTH = 4; // 0100  x = 01  y = 00
	public static final int EAST  = 9; // 1001  x = 10  y = 01
	public static final int SOUTH = 6; // 0110  x = 01  y = 10

	// The 2 bits in a number that determinss the direction on the x-axis.
	public static final int X_VALUE = 12; // 1100
	// The 2 bits in a number that determines the direction on the y-axis.
	public static final int Y_VALUE = 3;  // 0011
	
	
	// The direction of this bike.
	private int direction;
	// The x value for the front of this bike
	private float x;
	// The y value for the front of this bike
	private float y;
	// The speed in pixels-per-second of this bike
	private float speed;
	// The path this back has driven since the start
	private Path path;
	

	/**
	 * Initializes a Bike at a position with an initial direction and a starting
	 * speed in pixels-per-second as well as its path's color and width in pixels.
	 * 
	 * @param startX => The starting x value of this bike.
	 * @param startY => The starting y value of this bike.
	 * @param initialDirection => The initial direction of this bike.
	 * @param startSpeed => The start speed of this bike in pixels-per-second.
	 * @param pathColor => The color of this bike's path.
	 * @param pathWidth => The width of this bike's path in pixels.
	 */
	public Bike(int startX, int startY, int initialDirection, float startSpeed, Color pathColor, int pathWidth)
	{
		x = startX;
		y = startY;
		direction = initialDirection;
		speed = startSpeed;
		path = new Path(startX, startY, pathColor, pathWidth);
	}
	
	/**
	 * Resets the Bike to a new point with a new direction as well as reseting
	 * its path back to zero.
	 * 
	 * @param startX => The starting x value of this bike.
	 * @param startY => The starting x value of this bike.
	 * @param initialDirection => The initial direction of this bike.
	 */
	public void reset(int startX, int startY, int initialDirection)
	{
		x = startX;
		y = startY;
		direction = initialDirection;
		path.reset(startX, startY);
	}
	
	/**
	 * Updates the position of this bike based on its direction and speed.
	 * 
	 * @param deltatime => The time in seconds since the last update.
	 */
	public void update(float deltatime)
	{
		// The distance to go in pixels this frame
		float diff = speed * deltatime;
		// Update the position of this bike
		x += getXDirection() * diff;
		y += getYDirection() * diff;
		// Update the last point in the path
		path.getLast().set((int)x, (int)y);
	}
	
	/**
	 * Draws this bike's path.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void draw(Graphics2D gr)
	{
		path.draw(gr);
	}
	
	/**
	 * Gets the direction on the x-axis of the direction.
	 */
	public int getXDirection()
	{
		return ((direction & X_VALUE) >> 2) - 1;
	}

	/**
	 * Gets the direction on the y-axis of the direction.
	 */
	public int getYDirection()
	{
		return (direction & Y_VALUE) - 1;
	}
	
	/**
	 * Determines if this bike has hit (intersects) with its own path.
	 */
	public boolean hitsOwnPath()
	{
		return path.intersects(path.getLast(), path.getPrevious(), true);
	}
	
	/**
	 * Determines if this bike has hit (intersects) this specific path.
	 * 
	 * @param p => The path to test against.
	 */
	public boolean hitsPath(Path p)
	{
		return p.intersects(path.getLast(), path.getPrevious(), false);
	}
	
	/**
	 * Sets the direction of the bike and adds a point to the path only if
	 * the direction is a valid direction and is different from the current.
	 * 
	 * @param dir => The new direction.
	 */
	public void setDirection(int dir)
	{
		if (dir == direction || !validDirection(dir))
			return;
		
		// Make sure it can't go over itself
		if (dir == EAST && direction == WEST ||
			dir == WEST && direction == EAST ||
			dir == NORTH && direction == SOUTH ||
			dir == SOUTH && direction == NORTH)
			return;
		
		path.addPoint(new Point((int)x, (int)y));
		direction = dir;
	}
	
	/**
	 * Make sure the direction given has a valid direction. No tricky stuff!
	 */
	private boolean validDirection(int dir)
	{
		int x = ((dir & X_VALUE) >> 2) - 1;
		int y = (dir & Y_VALUE) - 1;
		// Make sure its vertical OR horizontal
		return ((x == 0 && y != 0) || (x != 0 && y == 0));
	}

	/**
	 * Gets the direction of this bike.
	 */
	public int getDirection()
	{
		return direction;
	}

	/**
	 * Gets the speed of this bike in pixels-per-second.
	 */
	public float getSpeed()
	{
		return speed;
	}
	
	/**
	 * Gets the path of this bike.
	 */
	public Path getPath()
	{
		return path;
	}

	
}
