package ship.battle;

/**
 * A battlefield interface used to send an opponent the state of your field.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface BattleField
{

	/**
	 * Returns the number of columns on the battlefield.
	 */
	public int getColumns();

	/**
	 * Returns the number of rows on the battliefield.
	 */
	public int getRows();

	/**
	 * Returns the state of the cell at the given position.
	 * 
	 * @param x The column of the cell.
	 * @param y The row of the cell.
	 * @return The state of the specified cell.
	 */
	public State getState(int x, int y);
	
	/**
	 * Returns true if the given point is on the board.
	 * 
	 * @param x The column index.
	 * @param y The row index.
	 * @return True if it exists on the board.
	 */
	public boolean onField(int x, int y);
	
	/**
	 * Calculates the number of cells on this battlefield with the given state.
	 * 
	 * @param s The state to count.
	 * @return The total number of cells on the battlefield with the given state.
	 */
	public int getStateCount(State s);
	
}
