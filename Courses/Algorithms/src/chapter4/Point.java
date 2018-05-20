package chapter4;

/**
 * A point is any coordinate in a 2d world. The distance
 * (squared) can be calculated from one point to another.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Point
{
	double x = 0f;
	double y = 0f;
	
	/**
	 * Initializes a point located at the origin.
	 */
	public Point()
	{
	}
	
	/**
	 * Initializes a point with an initial x component
	 * and an initial y component.
	 * 
	 * @param x => The initial component along the x-axis.
	 * @param y => The initial component along the y-axis.
	 */
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the coordinates of this point to some random
	 * value between a minimum and maximum value.
	 */
	public void random(double min, double max)
	{
		double range = max - min;
		x = Math.random() * range + min;
		y = Math.random() * range + min;
	}
	
	
	/**
	 * Calculates the distance (squared) between this point and another.
	 */
	public double distance(Point p)
	{
		double dx = x - p.x;
		double dy = y - p.y;
		return (dx * dx + dy * dy);
	}

	/**
	 * Determines if this points components are equal
	 * to another points.
	 */
	public boolean isEqual(Point p)
	{
		return (p.x == x && p.y == y);
	}
	
	/**
	 * Returns a string representaion of this point in the
	 * format of {x, y}. Where x and y are precise to two
	 * decimal places.
	 */
	public String toString()
	{
		return String.format("{%.2f, %.2f}", x, y);
	}
	
	
} 