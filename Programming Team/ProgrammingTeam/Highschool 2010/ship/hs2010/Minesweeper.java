package ship.hs2010;

import java.util.Scanner;


public class Minesweeper
{

	public static void main(String[] args) {
		new Minesweeper();
	}

	private int width;
	private int height;
	private char[][] field;
	private int[][] values;
	
	public Minesweeper() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		
		for (int caseNumber = 1; caseNumber <= caseCount; caseNumber++) {
			
			width = input.nextInt();
			height = input.nextInt();
			input.nextLine();
			
			field = new char[height][];
			values = new int[height][width];
			
			for (int y = 0; y < height; y++) {
				field[y] = input.nextLine().toCharArray();
			}
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (field[y][x] == '*') {
						placeMine(x, y);	
					}
				}
			}
			
			System.out.format("Board#%d\n", caseNumber);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (field[y][x] == '*') {
						System.out.print('*');
					}
					else {
						System.out.print(values[y][x]);	
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	private void placeMine(int x, int y) {
		add(x - 1, y);
		add(x - 1, y - 1);
		add(x, y - 1);
		add(x + 1, y - 1);
		add(x + 1, y);
		add(x + 1, y + 1);
		add(x, y + 1);
		add(x - 1, y + 1);
	}
	
	private void add(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		values[y][x]++;
	}
	
}
