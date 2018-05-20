package shipgames.tron;

/**
 * This point is a 2-dimensional point that is part of a path. It has a next
 * point that its pointing to.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Point 
{

	/**
	 * The x value of this point.
	 */
	public int x;
	/**
	 * The y value of this point.
	 */
	public int y;
	
	// The next point in a path of points.
	private Point next;
	
	/**
	 * Initializes a point at its coordinates.
	 * 
	 * @param x => The x value of this point.
	 * @param y => The y value of this point.
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets this points coordinates.
	 * 
	 * @param x => The x value of this point.
	 * @param y => The y value of this point.
	 */
	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets the next point in a path of points.
	 * 
	 * @param next => The next point.
	 */
	public void setNext(Point next)
	{
		this.next = next;
	}
	
	/**
	 * Gets the next point in a path of points.
	 */
	public Point getNext()
	{
		return next;
	}
	
}
