package ship.battle;

/**
 * A direction on the battle field.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum Direction 
{
	/**
	 * A direction on the positive x-axis (right).
	 */
	HorizontalPos(1, 0),
	/**
	 * A direction on the negative x-axis (left).
	 */
	HorizontalNeg(-1, 0),
	/**
	 * A direction on the positive y-axis (down).
	 */
	VerticalPos(0, 1),
	/**
	 * A direction on the negative y-axis (up).
	 */
	VerticalNeg(0, -1);
	
	/**
	 * The direction factor along the x-axis {-1,0,1}
	 */
	public final int x;
	
	/**
	 * The direction factor along the y-axis {-1,0,1}
	 */
	public final int y;
	
	/**
	 * Instantiates a Direction enum.
	 * 
	 * @param x The direction factor along the x-axis.
	 * @param y The direction factor along the y-axis.
	 */
	private Direction(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
}
