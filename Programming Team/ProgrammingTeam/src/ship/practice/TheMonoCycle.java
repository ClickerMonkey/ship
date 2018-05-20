package ship.practice;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Source:
 * http://uva.onlinejudge.org/external/100/10047.html
 *  
 * @author Philip Diffenderfer
 *
 */
public class TheMonoCycle
{

	public static void main(String[] args) {
		new TheMonoCycle();
	}

	private static final byte NORTH = 0;
	private static final byte EAST = 1;
	private static final byte SOUTH = 2;
	private static final byte WEST = 3;

	private static final boolean OPEN = false;
	private static final boolean BLOCKED = true;

	private boolean[][] graph;
	private int w, h;
	private LinkedList<Node> current;
	private VisitedTable visited;
	private byte startX, startY, endX, endY;
	/** Gets Input and sets up each case to solve */
	public void start() {
		Scanner sc = new Scanner(System.in);
		h = sc.nextInt();
		w = sc.nextInt();
		String line = "";
		byte x = 0, caseIndex = 0;
		while (w != 0 && h != 0) {
			graph = new boolean[w][h];
			for (byte y = 0; y < h; y++) {
				line = sc.next();
				x = 0;
				for (char c : line.toCharArray()) {
					if (c == '#') graph[x][y] = BLOCKED;
					if (c == 'S') { startX = x; startY = y; }
					if (c == 'T') { endX = x; endY = y; }
					x++;
				}
			}
			caseIndex++;
			System.out.println("\nCase #" + caseIndex);
			solve();
			h = sc.nextInt();
			w = sc.nextInt();
		}
	}
	/** Solves the current Graph */
	public void solve() {
		current = new LinkedList<Node>();
		visited = new VisitedTable();
		Node root = new Node(startX, startY, NORTH, 0, 0);
		root.expand();
		Node next;
		while (current.size() != 0) {
			int items = current.size();
			for (int i = 0; i < items; i++) {
				next = current.poll();
				if (!next.expand()) {
					System.out.println("minimum time = " + next.time + " sec");
					return;
				}
				visited.add(next);
			}
		}
		System.out.println("destination not reachable");
	}
	/** Returns true if x and y are in bounds and that cell is OPEN */
	private boolean canMove(byte x, byte y) {
		if (x < 0 || x >= w || y < 0 || y >= h) return false;
		return (graph[x][y] == OPEN);
	}

	/** Keeps track of each possible MonoCycle path */
	private class Node {
		byte x, y, dir;
		int length, time;
		/** Constructor*/
		public Node(byte X, byte Y, byte Dir, int Length, int Time) {
			x = X; y = Y; dir = Dir; length = Length; time = Time;
		}
		/** Returns false if this Node is at the end on green
		 *  Tries to branch off at 90 degrees in both directions.
		 *  Tries to move forward one space. */
		public boolean expand() {
			if (length % 5 == 0 && x == endX && y == endY) {
				return false;
			}
			Node n;			
			n = new Node(x, y, rotate(dir-1), length, time+1);
			if (!visited.contains(n)) current.offer(n);
			n = new Node(x, y, rotate(dir+1), length, time+1);
			if (!visited.contains(n)) current.offer(n);

			byte newX = (byte)(x + (dir == WEST ? -1 : (dir == EAST ? 1 : 0)));
			byte newY = (byte)(y + (dir == NORTH ? -1 : (dir == SOUTH ? 1 : 0)));
			if (canMove(newX, newY)) {
				n = new Node(newX, newY, dir, length+1, time+1);
				if (!visited.contains(n)) current.offer(n);
			}
			return true;
		}
		/** Returns the compass direction based on dir. */
		public byte rotate(int dir) {
			return (byte)(dir >= 0 ? dir % 4 : 4 + dir % 4);
		}
		/** Returns a unique integer based on its location, color, and direction. */
		public int hashCode() {
			return ((y * w) + x) * 20 + ((length % 5) * 4) + dir;
		}
	}

	/** Keeps track of each Node thats been visited based on their unique number. */
	private class VisitedTable {
		private int size;
		private boolean[] table;
		/** Sets the initial size based on the graph width and height and possibilities of colors and directions. */
		public VisitedTable() {
			size = w * h * 20;
			table = new boolean[size];
		}
		public void add(Node n) {
			table[n.hashCode()] = true;
		}
		public boolean contains(Node n) {
			return (table[n.hashCode()] != false);
		}
	}
}
