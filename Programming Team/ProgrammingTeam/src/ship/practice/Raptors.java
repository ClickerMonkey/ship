package ship.practice;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * @author Philip Diffenderfer
 * 
 * Are your neighbors raptors?
 *
 * Grant needs your help! He's trapped in Jurasic park and surrounded by deadly
 * velociraptors. In an instant he contacts you, his intel, and notifies you of
 * the situation. He's not sure how fast or intellegent they are but he can
 * make guesses. You must give him the coordinates to where he must run to to 
 * avoid being devoured by raptors. To determine his possibilities for survival,
 * he must approximate the speed and intelligence of a velociraptor and give 
 * them to you. Your job is to take these estimations and determine whether he 
 * has a chance at survival, and if so how much time to spare he would have.
 *
 * There are several obstacles on the map you must account for; the map is 
 * filled with walls and doors. Grant can make it through the doors just as
 * easily as running, however the raptors have trouble with doors. Grant 
 * measured a raptor's intelligence by how many seconds he thinks it takes for a
 * raptor to open a door. Once a raptor opens a door however he expects the time
 * to open the next door to be halved until at the minimum it takes a raptor 
 * only an additional second to open the door.
 *
 * To get a good estimation at survival, he gives you several trials where each
 * trial has the speed of a raptor in tiles-per-second and the intelligence of
 * the raptor, which is measured in the initial time it takes for a raptor to
 * open a door (of course each subsequent time is halved).
 *
 * Additional Intel:
 * 	1. Grant can only move at a speed of 1 tile-per-second.
 * 	2. Grant makes the first move, once he's at his target location, he's safe.
 * 	3. Grant knows the quickest way to get there, after all you sent him the 
 * 		coordinates.
 * 	4. Neither Grant nor the raptors can move diagonally, only along the x and
 * 		y-axis.
 * 	5. Raptors can share a single tile if they need to.
 * 	6. Raptors are very smart, in fact they will try to cut Grant off at all
 *			chances they have to make sure he doesn't get to his target.
 * 	7. If the raptors intelligence starts at 11 then the sequence of changing
 * 		intelligence is: 6,3,2,1,1,...1
 * 
 * Input:
 * 
 * The input will consist of an arbitrary number of cases. The first line of a 
 * case contains two integers w and h (2<=w<=1000, 2<=h<=1000) which stands for 
 * the width and height of the map respectively. Following this line there are
 * h lines where each line is w characters wide. There are several characters
 * expected for this map to contain, they are:
 * 
 * 	S = Initial location of Grant
 * 	E = Target location of Grant
 * 	R = Initial Location of a velociraptor
 * 	% = A door
 * 	[ ] = A walkable tile
 * 	[+-|] = A wall piece
 * 
 * After the map Grant gives you the number of trials to expect next as a single
 * integer t. Next there are t pairs of integers s and i (1<=s<=100, 1<=i<=200)
 * which stands for the speed of a raptor and its intelligence respectively. The
 * end of cases occurs when the w or h are both zero. You can expext all input
 * to be valid.
 * 
 * Output:
 * 
 * For each case, you must print the following message in a line alone:
 * Case #x:
 * Where x stands for the current case number, starting from 1. The next t lines
 * will contain the results from each trial. If Grant could not make it to the
 * target in time then the following message is displayed:
 * Trial y: Eaten alive by the raptors!
 * Where y stands for the current trial number under the current case starting
 * from 1. If Grant did make it to the target then this output is expected:
 * Trial y: You made it there in q seconds with p second(s) to spare!
 * 
 */
public class Raptors
{
	
	public static void main(String[] args) {
		new Raptors();
	}
	
	static final int TILE = 0;
	static final int DOOR = 1;
	static final int WALL = 2;

	int width;						// The width of the map
	int height;						// The height of the map
	Point start;					// Starting position of person on map
	Point end;						// Ending position of person on map
	ArrayList<Point> raptors;	// The list of raptor positions
	byte[][] map;					// The map of tiles, doors, and walls
	
	// Reads input for the Raptors problem
	Raptors() {
		Scanner in = new Scanner(System.in);
		
		width = in.nextInt();
		height = in.nextInt();
		int cases = 1;
		
		// Loop until a map is given with 0 width and height
		while (width != 0 || height != 0) {

			// Skip newline character
			in.nextLine();	
			// Read in each row of character
			String[] lines = new String[height];
			for (int y = 0; y < height; y++) {
				lines[y] = in.nextLine();
			}
			
			// Parse character map into actual map and important locations.
			parseMap(lines);
			
			System.out.format("Case #%d:\n", cases);
			// Solve the actual problem given your location, raptor locations, the
			// target location, and the maps description.
			solve(in);
			
			// Next case!
			width = in.nextInt();
			height = in.nextInt();
			cases++;
		}
		                                   
		
	}
	
	// Solves the Raptors problem given the current case
	void solve(Scanner in) {
		
		// Find your path form start to end. The length of your path is the
		// number of seconds it took you to get to the target location.
		Path yourPath = backtrack(search(start.x, start.y, end.x, end.y));
		
//		System.out.format("Your path: length(%d) doors(%d)\n", yourPath.length, yourPath.doors);
		
		// The paths (and count) of raptors that can reach the target location.
		int raptorCount = 0;
		Path[] raptorPath = new Path[raptors.size()];
		
		// Find each path of every raptor
		for (int i = 0; i < raptors.size(); i++) {
			
			// Do a search from raptor location to end location.
			SearchNode target = search(raptors.get(i).x, raptors.get(i).y, end.x, end.y);
			
			// Only compute the path if the raptor can get out of its room
			if (target != null) {
				raptorPath[raptorCount++] = backtrack(target);
			}
		}
		
		// Do each trial now
		int trials = in.nextInt();
		boolean eaten = false;
		
		for (int i = 1; i <= trials; i++) {
			// Read in the speed and intelligence of the raptors
			int speed = in.nextInt();
			int intelligence = in.nextInt();
			// How much time did we have to spare?
			int spareTime = Integer.MAX_VALUE;
			// Start out as not eaten =)
			eaten = false;
			
			for (int j = 0; j < raptorCount; j++) {
				// The time it took to run the path without obstacles (and truncated
				// to an integer since you get to move first.
				int pathTime = raptorPath[j].length / speed;
				// Total time it took to open any crossed doors
				int doorTime = getDoorTime(intelligence, raptorPath[j].doors);
				// Total accumulated in seconds.
				int totalTime = pathTime + doorTime;
				
//				System.out.format("Raptor path: length(%d) doors(%d) time(%d)\n", raptorPath[j].length, raptorPath[j].doors, totalTime);
				
				// If the raptor got there first then no matter what you would've
				// gotten eaten by him, he's full now.
				if (totalTime < yourPath.length) {
					eaten = true;
					break;
				}
				// Update the spare time you had.
				if (totalTime - yourPath.length < spareTime) {
					spareTime = totalTime - yourPath.length;
				}
			}
			
			// Print out the trial number
			System.out.format("Trial %d: ", i);
			if (eaten) {
				// Oh nos! Gobble Gobble Gobble
				System.out.println("Eaten alive by the raptors!");
			} else {
				// YOU MADE IT!!!! with time to spare?
				System.out.format("You made it there in %d seconds with %d second(s) to spare!\n", yourPath.length, spareTime);
			}
		}
	}
	
	// Returns the time it would take a raptor of a given intelligence to make
	// it through so many doors.
	int getDoorTime(int intelligence, int doors) {
		int time = 0;
		while (--doors >= 0) {
			time += intelligence;
			intelligence = (intelligence + 1) >> 1;
		}
		return time;
	}
	
	// Parses the array of rows to a map.
	void parseMap(String[] lines) {
		map = new byte[height][width];
		raptors = new ArrayList<Point>();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				switch (lines[y].charAt(x)) {
					case 'S':	// S is the starting location of the person
						start = new Point(x, y);
						break;
					case 'E':	// E is the ending location of the person
						end = new Point(x, y);
						break;
					case 'R':	// R represents the position of a raptor
						raptors.add(new Point(x, y));
						break;
					case '%':	// A percent represents a door (with handles)
						map[y][x] = DOOR;
						break;
					case ' ':	// A Space represents a tile (empty space)
						map[y][x] = TILE;
						break;
					default:		// If it's not any of the above it must be a wall
						map[y][x] = WALL;
				}
			}
		}
	}
	
	// Does a shortest path search starting at the given tile and ending at the
	// target tile. If no path is possible then null is returned, otherwise the
	// node on the target location is returned. (and ready for backtracking).
	SearchNode search(int sx, int sy, int tx, int ty) {
		// Keep track of visited tiles
		boolean[][] visited = new boolean[height][width];
		
		// Use a queue to make it a Breadth-First-Search
		Queue<SearchNode> queue = new ArrayDeque<SearchNode>();
		queue.offer(new SearchNode(null, sx, sy));
		
		// Continue while their exist possible moves.
		while (!queue.isEmpty()) {
			
			// Take the next node to search
			SearchNode node = queue.poll();
			int nx = node.x;
			int ny = node.y;
			
			// Mark it as visited.
			visited[ny][nx] = true;
			
			// If we arrived at the target position then return the last n
			if (nx == tx && ny == ty) {
				return node;
			}
			
			// Can we move left?
			if (isWalkable(nx - 1, ny) && !visited[ny][nx - 1]) {
				queue.offer(new SearchNode(node, nx - 1, ny));
			}
			// Can we move up?
			if (isWalkable(nx, ny - 1) && !visited[ny - 1][nx]) {
				queue.offer(new SearchNode(node, nx, ny - 1));
			}
			// Can we move right?
			if (isWalkable(nx + 1, ny) && !visited[ny][nx + 1]) {
				queue.offer(new SearchNode(node, nx + 1, ny));
			}
			// Can we move down?
			if (isWalkable(nx, ny + 1) && !visited[ny + 1][nx]) {
				queue.offer(new SearchNode(node, nx, ny + 1));
			}
		}
		
		// No path from start to target
		return null;
	}
	
	// Returns if tile x,y is walkable (on the map and not a wall)
	boolean isWalkable(int x, int y) {
		return (!(x < 0 || x >= width || y < 0 || y >= height) && (map[y][x] != WALL));
	}
	
	// Backtracks through the node keeping track of how many doors there were,
	// how many steps it took, and how many turns there were.
	Path backtrack(SearchNode node) {
		Path p = new Path();
		// The starting node does not have a parent, loop until its reached.
		while (node.parent != null) {
			// Increment the length of the path per node
			p.length++;
			// If the map is a door then add it to the counter
			p.doors += map[node.y][node.x]; //map = 0 for tile, 1 for door
			
			node = node.parent;
		}
		return p;
	}
	
	// Contains the length, number of doors, and number of corners of a path.
	class Path {
		int length;
		int doors;
	}
	
	// A node on the map with a parent (previous position) and the current position.
	class SearchNode {
		SearchNode parent;
		int x, y;
		
		public SearchNode(SearchNode parent, int x, int y) {
			this.parent = parent;
			this.x = x;
			this.y = y;
		}
	}
	
}
