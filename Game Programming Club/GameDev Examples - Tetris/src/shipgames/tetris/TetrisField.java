package shipgames.tetris;

import java.awt.Graphics2D;

/**
 * A tetris field is a grid of Squares that was built up
 * by placing blocks on the field. As the blocks build up
 * on the field if there are full rows, or 'lines', these
 * can be removed from the field for more building of blocks.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TetrisField 
{
	/** The number of squares wide a field is. */
	public final static int WIDTH = 12;
	/** The number of squares high a field is. */
	public final static int HEIGHT = 20;
	
	// The 2-dimensional array of squares which make
	// up this tetris field.
	private Square[][] grid;
	
	/**
	 * Initializes a tetris field creating the Square grid.
	 */
	public TetrisField()
	{
		grid = new Square[HEIGHT][WIDTH];
	}
	
	/**
	 * Draws the tetris field to the Graphics object.
	 * 
	 * @param gr => The graphics to draw on.
	 */
	public void draw(Graphics2D gr)
	{
		// Draw each square.
		for (int y = 0; y < HEIGHT; y++)
			for (int x = 0; x < WIDTH; x++)
				if (grid[y][x] != null)
					grid[y][x].draw(gr);
	}
	
	/**
	 * Clears the square grid so this field contains
	 * no squares.
	 */
	public void clear()
	{
		for (int y = 0; y < HEIGHT; y++)
			for (int x = 0; x < WIDTH; x++)
				grid[y][x] = null;
	}
	
	/**
	 * Determines if a given point exists on this field
	 * and also is not already a square.
	 * 
	 * @param location => The location to check for emptiness.
	 */
	public boolean isEmpty(Point location)
	{
		// If the location is outside the field's bounds
		// then this is not an available space!
		if (location.x < 0 || location.x >= WIDTH ||
			location.y < 0 || location.y >= HEIGHT)
			return false;
		// Return whether a square is there or not.
		return (grid[location.y][location.x] == null);
	}
	
	/**
	 * Places a block on this field.
	 * 
	 * @param b => The block to place.
	 */
	public void placeBlock(Block b)
	{
		Square s;
		s = b.getSquare(0);
		grid[s.getY()][s.getX()] = s;
		s = b.getSquare(1);
		grid[s.getY()][s.getX()] = s;
		s = b.getSquare(2);
		grid[s.getY()][s.getX()] = s;
		s = b.getSquare(3);
		grid[s.getY()][s.getX()] = s;
	}
	
	/**
	 * This is used for the game over screen where all rows
	 * starting from the bottom fill up. This will fill a row's
	 * empty spaces with random squares.
	 * 
	 * @param row => The row to fill up. 
	 */
	public void fillInSquares(int row)
	{
		for (int x = 0; x < WIDTH; x++)
			if (grid[row][x] == null)
				grid[row][x] = new Square(x, row, null);
	}
	
	/**
	 * This counts how many empty squares are in a give row.
	 * 
	 * @param row => The row to count.
	 */
	public int countEmptySquares(int row)
	{
		int total = 0;
		for (int x = 0; x < WIDTH; x++)
			if (grid[row][x] == null)
				total++;
		
		return total;
	}
	
	/**
	 * This will check the tetris field for any complete lines.
	 * for every line thats found the blocks above that line are
	 * shifted downwards. This will return how many, if any, lines
	 * were removed from this field.
	 * 
	 * @return The number of lines (rows) removed.
	 */
	public int checkLines()
	{
		int linesCleared = 0;
		
		// Start at the bottom line
		int y = HEIGHT - 1;
		
		// Move up towards the top lines.
		while (y >= 0)
		{
			int empty = countEmptySquares(y);
			// If every block is empty then discontinue.
			if (empty == WIDTH)
				break;
			// If the line contains no empty squares then
			// remove the line and shift everything above it down.
			if (empty == 0)
			{
				linesCleared++;
				// Shift everything down!
				for (int current = y; current > 0; current--)
					for (int x = 0; x < WIDTH; x++)
						shiftSquare(x, current);
			}
			else
			// The line contains some empty squares so skip it.
				y--;
		}
		
		return linesCleared;
	}
	
	/**
	 * This will shift down a square based on the given
	 * x value (column) and the current row to shift
	 * down onto.
	 * 
	 * @param x => The current column to shift down.
	 * @param current => The current row to shift down a Square on.
	 */
	private void shiftSquare(int x, int current)
	{
		int above = current - 1;
		Square belowSq = grid[current][x];
		Square aboveSq = grid[above][x];
		
		// If both are null then return
		if (belowSq == null && aboveSq == null)
			return;
		// If there was a below square but nothing
		// above it then the below square is nothing
		else if (belowSq != null && aboveSq == null)
			grid[current][x] = null;
		// If there was a below and above square
		// set the below squares new type.
		else if (belowSq != null && aboveSq != null)
			grid[current][x].setType(aboveSq);
		// If there was no below square but there
		// is an above square dropping.
		else
		{
			grid[current][x] = grid[above][x];
			grid[current][x].setY(current);
			grid[above][x] = null;
		}
	}
	
}
