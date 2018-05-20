package finalproject;

/**
 * A list of points that represent a closed polygon.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Polygon 
{

	// The array of connecting points that form a closed polygon.
	private Vector points[];
	// The number of points in the polygon.
	private int total;
	
	/**
	 * Initializes a polygon with an initial capacity of points.
	 * 
	 * @param count => The initial capacity of points.
	 */
	public Polygon(int count)
	{
		points = new Vector[count];
		total = 0;
	}
	
	/**
	 * Initializes a polygon with an initial set of points.
	 * 
	 * @param points => The points of the new polygon.
	 */
	public Polygon(Vector[] points)
	{
		this.points = points;
		this.total = points.length;
	}
	
	/**
	 * Sets the point at the given index.
	 * 
	 * @param point => The index of the point in the polygon.
	 * @param x => The new x value of that point.
	 * @param y => The new y value of that point.
	 */
	public void set(int point, double x, double y)
	{
		// You can't set a point thats not in the polygon
		if (point >= total)
			return;
		
		points[point].set(x, y);
	}
	
	/**
	 * Returns a point in the polygon at the given location in a circular 
	 * fashion so any index larger then the number of points in the polygon the
	 * index is wrapped around to the beginning.
	 * 
	 * @param point => The index of the point to get.
	 */
	public Vector get(int point)
	{
		return points[point % total];
	}

	/**
	 * Adds a vector to the polygon that's connected to the last and first
	 * points in the polygon.
	 * 
	 * @param v => The point to add.
	 */
	public void add(Vector v)
	{
		add(v.x, v.y);
	}
	
	/**
	 * Adds the vector to the polygon that's connected to the last and first
	 * points in the polygon.
	 * 
	 * @param x => The x value of the point to add.
	 * @param y => The y value of the point to add.
	 */
	public void add(double x, double y)
	{
		// If the polygon has met its maximum capacity increase it.
		if (total == points.length)
		{
			Vector old[] = points;
			// Double the capacity of the polygon
			points = new Vector[total << 1];
			
			for (int i = 0; i < total; i++)
				points[i] = old[i];
		}
		
		points[total] = new Vector(x, y);
		total++;
	}
	
	/**
	 * Returns the array of points that make up this polygon.
	 */
	public Vector[] getPoints()
	{
		return points;
	}

	/**
	 * Returns the number of points in this polygon.
	 */
	public int getSize()
	{
		return total;
	}
	
}
