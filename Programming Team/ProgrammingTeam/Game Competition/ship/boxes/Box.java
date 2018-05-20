package ship.boxes;

/**
 * A read-only box on the field.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Box
{

	// The x-coord of the box (column index)
	public final int x;
	// The y-coord of the box (row index)
	public final int y;
	// Whether this box has a left side
	public final boolean left;
	// Whether this box has a top side
	public final boolean top;
	// Whether this box has a right side
	public final boolean right;
	// Whether this box has a bottom side
	public final boolean bottom;

	/**
	 * Instantiates a new Box.
	 */
	public Box(int x, int y, boolean left, boolean top, boolean right, boolean bottom) 
	{
		this.x = x;
		this.y = y;
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	/**
	 * Returns true if this box has all four sides.
	 */
	public boolean isClosed()
	{
		return (left && top && right && bottom);
	}

	/**
	 * Returns the number of lines on this box.
	 */
	public int lines() 
	{
		int count = 0;
		if (left) count++;
		if (top) count++;
		if (right) count++;
		if (bottom) count++;
		return count;
	}

	/**
	 * Returns whether the given side exists on this box.
	 * 
	 * @param side The side to check for existence.
	 * @return True if the side exists on this box.
	 */
	public boolean exists(Side side)
	{
		switch (side) {
			case Left: 		return left;
			case Top: 		return top;
			case Right: 	return right;
			case Bottom: 	return bottom;
		}
		return false;
	}

	/**
	 * Returns the first line that exists in this Box. The order for returning
	 * is left, top, right, then bottom.
	 */
	public Line getFirstLine() 
	{
		if (!left) return new Line(x, y, Side.Left);
		if (!top) return new Line(x, y, Side.Top);
		if (!right) return new Line(x, y, Side.Right);
		if (!bottom) return new Line(x, y, Side.Bottom);
		return null;
	}

	/**
	 * Gets a Line given the side.
	 */
	public Line getLine(Side side) 
	{
		return new Line(x, y, side);
	}

}
