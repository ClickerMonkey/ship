package ship.boxes;

/**
 * A field of lines and boxes.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Field
{
	
	/**
	 * The x-axis
	 */
	public static final int AXIS_X = 0;
	
	/**
	 * The y-axis
	 */
	public static final int AXIS_Y = 1;

	/**
	 * Returns the width of this field (number of columns).
	 */
	public int getWidth();
	
	/**
	 * Returns the height of this field (number of rows).
	 */
	public int getHeight();
	
	/**
	 * Returns true if the given box indices lie on the field.
	 * 
	 * @param x The column of the box.
	 * @param y The row of the box.
	 * @return True if there exists a box at {x, y}
	 */
	public boolean onField(int x, int y);
	
	/**
	 * Returns the read-only box at the given location.
	 * 
	 * @param x The column of the box.
	 * @param y The row of the box.
	 * @return A box at {x, y} or null if none exists.
	 */
	public Box getBox(int x, int y); 

	/**
	 * Gets the box before the given Line. If the line is on the x-axis then
	 * this will return the box right above the line. If the line is on the 
	 * y-axis this will return the box to the left of the line.
	 * 
	 * @param line The line to get the box before.
	 * @return The box before the given line or null of none exists.
	 */
	public Box getBefore(Line line);
	
	/**
	 * Gets the box after the given Line. If the line is on the x-axis then this
	 * will return the box right below the line. If the line is on the y-axis 
	 * then this will return the box to the right of the line.
	 * 
	 * @param line The line to get the box after.
	 * @return The box after the given line or null of none exists.
	 */
	public Box getAfter(Line line);
	
	/**
	 * Returns true if the box at the given position has already been captured.
	 * 
	 * @param x The column of the box.
	 * @param y The row of the box.
	 * @return True if the box has been captured already.
	 */
	public boolean isCaptured(int x, int y);
	
	/**
	 * Returns whether the given Line already exists on the field.
	 * 
	 * @param line The line to test for existence.
	 * @return True if the line exists, false otherwise.
	 */
	public boolean hasLine(Line line);
	
	/**
	 * Returns whether the given Line already exists on the field.
	 * 
	 * @param x The column index of the box which the line lies on.
	 * @param y The row index of the box which the line lies on.
	 * @param side The side of the box the line lies.
	 * @return True if the line exists, false otherwise.
	 */
	public boolean hasLine(int x, int y, Side side);
	
}
