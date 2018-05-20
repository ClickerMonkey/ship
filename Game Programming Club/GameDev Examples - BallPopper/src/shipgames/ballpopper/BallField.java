package shipgames.ballpopper;

import static shipgames.ballpopper.Resources.*;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * A field contains all the balls and handles drawing them, drawing their 
 * explosion effects, and handling the user clicking on the field to remove a
 * ball. A field dynamically keeps track of the number of columns and how many
 * balls in each column to provide a better efficiency. A field is thread-safe
 * so updates/draws don't overlap with the user removing a group of balls (which
 * happens ALOT since we're constantly updating and drawing)
 * 
 * For example (a field with 6 rows and 8 columns)
 * <pre>
 *              columns
 *           /-------------\
 *          +---------------+
 *      / 5 |  x            |
 *      | 4 |  x       x    |
 * rows | 3 |x x       x    |
 *      | 2 |x x x     x    |
 *      | 1 |x x x   x x    |
 *      \ 0 |x x x x x x    |
 *          +---------------+
 * heights{  4 6 3 1 2 5 0 0  }
 *           \---------/
 *             width
 *</pre>
 *
 * @author Philip Diffenderfer
 *
 */
public class BallField 
{

	// The offset on the X axis in pixels this field is on the game screen.
	private final int offsetX;

	// The offset on the Y axis in pixels this field is on the game screen.
	private final int offsetY;

	// The initial number of rows of balls that are created when the field is filled.
	private final int rows;

	// The initial number of columns of balls that are created when the field is filled.
	private final int columns;

	// The number of different types of balls created on this field when it's filled.
	private final int colorCount;

	// The array of balls where null is an empty space and everything else is either
	// a moving or resting balls.
	private final Ball[][] balls;

	
	// The heights of each of the columns (number of balls in the column) as 
	// the game goes on. The columns start at the bottom (0) and go up to one
	// less the number of rows.
	private final int[] heights;

	// The number of columns that still exist on this field.
	private int width;
	

	// The state of all the balls on the field. All balls must be at the
	// resting state for this field to be at rest.
	private boolean resting = true;

	// The list of live ball pops that are occurring due to removed balls.
	private final List<BallPop> pops;

	
	/**
	 * Initializes a ball field given its number of rows and columns, its 
	 * offset in pixels on the game screen, and the number (or range) of
	 * different types of balls.
	 *  
	 * @param rows => The initial number of rows of balls that are created
	 * 		when the field is filled.
	 * @param columns => The initial number of columns of balls that are 
	 * 		created when the field is filled.
	 * @param offsetX => The offset on the X axis in pixels this field is 
	 * 		on the game screen.
	 * @param offsetY => The offset on the Y axis in pixels this field is 
	 * 		on the game screen.
	 * @param colorCount => The number of different types of balls created 
	 * 		on this field when it's filled.
	 */
	public BallField(int rows, int columns, int offsetX, int offsetY, int colorCount)
	{
		this.rows = rows;
		this.columns = columns;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.colorCount = colorCount;
		this.balls = new Ball[rows][columns];
		this.heights = new int[columns];
		this.pops = new LinkedList<BallPop>();
	}

	/**
	 * Draws all the balls in the field to the graphics object.
	 *  
	 * @param gr => The graphics object to draw on.
	 */
	public void draw(Graphics2D gr) {
		synchronized (this) {
			
			gr.translate(offsetX, offsetY);
			gr.clipRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);

			// Draw the balls on the field first.
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < heights[x]; y++) {
					balls[y][x].draw(gr);
				}
			}

			// Then draw the ball pops.
			for (BallPop e : pops) {
				e.draw(gr);
			}

			gr.translate(-offsetX, -offsetY);
			gr.setClip(null);
		}
	}

	/**
	 * Updates this field's balls if the field is currently not at the rest 
	 * state.
	 * 
	 * @param deltatime => The time in seconds since the last update.
	 */
	public void update(float deltatime)
	{
		synchronized (this) 
		{
			// Update the ball pops and remove any dead ones.
			BallPop e;
			Iterator<BallPop> iter = pops.iterator();
			while (iter.hasNext())
			{
				e = iter.next();
				e.update(deltatime);

				// If the pop is dead remove it.
				if (e.isDead()) {
					iter.remove();
				}
			}

			// If the field is not shifting then we won't update them
			if (isResting()) {
				return;
			}

			// Shift all existing balls until they're at their desired positions.
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < heights[x]; y++) {
					balls[y][x].update(deltatime);
				}
			}
		}
	}

	/**
	 * Returns whether this field is 'stuck'. A field is stuck if every
	 * ball on the field DOES NOT have a neighbor of the same type, meaning
	 * that balls can no longer be removed.
	 * 
	 * @return True if the field is stuck, false if balls can still be removed.
	 */
	public boolean isStuck()
	{
		synchronized (this) 
		{
			BallType type;
			// Check every existing column and ball in each column.
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < heights[x]; y++)
				{
					type = balls[y][x].type;

					// If any of the surrounding balls have the same type then
					// return false because balls can still be removed.
					if (matches(y, x - 1, type))
						return false;
					if (matches(y, x + 1, type))
						return false;
					if (matches(y - 1, x, type))
						return false;
					if (matches(y + 1, x, type))
						return false;
				}
			}

			// Return true since all balls have no neighbors of the same type.
			return true;
		}
	}

	/**
	 * Returns whether there's a ball under the given cursor coordinates.
	 * 
	 * @param cursorX => The x-coordinate of the cursor on the screen.
	 * @param cursorY => The y-coordinate of the cursor on the screen.
	 * @return => True if there's a ball under the cursor, fall if it's empty.
	 */
	public boolean isBall(int cursorX, int cursorY)
	{
		synchronized (this) 
		{
			// Calculate the index of the block selected based on the cursor
			int x = cursorToIndexX(cursorX);
			int y = cursorToIndexY(cursorY);

			// Check that the index is within the bounds of the field
			return !(x < 0 || x >= width || y < 0 || y >= heights[x]);
		}
	}

	/**
	 * Attempts to add a row of balls at the top of the field that fall onto
	 * the tops of the existing columns. No ball is added above a full column
	 * or an empty column however.
	 */
	public void addFallingRow()
	{
		synchronized (this)
		{
			// Only add a ball above existing columns
			for (int x = 0; x < width; x++)
			{
				int h = heights[x];

				// Only add a ball if the column isn't full.
				if (h == rows)
					continue;

				// Create the ball above the field and reset its y index
				// to force it to fall down to the column below it.
				balls[h][x] = new Ball(x, rows, BallType.getRandom(colorCount));
				balls[h][x].setY(h);
				heights[x]++;
			}

			// Signal the field as not resting to force the balls to move (update)
			resting = false;
		}
	}

	/**
	 * Attempts to remove any balls under the given cursor coordinates. If
	 * there are no balls under the cursor coordinates then this returns (0)
	 * immediately. If there is a ball under the cursor and it has no
	 * neighbors of the same type then this returns 0 immediately. If there
	 * are balls of the same type neighboring the ball under the cursor 
	 * they are removed from the field, the field is compacted down, the field
	 * is marked as not resting (to signal balls need to move) and the total 
	 * number of balls removed is returned.
	 * 
	 * @param cursorX => The x-coordinate of the cursor on the screen.
	 * @param cursorY => The y-coordinate of the cursor on the screen.
	 * @return => The number of balls removed (if any).
	 */
	public int removeBalls(int cursorX, int cursorY)
	{
		synchronized (this) 
		{
			// If the field is currently shifting then we can't handle
			// removing any bricks.
			if (!resting)
				return 0;

			// Calculate the index of the block selected based on the cursor
			int x = cursorToIndexX(cursorX);
			int y = cursorToIndexY(cursorY);

			// Check that the index is within the bounds of the field
			// and there is a brick beneath it.
			if (x < 0 || x >= width || y < 0 || y >= heights[x])
				return 0;

			// Removes all bricks around the given brick under the cursor.
			int removed = removeAround(balls[y][x]);

			// If at least one has been removed compact the field.
			if (removed != 0)
			{
				// Make all blocks shift down to the next empty space.
				compactDown();

				// Make all columns shift left to the next empty column.
				compactLeft();

				// Signal that the field is no longer resting and bricks
				// need to be shifted (updated).
				resting = false;
			}

			return removed;
		}
	}

	/**
	 * Converts the cursor on the field to the x index of the ball behind it.
	 * 
	 * @param x => The x-coordinate of the mouse on the screen.
	 * @return => The x index of the ball behind it.
	 */
	private int cursorToIndexX(int x)
	{
		return (int)((x - offsetX) / (float)BALL_SIZE);
	}

	/**
	 * Converts the cursor on the field to the y index of the ball behind it.
	 * 
	 * @param y => The y-coordinate of the mouse on the screen.
	 * @return => The y index of the ball behind it.
	 */
	private int cursorToIndexY(int y)
	{
		return (int)((y - offsetY) / (float)BALL_SIZE);
	}

	/**
	 * Given a ball, this will attempt to move all surrounding balls of the 
	 * same type from the field. If no balls of the same type surround
	 * it then this returns 0. Once this is invoked and at least one ball has
	 * been removed the field must be compacted to adjust the new heights
	 * and width of the field.
	 * 
	 * @param ball => The ball to try to remove (if there are neighboring balls
	 * 		of the same type, which are removed as well).
	 * @return => The number of balls removed from the field.
	 */
	private int removeAround(Ball ball)
	{
		int x, y, removed = 0;
		Ball b;
		BallType type = ball.type;

		// Performs a 'flood fill' type algorithm by checking all neighbors
		// and any neighbors with matching types are pushed on the stack to
		// be checked as well.
		Stack<Ball> stack = new Stack<Ball>();
		// Start off with the root ball.
		stack.push(ball);

		// Continue until all neigbors of the matching type have been exhausted.
		while (!stack.isEmpty())
		{
			// Take the next ball off the stack and check all neighbors.
			b = stack.pop();
			x = b.indexX;
			y = b.indexY;

			// If the ball has already been removed then skip it.
			if (balls[y][x] == null)
				continue;

			// If a neighbor matches the type of the given ball then push
			// it on the stack so its neighbors can be checked.
			if (matches(y, x - 1, type))
				stack.push(balls[y][x - 1]);
			if (matches(y, x + 1, type))
				stack.push(balls[y][x + 1]);
			if (matches(y - 1, x, type))
				stack.push(balls[y - 1][x]);
			if (matches(y + 1, x, type))
				stack.push(balls[y + 1][x]);

			// If the current brick was the given brick and no surrounding
			// bricks matched the type then exit returning false, no bricks
			// removed from the field
			if (b == ball && stack.isEmpty())
				break;

			// Else remove the current block from the field.
			balls[y][x] = null;
			removed++;

			// Add a pop where the block was
			pops.add(new BallPop(x, y));
		}

		return removed;
	}

	/**
	 * Returns whether the ball at the given index (if any) has the same given
	 * type. If no ball exists at the given index then false is returned.
	 * 
	 * @param y => The y index of the ball.
	 * @param x => The x index of the ball.
	 * @param type => The ball type to compare against.
	 * @return => True if a ball exists and has the same type.
	 */
	private boolean matches(int y, int x, BallType type)
	{
		if (x < 0 || x >= width || y < 0 || y >= heights[x])
			return false;

		return (balls[y][x] != null && balls[y][x].type == type);
	}

	/**
	 * Compacts the field vertically causing all balls to move down
	 * over any empty spaces. Every time a column is finished being
	 * adjusted the new height of the column is updated.
	 */
	private void compactDown()
	{
		int freeSpace = -1;

		// Start on the left side and move right...
		for (int x = 0; x < width; x++)
		{
			// Start on the bottom and move up...
			for (int y = 0; y < heights[x]; y++)
			{
				// If the free space is unmarked and we found a free space
				// then mark it, the next ball above this space will shift to it.
				if (freeSpace == -1 && balls[y][x] == null)
					freeSpace = y;

				// If there's a ball here and a free space is available use it.
				else if (balls[y][x] != null && freeSpace != -1)
				{
					// Set the ball's goto Y position.
					balls[y][x].setY(freeSpace);
					// Set the reference at the new space.
					balls[freeSpace][x] = balls[y][x];
					// Clear the reference at the old space.
					balls[y][x] = null;

					// Shift the free space up to the next empty space.
					while (freeSpace < y && balls[freeSpace][x] != null)
						freeSpace++;
				}
			}
			// If balls have been moved down then adjust the column height.
			if (freeSpace != -1)
				heights[x] = freeSpace;

			// Reset the free space to none.
			freeSpace = -1;
		}
	}

	/**
	 * Compacts the field horizontally causing all columns to move
	 * left over any empty columns. After the shift is complete
	 * the width of the field is adjusted to the new number of columns.
	 */
	private void compactLeft()
	{
		// The number of places the current column traversed needs to shift left.
		int currentMove = 0;

		// Start on the left and check for empty columns, mark the number of
		// spaces each columns need to move then perform the shift.
		for (int x = 0; x < width; x++)
		{
			boolean empty = isColumnEmpty(x);

			// Only shift the column if it's not empty and it needs to move.
			if (!empty && currentMove != 0)
				shiftLeft(x, currentMove);

			// If the current column is empty shift all columns to the right
			// of the current column, to the left side of the field.
			if (empty)
				currentMove++;
		}

		// Adjust the width (number of columns) of the field to the correct
		// number of columns.
		width -= currentMove;
	}

	/**
	 * Returns whether the given column is empty given its index.
	 * 
	 * @param x => The index of the column.
	 */
	private boolean isColumnEmpty(int x)
	{
		return (heights[x] == 0);
	}

	/**
	 * Moves the column at index 'x' to the left by 'shift' places.
	 * The columns height gets moved as well as the previous column
	 * it was at (x) has a height of 0.
	 * 
	 * @param x => The index of the column to move.
	 * @param shift => The number of places left to move it.
	 */
	private void shiftLeft(int x, int shift)
	{
		int newX = x - shift;

		// Start at the top and work your way towards the bottom
		// shifting balls over to the left.
		for (int y = 0; y < heights[x]; y++)
		{
			// Set the ball's goto X position.
			balls[y][x].setX(newX);
			// Set the reference at the new column.
			balls[y][newX] = balls[y][x];
			// Clear the reference at the old column.
			balls[y][x] = null;
		}

		// Set the height of the new column
		heights[newX] = heights[x];

		// Clear the height of the old column
		heights[x] = 0;
	}

	/**
	 * Clears this field of all balls.
	 */
	public void clear()
	{
		synchronized (this) 
		{
			for (int x = 0; x < width; x++)
				heights[x] = 0;

			width = 0;
		}
	}

	/**
	 * Fills this field completely with random ball types. 
	 */
	public void fill()
	{
		synchronized (this) 
		{
			for (int x = 0; x < columns; x++)
			{
				heights[x] = rows;

				for (int y = 0; y < rows; y++)
					balls[y][x] = new Ball(x, y, BallType.getRandom(colorCount));
			}

			width = columns;
		}
	}

	/**
	 * Returns whether this field and all of its balls are in the resting state.
	 * If the field is in resting state then the field does not check each
	 * ball, but if the field has been marked as not resting then every ball on
	 * the field are checked for resting.
	 */
	public boolean isResting()
	{
		synchronized (this) 
		{
			// If the field is currently resting return immediately.
			if (resting)
				return true;

			// For every ball on the field test it for resting, if its
			// not resting then return false immediately.
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < heights[x]; y++)
				{
					if (!balls[y][x].isResting())
						return false;
				}
			}

			// At this point every ball checked is true.
			return (resting = true);
		}
	}

	/**
	 * Returns whether this field is empty (has no balls). This can be 
	 * determined by checking the height of the left-most column, if it's
	 * zero then the field is empty.
	 */
	public boolean isEmpty()
	{
		synchronized (this) 
		{
			return (heights[0] == 0);
		}
	}

	/**
	 * Returns the number of columns currently on the field.
	 */
	public final int getWidth()
	{
		synchronized (this) 
		{
			return width;
		}
	}

}
