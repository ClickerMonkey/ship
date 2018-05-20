package ship.dickinson09;

import java.util.HashMap;
import java.util.Scanner;


public class othello 
{

	public static void main(String[] args)
	{
		new othello();
	}
	
	public static final int SIZE = 8;
	public int[][] board;
	public HashMap<String, Integer> columns;
	
	public othello()
	{
		Scanner sc = new Scanner(System.in);
		
		initializeColumns();
		
		int cases = sc.nextInt();
		int moves, row, col, player;
		
		for (int c = 0; c < cases; c++)
		{
			initializeBoard();
			
			moves = sc.nextInt();
			
			for (int m = 0; m < moves; m++)
			{
				row = sc.nextInt() - 1;
				col = columns.get(sc.next());
				player = 2 - (m & 1);
				
				makeMove(row, col, player);
			}
			
			System.out.format("Case %d:\n", c + 1);
			
			printBoard();
		}
	}
	
	public void initializeBoard()
	{
		// Black = 2
		// White = 1
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 2, 0, 0, 0},
			{0, 0, 0, 2, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}
	
	public void initializeColumns()
	{
		columns = new HashMap<String, Integer>(SIZE);
		columns.put("A", 0);
		columns.put("B", 1);
		columns.put("C", 2);
		columns.put("D", 3);
		columns.put("E", 4);
		columns.put("F", 5);
		columns.put("G", 6);
		columns.put("H", 7);
	}
	
	public void printBoard()
	{
		char[] pieces = {'.', 'w', 'b'};
		
		for (int r = 0; r < SIZE; r++)
		{
			for (int c = 0; c < SIZE; c++)
				System.out.print(pieces[board[c][r]]);
			System.out.println();
		}
		// A Blank Line.
		System.out.println();
	}
	
	public void makeMove(int x, int y, int player)
	{		
		board[y][x] = player;
		
		doSearch(x, y, -1,  0, player); // left
		doSearch(x, y,  1,  0, player); // right
		doSearch(x, y,  0, -1, player); // top
		doSearch(x, y,  0,  1, player); // bottom
		doSearch(x, y, -1, -1, player); // topleft
		doSearch(x, y,  1, -1, player); // topright
		doSearch(x, y, -1,  1, player); // bottomleft
		doSearch(x, y,  1,  1, player); // bottomright
	}
	
	public void doSearch(int x, int y, int incX, int incY, int player)
	{
		// How many spaces are being changed
		int spaces = 0;

		// Move the current position
		x += incX;
		y += incY;
		
		// Try to loop until a side is hit.
		while (x >= 0 && x < SIZE && y >= 0 && y < SIZE)
		{
			spaces++;
			
			// If we hit an empty piece then exit!
			if (board[y][x] == 0)
				return;
			
			// If we hit our piece then go back to the start changing
			// pieces along the way
			if (board[y][x] == player)
			{
				for (int s = 0; s < spaces; s++)
				{
					x -= incX;
					y -= incY;
					
					board[y][x] = player;
				}
				break;
			}

			// Move the current position
			x += incX;
			y += incY;
		}
	}
	
	
}

/**
 * 
1
11
6 E
4 F
3 E
6 F
5 G
2 F
6 G
5 C
4 C
7 E
1 G
 */
