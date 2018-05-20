package ship.pacise10;

import java.util.*;

public class problem3 {
	public static void main(String[] args) {
		new problem3();
	}

	static final int dirs[][] = {
		{0, 1},
		{0, -1},
		{1, 0},
		{-1, 0}
	};

	static final int PIT = -1;
	static final int EMPTY = 0;
	int goldx, goldy;
	int agentx, agenty;
	int wompx, wompy;
	int size;
	int board[][];
	int visited[][];

	problem3() {
		Scanner in = new Scanner(System.in);
		
		size = in.nextInt();
		in.nextLine();
		board = new int[size][size];
		visited = new int[size][size];
		resetVisited();

		for (int y = 0; y < size; y++) {
			String line = in.nextLine().trim();
			for (int x = 0; x < size; x++) {
				switch (line.charAt(x * 2)) {
				case '-':
					board[y][x] = EMPTY;
					break;
				case 'G':
					goldx = x;
					goldy = y;
					break;
				case 'P':
					board[y][x] = PIT;
					break;
				case 'A':
					agentx = x;
					agenty = y;
					break;
				case 'W':
					wompx = x;
					wompy = y;
					break;
				}
			}
		}

		ArrayList<Node> toWomp = getPath(agentx, agenty, wompx, wompy);
		resetVisited();
		ArrayList<Node> toGold = getPath(wompx, wompy, goldx, goldy);
		
		// Cross product of two sets
		int pathNum = 1;
		for (Node n1 : toWomp) {
			for (Node n2 : toGold) {
				System.out.format("Path number %d:", pathNum);
				printBackwards(n1.parent);
				printBackwards(n2);
				System.out.println();
				pathNum++;
			}
		}

		if (pathNum == 1) {
			System.out.println("Impossible");
		}
		
	}

	void resetVisited() {
		
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				visited[y][x] = Integer.MAX_VALUE;
			}
		}
	}

	void printBackwards(Node node) {
		if (node == null) {
			return;
		}
		if (node.parent != null) {
			printBackwards(node.parent);
		}
		System.out.format(" (%d,%d)", node.y, node.x);
	}

	ArrayList<Node> getPath(int startx, int starty, int endx, int endy) {
		Queue<Node> queue = new LinkedList<Node>();
		Node root = new Node(startx, starty, null);	
		queue.add(root);

		ArrayList<Node> solution = new ArrayList<Node>();
		int solvedDepth = Integer.MAX_VALUE;
		while (!queue.isEmpty()) {
			Node n = queue.poll();
			visited[n.y][n.x] = n.depth;
			//System.out.format("%d, %d\n", n.x, n.y);
			// Stop once we hit all nodes at the solution depth
			if (n.depth > solvedDepth) {
				break;
			}
			if (n.x == endx && n.y == endy) {
				solution.add(n);
				solvedDepth = n.depth;
			}
			else if (n.x == goldx && n.y == goldy) {
				// Ignore, we can't walk on gold
				continue;
			}
			else {
				for (int[] dir : dirs) {
					int px = n.x + dir[0];
					int py = n.y + dir[1];
					if (!canWalk(px, py)) {
						continue;
					}
					if (visited[py][px] <= n.depth) {
						continue;
					}
					Node child = new Node(px, py, n);
					queue.offer(child);
				}
			}
		}
		return solution;
	}

	boolean canWalk(int x, int y) {
		if (x < 0 || x >= size || y < 0 || y >= size) {
			return false;
		}
		return board[y][x] != PIT;
	}

	class Node {
		Node parent;
		int depth;
		int x, y;
		Node(int x, int y, Node parent) {
			this.x = x;
			this.y = y;
			if (parent != null) {
				this.depth = parent.depth + 1;
				this.parent = parent;
			}
		}
	}

}
