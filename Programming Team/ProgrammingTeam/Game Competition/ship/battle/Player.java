package ship.battle;
import java.util.Vector;

/**
 * An abstract BattleShip player. An implementation should appropriately follow
 * the guidelines for each method.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Player 
{
	
	/**
	 * Returns the name of this player.
	 */
	public String getName();
	
	/**
	 * At the start of the game each player implementation will place each ship
	 * in the given array on their board. At the end of this method the ship
	 * should have been successfully placed on the board. If they're not
	 * properly placed on the board then the player forfeits the game. If both 
	 * players fail to place all of the ships then it's a tie.
	 * 
	 * @param board The players board to place the ship on.
	 * @param ships The ship to place on the board.
	 */
	public void place(Board board, Ship ship);
	
	/**
	 * Calls the players implementation to determine which cell to bomb next.
	 * The opponents battlefield is given (the state of each cell on the field).
	 * A copy of the previous moves for this user is given, as well as a 
	 * read-only list of any opponent ships hit since the start of the game. If
	 * the coordinate given is null, already has been bombed, or a runtime
	 * exception was thrown in the implementation then nothing is done. Don't let
	 * this happen!
	 * 
	 * @param opponent The opponents battle field.
	 * @param moves Your history of moves as a stack.
	 * @param sunkShips Your history of ships sunk as a stack. 
	 * @return The coordinate to bomb.
	 */
	public Coord bomb(BattleField opponent, Vector<Move> moves, Vector<Ship> sunkShips);
	
	/**
	 * If the player implementation has hit a ship this method is called so a
	 * message can be gotten to express how this player feels about hitting the
	 * given ship at the given coordinate. Don't be vulgar.
	 * 
	 * @param coord The coordinate containing the ship.
	 * @param type The type of ship hit.
	 * @return The message expressing the players feeling.
	 */
	public String getHitMessage(Coord coord, ShipType type);
	
	/**
	 * If the player implementation has missed a ship this method is called so a
	 * message can be gotten to express how this player feels about missing at
	 * the given position. Don't be vulgar.
	 *  
	 * @param coord The coordinate missed.
	 * @return The message expressing the players feeling.
	 */
	public String getMissMessage(Coord coord);
	
	/**
	 * If the player implementation has sunk a ship this method is called so a
	 * message can be gotten to express how this player feels about sinking the
	 * given ship. Don't be vulgar.
	 *   
	 * @param type The type of ship sunk.
	 * @return The message expressing the players feeling.
	 */
	public String getSunkMessage(ShipType type);
	
}