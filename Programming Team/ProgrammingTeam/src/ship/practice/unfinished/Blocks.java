package ship.practice.unfinished;
import java.awt.Point;
import java.util.Scanner;

public class Blocks
{
	
	public static int [][] stacks;
	public static int total;
	
	public static void main(String args[])
	{
		// Grab input from the command-line
		Scanner in = new Scanner(System.in);
		total = in.nextInt();
		int m = in.nextInt();
		
		// The stacks can only be as tall as it is wide.
		stacks = new int [total][total];
		
		// Place the initial blocks at their starting positions.
		for(int i = 0; i < total; i++)
			stacks[i][0]= i + 1;

		// Parse or read each command
		for(int i = 0; i < m; i++)
		{
			// Grab the command, which block, and which stack.
			String com = in.next();
			int block = in.nextInt();
			int stack = in.nextInt();

			if(com.equals("move"))
				place(block,stack-1);
			else
				pile(block,stack-1);
		}
		
		// Output the stacks to System.out
		for(int i = 0; i < total; i++)
		{
			System.out.print((i + 1) + ":");
			for(int j = 0; j < total; j++) 
			{
				if(stacks[i][j] != 0)
					System.out.print(" " + stacks[i][j]);
				else
					break;
			}
			System.out.println();
		}
		
	}
	
	/**
	 * Piles a block and everything above it to ontop of a stack.
	 */
	public static void pile(int b, int dest)
	{
		// Find the block to move.
		Point p = findBlock(b);
		// Move it and everything above it.
		for(int i = p.y; i < total; i++)
		{
			int block = stacks[p.x][i];
			// If the space in the stack is a block place it.
			if (block != 0)
				place(block, dest);
		}
	}

	/**
	 * Finds a block according to its number and returns its location (x,y) 
	 * where x is the stack index and y is the height on the stack
	 */
	public static Point findBlock(int b)
	{
		for(int y = 0; y < total; y++)
			for(int x = 0; x < total; x++)
				if(stacks[x][y] == b)
					return new Point(x, y);
		return new Point(-1, -1);
	}
	
	/**
	 * Places a block ontop of another stack and moves all blocks above it to their original position.
	 */
	public static void place(int b, int dest)
	{
		Point p = findBlock(b);
		stacks[p.x][p.y] = 0;
		// Put block on destination stack
		for (int i= 0; i < total; i++)
		{
			if(stacks[dest][i] == 0)
			{
				stacks[dest][i] = b;
				break;
			}
		}
		// Move all blocks above it to original positions
		for(int i = p.y + 1; i < total; i++)
		{
			int block = stacks[p.x][i];
			if(block != 0)
			{
				place(block, block - 1);
			}
		}
		
	}

}
