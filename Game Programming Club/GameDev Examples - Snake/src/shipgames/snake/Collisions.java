package shipgames.snake;

import shipgames.Bounds;
import shipgames.Vector;

public final class Collisions 
{

	private Collisions()
	{
	}

	/**
	 * Returns true if the circle is inside the given bounds. Touching the edges
	 * of the bounds is considered inside.
	 * 
	 * @param bounds => The bounds to use.
	 * @param center => The center of the circle.
	 * @param radius => The radius of the circle.
	 */
	public static boolean inside(Bounds bounds, Vector center, float radius)
	{
		return !(center.x - radius < bounds.getX() ||
				 center.x + radius > bounds.getRight() ||
				 center.y - radius < bounds.getY() ||
				 center.y + radius > bounds.getBottom());
	}
	
	/**
	 * Returns true if two circles are touching or intersecting.
	 * 
	 * @param center1 => The center of the first circle.
	 * @param radius1 => The radius of the first circle.
	 * @param center2 => The center of the second circle.
	 * @param radius2 => The radius of the second circle.
	 */
	public static boolean intersects(Vector center1, float radius1, Vector center2, float radius2)
	{
		float dx = center1.x - center2.x;
		float dy = center1.y - center2.y;
		float dr = radius1 + radius2;
		return (dx * dx + dy * dy <= dr * dr);
	}
	
	/**
	 * Returns true if the circle intersects with the bounds.
	 */
	public static boolean intersects(Bounds bounds, Vector center, float radius)
	{
		// Find a point on the bounds or within the rectangle
		// that is the closest possible point.
		float closestX = center.x;
		float closestY = center.y;

		// Clamp the closest point based on the rectangle's bounds.
		if (closestX > bounds.getRight())
			closestX = bounds.getRight();
		if (closestX < bounds.getX())
			closestX = bounds.getX();
		if (closestY < bounds.getY())
			closestY = bounds.getY();
		if (closestY > bounds.getBottom())
			closestY = bounds.getBottom();
		
		// The difference between the closest point and the circle
		float dx = closestX - center.x;
		float dy = closestY - center.y;
		
		// If the difference is zero, the circle is inside the rectangle.
		if (dx == 0f && dy == 0f)
			return true;
		
		// If the distance from the closest point on the bar to the circle's 
		// center is less than the circle's radius then there is an edge of
		// intersection. If the distance is equal to the radius then there was
		// an intersection at one point.
		return (dx * dx + dy * dy) <= radius * radius;
	}
	
}
