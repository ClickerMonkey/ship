package ship.battle.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import ship.battle.*;

public class SkyNetPlayer implements Player
{
	private ArrayList<Ship> allShips = new ArrayList<Ship>();
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<Coord> used = new ArrayList<Coord>();
	private BattleField opponent;
	private Random randomGenerator = new Random();
	private HitLog myHitLog = new HitLog();
	private Coord lastBomb = new Coord(0,0);
	
	// Set up file I/O for hit log
	SkyNetPlayer()
	{
		File file = new File("hit.log");
		Scanner input=null;
		
		try {
			input = new Scanner(file);
		} 
		
		catch (FileNotFoundException e) {
			PrintWriter output = null;
			try {
				output = new PrintWriter(new FileWriter("hit.log"));
				output.print("0");
				output.close();
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(input != null)
			if(input.hasNext())
				myHitLog.borderHits = input.nextInt();
	}
	
	// World domination is inevitable
	public String getName()
	{
		return "SkyNet";
	}
	
	// Class for keeping track of edge hits
	class HitLog
	{
		int borderHits=0;
		
		// Add if true, reset if false
		void add(boolean border)
		{
			int change = borderHits;
			
			if(border == true && borderHits < 50)
				borderHits++;
			if(border == false)
				borderHits=0;
			
			// If there is a change, write it to disk so it can store state information between games
			if(borderHits != change)
			{
				PrintWriter output = null;			
				try {
					output = new PrintWriter(new FileWriter("hit.log"));
					output.print(borderHits);
					output.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			
			}
		}
	}
	
	
	// Place ships on the board
	public void place(Board board, Ship ship)
	{
		this.opponent = board;
		boolean done = false;
		
		// Keep trying to place until successful
		while(!done)
		{
			// If it doesn't work the first time (due to direction or whatever), lowestValue() will
			// return the next lowest value (with an element of randomness in case of ties)
			Coord starting = lowestValue();
			Direction direction = Direction.VerticalNeg;
			
			switch(randomGenerator.nextInt(4))
			{
			case 0: 
				direction = Direction.VerticalPos;
				break;
			case 1:
				direction = Direction.VerticalNeg;
				break;
			case 2: 
				direction = Direction.HorizontalPos;
				break;
			case 3:
				direction = Direction.HorizontalNeg;
				break;
			}
			
			ship.place(starting.x, starting.y, direction);

			boolean isTouching = false;
			for(int i=0; i < ship.coords.length; i++)
			{
				if(isTouching(new Coord(ship.coords[i].x,ship.coords[i].y)))
					isTouching = true;
			}
			
			// Try lowest point value
			if(board.canAddShip(ship) && !isTouching)
				done = true;
		}
		board.addShip(ship);
		allShips.add(ship);
	}
	
	// Bomb opponents board
	public Coord bomb(BattleField opponent, Vector<Move> moves, Vector<Ship> sunkShips)
	{
		// Reset ships
		ships.clear();
		for(int i=0; i < allShips.size(); i++)
			ships.add(allShips.get(i));
		
		// Remove sunken ships
		for(int i=0; i < sunkShips.size(); i++)
		{
			Ship sunkShip = sunkShips.get(i);
			for(int j=0; j < ships.size(); j++)
			{
				if(sunkShip.type == ships.get(j).type)
				{
					ships.remove(j);
					break;
				}
			}
		}

		// Update myHitLog on successful bombs
		if(opponent.getState(lastBomb.x, lastBomb.y) == State.Hit)
		{
			if(isEdge(lastBomb))
				myHitLog.add(true);
			else
				myHitLog.add(false);
		}
		
		this.opponent = opponent;
		ArrayList<Coord> bestCoordinates = new ArrayList<Coord>();
		double max=-1;
		
		// If the opponent is only putting ships on the edge.. only look there
		if(myHitLog.borderHits == 50)
			return bombEdges(opponent, moves, sunkShips);
		
		// Go after damaged ships next
		for(int row=0; row < opponent.getRows(); row++)
			for(int col=0; col < opponent.getColumns(); col++)
			{
				State left = opponent.getState(row-1, col);
				State right = opponent.getState(row+1, col);
				State up = opponent.getState(row, col-1);
				State down = opponent.getState(row, col+1);
				
				// I smell blood in the water
				if(opponent.getState(row, col) == State.Hit)
				{
					// If nothing surrounding the hit is available for attack, skip to the next hit
					if(left != State.Empty && right != State.Empty && up != State.Empty && down != State.Empty)
						continue;
					
					// Should I try hitting left?
					if(left == State.Empty)
					{
						if(right == State.Hit)
						{
							lastBomb = new Coord(row-1,col);
							return new Coord(row-1, col);
						}
						bestCoordinates.add(new Coord(row-1, col));
					}
					
					// Should I try hitting right?
					if(right == State.Empty)
					{
						if(left == State.Hit)
						{
							lastBomb = new Coord(row+1,col);
							return new Coord(row+1, col);
						}
						bestCoordinates.add(new Coord(row+1, col));
					}
					
					// Should I try hitting up?
					if(up == State.Empty)
					{
						if(down == State.Hit)
						{
							lastBomb = new Coord(row,col-1);
							return new Coord(row, col-1);
						}
						bestCoordinates.add(new Coord(row, col-1));	
					}
					
					// Should I try hitting down?
					if(down == State.Empty)
					{
						if(up == State.Hit)
						{
							lastBomb = new Coord(row,col+1);
							return new Coord(row, col+1);
						}
						bestCoordinates.add(new Coord(row, col+1));
					}
				}
			}
		
		// If we need to work on destroying a ship with only one hit, use the target value to determine the next guess
		if(bestCoordinates.size() != 0)
		{
			double tempMax=-1;
			Coord best=new Coord(1, 1);			
			for(int i=0; i < bestCoordinates.size(); i++)
			{
				Coord temp = bestCoordinates.get(i);
				double size=calculateTargetValue(temp);
				if(size > tempMax)
				{
						best = temp; 
						tempMax = size;
				}
			}
			lastBomb = best;
			return best;
		}
		
		// Calculate target values for each empty spot
		// Go with the Coordinate that has the highest value
		// If there is more than one Coordinate with the exact same value, pick one randomly
		for(int row=0; row < opponent.getRows(); row++)
		{
			for(int col=0; col < opponent.getColumns(); col++)
			{
				Coord testCoordinate = new Coord(row, col);
				if(opponent.getState(row, col) == State.Empty)
				{
					double score = calculateTargetValue(testCoordinate);
					// Tie
					if(score == max)
					{
						boolean duplicate = false;
						// Avoid duplicates (like 4,4 and 4,4) which can throw off randomness
						for(int i=0; i < bestCoordinates.size(); i++)
						{
							if(bestCoordinates.get(i).x == testCoordinate.x && bestCoordinates.get(i).y == testCoordinate.y)
								duplicate = true;
						}
						if(!duplicate)
							bestCoordinates.add(testCoordinate);
					}
					// The coordinate we're looking at is the best
					if(score > max)
					{
						bestCoordinates.clear();
						bestCoordinates.add(testCoordinate);
						max = score;
					}
				}
			}
		}
		
		// Return the best coordinate (or randomly pick one if there is more than one with the same value)
		int rn = randomGenerator.nextInt(bestCoordinates.size());
		lastBomb = bestCoordinates.get(rn);
		return bestCoordinates.get(rn);
	}
	
	/**
	 * Same thing as above, but only use edges
	 */
	public Coord bombEdges(BattleField opponent, Vector<Move> moves, Vector<Ship> sunkShips)
	{
		this.opponent = opponent;
		ArrayList<Coord> bestCoordinates = new ArrayList<Coord>();
		double max=-1;
		
		// Go after damaged ships next
		for(int row=0; row < opponent.getRows(); row++)
			for(int col=0; col < opponent.getColumns(); col++)
			{
				State left = opponent.getState(row-1, col);
				State right = opponent.getState(row+1, col);
				State up = opponent.getState(row, col-1);
				State down = opponent.getState(row, col+1);
				
				// I smell blood in the water
				if(opponent.getState(row, col) == State.Hit)
				{
					// If on the corners, go any direction
					if((row == 0 && col == 0) || (row == 0 && col == opponent.getColumns()-1) || (row == opponent.getColumns() && col == 0) || (row == opponent.getRows() && col == opponent.getColumns()))
					{
						// Stuck
						if( (!opponent.onField(row-1, col)||left!=State.Empty) && (!opponent.onField(row+1,col)||right!=State.Empty) && 
							(!opponent.onField(row,col-1)||up!=State.Empty) && (!opponent.onField(row,col+1)||down!=State.Empty))
						{	
							continue;
						}
						if(up == State.Empty && opponent.onField(row, col-1))
						{
							lastBomb = new Coord(row,col-1);
							return new Coord(row, col-1);
						}
						if(down == State.Empty && opponent.onField(row, col+1))
						{
							lastBomb = new Coord(row,col+1);
							return new Coord(row, col+1);
						}
						if(left == State.Empty && opponent.onField(row-1, col))
						{
							lastBomb = new Coord(row-1,col);
							return new Coord(row-1, col);
						}
						if(right == State.Empty && opponent.onField(row+1, col))
						{
							lastBomb = new Coord(row+1,col);
							return new Coord(row+1, col);
						}
					}
					// If on left or right edge, go up or down
					if(row == 0 || row == opponent.getRows()-1)
					{
						// If we can't go up or down, skip
						if((!opponent.onField(row, col-1) || up != State.Empty) && (!opponent.onField(row, col+1) || down != State.Empty))
							continue;
						if(up == State.Empty && opponent.onField(row, col-1))
						{
							lastBomb = new Coord(row,col-1);
							return new Coord(row, col-1);
						}
						if(down == State.Empty && opponent.onField(row, col+1))
						{
							lastBomb = new Coord(row,col+1);
							return new Coord(row, col+1);
						}
					}
					// If on top or bottom, go left or right
					if(col == 0 || col == opponent.getColumns()-1)
					{
						if((!opponent.onField(row-1, col) || left != State.Empty) && (!opponent.onField(row+1, col) || right != State.Empty))
							continue;
						if(left == State.Empty && opponent.onField(row-1, col))
						{
							lastBomb = new Coord(row-1,col);
							return new Coord(row-1, col);
						}
						if(right == State.Empty && opponent.onField(row+1, col))
						{
							lastBomb = new Coord(row+1,col);
							return new Coord(row+1, col);
						}
					}
				}
			}
	
		// Calculate target values for each Empty spot
		// Go with the Coordinate that has the highest value
		// If there is more than one Coordinate with the exact same value, pick one randomly
		for(int row=0; row < opponent.getRows(); row++)
		{
			for(int col=0; col < opponent.getColumns(); col++)
			{
				Coord testCoordinate = new Coord(row, col);
				if(opponent.getState(row, col) == State.Empty)
				{
					double score = calculateTargetValueEdge(testCoordinate);
					// Tie
					if(score == max)
					{
						boolean duplicate = false;
						// Avoid duplicates (like 4,4 and 4,4) which can throw off randomness
						for(int i=0; i < bestCoordinates.size(); i++)
						{
							if(bestCoordinates.get(i).x == testCoordinate.x && bestCoordinates.get(i).y == testCoordinate.y)
								duplicate = true;
						}
						if(!duplicate && isEdge(testCoordinate))
							bestCoordinates.add(testCoordinate);
					}
					// The coordinate we're looking at is the best
					if(score > max && isEdge(testCoordinate))
					{
						bestCoordinates.clear();
						bestCoordinates.add(testCoordinate);
						max = score;
					}
				}
			}
		}
		
		// Nowhere to go, revert to normal bomb function
		if(bestCoordinates.size() == 0)
		{
			myHitLog.add(false);
			Coord regular = bomb(opponent, moves, sunkShips);
			return regular;
		}
		int rn = randomGenerator.nextInt(bestCoordinates.size());
		lastBomb = bestCoordinates.get(rn);
		return bestCoordinates.get(rn);
	}
	

	/**
	 * Given a Coordinate, calculate a score based on how likely it is to be hiding each ship
	 * @param coord Coordinate to examine
	 * @return Value of target (higher = better)
	 */
	public double calculateTargetValue(Coord coord)
	{
		double numConnected=0, amtIncrease=0;
		int row, col;
		
		// Return 0 for an invalid Coord
		if(opponent.getState(coord.x, coord.y) == State.Miss)
			return 0;

		// Look at every remaining ship in play
		for(int i=0; i < ships.size(); i++)
		{
			int sizeOfShipToSearchFor = ships.get(i).type.length;
			
			// Determine number of clear spaces in a given direction, up to sizeOfShipToSearchFor-1
			// Add 1 if it can fit the entire ship in the given direction
			// If it can fit only a part of the ship, add a portion of the number of clear spaces in that direction (useful for tie breakers)
			
			// North
			boolean stillConnected = true;
			col=coord.y-1;
			row=coord.x;
			while(col >= 0 && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
			{
				if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
					amtIncrease++;
				else
					stillConnected=false;
				col--;
			}			
			if(amtIncrease == sizeOfShipToSearchFor-1)
				amtIncrease = 1;
			else if(amtIncrease < sizeOfShipToSearchFor-1)
				amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
			numConnected += amtIncrease;
		
			// South
			stillConnected=true;
			amtIncrease=0;
			col=coord.y+1;
			row=coord.x;
			while(col < opponent.getColumns() && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
			{
				if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
					amtIncrease++;
				else
					stillConnected=false;
				col++;
			}
			if(amtIncrease == sizeOfShipToSearchFor-1)
				amtIncrease = 1;
			else if(amtIncrease < sizeOfShipToSearchFor-1)
				amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
			numConnected += amtIncrease;
		
			// West
			stillConnected=true;
			amtIncrease=0;
			col=coord.y;
			row=coord.x-1;
			while(row >= 0 && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
			{
				if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
					amtIncrease++;
				else
					stillConnected=false;
				row--;
			}
			if(amtIncrease == sizeOfShipToSearchFor-1)
				amtIncrease = 1;
			else if(amtIncrease < sizeOfShipToSearchFor-1)
				amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
			numConnected += amtIncrease;
	
			// East
			stillConnected=true;
			col=coord.y;
			row=coord.x+1;
			amtIncrease=0;
			while(row < opponent.getRows() && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
			{
				if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
					amtIncrease++;
				else
					stillConnected=false;
				row++;
			}
			if(amtIncrease == sizeOfShipToSearchFor-1)
				amtIncrease = 1;
			else if(amtIncrease < sizeOfShipToSearchFor-1)
				amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
			numConnected += amtIncrease;
		}
		
		return numConnected;
	}
	
	/**
	 * Print message for a hit
	 */
	public String getHitMessage(Coord coord, ShipType type)
	{
		return null;
	}

	/**
	 * Print message for a miss
	 */
	public String getMissMessage(Coord coord)
	{
			return null;
	}

	/**
	 * Print sunk message, and remove from sunken ship the list of ships
	 */
	public String getSunkMessage(ShipType sunkenShipType)
	{		
		return null;
	}
		
	public boolean isEdge(Coord c)
	{
		if(c.x == 0 || c.x == opponent.getRows()-1 || c.y == 0 || c.y == opponent.getColumns()-1)
			return true;
		return false;
	}
	
	/**
	 * Given a Coordinate, calculate a score based on how likely it is to be hiding each ship
	 * @param coord Coordinate to examine
	 * @return Value of target (higher = better)
	 */
	public double calculateTargetValueEdge(Coord coord)
	{
		double numConnected=0, amtIncrease=0;
		int row, col;
		
		// Return 0 for an invalid Coord
		if(opponent.getState(coord.x, coord.y) == State.Miss)
			return 0;

		// Look at every remaining ship in play
		for(int i=0; i < ships.size(); i++)
		{
			int sizeOfShipToSearchFor = ships.get(i).type.length;
			
			// Determine number of clear spaces in a given direction, up to sizeOfShipToSearchFor-1
			// Add 1 if it can fit the entire ship in the given direction
			// If it can fit only a part of the ship, add a portion of the number of clear spaces in that direction (useful for tie breakers)
			
			// North
			if(coord.x == 0 || coord.x == opponent.getRows()-1)
			{
				boolean stillConnected = true;
				col=coord.y-1;
				row=coord.x;
				while(col >= 0 && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
				{
					if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
						amtIncrease++;
					else
						stillConnected=false;
					col--;
				}			
				if(amtIncrease == sizeOfShipToSearchFor-1)
					amtIncrease = 1;
				else if(amtIncrease < sizeOfShipToSearchFor-1)
					amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
				numConnected += amtIncrease;
			}
			
			// South
			if(coord.x == 0 || coord.x == opponent.getRows()-1)
			{
				boolean stillConnected=true;
				amtIncrease=0;
				col=coord.y+1;
				row=coord.x;
				while(col < opponent.getColumns() && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
				{
					if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
						amtIncrease++;
					else
						stillConnected=false;
					col++;
				}
				if(amtIncrease == sizeOfShipToSearchFor-1)
					amtIncrease = 1;
				else if(amtIncrease < sizeOfShipToSearchFor-1)
					amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
				numConnected += amtIncrease;
			}
			
			// West
			if(coord.y == 0 || coord.y == opponent.getColumns()-1)
			{
				boolean stillConnected=true;
				amtIncrease=0;
				col=coord.y;
				row=coord.x-1;
				while(row >= 0 && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
				{
					if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
						amtIncrease++;
					else
						stillConnected=false;
					row--;
				}
				if(amtIncrease == sizeOfShipToSearchFor-1)
					amtIncrease = 1;
				else if(amtIncrease < sizeOfShipToSearchFor-1)
					amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
				numConnected += amtIncrease;
			}
			
			// East
			if(coord.y == 0 || coord.y == opponent.getColumns()-1)
			{
				boolean stillConnected=true;
				col=coord.y;
				row=coord.x+1;
				amtIncrease=0;
				while(row < opponent.getRows() && stillConnected && amtIncrease < sizeOfShipToSearchFor-1)
				{
					if(opponent.getState(row, col) == State.Empty && opponent.onField(row, col))
						amtIncrease++;
					else
						stillConnected=false;
					row++;
				}
				if(amtIncrease == sizeOfShipToSearchFor-1)
					amtIncrease = 1;
				else if(amtIncrease < sizeOfShipToSearchFor-1)
					amtIncrease = amtIncrease/(sizeOfShipToSearchFor-1)/2;
				numConnected += amtIncrease;
			}
	}
		return numConnected;
	}
	
	// Returns true if coordinate is touching a ship
	public boolean isTouching(Coord c)
	{
		boolean result = false;
		
		for(int a=0; a < allShips.size(); a++)
		{
			Ship s = allShips.get(a);
			for(int b=0; b < s.coords.length; b++)
			{
				int x = s.coords[b].x;
				int y = s.coords[b].y;
				if(c.x == x && c.y+1 == y) result = true;
				if(c.x == x && c.y-1 == y) result = true;
				if(c.x+1 == x && c.y == y) result = true;
				if(c.x-1 == x && c.y == y) result = true;
			}
		}
		return result;
	}
	
	public Coord lowestValue()
	{
		double min = 999999999;
		Coord minCoord = new Coord(-1, -1);
		ArrayList<Coord> minCoords = new ArrayList<Coord>();
		
		for(int row=0; row < opponent.getRows(); row++)
		{
			for(int col=0; col < opponent.getColumns(); col++)
			{
				// Don't look at used values
				boolean alreadyUsed = false;
				for(int i=0; i < used.size(); i++)
				{
					if(used.get(i).x == row && used.get(i).y == col)
						alreadyUsed = true;
				}
				double score = calculateTargetValue(new Coord(row, col));
	
				if(score == min && !alreadyUsed)
					minCoords.add(new Coord(row,col));
				
				if(score < min && !alreadyUsed)
				{
					minCoords.clear();
					minCoords.add(new Coord(row, col));
					min = score;
				}
			}
		}
		minCoord = minCoords.get(randomGenerator.nextInt(minCoords.size()));
		used.add(minCoord);
		return minCoord;
	}
}