package ship.dickinson10;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Solves the rook problem. This starts at the 'destination' and from there
 * branches out to every neighboring space that can move to that position. This
 * continually happens in a breadth-first way until we hit the starting 
 * position. If the starting position isn't hit then this returns no solution.
 * Each space on a map is visited only once, and when it is its number of
 * moves from the destination point is saved.
 * 
 * @author Philip Diffenderfer, Keith Porter
 */
public class rook {

	/*

5 5 0 0 1 3
3 4 1 3 1 
3 3 3 0 2
3 1 2 2 3
4 2 3 3 3
4 1 4 3 2
5 5 0 0 4 4
3 3 2 4 3
2 2 2 1 1
4 3 1 3 4
2 3 1 1 3
1 1 3 2 0
1 30 0 0 0 10
15 11 19 21 7 1 23 12 17 1 0 10 4 9 12 1 10 2 6 10 3 7 4 6 1 3 24 25 23 2 
0
	 */
	
	public static void main(String[] args) {
		new rook();
	}

	/**
	 * A single space that has a depth (number of moves since start), whether it
	 * has been visited already, a value (how many spaces from it you must move),
	 * and its position on the map.
	 */
	class Point {
		boolean visited = false;
		int depth = 0;
		int value;
		int x, y;
		Point (int x0, int y0, int value0) {
			x = x0; y = y0; value = value0;
		}
	}
	
	int rows;
	int cols;
	int startx, starty;
	int endx, endy;
	Point[][] map;
	
	rook() {
		Scanner in = new Scanner(System.in);
		
		rows = in.nextInt();
		
		while (rows > 0) {
			// Read in input
			cols = in.nextInt();
			starty = in.nextInt();
			startx = in.nextInt();
			endy = in.nextInt();
			endx = in.nextInt();
			
			// Build map of points
			map = new Point[rows][cols];
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					map[y][x] = new Point(x, y, in.nextInt());
				}
			}
			
			// If the end and start are the same, its 0 moves.
			if (endx == startx && endy == starty) {
				System.out.println(0);
			}
			else {
				// Calculate how many moves starting at the ending space moving
				// towards the starting space.
				int moves = solve(map[endy][endx]);
				// No solution?
				if (moves == -1) {
					System.out.println("No solution.");
				}
				// Minimum number of moves found.
				else {
					System.out.println(moves);
				}
			}
			
			// More rows?
			rows = in.nextInt();
		}
		
	}

	/**
	 * Performs a breadth-first-search for the solution given the starting 
	 * position. If no solution exists then -1 is returned.
	 */
	int solve(Point start) {
		Queue<Point> queue = new LinkedList<Point>();
		queue.offer(start);
		
		// While there are possible spaces to jump to...
		while (!queue.isEmpty()) {
			Point curr = queue.poll();
			Point solution = null;
			
			// Move in all 4 directions and if a solution is found return its depth
			solution = move(curr.x, curr.y, -1, 0, queue);
			if (solution != null) return solution.depth;
			solution = move(curr.x, curr.y, 1, 0, queue);
			if (solution != null) return solution.depth;
			solution = move(curr.x, curr.y, 0, 1, queue);
			if (solution != null) return solution.depth;
			solution = move(curr.x, curr.y, 0, -1, queue);
			if (solution != null) return solution.depth;
		}
		
		// No solution exists.
		return -1;
	}
	
	/**
	 * Given a start space and a direction this will cover all spaces in that 
	 * direction and for each space which can reach the starting space this will
	 * offer its point to the given queue. If the destination is found then it is
	 * returned as a point, else null is returned.
	 */
	Point move(int sx, int sy, int dirx, int diry, Queue<Point> queue) {
		// Move to the next place by default.
		int x = sx + dirx;
		int y = sy + diry;
		int length = 1;
		// While this space is on the map...
		while (onMap(x, y)) {
			// If the current space can reach the starting space and it hasn't been
			// visited yet then set its depth, visit it, and enqueue it. If it is
			// the destination space then return it.
			if (map[y][x].value == length && !map[y][x].visited) {
				map[y][x].depth = map[sy][sx].depth + 1;
				map[y][x].visited = true;
				if (x == startx && y == starty) {
					return map[y][x];
				}
				queue.offer(map[y][x]);
			}
			// Proceed in this direction
			x += dirx;
			y += diry;
			length++;
		}
		// Solution not found in this direction
		return null;
	}
	
	/**
	 * Returns true if x,y is on the map. 
	 */
	boolean onMap(int x, int y) {
		return !(x < 0 || x >= cols || y < 0 || y >= rows);
	}
	
}
