package ship.mazes;

import java.awt.Point;
import java.util.ArrayList;


public class Backtracking
{
	
	public static void main(String[] args)
	{
		new Backtracking();	
	}

	// Size of the maze.
	private int n = 8;
	// The maze as 0's and 1's
	private int maze[][] = {
		{0,0,0,1,1,1,0,1},
		{1,1,0,1,0,0,0,1},
		{0,0,0,1,0,1,0,1},
		{1,1,0,1,0,1,0,1},
		{1,1,0,1,0,1,0,1},
		{1,0,0,0,0,1,0,1},
		{1,0,1,1,0,1,0,1},
		{1,1,1,1,1,1,0,0}
	};	
	// The list of paths
	private ArrayList<Point> path = new ArrayList<Point>();
	
	public Backtracking()
	{
		// Start at (0,0)
		findPath(0, 0);
		
		// Print out the path
		for (int i = path.size() - 1; i >= 0; i--)
			System.out.format("(%d, %d) ", path.get(i).x, path.get(i).y); 
	}
	
	public boolean findPath(int x, int y)
	{		
		// Mark it as visited.
		maze[y][x] = 2;
		
		// If we are at the destination
		if (x == n - 1 && y == n - 1)
			return true;
		
		// Test to the right
		if (free(x + 1, y))
			if (findPath(x + 1, y))
				return path.add(new Point(x + 1, y));
		// Test to the left
		if (free(x - 1, y ))
			if (findPath(x - 1, y))
				return path.add(new Point(x - 1, y));
		// Test above
		if (free(x, y - 1))
			if (findPath(x, y - 1))
				return path.add(new Point(x, y - 1));
		// Test below
		if (free(x, y + 1))
			if (findPath(x, y + 1))
				return path.add(new Point(x, y + 1));
		
		return false;
	}
	
	// Determines if (x,y) is on the maze and is a free space.
	public boolean free(int x, int y)
	{
		if (x < 0 || x >= n || y < 0 || y >= n)
			return false;
		
		return (maze[y][x] == 0);
	}
	
}
