package ship.battle.impl;

import java.util.Random;
import java.util.Vector;

import ship.battle.*;

public class EdgePlayer implements Player
{
	
	private final String name;
	
	public EdgePlayer(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	public void place(Board board, Ship ship)
	{
		Random rng = new Random();
		
		boolean done = false;
		while(!done)
		{
			Direction direction = Direction.VerticalNeg;
		
			int row = rng.nextInt(board.getRows());
			int col = rng.nextInt(board.getColumns());
				
			// Must be an edge
			if(isEdge(new Coord(row, col), board))
			{
				if(row == 0 || row == board.getRows()-1)
					direction = Direction.VerticalPos;
				if(col == 0 || col == board.getRows()-1)
					direction = Direction.HorizontalPos;
				ship.place(row, col, direction);
			}
			else
				continue;


			
			if(board.canAddShip(ship))
			{
				done = true;	
			}
		}
	
		board.addShip(ship);
	}
	
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

	public String getHitMessage(Coord coord, ShipType type)
	{
		//return String.format("I hit your %s!", type.toString());
		return null;
	}

	public String getMissMessage(Coord coord)
	{
//		return String.format("I knew you didnt have a ship at {%d,%d}", coord.x, coord.y);
		return null;
	}

	public String getSunkMessage(ShipType type)
	{
		//return String.format("I sunk your %s!", type.toString());
		return null;
	}

	public boolean isEdge(Coord c, Board b)
	{
		return (c.x == 0 || c.x == b.getRows()-1 || c.y == 0 || c.y == b.getColumns()-1);
	}
}
