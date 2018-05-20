package ship.pacise10;

import java.util.*;

public class problem2 {
	public static void main(String[] args) {
		new problem2();
	}

	int[][] dirs = {
		{0, 1},
		{0, -1},
		{1, 0},
		{-1, 0},
		{-1, 1},
		{-1, -1},
		{1, -1},
		{1, 1},
	};

	int size;
	int inarow;
	int height[];
	int board[][];

	problem2() {
		Scanner in = new Scanner(System.in);

		size = in.nextInt();
		inarow = in.nextInt();
		board = new int[size][size];
		height = new int[size];
		boolean found = false;
		in.nextLine();	
		while (in.hasNextLine()) {
			String move = in.nextLine();
			
			int player = getPlayer(move);
			int column = getColumn(move);
			
			if (player == -1 || column == -1) {
				break;
			}

			if (!place(column, player)) {
				System.out.println("ERROR");
				found = true;
				break;
			}

			if (winner(player)) {
				if (player == 1) {
					System.out.println("R WINS");
					found = true;
					break;
				} else {
					System.out.println("Y WINS");
					found = true;
					break;
				}
			}
		}	
		
		if (!found) {
			if (isFull()) {
				System.out.println("DRAW");
			} else {
				System.out.println("IN PROGRESS");
			}
		}
	}

	int getPlayer(String move) {
		if (move.contains("R")) return 1;
		if (move.contains("Y")) return 2;
		return -1;
	}
	int getColumn(String move) {
		String[] parts = move.split(" ");
		for (int i = 0; i < parts.length; i++) {
			if (!parts[i].equals("-")) {
				return i;
			}
		}
		return -1;
	}
	boolean place(int column, int player) {
		if (height[column] == size) {
			return false;
		}
		board[height[column]++][column] = player;
		return true;
	}
	boolean isFull() {
		for (int i = 0; i < size; i++) {
			if (height[i] < size) {
				return false;
			}
		}
		return true;
	}
	boolean winner(int player) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < height[x]; y++) {
				for (int[] dir : dirs) {
					if (matches(x, y, dir[0], dir[1], player) >= inarow) {
						return true;
					}
				}
			}
		}
		return false;
	}
	boolean onBoard(int x, int y) {
		return !(x < 0 || x >= size || y < 0 || y >= size);
	}
	int matches(int x, int y, int dirx, int diry, int player) {
		int matchCount = 0;
		while (onBoard(x, y) && board[y][x] == player) {
			x += dirx;
			y += diry;
			matchCount++;
		}
		return matchCount;
	}

}
