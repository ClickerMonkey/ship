package shipgames.tron;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * A linked list of connected vertical and horizontal lines. A linked method was
 * used because of the dynamic number of lines in the path.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Path
{

	// Last point added to the path
	private Point last;
	// The point before the last point that was added.
	private Point previous;
	// The starting point of the path.
	private Point start;
	// The color of this path.
	private Color color;
	// The stroke (pen) used to draw the path.
	private Stroke stroke;
	// The width in pixels of the path.
	private int halfWidth;
	// The number of lines contained in this path.
	private int lines;
	
	/**
	 * Initializes a Path of connected vertical and horizontal lines. This path
	 * starts at one point and as points are added lines are created between the
	 * last and newly added point.
	 * 
	 * @param startX => The starting x value of the Path.
	 * @param startY => The starting y value of the Path.
	 * @param pathColor => The color of the path.
	 * @param pathWidth => The width in pixels of the path.
	 */
	public Path(int startX, int startY, Color pathColor, int pathWidth)
	{
		start = new Point(startX, startY);
		last = new Point(startX, startY);
		start.setNext(last);
		previous = start;
		lines = 1;
		
		color = pathColor;
		halfWidth = pathWidth >> 1;
		stroke = new BasicStroke(pathWidth);
	}
	
	/**
	 * Resets the path to a new point and no length;
	 * 
	 * @param startX => The starting x value of the Path.
	 * @param startY => The starting y value of the Path.
	 */
	public void reset(int startX, int startY)
	{	
		start.x = startX;
		start.y = startY;
		last.x = startX;
		last.y = startY;
		
		last.setNext(null);
		start.setNext(last);
		previous = start;
		lines = 1;
	}
	
	/**
	 * Adds a point to this path. This forms a line between the last point added
	 * and this point. The point to add must form a vertical or horizontal line
	 * betwwn it and the last point of the path.
	 * 
	 * @param p => The point to add.
	 */
	public boolean addPoint(Point p)
	{
		// Check that the point to add will make a horizontal or vertical line,
		// if not then the intersection method wont work.
		if (p.x != last.x && p.y != last.y)
			return false;

		// Set the previous point to the last point
		previous = last;
		// Set the pointer to the next point
		previous.setNext(p);
		// Set the last point
		last = p;
		
		lines++;
		
		return true;
	}
	
	/**
	 * Draws this path with the specified stroke and color.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void draw(Graphics2D gr)
	{
		gr.setStroke(stroke);
		gr.setColor(color);
		// Draw each line of the path
		Point current = start;
		Point next = start.getNext();
		while (next != null)
		{
			gr.drawLine(current.x, current.y, next.x, next.y);
			current = next;
			next = next.getNext();
		}
		
//		// Draw the start as green
//		gr.setColor(Color.green);
//		gr.fillOval(start.x - 2, start.y - 2, 4, 4);
//		// Draw previous
//		gr.setColor(Color.gray);
//		gr.fillOval(previous.x - 3, previous.y - 3, 6, 6);
//		// Draw Last
//		gr.setColor(Color.lightGray);
//		gr.fillOval(last.x - 2, last.y - 2, 4, 4);
	}
	
	/**
	 * Determines if the line between head and tail intersect over any line
	 * contained in this path.
	 * 
	 * @param head => The first point of the line.
	 * @param tail => The second point of the line.
	 * @param itself => Set to true if the path is checking intersection for itself.
	 * 
	 * @return True if the line of head and tail intersects a line in this path.
	 */
	public boolean intersects(Point head, Point tail, boolean itself)
	{
		int check = (itself ? lines - 2 : lines);
		Point current = start;
		while (current.getNext() != null && check-- > 0)
		{
			if (crosses(current, current.getNext(), head, tail))
				return true;
			
			current = current.getNext();
		}
		return false;
	}
	
	// Keep these variables in memory for checking the crossing of the two axis
	// aligned lines. Saves allocation per cross check.
	private float aLeft, aTop, aRight, aBottom;
	private float bLeft, bTop, bRight, bBottom;
	
	/**
	 * Determines if the line between (a1, a2) crosses the line (b1, b2).
	 * 
	 * @param a1 => First point of line a.
	 * @param a2 => Second point of line a.
	 * @param b1 => First point of line b.
	 * @param b2 => Second point of line b.
	 * 
	 * @return True if line a crosses line b.
	 */
	private boolean crosses(Point a1, Point a2, Point b1, Point b2)
	{
		// Calculate a's bounds
		aLeft   = (a1.x < a2.x ? a1.x : a2.x) - halfWidth;
		aTop    = (a1.y < a2.y ? a1.y : a2.y) - halfWidth;
		aRight  = (a1.x > a2.x ? a1.x : a2.x) + halfWidth;
		aBottom = (a1.y > a2.y ? a1.y : a2.y) + halfWidth;
		// Calculate b's bounds
		bLeft   = (b1.x < b2.x ? b1.x : b2.x) - halfWidth;
		bTop    = (b1.y < b2.y ? b1.y : b2.y) - halfWidth;
		bRight  = (b1.x > b2.x ? b1.x : b2.x) + halfWidth;
		bBottom = (b1.y > b2.y ? b1.y : b2.y) + halfWidth;
		// Determine intersection
		return !(aLeft > bRight || 
				 aTop > bBottom || 
				 aRight < bLeft || 
				 aBottom < bTop);
	}
	
	/**
	 * Sets the color of this Path.
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	/**
	 * Sets the stroke (pen) used to draw this path.
	 */
	public void setStroke(Stroke stroke)
	{
		this.stroke = stroke;
	}
	
	/**
	 * Gets the starting point of this path.
	 */
	public Point getStart()
	{
		return start;
	}
	
	/**
	 * Gets the last point added in this path.
	 */
	public Point getLast()
	{
		return last;
	}

	/**
	 * Gets the point before the last point added in this path.
	 */
	public Point getPrevious()
	{
		return previous;
	}
	
	/**
	 * Gets the number of lines in this path.
	 */
	public int getLines()
	{
		return lines;
	}

	/**
	 * Gets the number of points in this path.
	 */
	public int getPoints()
	{
		return lines + 1;
	}

	/**
	 * Gets the color of this path.
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Gets the width of this path in pixels.
	 */
	public int getWidth()
	{
		return halfWidth >> 1;
	}
	
	/**
	 * Gets the stroke (pen) used to draw this path.
	 */
	public Stroke getStroke()
	{
		return stroke;
	}
	
}
