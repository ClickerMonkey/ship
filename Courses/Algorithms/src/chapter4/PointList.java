package chapter4;

/**
 * A point list is not an actual list but rather a subset
 * of some list of points. This list keeps track of the beginning
 * and the end of the subset from the source set of points. This
 * list also maintains a delta (minimum distance calculated between
 * all the points in this list) and the closest pair in this set.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PointList
{

	private double delta;
	private Point[] closestPair;
	private int firstPoint;
	private int lastPoint;
	private Point[] source;
	
	/**
	 * Creates a point list which is a subset of an array
	 * of points. A point list represents a section of points
	 * contained in the list specified by starting and ending
	 * indices.
	 * 
	 * @param sourcePoints => The source points to pull from.
	 */
	public PointList(Point[] sourcePoints)
	{
		source = sourcePoints;
		delta = Double.MAX_VALUE;
		closestPair = new Point[2];
		firstPoint = 0;
		lastPoint = source.length - 1;
	}
	
	/**
	 * Gets a point in this list at a specified index.
	 * 
	 * @param index => A number between 0 and getSize() - 1.
	 */
	public Point get(int index)
	{
		int actual = firstPoint + index;
		
		return source[actual];
	}
	
	/**
	 * Returns the number of points contained in this list.
	 */
	public int getSize()
	{
		return (lastPoint - firstPoint + 1);
	}
	
	/**
	 * Returns a pointlist split from this where the
	 * first point in the returned list is the point
	 * at 'start' and the last point in the list is at
	 * 'last'.
	 * 
	 * @param first => The index of the first point. 
	 * 		Between 0 and getSize() - 1.
	 * @param last => The index of the last point.
	 * 		Between 0 and getSize() - 1, greater then
	 * 		or equal to first.
	 */
	public PointList split(int first, int last)
	{
		PointList list = new PointList(source);
		// Reset the first and last points
		list.firstPoint = firstPoint + first;
		list.lastPoint = firstPoint + last;
		
		return list;
	}
	
	/**
	 * Determines if this list contains the given Point.
	 * 
	 * @param p => The given point to test for containment.
	 */
	public boolean contains(Point p)
	{
		for (int i = firstPoint; i <= lastPoint; i++)
			if (source[i].isEqual(p))
				return true;
		
		return false;
	}
	
	/**
	 * Sets the closest pair in this list.
	 * 
	 * @param a => The first point.
	 * @param b => The second point.
	 */
	public void setClosestPair(Point a, Point b)
	{
		closestPair[0] = a;
		closestPair[1] = b;
	}
	
	/**
	 * Gets the delta (min distance) contained thus
	 * far in the search for this point list.
	 */
	public double getDelta()
	{
		return delta;
	}
	
	/**
	 * Sets the delta (min distance) calculated in
	 * this point list thus far.
	 */
	public void setDelta(double d)
	{
		delta = d;
	}

	/**
	 * Gets the closest pair in this list.
	 */
	public Point[] getClosestPair() 
	{
		return closestPair;
	}
	
	
	
	
}
