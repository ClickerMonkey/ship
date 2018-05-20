package ship.battle.impl;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import ship.battle.BattleField;
import ship.battle.Board;
import ship.battle.Coord;
import ship.battle.Direction;
import ship.battle.Move;
import ship.battle.Player;
import ship.battle.Ship;
import ship.battle.ShipType;
import ship.battle.State;

/**
 *     
 * @author Philip Diffenderfer
 *
 */
public class Terminator implements Player
{
	
	// The set of defensive strategies for placing a ship.
	private final PlacementStrategy strategies[] = {
//			new PlacementColumns(),
//			new PlacementRows(),
			new PlacementEdges(),
//			new PlacementRandom(),
			new PlacementFrame(),
	};


	// The area map used to add influence to which coordinate we're going to bomb
	// next. This map is applied at every hit cell (not sunk).
	private final AreaMap BOMB_MAP = new AreaMap(new int[][] {
			{0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 4, 0, 0, 0},
			{0, 0, 0, 7, 0, 0, 0},
			{1, 4, 7, 0, 7, 4, 1},
			{0, 0, 0, 7, 0, 0, 0},
			{0, 0, 0, 4, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0},
	});
	
	// The area map used to add influence to which coordinate we're going to bomb
	// next. This map is applied at every corner
	private final AreaMap CORNER_MAP = new AreaMap(new int[][] {
			{0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 4, 0, 0, 0},
			{0, 0, 0, 7, 0, 0, 0},
			{1, 4, 7, 9, 7, 4, 1},
			{0, 0, 0, 7, 0, 0, 0},
			{0, 0, 0, 4, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0},
	});
	
	// The area map used to add influence to which coordinate we're going to bomb
	// next. This map is applied when we see a vertical pattern of ships.
	private final AreaMap VERTICAL_MAP = new AreaMap(new int[][] {
			{1},
			{4},
			{7},
			{0},
			{7},
			{4},
			{1},
	});
	
	// The area map used to add influence to which coordinate we're going to bomb
	// next. This map is applied when we see a vertical pattern of ships.
	private final AreaMap HORIZONTAL_MAP = new AreaMap(new int[][] {
			{1, 4, 7, 0, 7, 4, 1},
	});

	
	// A cell with a weight. The nodes are held in groups (of the same weight)
	// and priority queues ordered by the heaviest nodes (most probable hits).
	private class Node implements Comparable<Node> {
		// Cell position
		private final Coord coord;
		// Probability this node will be a hit (the higher the better)
		private final int weight;
		// Initializes a new node at {x,y} with the given weight.
		public Node(int x, int y, int weight) {
			this.coord = new Coord(x, y);
			this.weight = weight;
		}
		// Compares two nodes for descending ordering.
		public int compareTo(Node o) {
			return o.weight - weight;
		}
	}

	// A group of nodes with the same weight. These nodes have the same
	// probability of being a hit when bombed.
	private class NodeGroup extends Vector<Node> {
		// The weight of the group.
		private int weight;
		// Instantiate a weightless group
		public NodeGroup() {
		}
		// Instantiate a group based on its first member.
		public NodeGroup(Node first) {
			weight = first.weight;
			add(first);
		}
		// Returns true if the given node can go in this group.
		public boolean matches(Node n) {
			return (n.weight == weight);
		}
	}

	
	// A map holding an influence area (2d array of cell weights).
	private class AreaMap {
		// The center indices of the area.
		private int cx;
		private int cy;
		// The dimensions of the area.
		private int width;
		private int height;
		// The weight of the area.
		private int[][] map;
		// Instantiates a new AreaMap given its weight map.
		public AreaMap(int[][] map) {
			this.map = map;
			this.width = map[0].length;
			this.height = map.length;
			this.cx = (width / 2);
			this.cy = (height / 2);
		}
	}

	// A map of the entire battlefield. AreaMaps can be applied to this map and
	// once all have been applied a queue can be built of the weight of all empty
	// cells, in descending order.
	private class InfluenceMap {
		// The dimensions of the battlefield.
		private int width;
		private int height;
		// The weights of each cell on the battlefield.
		private int[][] map;
		// Whether the cell at [y][x] is empty.
		private boolean[][] empty;
		// 
		public InfluenceMap(BattleField field) {
			width = field.getColumns();
			height = field.getRows();
			map = new int[height][width];
			empty = new boolean[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					empty[y][x] = (field.getState(x, y) == State.Empty);
				}
			}
		}
		//
		public void apply(AreaMap area, int px, int py, int sign) {
			for (int y = 0; y < area.height; y++) {
				for (int x = 0; x < area.width; x++) {
					int mx = x + px - area.cx;
					int my = y + py - area.cy;
					if (inMap(mx, my)) {
						map[my][mx] += area.map[y][x] * sign;
					}
				}
			}
		}
		//
		public int apply(AreaMap area, BattleField field, State state, int sign) {
			int applied = 0;
			for (int my = 0; my < height; my++) {
				for (int mx = 0; mx < width; mx++) {
					if (field.getState(mx, my) == state) {
						apply(area, mx, my, sign);
						applied++;
					}
				}
			}	
			return applied;
		}
		// Adds the given amount to the edges.
		public int applyEdges(int addAmount) {
			int xspan = width - 1;
			int yspan = height - 1;
			for (int y = 0; y < height; y++) {
				map[y][0] += addAmount;
				map[y][xspan] += addAmount;
			}
			for (int x = 1; x < xspan; x++) {
				map[0][x] += addAmount;
				map[yspan][x] += addAmount;
			}
			return 4;
		}
		// Adds the given areay to the corner of the area
		public int applyCorners(AreaMap area) {
			int xspan = width - 1;
			int yspan = height - 1;
			apply(area, 0, 0, +1);
			apply(area, 0, yspan, +1);
			apply(area, xspan, 0, +1);
			apply(area, xspan, yspan, +1);
			return 4;
		}
		//
		private boolean inMap(int x, int y) {
			return !(x < 0 || x >= width || y < 0 || y >= height);
		}
		//
		public PriorityQueue<Node> getQueue() {
			PriorityQueue<Node> queue = new PriorityQueue<Node>();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (empty[y][x]) {
						queue.add(new Node(x, y, map[y][x]));	
					}
				}
			}
			return queue;
		}
	}

	// A map used to generate the probability of every ship currently not sunk
	// in the game of landing on a given cell - for the entire field.
	private class ProbabilityMap {
		private int width;
		private int height;
		private int[][] map;
		private boolean[][] empty;
		public ProbabilityMap(BattleField field) {
			width = field.getColumns();
			height = field.getRows();
			map = new int[height][width];
			empty = new boolean[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					empty[y][x] = (field.getState(x, y) == State.Empty);
				}
			}
		}
		public void add(int length) {
			int jump = length - 1;
			int endx = width - jump;
			int pos;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < endx; x++) {
					for (pos = 0; pos < length; pos++) {
						if (!empty[y][x + pos]) {
							break;
						}
					}
					if (pos == length) {
						for (pos = 0; pos < length; pos++) {
							map[y][x + pos]++;
						}
					} else {
						x += pos;
					}
				}
			}
			int endy = height - jump;
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < endy; y++) {
					for (pos = 0; pos < length; pos++) {
						if (!empty[y + pos][x]) {
							break;
						}
					}
					if (pos == length) {
						for (pos = 0; pos < length; pos++) {
							map[y + pos][x]++;
						}
					} else {
						y += pos;
					}
				}
			}
		}
		public PriorityQueue<Node> getQueue() {
			PriorityQueue<Node> queue = new PriorityQueue<Node>();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (empty[y][x]) {
						queue.add(new Node(x, y, map[y][x]));	
					}
				}
			}
			return queue;
		}
	}

	
	// A single strategy for placing ships.
	public static  interface PlacementStrategy {
		public void place(Board board, Ship ship);
	}
	
	// A random placement of ships with a padding of one cell around them.
	public static class PlacementRandom implements PlacementStrategy {
		public void place(Board board, Ship ship) {
			int width = board.getColumns();
			int height = board.getRows();
			int dirs = Direction.values().length;
			int area = width * height * dirs;
			do {
				int index = rnd.nextInt(area);
				int d = index % dirs;
				int x = (index / dirs) % width;
				int y = (index / dirs) / width;
				ship.place(x, y, Direction.values()[d]);	
			} while (!isFree(board, ship));			
		}
	}
	
	// A placement of ships on the edges.
	public static class PlacementEdges implements PlacementStrategy {
		public void place(Board board, Ship ship) {
			int width = board.getColumns();
			int height = board.getRows();
			int x, y, value;
			Direction dir;
			do {
				value = rnd.nextInt(4);
				if (value < 2) {
					dir = Direction.VerticalPos;
					y = rnd.nextInt(height - ship.type.length);
					x = (value == 0 ? 0 : width - 1);
				}
				else {
					dir = Direction.HorizontalPos;
					x = rnd.nextInt(width - ship.type.length);
					y = (value == 2 ? 0 : height - 1);
				}
				ship.place(x, y, dir);
			} while (!isFree(board, ship));
		}
	}

	// A placement of ships on the edges.
	public static class PlacementFrame implements PlacementStrategy {
		public void place(Board board, Ship ship) {
			int width = board.getColumns();
			int height = board.getRows();
			int x, y, value;
			Direction dir;
			do {
				value = rnd.nextInt(4);
				if (value < 2) {
					dir = Direction.VerticalPos;
					y = rnd.nextInt(height - ship.type.length);
					x = (value == 0 ? 1 : width - 2);
				}
				else {
					dir = Direction.HorizontalPos;
					x = rnd.nextInt(width - ship.type.length);
					y = (value == 2 ? 1 : height - 2);
				}
				ship.place(x, y, dir);
			} while (!isFree(board, ship));
		}
	}

	// A placement of ships on rows (laying horizontally)
	public static class PlacementRows implements PlacementStrategy {
		public void place(Board board, Ship ship) {
			int width = board.getColumns();
			int height = board.getRows();
			do {
				int x = rnd.nextInt(width - ship.type.length);
				int y = rnd.nextInt(height);
				ship.place(x, y, Direction.HorizontalPos);
			} while (!isFree(board, ship));
		}
	}
	
	// A placement of ships on columns (laying vertically)
	public static class PlacementColumns implements PlacementStrategy {
		public void place(Board board, Ship ship) {
			int width = board.getColumns();
			int height = board.getRows();
			do {
				int x = rnd.nextInt(width);
				int y = rnd.nextInt(height - ship.type.length);
				ship.place(x, y, Direction.VerticalPos);
			} while (!isFree(board, ship));
		}
	}

	
	// Bit Codes for determining the placement of a ship on the board.
	public static final int PATTERN_VERTICAL = 1;	// 0b0001
	public static final int PATTERN_HORIZONTAL = 2;	// 0b0010
	public static final int PATTERN_EDGE = 4;			// 0b0100
	public static final int PATTERN_CORNER = 8;		// 0b1000
	
	// A random number generator.
	private static final Random rnd = new Random();

	// The set of all ship types in this game realm.
	private final Vector<ShipType> types = new Vector<ShipType>();

	// The placement strategy being used
	private final PlacementStrategy placementStrategy;

	// The minimum number of sunken ships that must exist for placement
	// patterns to be used in influencing bombing.
	private final int patternThreshold;
	
	/**
	 * Instantiates a new Terminator Player.
	 */
	public Terminator()
	{
		// The placement strategy for this terminator is random per instantiation.
		placementStrategy = strategies[rnd.nextInt(strategies.length)];
		patternThreshold = 2;
	}
	
	/**
	 * Instantiates a new Terminator player with a given Strategy.
	 */
	public Terminator(PlacementStrategy strategy, int threshold) 
	{
		placementStrategy = strategy;
		patternThreshold = threshold;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return "Terminator";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void place(Board board, Ship ship)
	{
		// Use current placement strategy
		placementStrategy.place(board, ship);
		// Add the type to the world list of ship types.
		types.add(ship.type);
		// Add the ship to the board!
		board.addShip(ship);
	}

	/**
	 * Returns whether the given ship is able to be placed in its current position
	 * on the given board (including a single cell padding around its edges)
	 */
	private static boolean isFree(Board board, Ship ship) 
	{
		/// Check if it can be added altogether
		if (!board.canAddShip(ship)) {
			return false;
		}
		// The bounds of the board and ship
		int spanX = board.getColumns() - 1;
		int spanY = board.getRows() - 1;
		int minX = spanX;
		int minY = spanY;
		int maxX = 0;
		int maxY = 0;
		
		// Update the bounds of the ship
		for (Coord c : ship.coords) {
			minX = Math.min(minX, c.x);
			minY = Math.min(minY, c.y);
			maxX = Math.max(maxX, c.x);
			maxY = Math.max(maxY, c.y);
		}

		// Grow bounds on all sides by a single cell
		minX = Math.max(minX - 1, 0);
		minY = Math.max(minY - 1, 0);
		maxX = Math.min(maxX + 1, spanX);
		maxY = Math.min(maxY + 1, spanY);

		int overlap = 0;
		// Ensure no ship exists at each cell in bounds.
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				if (board.getShip(x, y) != null) {
					overlap++;
				}
			}
		}
		
		// Don't look at corners
		if (board.getShip(minX, minY) != null) {
			overlap--;
		}
		if (board.getShip(minX, maxY) != null) {
			overlap--;
		}
		if (board.getShip(maxX, maxY) != null) {
			overlap--;
		}
		if (board.getShip(maxX, minY) != null) {
			overlap--;
		}
		
		// Its free!
		return (overlap == 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Coord bomb(BattleField opponent, Vector<Move> moves, Vector<Ship> sunkShips)
	{
		// The accumulative code of all sunken ships
		int code = 0;
		
		// If sunken ships exist (atleast 2) try to find a pattern.
		if (sunkShips.size() >= patternThreshold) {
			// Get the accumulative code of all sunken ships to look for a pattern.
			code = getCode(opponent, sunkShips.get(0));
			for (int i = 1; i < sunkShips.size(); i++) {
				code &= getCode(opponent, sunkShips.get(i));
			}
		}
		
		// Take the set of all ships and subtract the set of sunk ships. This will
		// result in a set of remaining ships.
		Vector<ShipType> remaining = new Vector<ShipType>(types);
		for (Ship sunken : sunkShips) {
			remaining.remove(sunken.type);
		}

		// For every remaining ship calculate the probability of the ship being
		// placed on each cell on the field (based on their length).
		ProbabilityMap pmap = new ProbabilityMap(opponent);
		for (ShipType tofind : remaining) {
			pmap.add(tofind.length);
		}

		// Get the queue of all empty nodes on the map. The nodes with the most 
		// weight are the nodes that have the highest probability of containing a
		// boat.
		PriorityQueue<Node> pqueue = pmap.getQueue();
		Vector<NodeGroup> pgroups = getGroups(pqueue);

		// The second part of the algorithm consists of building an influence map
		// based on all of the current hits on the map (sinks don't count).
		InfluenceMap imap = new InfluenceMap(opponent);
		int total = imap.apply(BOMB_MAP, opponent, State.Hit, +1);
		
		// APPLY INFLUENCES BASED ON PATTERNS
		// A horiztonal pattern of sunken ships?
		if ((code & PATTERN_HORIZONTAL) != 0) {
			total += imap.apply(HORIZONTAL_MAP, opponent, State.Hit, +1);
		}
		// A vertical pattern of sunken ships?
		if ((code & PATTERN_VERTICAL) != 0) {
			total += imap.apply(VERTICAL_MAP, opponent, State.Hit, +1);
		}
		// Ships just on edges?
		if ((code & PATTERN_EDGE) != 0) {
			total += imap.applyEdges(7);
		}
		// Ships on corners?
		if ((code & PATTERN_CORNER) != 0) {
			total += imap.applyCorners(CORNER_MAP);
		}
		
		// If the influence map is empty then use a random position from the
		// probability map (first group).
		if (total == 0) {
			NodeGroup set = pgroups.get(0);
			return set.get(rnd.nextInt(set.size())).coord;
		}

		// Create groups based on influence.
		PriorityQueue<Node> iqueue = imap.getQueue();
		Vector<NodeGroup> igroups = getGroups(iqueue);

		// The optimal choice is in the first influence group. If no hits were on
		// the map then this group is the remaining empty cells on the battlefield
		NodeGroup elite = igroups.firstElement();
		for (NodeGroup pgroup : pgroups) {
			// Intersect the currently most influencial nodes with the 
			// currently most probable nodes.
			NodeGroup set = intersection(elite, pgroup);
			// If a non-empty set is returned we found the set of nodes that
			// are currently the best moves, pick randomly between them.
			if (set.size() > 0) {
				return set.get(rnd.nextInt(set.size())).coord;
			}
		}

		// Shouldn't ever happen
		return new Coord(-1, -1);
	}

	// Gets a list of groups of nodes. A group node is a set of nodes with the
	// same weight.
	private Vector<NodeGroup> getGroups(PriorityQueue<Node> queue) 
	{
		Vector<NodeGroup> groups = new Vector<NodeGroup>();
		while (!queue.isEmpty()) {
			NodeGroup group = new NodeGroup(queue.poll());
			while (!queue.isEmpty() && group.matches(queue.peek())) {
				group.add(queue.poll());
			}
			groups.add(group);
		}
		return groups;
	}

	// Returns the intersection of the two sets of node. Two nodes intersect
	// when they lie on the same cell on the field.
	private NodeGroup intersection(NodeGroup g1, NodeGroup g2) 
	{
		NodeGroup result = new NodeGroup();
		for (Node n1 : g1) {
			for (Node n2 : g2) {
				if (n1.coord.x == n2.coord.x && n1.coord.y == n2.coord.y) {
					result.add(n1);
				}
			}
		}
		return result;
	}
	
	// Returns the union of the two sets of nodes. Both groups given are unique
	// anyway so it just appends all nodes from each group to a resulting group.
	@SuppressWarnings("unused")
	private NodeGroup union(NodeGroup g1, NodeGroup g2) 
	{
		NodeGroup result = new NodeGroup();
		for (Node n1 : g1) {
			result.add(n1);
		}
		for (Node n2 : g2) {
			result.add(n2);
		}
		return result;
	}

	// Gets the pattern code of the given ship.
	public int getCode(BattleField field, Ship ship) 
	{
		int xspan = field.getColumns() - 1;
		int yspan = field.getRows() - 1;
		int code = 0;
		// Is it horizontal?
		if (ship.direction.y == 0) {
			code |= PATTERN_HORIZONTAL;
			// Is it on a horizontal edge?
			if (ship.y == 0 || ship.y == yspan) {
				code |= PATTERN_EDGE;
			}
		}
		// Is it vertical?
		if (ship.direction.x == 0) {
			code |= PATTERN_VERTICAL;
			// Is it on a vertical edge?
			if (ship.x == 0 || ship.x == xspan) {
				code |= PATTERN_EDGE;
			}
		}
		// If its atleast on one edge then test it for corner...
		if ((code & PATTERN_EDGE) != 0) {
			// If it's on a vertical edge...
			if ((code & PATTERN_VERTICAL) != 0) {
				// If any parts of the ship are on the top or bottom its on a corner
				for (Coord c : ship.coords) {
					if (c.y == 0 || c.y == yspan) {
						code |= PATTERN_CORNER;
					}
				}
			}
			// If it's on a horiztonal edge...
			if ((code & PATTERN_HORIZONTAL) != 0) {
				// If any parts of the ship are on the left or right its on a corner
				for (Coord c : ship.coords) {
					if (c.x == 0 || c.x == xspan) {
						code |= PATTERN_CORNER;
					}
				}
			}
		}
		return code;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHitMessage(Coord coord, ShipType type)
	{
		return String.format("I hit your %s!", type.toString());
//		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMissMessage(Coord coord)
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSunkMessage(ShipType type)
	{
		return String.format("I sunk your %s!", type.toString());
//		return null;
	}

	//	System.out.println("============================");
	//	for (int y = 0; y < imap.height; y++) {
	//		for (int x = 0; x < imap.width; x++) {
	//			if (opponent.getState(x, y).isFatal()) {
	//				System.out.print("  X");
	//			}
	//			else {
	//				System.out.format("%3d", imap.map[y][x]);						
	//			}
	//		}
	//		System.out.println();
	//	}
	//	System.out.println("============================");		

}
