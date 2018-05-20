package ship.battle;

/**
 * A single move on the battle field. This holds the coordinate of the move and
 * its state (hit, miss, or empty).
 * 
 * @author Philip Diffenderfer
 *
 */
public class Move 
{
	
	/**
	 * The coordinate of the move.
	 */
	public final Coord coord;
	
	/**
	 * The state after the move.
	 */
	public final State state;
	
	/**
	 * Instantiates a new Move.
	 * 
	 * @param coord The coordinate of the move.
	 * @param state The state after the move.
	 */
	public Move(Coord coord, State state) 
	{
		this.coord = coord;
		this.state = state;
	}
	
}