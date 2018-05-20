package ship.practice.unfinished;
import java.util.Scanner;



public class SlashMaze
{

	public static void main(String[] args)
	{
		/*
4 6
\//\\/
\///\/
//\\/\
\/\///
		 */
		Scanner input = new Scanner(System.in);
		int rows = input.nextInt(); 
//		int columns = input.nextInt();
		
		String slashMaze[] = new String[rows];
		for (int i = 0; i < rows; i++)
			slashMaze[i] = input.next();
		
		boolean maze[][] = parse(slashMaze);
		for (boolean[] row : maze)	{
			for (boolean col : row)
				System.out.print(col ? " " : "X");
			System.out.println();
		}
	}
	
	public static boolean[][] parse(String[] rows)
	{
		int slashRows = rows.length;
		int slashCols = rows[0].length();
		int mazeSize = (slashRows + slashCols) * 2 + 1;
		boolean[][] maze = new boolean[mazeSize][mazeSize];

		// All spaces are walk-able at first
		for (int row = 0; row < mazeSize; row++)
			for (int col = 0; col < mazeSize; col++)
				maze[row][col] = true;
		
		// Set all corners
		for (int row = 0; row < mazeSize; row += 2)
			for (int col = 0; col < mazeSize; col += 2)
				maze[row][col] = false;

		// Set the upper left and lower right as blocked
		int depth = slashRows * 2 + 1;
		for (int row = 0; row < depth; row++)
		{
			for (int col = 0; col < (depth - row); col++)
			{
				maze[row][col] = false;
				maze[mazeSize - row - 1][mazeSize - col - 1] = false;
			}
		}
		
		// Set the upper right and lower left as blocked
		depth = slashCols * 2 + 1;
		for (int row = 0; row < depth; row++)
		{
			for (int col = 0; col < (depth - row); col++)
			{
				maze[row][mazeSize - col - 1] = false;
				maze[mazeSize - row - 1][col] = false;
			}
		}
		
		char slash;
		int x, y;
		for (int row = 0; row < slashRows; row++)
		{
			for (int col = 0; col < slashCols; col++)
			{
				slash = rows[row].charAt(col);
				
				x = (slashRows - (row - col)) * 2;
				y = (row + col) * 2;
				
				if (slash == '/')
				{
					maze[y + 1][x] = false;
					maze[y - 1][x] = false;
				} else if (slash == '\\')
				{
					maze[y][x + 1] = false;
					maze[y][x - 1] = false;
				}
			}
		}
		
		return maze;
	}
	
}
