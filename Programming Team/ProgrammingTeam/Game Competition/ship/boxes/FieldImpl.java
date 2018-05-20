package ship.boxes;

/**
 * A field of lines and boxes.
 * 
 * @author Philip Diffenderfer
 *
 */
public class FieldImpl implements Field
{
	
	// The width of the field.
	public final int width;
	
	// The height of the field.
	public final int height;

	// The array of line existence. Line x exist if ( lines[axis][row][column] ) 
	private final boolean lines[][][];
	
	// The array of box id's
	private final int boxes[][];

	/**
	 * Instantiates a new Field implementation.
	 * 
	 * @param width The width of the field in boxes (number of columns).
	 * @param height The height of the field in boxes (number of rows).
	 */
	public FieldImpl(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.lines = new boolean[2][][];
		this.lines[AXIS_X] = new boolean[height + 1][width];
		this.lines[AXIS_Y] = new boolean[height][width + 1];
		this.boxes = new int[height][width];
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean onField(int x, int y)
	{
		return !(x < 0 || x >= width || y < 0 || y >= height);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Box getBox(int x, int y) 
	{
		if (onField(x, y)) {
			boolean l = lines[AXIS_Y][y][x];
			boolean t = lines[AXIS_X][y][x];
			boolean r = lines[AXIS_Y][y][x + 1];
			boolean b = lines[AXIS_X][y + 1][x]; 
			return new Box(x, y, l, t, r, b);
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Box getBefore(Line line)
	{
		int x = line.boxX;
		int y = line.boxY;
		if (line.side == Side.Left) x--;		// left
		if (line.side == Side.Top) y--; 		// top
		return getBox(x, y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Box getAfter(Line line)
	{
		int x = line.boxX;
		int ox = line.side.offx;
		int y = line.boxY;
		int oy = line.side.offy;
		return getBox(x + ox, y + oy);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getBoxId(int x, int y)
	{
		if (onField(x, y)) {
			return boxes[y][x];
		}
		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isCaptured(int x, int y)
	{
		return (onField(x, y) && boxes[y][x] != 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean hasLine(Line line)
	{
		return hasLine(line.boxX, line.boxY, line.side);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean hasLine(int x, int y, Side side)
	{
		if (!onField(x, y)) {
			return false;
		}
		int vx = x + side.offx;
		int vy = y + side.offy;
		int axis = side.axis;
		return lines[axis][vy][vx];
	}
	
	/**
	 * Places the line on the field and continually adds lines if possible.
	 * This will return how many boxes were captured. If the line could not be
	 * placed then -1 is returned. If the line was placed and no boxes were
	 * captured then 0 is returned. 
	 * 
	 * @param line The line to place.
	 * @param boxId The id to mark the captured boxes with.
	 * @return The number of boxes captured by placing the given line.
	 */
	protected int place(Line line, int boxId)
	{
		// If the line's box isn't on the field then return failure.
		if (line == null || !onField(line.boxX, line.boxY)) {
			return -1;
		}
		
		// Get the indices for the line.
		int vx = line.boxX + line.side.offx;
		int vy = line.boxY + line.side.offy;
		int axis = line.side.axis;
		
		// If the line is already set then don't place it.
		if (lines[axis][vy][vx]) {
			return -1;
		}
		
		// Place a line here.
		lines[axis][vy][vx] = true;
		
		// Capture all boxes possible
		// Get the boxes before and after the line.
		Box before = getBefore(line);
		Box after = getAfter(line);
		
		int closed = 0;
		
		// If theres a box before and this line closed it..
		if (before != null && before.isClosed()) {
			// Capture the box...
			boxes[before.y][before.x] = boxId;
			closed++;
			
			// Since before is closed check after as the next box to capture. It
			// can only be captured if it has three lines covered
			if (after != null && after.lines() == 3) {
				// Capture the line and continue trying to capture more.
				closed += place(after.getFirstLine(), boxId); 
			}
		}
		
		// If theres a box after and this line closed it..
		if (after != null && after.isClosed()) {
			boxes[after.y][after.x] = boxId;
			closed++;
			
			// Since after is closed check before as the next box to capture. It
			// can only be captured if it has three lines covered
			if (before != null && before.lines() == 3) {
				// Capture the line and continue trying to capture more.
				closed += place(before.getFirstLine(), boxId);
			}
		}
		
		// Any recursive capturing is done...
		return closed;
	}	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sb.append("+");
				if (lines[AXIS_X][y][x]) {
					sb.append("-");
				}
				else {
					sb.append(" ");
				}
			}
			sb.append("+\n");
			for (int x = 0; x < width; x++) {
				if (lines[AXIS_Y][y][x]) {
					sb.append("|");	
				}
				else {
					sb.append(" ");
				}
				if (boxes[y][x] == 0) {
					sb.append(" ");
				}
				else {
					sb.append(boxes[y][x]);
				}
			}
			if (lines[AXIS_Y][y][width]) {
				sb.append("|\n");	
			}
			else {
				sb.append(" \n");
			}
		}
		for (int x = 0; x < width; x++) {
			sb.append("+");
			if (lines[AXIS_X][height][x]) {
				sb.append("-");
			}
			else {
				sb.append(" ");
			}
		}
		sb.append("+\n");
		return sb.toString();
	}
	
	
}
