package ship.practice;

import java.util.Scanner;

/**
 * Source:
 *	http://uva.onlinejudge.org/external/101/10189.html
 *
 * @author Philip Diffenderfer
 *
 */
public class MineSweeper
{

	public static void main(String[] args) { 
		new MineSweeper();
	}
	
	static final char MINE = '*';

	
	// The current field as a 2d array of asterisks (mines) and dots (empty)
	char[][] field;	
	// The current field as a 2d array of values.
	int[][] mines;
	// The width of the current field
	int width;
	// The height of the current field
	int height;

	
	// Starts a new mine class prompting for input.
	MineSweeper() {
		Scanner input = new Scanner(System.in);

		height = input.nextInt();
		width = input.nextInt();

		int fieldNumber = 1;

		while (!(height == 0 && width == 0)) {
			
			field = new char[height][width];
			mines = new int[height][width];

			// Read in each row and split it into a character array.
			for (int y = 0; y < height; y++) {
				String row = input.next();
				field[y] = row.toCharArray();
			}

			// For each asterisk place a mine on the mine field.
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (field[y][x] == MINE) {
						placeMine(x, y);
					}
				}
			}

			System.out.println("Field #" + fieldNumber + ":");

			// Print out the resulting mine field
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					
					// If this is a mine print it as an asterisk
					if (field[y][x] == MINE) {
						System.out.print(MINE);
					}
					// If it's not a mine then print the value on the field
					else {
						System.out.print(mines[y][x]);
					}
				}
				// Continue to next line.
				System.out.println();
			}

			height = input.nextInt();
			width = input.nextInt();
			fieldNumber++;
		}

	}

	// Places the values around a mine on the field given the mines row and column.
	void placeMine(int x, int y) {
		increment(x - 1, y);			// Left
		increment(x - 1, y - 1);	// Top Left
		increment(x, y - 1);			// Top
		increment(x + 1, y - 1);	// Top Right
		increment(x + 1, y);			// Right
		increment(x + 1, y + 1);	// Bottom Right
		increment(x, y + 1);			// Bottom
		increment(x - 1, y + 1);	// Bottom Left
	}

	// Increments the value in the mine field at the given position IF it's 
	// located on the field.
	void increment(int x, int y) {
		// If the given column or row is not on the field then return.
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		// Increment the field
		mines[y][x] = mines[y][x] + 1;
	}


}