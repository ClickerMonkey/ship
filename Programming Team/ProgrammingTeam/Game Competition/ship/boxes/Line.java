package ship.boxes;

/**
 * A line on the field.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Line
{
	
	// The x-coord of the box this line lies on (column index)
	public final int boxX;
	// The y-coord of the box this lies on (row index)
	public final int boxY;
	// The side of the box this line lies on.
	public final Side side;
	
	/**
	 * Instantiates a new Line.
	 */
	public Line(int x, int y, Side side)
	{
		this.boxX = x;
		this.boxY = y;
		this.side = side;
	}

	/**
	 * Returns the flip of this line. 
	 */
	public Line flip() 
	{
		switch (side) {
			case Bottom:
				return new Line(boxX, boxY + 1, Side.Top);
			case Top:
				return new Line(boxX, boxY - 1, Side.Bottom);
			case Right:
				return new Line(boxX + 1, boxY, Side.Left);
			case Left:
				return new Line(boxX - 1, boxY, Side.Right);
		}
		return null;
	}
	
}
