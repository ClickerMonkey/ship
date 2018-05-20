package ship.acm2009;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * 
 * @author Philip Diffenderfer
 *
 */
public class BlockGame
{

	public static void main(String[] args) {
		new BlockGame();
	}

	static final int VERTICAL = 0;
	static final int HORIZONTAL = 1;
	static final int MAX_BLOCK_LENGTH = 3;
	static final int MIN_BLOCK_LENGTH = 2;
	static final int BOARD_SIZE = 6;
	static final int STATE_VALUE_SCALE = BOARD_SIZE - MIN_BLOCK_LENGTH + 1;


	HashSet<Integer> stateVals;
	Block[] blocks;		// The array of different blocks (without their position)
	int target;				// Index of the block to try to move to the complete right.
	int blockCount;		// The number of blocks on the current board


	//==================================================================
	// Initializes the BlockGame Solver
	//==================================================================
	BlockGame() 
	{
		Scanner sc = new Scanner(System.in);

		// Get the target char of the first board
		char targetChar = sc.nextLine().charAt(0);

		// Loop until asterisk is the targetChar
		while (targetChar != '*') {

			// Read in the board.
			String[] board = new String[BOARD_SIZE];
			for (int i = 0; i < BOARD_SIZE; i++) {
				board[i] = sc.nextLine();
			}

			// Create the initial state with the board
			State initial = parse(board, targetChar);
			// Solve the board.
			int moves = solve(initial);
			// Print out the minimum number of moves.
			System.out.println(moves);

			// Get the target char of the next board
			targetChar = sc.nextLine().charAt(0);
		}
	}

	//==================================================================
	// Parses the lines of the board into a state.
	//==================================================================
	State parse(String[] board, char targetChar) {
		State s = new State();

		// A map of blocks hashed by their character
		HashMap<Character, Block> blockMap = new HashMap<Character, Block>(); 
		HashMap<Character, Integer> positionMap = new HashMap<Character, Integer>();
		ArrayList<Character> charList = new ArrayList<Character>();

		char c;
		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				c = board[y].charAt(x);
				// If its not an empty space
				if (c != '.') {
					// If the block has not been added then add it.
					if (!blockMap.containsKey(c)) {
						// Calculate its direction
						int dir = HORIZONTAL;
						if (y < BOARD_SIZE - 1 && board[y + 1].charAt(x) == c)
							dir = VERTICAL;
						
						int xdir = dir;
						int ydir = (1 - dir);
						// Calculate its length
						int length = 1;
						int bx = x + xdir;
						int by = y + ydir;
						while (bx < BOARD_SIZE && by < BOARD_SIZE && board[by].charAt(bx) == c) {
							length++;
							bx += xdir;
							by += ydir;
						}
						// Add it to the map
						blockMap.put(c, new Block(dir, length, y, x));

						// Calculate its initial position
						int position = (xdir * x) + (ydir * y);
						// Add it to the map
						positionMap.put(c, position);

						// Keep track of the character
						charList.add(c);
						
//						System.out.format("%c: Dir:%s Length:%d Pos:%d\n", c, (dir == HORIZONTAL ? "Horizontal" : "Vertical"), length, position);
					}
				}
			}
		}

		// Get the number of blocks on this map
		blockCount = charList.size();
		// Create the array of blocks
		blocks = new Block[blockCount];
		// Create the initial states positon array
		s.positions = new int[blockCount];
		// Get the index of the target block to move.
		target = charList.indexOf(targetChar);

		// Load the blocks and initial state positions.
		for (int i = 0; i < blockCount; i++) {
			char b = charList.get(i);
			blocks[i] = blockMap.get(b);
			s.positions[i] = positionMap.get(b);
			
//			System.out.format("%c: [%d,%d,%dx%d] -> {%d,%d}", b, blocks[i].column, blocks[i].row, blocks[i].w, blocks[i].h, blocks[i].xdir, blocks[i].ydir);
//			System.out.format(" bounds[%d,%d,%d,%d]\n", blocks[i].getLeft(s.positions[i]), blocks[i].getTop(s.positions[i]), blocks[i].getRight(s.positions[i]), blocks[i].getBottom(s.positions[i]));
		}

		// It has an intial depth of 0, return it!
		s.depth = 0;
		return s;
	}

	//==================================================================
	// Solves the block game from the given initial game state and returns the
	// minimum number of moves it takes. If the board is impossible to solve then
	// -1 is returned.
	//==================================================================
	int solve(State initial) {
		stateVals = new HashSet<Integer>(1024);
		// Use a queue to simulate depth-first-search
		Queue<State> q = new ArrayDeque<State>();
		q.offer(initial);
		// Don't repeat initial state
		stateVals.add(initial.getStateValue(-1, -1));

		// Loop until all possible paths have been searched.
		while (!q.isEmpty()) {
			State state = q.poll();
			// If the final state is reachable return its depth.
			if (state.isFinalState()) {
//				Stack<State> path = new Stack<State>();
//				State current = state;
//				while (current != null) {
//					path.push(current);
//					current = current.parent;
//				}
//				while (!path.isEmpty()) {
//					printState(path.pop());
//				}
//				System.out.println("END");
				
				return state.depth + 1;
			}
			state.branch(q);
		}
		
		// No solution exists!
		return -1;
	}

//	private void printState(State s) {
//		char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
//		
//		for (int y = 0; y < BOARD_SIZE; y++)
//			for (int x = 0; x < BOARD_SIZE; x++)
//				board[y][x] = '.';
//		
//		for (int i = 0; i < blockCount; i++) {
//			int pos = s.positions[i];
//			int l = blocks[i].getLeft(pos);
//			int r = blocks[i].getRight(pos);
//			int t = blocks[i].getTop(pos);
//			int b = blocks[i].getBottom(pos);
//			for (int y = t; y <= b; y++)
//				for (int x = l; x <= r; x++)
//					board[y][x] = (char)(i + 'A');
//		}
//		
//		for (int y = 0; y < BOARD_SIZE; y++)
//			System.out.println(String.valueOf(board[y]));
//		System.out.println("  \\/  ");
//	}
	
	//==================================================================
	// A block without a position (only dimension and axis information)
	//==================================================================
	class Block {
		int xdir, ydir, w, h, row, column;
		// Initializes a new Block
		Block(int dir, int length, int row, int column) {
			this.xdir = dir;					// 1 for horizontal, 0 for vertical
			this.ydir = 1 - dir;				// 0 for horizontal, 1 for vertical
			this.w = (length - 1) * xdir;	// Width in blocks - 1
			this.h = (length - 1) * ydir;	// Height in blocks - 1
			this.row = row * xdir;			// Row location (0 if vertical)
			this.column = column * ydir;	// Column location (0 if horizontal)
		}
		// Returns whether the given x,y point lies on this block at the given 
		// position on its axis.
		boolean contains(int x, int y, int position) {
			int l = getLeft(position);
			int t = getTop(position);
			int r = getRight(position);
			int b = getBottom(position);
			return !(x < l || x > r || y < t || y > b);
		}
		// Gets the column index of the left of this bar.
		int getLeft(int position) {
			return (position * xdir) + column;
		}
		// Gets the column index of the right of this bar.
		int getRight(int position) {
			return (position * xdir) + column + w;
		}
		// Gets the row index of the top of this bar.
		int getTop(int position) {
			return (position * ydir) + row;
		}
		// Gets the row index of the bottom of this bar.
		int getBottom(int position) {
			return (position * ydir) + row + h;
		}

	}

	//==================================================================
	// A state of the game (move count and positions of the bars)
	//==================================================================
	class State {
		int depth;
		int[] positions;
//		State parent;

		// Returns whether the space x,y is free in this state
		boolean isFree(int x, int y) {
			if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE)
				return false;
			
			for (int i = 0; i < positions.length; i++) {
				if (blocks[i].contains(x, y, positions[i]))
					return false;
			}
			return true;
		}
		// Returns whether this is the final state
		boolean isFinalState() {
			int right = blocks[target].getRight(positions[target]) + 1;
			int row = blocks[target].row;
			while (right < BOARD_SIZE) {
				if (!isFree(right, row))
					return false;
				right++;
			}
			return true;
		}
		// Returns the unique value for this state with a modification of one position
		int getStateValue(int difIndex, int difPosition) {
			int scale = 1, value = 0;
			for (int i = 0; i < blockCount; i++) {
				if (i != difIndex) {
					value += positions[i] * scale;
				} else {
					value += difPosition * scale;
				}
				scale *= STATE_VALUE_SCALE;
			}
			return value;
		}
		// Branches any child states to the given queue
		void branch(Queue<State> states) {
			// For each block in this state...
			for (int i = 0; i < blockCount; i++) {

				//==================================================================
				// For each position before the block...
				//==================================================================
				int beforeX = blocks[i].getLeft(positions[i]) - blocks[i].xdir;
				int beforeY = blocks[i].getTop(positions[i]) - blocks[i].ydir;
				int beforePosition = positions[i] - 1;
				while (beforeX >=0 && beforeY >= 0) {
					// If we can move here...
					if (isFree(beforeX, beforeY)) {
						int value = getStateValue(i, beforePosition);
						// Make sure states arent revisited
						if (!stateVals.contains(value)) {
							State s = new State();
							s.positions = Arrays.copyOf(positions, blockCount);	// Copy the positions
							s.positions[i] = beforePosition;								// Only change the position of the current block
							s.depth = depth + 1;												// Its at the next depth
//							s.parent = this;													// Keep track of previous state
							states.offer(s);													// Add it to the queue				
							stateVals.add(value);											// Avoid repititious states
						}
					} else {
						break;
					}
					beforeX -= blocks[i].xdir;
					beforeY -= blocks[i].ydir;
					beforePosition--;
				}
				
				//==================================================================
				// For each position after the block
				//==================================================================
				int afterX = blocks[i].getRight(positions[i]) + blocks[i].xdir;
				int afterY = blocks[i].getBottom(positions[i]) + blocks[i].ydir;
				int afterPosition = positions[i] + 1;
				while (afterX < BOARD_SIZE && afterY < BOARD_SIZE) {
					// If we can move here...
					if (isFree(afterX, afterY)) {
						int value = getStateValue(i, afterPosition);
						// Make sure states arent revisited
						if (!stateVals.contains(value)) {
							State s = new State();
							s.positions = Arrays.copyOf(positions, blockCount);	// Copy the positions
							s.positions[i] = afterPosition;								// Only change the position of the current block
							s.depth = depth + 1;												// Its at the next depth
//							s.parent = this;													// Keep track of previous state
							states.offer(s);													// Add it to the queue
							stateVals.add(value);											// Avoid repititious states
						}
					} else {
						break;
					}
					afterX += blocks[i].xdir;
					afterY += blocks[i].ydir;
					afterPosition++;
				}
			}
		}
	}

}
