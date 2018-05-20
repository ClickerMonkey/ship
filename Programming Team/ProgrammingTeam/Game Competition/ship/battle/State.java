package ship.battle;

/**
 * A state of any given cell on the battlefield.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum State 
{
	/**
	 * The initial state of every cell.
	 */
	Empty('.', false), 
	/**
	 * The state where a ship was hit, but not yet sunk.
	 */
	Hit('X', true),
	/**
	 * The state where a bomb missed a ship.
	 */
	Miss('O', false),
	/**
	 * The state where the cell is a part of a sunk ship.
	 */
	Sunk('*', true);
	
	// The character representation of a state
	private final char token;
	
	// Whether this state is fatal to a boat
	private final boolean fatal;
	
	/**
	 * Instantiates a new State enum.
	 * 
	 * @param token This states token.
	 * @param fatal This states fatality.
	 */
	private State(char token, boolean fatal)
	{
		this.token = token;
		this.fatal = fatal;
	}
	
	/**
	 * Returns the character representation of this state.
	 */
	public char getToken()
	{
		return token;
	}

	/**
	 * Returns whether this state is fatal to a ship. 
	 */
	public boolean isFatal()
	{
		return fatal;
	}
	
}
