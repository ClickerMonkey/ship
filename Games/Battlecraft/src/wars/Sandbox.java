package wars;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Sandbox 
{
	
	
	/**

Map m = new Map(32, 32, new MapListener() {
  public boolean canMove(int tox, int toy, int fromx, int fromy) {
     return tiles[t0x][toy] == AIR;
  }
});
// Bird
Stack<Node> moves = map.moves(birdNode, playerNode, 64, Map.FLYER_PATHS);
// Jumper
Stack<Node> moves = map.moves(jumpNode, playerNode, 64, Map.JUMPER_PATHS);

	 */
	
	public class Node {
		private byte visits;
		private int depth;
		private short x, y;
		private Node parent;
		public Node(short x, short y) {
			this.x = x;
			this.y = y;
		}
		public void clear(byte intialVisit) {
			visits = intialVisit;
			parent = null;
			depth = 0;
		}
	}
	public interface MapListener {
		public boolean canMove(int tox, int toy, int fromx, int fromy);
	}
	public class Map {
		public final int[][][] JUMPER_PATHS = {
				{{-1, 0}}, // Left
				{{1, 0}},  // Right
				{{0, 1}},  // Below
				{{0, -1}, {-1, 0}}, // Top Left 1
				{{0, -1}, {1, 0}},  // Top Right 1
				{{0, -1}, {0, -1}, {-1, 0}}, // Top Left 2
				{{0, -1}, {0, -1}, {1, 0}},  // Top Right 2
		};
		public final int[][][] FLYER_PATHS = {
				{{-1, 0}}, // Left
				{{-1, 0}, {0, 1}}, //Left Below
				{{0, 1}, {-1, 0}}, //Below Left
				{{0, 1}},  // Below
				{{1, 0}, {0, 1}}, //Right Below
				{{0, 1}, {1, 0}}, //Below Right
				{{1, 0}},  // Right
				{{1, 0}, {0, -1}}, //Right Above
				{{0, -1}, {1, 0}}, //Above Right
				{{0, -1}},  // Above
				{{-1, 0}, {0, -1}}, //Left Above
				{{0, -1}, {-1, 0}}, //Above Left
		};
		public int width, height;
		public Node[][] nodes;
		public byte searches;
		public MapListener listener;
		public Map(int w, int h, MapListener mapListener) {
			width = w;
			height = h;
			nodes = new Node[h][w];
			for (short y = 0; y < h; y++) {
				for (short x = 0; x < w; x++) {
					nodes[y][x] = new Node(x, y);
				}
			}
			listener = mapListener;
		}
		public Stack<Node> moves(Node start, Node end, int radius, int[][][] paths) {
			Stack<Node> moves = new Stack<Node>();
			if (search(start, end, radius, paths)) {
				while (end != null) {
					moves.push(end);
					end = end.parent;
				}
			}
			return moves;
		}
		public boolean search(Node start, Node end, int radius, int[][][] paths) {
			start.clear(++searches);
			Queue<Node> queue = new LinkedList<Node>();
			queue.offer(start);
			while (!queue.isEmpty()) {
				Node c = queue.poll();
				// Has the ending node been found?
				if (c == end) {
					return true;
				}
				// If this node is outside the radius the end could not be found
				if (c.depth > radius) {
					return false;
				}
				// For all possible paths the entity can make in one "move"...
				for (int[][] path : paths) {
					// Keep track of last position
					int px = c.x;
					int py = c.y;
					boolean reaches = true;
					// For each movement in the path...
					for (int[] move : path) {
						// Compute the next node
						int nx = px + move[0];
						int ny = py + move[1];
						// If something exists here and the entity can move
						if (!exists(nx, ny) || !listener.canMove(nx, ny, px, py) || nodes[nx][ny].visits == searches) {
							reaches = false; 
							break;
						}
						// Save last node
						px = nx;
						py = ny;
					}
					// If the final node could be reached...
					if (reaches) {
						// Add this as a possibility
						Node m = nodes[py][px];
						m.parent = c;
						m.visits = searches;
						m.depth = c.depth + 1;
						queue.offer(m);
					}
				}
			}
			return false;
		}
		public boolean exists(int x, int y) {
			return !(x < 0 || x >= width || y < 0 || y >= height);
		}
	}
	
}
