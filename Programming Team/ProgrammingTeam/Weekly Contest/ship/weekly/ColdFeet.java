package ship.weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class ColdFeet
{
	private static final int ICE = 0;
	private static final int NORTHEAST = 1;
	private static final int SOUTHWEST = 2;
	private static final int SOUTHEAST = 3;
	private static final int NORTHWEST = 4;
	private static final int DIAMOND = 5;
	private static final int START = 6;
	private static final int END = 7;
	private static final int PLATFORM = 8;

	public static void main(String[] args) {
		new ColdFeet();
	}

	private HashMap<String, Integer> hashmap;
	private ArrayList<Node> nodes;
	private int startx, starty;
	private int endx, endy;
	private int width;
	private int height;
	private int[][] map;
	private Edge[][] graph;

	ColdFeet() {
		hashmap = new HashMap<String, Integer>();
		hashmap.put("IC", ICE);
		hashmap.put("NE", NORTHEAST);
		hashmap.put("SW", SOUTHWEST);
		hashmap.put("SE", SOUTHEAST);
		hashmap.put("NW", NORTHWEST);
		hashmap.put("DI", DIAMOND);
		hashmap.put("SP", START);
		hashmap.put("EP", END);
		hashmap.put("PF", PLATFORM);

		Scanner in = new Scanner(System.in);

		int caseCount = in.nextInt();

		for (int caseNum = 1; caseNum <= caseCount; caseNum++) {

			width = in.nextInt();
			height = in.nextInt();
			in.nextLine();

			map = new int[height][width];
			nodes = new ArrayList<Node>();

			for (int y = 0; y < height; y++) {
				String line = in.nextLine();
				for (int x = 0; x < width; x++) {
					int offset = x * 2;
					map[y][x] = hashmap.get(line.substring(offset, offset + 2));

					switch (map[y][x]) {
						case START:
							startx = x;
							starty = y;
							break;
						case END:
							endx = x;
							endy = y;
							break;
						case PLATFORM:
							nodes.add(new Node(x, y));
							break;
					}
				}
			}
			
			int nodeCount = nodes.size();
			
			graph = new Edge[nodeCount][nodeCount];
			for (int y = 0; y < nodeCount; y++) {
				for (int x = 0; x < nodeCount; x++) {
					graph[y][x] = new Edge();
				}
			}

			move(startx, starty, 1, 0);
			move(startx, starty, -1, 0);
			move(startx, starty, 0, 1);
			move(startx, starty, 0, -1);
			
			
		}

	}

	private void move(int sx, int sy, int dirx, int diry) {
		int x = sx;
		int y = sy;
		int moves = 0;
		ArrayList<Diamond> diamonds = new ArrayList<Diamond>();
		
		while (true) {
			x += dirx;
			y += diry;
			moves++;

			if (!canMove(x, y, dirx, diry)) {
				return;
			}
			
			switch (map[y][x]) {
				case DIAMOND:
					diamonds.add(new Diamond(x, y));
					break;
				case END:
					
					break;
				case PLATFORM:

					move(x, y, -x, -y);
					move(x, y, -y, -x);
					move(x, y, y, x);
					break;
			}

			dirx = getDirx(x, y, dirx, diry);
			diry = getDiry(x, y, dirx, diry);
		}
	}

	
	
	private boolean canMove(int tox, int toy, int dirx, int diry) {
		if (tox < 0 || tox >= width || toy < 0 || toy >= height) {
			return false;
		}
		switch (map[toy][tox]) {
			case END:
			case DIAMOND:
			case PLATFORM:
			case ICE:
				return true;
			case START:
				return false;
			case NORTHEAST:
				return (dirx == -1 || diry == 1);
			case NORTHWEST:
				return (dirx == 1 || diry == 1);
			case SOUTHEAST:
				return (dirx == -1 || diry == -1);
			case SOUTHWEST:
				return (dirx == 1 || diry == -1);
		}
		return false;
	}
	
	private int getDirx(int x, int y, int dirx, int diry) {
		switch (map[y][x]) {
			case END:
			case START:
			case DIAMOND:
			case PLATFORM:
			case ICE:
				return dirx;
			case NORTHEAST:
			case SOUTHWEST:
				return diry;
			case NORTHWEST:
			case SOUTHEAST:
				return -diry;
		}
		return 0;
	}
	
	private int getDiry(int x, int y, int dirx, int diry) {
		switch (map[y][x]) {
			case END:
			case START:
			case DIAMOND:
			case PLATFORM:
			case ICE:
				return diry;
			case NORTHEAST:
			case SOUTHWEST:
				return dirx;
			case NORTHWEST:
			case SOUTHEAST:
				return -dirx;
		}
		return 0;
	}
	
	class Node {
		int x, y;
		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	class Edge {
		int length;
		ArrayList<Diamond> diamonds;
		Edge() {
			this.length = 0;
			this.diamonds = new ArrayList<Diamond>();
		}
	}

	class Diamond {
		boolean taken;
		int x, y;
		Diamond(int x, int y) {
			this.x = x;
			this.y = y;
			this.taken = false;
		}
	}



}
