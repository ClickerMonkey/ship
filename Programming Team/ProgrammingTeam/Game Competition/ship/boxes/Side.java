package ship.boxes;

/**
 * A side of a box.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum Side
{
	// Left side lies on the y-axis.
	Left(0, 0, Field.AXIS_Y),
	// Top side lies on the x-axis.
	Top(0, 0, Field.AXIS_X),
	// Right side lies on the y-axis.
	Right(1, 0, Field.AXIS_Y),
	// Bottom side lies on the x-axis.
	Bottom(0, 1, Field.AXIS_X);

	// Offset on x-axis of this side relative to other sides in the same box.
	public final int offx;
	// Offset on y-axis of this side relative to other sides in the same box.
	public final int offy;
	// The axis this side lies on.
	public final int axis;
	
	/**
	 * Instantiates a new Side.
	 */
	private Side(int dx, int dy, int axis)
	{
		this.offx = dx;
		this.offy = dy;
		this.axis = axis;
	}
	
	/**
	 * Returns the opposite side to this one.
	 */
	public Side opposite() 
	{
		switch (this) {
			case Bottom:	return Top;
			case Left:		return Right;
			case Top:		return Bottom;
			case Right:		return Left;
		}
		return null;
	}
	
}
