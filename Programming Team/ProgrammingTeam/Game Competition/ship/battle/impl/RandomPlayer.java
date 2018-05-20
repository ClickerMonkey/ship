package ship.battle.impl;

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


public class RandomPlayer implements Player
{
	
	private final String name;
	private final Random rnd = new Random();
	
	public RandomPlayer(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getName()
	{
		return name;
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
		final int width = opponent.getColumns();
		final int height = opponent.getRows();
		final int area = width * height;
		Random rnd = new Random();
		int x, y, index, tries = 0;
		
		do {
			index = rnd.nextInt(area); 
			x = index % width;
			y = index / width;
			tries++;
		} while (opponent.getState(x, y) != State.Empty && tries < area);
		
		// If one hasn't been placed yet then bomb in the first available space.
		if (opponent.getState(x, y) != State.Empty) {
			for (y = 0; y < height; y++) {
				for (x = 0; x < width; x++) {
					if (opponent.getState(x, y) == State.Empty) {
						break;
					}
				}
			}
		}
		// Return a coord garuanteed to be empty (or off the map). 
		return new Coord(x, y);
	}

	@Override
	public String getHitMessage(Coord coord, ShipType type)
	{
		return String.format("I hit your %s!", type.toString());
	}

	@Override
	public String getMissMessage(Coord coord)
	{
//		return String.format("I knew you didnt have a ship at {%d,%d}", coord.x, coord.y);
		return null;
	}

	@Override
	public String getSunkMessage(ShipType type)
	{
		return String.format("I sunk your %s!", type.toString());
	}


}
