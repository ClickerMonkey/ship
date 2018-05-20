package shipgames.tetrisbasic;

/**
 * A point on the tetris field.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Point 
{

	// This point's x component
	public int x;
	// This point's y component
	public int y;
	
	/**
	 * Initializes a point located at the origin.
	 */
	public Point()
	{
		x = y = 0;
	}
	
	/**
	 * Initializes a point on the tetris field.
	 * 
	 * @param x => The x value, or column on the field.
	 * @param y => The y value, or row on the field.
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Initializes a point on the tetris field.
	 * 
	 * @param p => The point to copy from on the field.
	 */
	public Point(Point p)
	{
		x = p.x;
		y = p.y;
	}
	
	/**
	 * Sets this point's components.
	 * 
	 * @param x => The new x component.
	 * @param y => The new y component.
	 */
	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets this point's components based on anothers.
	 * 
	 * @param p => The point to copy from.
	 */
	public void set(Point p)
	{
		x = p.x;
		y = p.y;
	}
	
}
