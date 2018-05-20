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
 *     1
 *     2
 *    131
 *   12421
 * 1234x4321
 *   12421
 *    131
 *     2
 *     1
 *     
 * @author Philip Diffenderfer
 *
 */
public class PhilsPlayer implements Player
{

	private final Random rnd = new Random();
	
	private final int BOMB_MAP_WIDTH = 9;
	private final int BOMB_MAP_HEIGHT = 9;
	private final int BOMB_MAP_RADIUS = 4;
	private final int[][] BOMB_MAP = {
			{0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 2, 0, 0, 0, 0},
			{0, 0, 0, 1, 3, 1, 0, 0, 0},
			{0, 0, 1, 2, 4, 2, 1, 0, 0},
			{1, 2, 3, 4, 0, 4, 3, 2, 1},
			{0, 0, 1, 2, 4, 2, 1, 0, 0},
			{0, 0, 0, 1, 3, 1, 0, 0, 0},
			{0, 0, 0, 0, 2, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0, 0},
	};
	
	private class Influence implements Comparable<Influence> {
		Coord coord;
		int value;
		public int compareTo(Influence o) {
			return o.value - value;
		}
		
	}
	
	@Override
	public String getName()
	{
		return "ClickerMonkey";
	}
	
	@Override
	public void place(Board board, Ship ship)
	{
		final int width = board.getColumns();
		final int height = board.getRows();
		final Direction directions[] = {
				Direction.HorizontalNeg,
				Direction.VerticalNeg,
				Direction.HorizontalPos,
				Direction.VerticalPos,
		};
		
		do {
			int index = rnd.nextInt(width * height * directions.length);
			int dir = index & 3;
			int x = (index >> 2) % width;
			int y = (index >> 2) / width;
			ship.place(x, y, directions[dir]);	
		} while (!board.canAddShip(ship));
		
		board.addShip(ship);
	}
	
	@Override
	public Coord bomb(BattleField opponent, Vector<Move> moves, Vector<Ship> sunkShips)
	{
		// Phil's Bombing Algorithm:
		// For all previous moves that hit:
		//		Add an influence around that point.
		// For every point on the influence map that's empty, find its max
		// Use the max as the bombing point.
		
		final int width = opponent.getColumns();
		final int height = opponent.getRows();
		
		// The influence map for the current board.
		int[][] influence = new int[height][width];
		
		// The number of hits in the past.
		int hitCount = 0;
		
		// Go through all places
		for (int my = 0; my < height; my++) {
			for (int mx = 0; mx < width; mx++) {
				if (opponent.getState(mx, my) != State.Hit) {
					continue;
				}
				// Go through map and add to influence
				for (int y = 0; y < BOMB_MAP_HEIGHT; y++) {
					for (int x = 0; x < BOMB_MAP_WIDTH; x++) {
						int ix = x + mx - BOMB_MAP_RADIUS;
						int iy = y + my - BOMB_MAP_RADIUS;
						if (opponent.onField(ix, iy)) {
							influence[iy][ix] += BOMB_MAP[y][x];
						}
					}
				}
				hitCount++;
			}
		}
		
//		// Print out the influence map
//		if (hitCount != 0) {
//			System.out.println("============================");
//			for (int y = 0; y < height; y++) {
//				for (int x = 0; x < width; x++) {
//					if (opponent.getState(x, y).isFatal()) {
//						System.out.print("X  ");
//					}
//					else {
//						System.out.format("%-3d", influence[y][x]);						
//					}
//				}
//				System.out.println();
//			}
//			System.out.println("============================");
//		}
		
		
		// While we haven't hit anything yet...
		if (hitCount == 0) {
			int x, y;
			// Find an empty cell...
			do {
				x = rnd.nextInt(width);
				y = rnd.nextInt(height);
			} while (opponent.getState(x, y) != State.Empty);
			return new Coord(x, y);
		}
		
		// Go through influence and for every point on the map that is empty
		// add it to a priority queue.
		PriorityQueue<Influence> queue = new PriorityQueue<Influence>(width * height);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Only add where we haven't bombed yet.
				if (opponent.getState(x, y) == State.Empty) {
					Influence infl = new Influence();
					infl.coord = new Coord(x, y);
					infl.value = influence[y][x];
					queue.add(infl);
				}
			}
		}
	
		return (queue.isEmpty() ? null : queue.peek().coord);
	}

	@Override
	public String getHitMessage(Coord coord, ShipType type)
	{
		return String.format("I hit your %s!", type.toString());
	}

	@Override
	public String getMissMessage(Coord coord)
	{
		return null;
	}

	@Override
	public String getSunkMessage(ShipType type)
	{
		return String.format("I sunk your %s!", type.toString());
	}


}
