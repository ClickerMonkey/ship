package ship.practice.unfinished;

import java.util.Scanner;


public class BrickWall
{

	public static final int EMPTY = 0;
	
	private class Brick {
		public int start, end, row;
		public boolean fallen;
		public int size() {
			return (end - start);
		}
	}
	
	private int width;
	private int height;
	
	private int[][] wall;
	private int[][] backup;
	
	private Brick[] allBricks;
	private Brick[][] rowBricks;

	public boolean load(Scanner input)
	{
		height = input.nextInt();
		width = input.nextInt();
	
		// If either dimension was 0 then the input is finished.
		if (height == 0 || width == 0)
			return false;
		
		// Initialize the wall
		wall = new int[height][width];
		
		
		
		// Create the backup
		backup = copy(wall);
		
		
		// Return true meaning that there was input.
		return true;
	}
	
	
	/**
	 * Solves the brick wall problem. This returns the maximum number of wall
	 * spaces which will be freed by removing one brick, and causing all bricks
	 * above (given constraints) to fall and open up more freed spaces.
	 */
	public int solve()
	{
		// The maximum bricks removed by taking a single brick out of the wall.
		int maxRemoved = 0;
		
		// How many brick (free) spaces were removed in the current case.
		int removed;
		
		// The starting index of the bricks to iterate. The last brick (lower right)
		// brick is the first brick removed and the path of iteration traverses
		// the current row to the left then once exhausted it moves to the row
		// above it.
		int rootIndex = (width * height) - 1;
		
		// The current brick of iteration.
		Brick current;
		
		// Try each brick in the current case as the brick which is removed
		// from the wall which results in a collapse (hopefully)
		while (rootIndex >= 0) {
			current = allBricks[rootIndex];
			
			// If the current brick hasn't fallen yet then test all bricks above it
			// for falling. If the brick has fallen then its path of destruction has
			// been simulated already and will result in a lower maxRemoved since
			// we're starting at the bottom and working to the top.
			if (!current.fallen) {
				// Start the row above the current brick.
				int start = current.row - 1;
				
				// Clear the counter which counts brick spaces removed.
				removed = 0;
				
				while (start >= 0) {
					// Get the number of bricks in the current row.
					int bricksInRow = rowBricks[start].length;
					
					// The number of bricks that fell.
					int falls = 0;
					
					// For each brick in the row check if it can fall.... if it can
					// fall then remove it and mark it as fallen
					for (int x = 0; x < bricksInRow; x++) {
						// Get the current brick
						current = rowBricks[start][x];
						
						// If the brick can fall...
						if (canFall(current)) {
							// Remove it from the wall grid...
							remove(current);
							
							// Increment the counter of removed (free) spaces.
							removed += current.size();
							
							// Set the brick as fallen so it's not reiterated.
							current.fallen = true;
							
							// Increment the number of bricks that fell in the current row.
							falls++;
						}
					}
					
					// If no bricks fell in the current row then we are done in this case.
					if (falls == 0)
						break;
					
					// Continue falling bricks to the row above the current.
					start--;
				}
				
				// If the number of removed brick spaces is > then the current
				// maximum then update it.
				if (removed > maxRemoved)
					maxRemoved = removed;
				
				// Restore the state of the wall, after this method is called then
				// all the remove() methods will essentially be undone and the wall
				// grid will match its state when first parsed from input.
				restoreWall();
			}
			
			rootIndex--;
		}
		
		// Return our maximum solution.
		return maxRemoved;
	}
	
	
	/**
	 * Removes the given brick from the wall grid.
	 * 
	 * @param b => The brick to remove.
	 */
	private void remove(Brick b)
	{
		for (int x = b.start; x <= b.end; x++) {
			wall[b.row][x] = EMPTY;
		}
	}
	
	/**
	 * Adds the given brick to the wall grid.
	 * 
	 * @param b => The brick to add.
	 */
	private void add(Brick b)
	{
		int size = b.size();
		
		for (int x = b.start; x <= b.end; x++) {
			wall[b.row][x] = size;
		}
	}
	
	/**
	 * Returns whether the given brick can fall based on the wall grid. A Brick
	 * can fall if the spaces in the row directly below are all EMPTY (0).
	 * 
	 * @param b => The brick to determine fallability.
	 */
	private boolean canFall(Brick b)
	{
		// If the brick is on the bottom of the wall it can fall by default.
		if (b.row == height - 1)
			return true;
		
		// For every space below the given brick...
		for (int x = b.start; x <= b.end; x++) {
			// If the space below the brick is occupied then this brick cannot
			// fall, return false immediately.
			if (wall[b.row + 1][x] != EMPTY) {
				return false;
			}
		}
		
		// All spaces below the brick are open (0), return true.
		return true;
	}

	/**
	 * Restores the wall to its original state (without empty spaces, except for
	 * given empty spaces created at initializaton).
	 */
	private void restoreWall()
	{
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				wall[row][col] = backup[row][col];
			}
		}
	}

	/**
	 * Copies a 2-dimensional integer array to a new 2-dimensional integer array.
	 * 
	 * @param a => The array to copy.
	 * @return => The reference to the new copied array.
	 */
	private int[][] copy(int[][] a)
	{
		// Get the dimensions of the given 2D array.
		int height = a.length;
		int width = a[0].length;
		
		// Create the copied array to return.
		int[][] c = new int[height][width];
		
		// Perform the copying on every row and each column.
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				c[row][col] = a[row][col];
			}
		}
		
		// Return the copy.
		return c;
	}
	
}
