package finalproject;

/**
 * A 2D line with starting and ending points.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Line 
{
	
	// Starting point of the line
	private Vector start = new Vector();
	// Ending point of the line
	private Vector end = new Vector();
	
	/**
	 * Initializes a line based off another line.
	 * 
	 * @param l => The line to match.
	 */
	public Line(Line l)
	{
		this.start.set(l.getStart());
		this.end.set(l.getEnd());
	}
	
	/**
	 * Initializes a line with a starting and ending points.
	 * 
	 * @param start => The starting point of the line.
	 * @param end => The ending point of the line.
	 */
	public Line(Vector start, Vector end)
	{
		this.start.set(start);
		this.end.set(end);
	}
	
	/**
	 * Returns true if the object given is a line with matching starting and
	 * ending points.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Line)
		{
			Line l = (Line)o;
			return (start.isEqual(l.start) && end.isEqual(l.end));
		}
		return false;
	}
	
	/**
	 * Sets the new starting point of the line.
	 * 
	 * @param start => The new starting point of the line.
	 */
	public void setStart(Vector start)
	{
		this.start.set(start);
	}
	
	/**
	 * Sets the new ending point of the line.
	 * 
	 * @param end => The new ending point of the line.
	 */
	public void setEnd(Vector end)
	{
		this.end.set(end);
	}
	
	/**
	 * Returns the starting point of the line.
	 */
	public Vector getStart()
	{
		return start;
	}

	/**
	 * Returns the ending point of the line.
	 */
	public Vector getEnd()
	{
		return end;
	}
	
}
