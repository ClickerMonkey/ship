package ship.battle;
import java.util.Vector;

/**
 * The current state of player in the game. A state consists of the player 
 * themself, their board and its state, their last moves, their last ships
 * that they have sunk on the opponents board.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PlayerState
{
	
	/**
	 * The player with the state.
	 */
	public final Player player;
	
	/**
	 * The name of the player.
	 */
	public final String name;
	
	/**
	 * The current state of the players board.
	 */
	public final Board board;
	
	/**
	 * The stack of previous moves for this player.
	 */
	public final Vector<Move> moves;
	
	/**
	 * The stack of sunken ships on the opponents board.
	 */
	public final Vector<Ship> sunkShips;
	
	/**
	 * Instantiates a new player state given the size of their board, and the 
	 * associated player.
	 * 
	 * @param width The width (column count) of the board.
	 * @param height The height (row count) of the board.
	 * @param player The associated player.
	 */
	public PlayerState(int width, int height, Player player) 
	{
		this.player = player;
		this.name = player.getName();
		this.board = new Board(width, height);
		this.moves = new Vector<Move>();
		this.sunkShips = new Vector<Ship>();
	}
	
	/**
	 * Initializes the players board by making sure he places his ships on his
	 * board. If not all ships have been placed on the board then false is 
	 * returned.
	 */
	public boolean initialize()
	{
		// The set of ships available for placement.
		Ship[] ships = {
				new Ship(ShipType.AircraftCarrier),
				new Ship(ShipType.Battleship),
				new Ship(ShipType.Destroyer),
				new Ship(ShipType.Submarine),
				new Ship(ShipType.PatrolBoat),
		};
		
		for (Ship s : ships) {
			// Place the ship on the board.
			player.place(board, s);
			
			// If the ship has not been placed then fail!
			if (!board.hasShip(s)) {
				return false;
			}
		}
		
		// All ships have been placed successfully!
		return true;
	}
	
	/**
	 * Returns true if all the ships on this players board have been sunk.
	 */
	public boolean isDead()
	{
		// Assume a board is dead if the number of sunken cells is equal to
		// the sum of the lengths of all the ships on the board.
		int lengthSum = 0;
		int shipCount = board.getShipCount();
		for (int i = 0; i < shipCount; i++) {
			lengthSum += board.getShip(i).type.length;
		}
		int sunkSum = board.getStateCount(State.Sunk);
		return (lengthSum == sunkSum);
	}
	
}