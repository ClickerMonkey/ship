package ship.boxes;

/**
 * The state of a player in the game.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PlayerState
{

	// The player of the state.
	public final Player player;
	// The id of the player (box capturing id).
	public final int id;
	// How many boxes the player has captured on the field
	public int captured;
	// How many moves this player has performed.
	public int moves;
	// How many invalid lines the player gave so far.
	public int invalids;

	/**
	 * Instantiates a new PlayerState.
	 * 
	 * @param player The player of the state.
	 * @param id The id of the player.
	 */
	public PlayerState(Player player, int id)
	{
		this.player = player;
		this.id = id;
		this.captured = 0;
	}
	
	/**
	 * The player tries to place a line on the field. If the placement results
	 * in a box being captured then the player contues to go until they did not
	 * capture anymore boxes. 
	 * 
	 * @param field The field implementation to play on.
	 * @return The number of boxes captured by this player.
	 */
	public int play(FieldImpl field)
	{
		// Get the line the player is playing.
		Line move = player.getLine(field);
		
		// If the move is null return.
		if (move == null) {
			invalids++;
			return 0;
		}
		
		// Try to place it on the field, also get how many boxes it captured.
		int capd = field.place(move, id);

		// Invalid line placement.
		if (capd < 0) {
			invalids++;
			return 0;
		}
		
		// A valid move has occurred.
		moves++;
		// Counter how many boxes captured.
		captured += capd;
		
		// If the player has captured any then do another move.
		return (capd == 0 ? 0 : capd + play(field));
	}

	/**
	 * Clears this state from the last simulation.
	 */
	public void clear()
	{
		captured = 0;
		moves = 0;
		invalids = 0;
	}
	
}
