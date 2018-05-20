package ship.battle;
import java.util.ArrayList;

/**
 * A Battlefield implementation where a player can place their ships on the 
 * board. 
 * 
 * @author Philip Diffenderfer
 *
 */
public class Board implements BattleField
{
	
	// The width of the board in cells (column count).
	private final int width;
	
	// The height of the board in cells (row count).
	private final int height;
	
	// The state of each cell on the board.
	private final State[][] state;
	
	// A 2d array of ship references for each cell.
	private final Ship[][] ships;
	
	// The list of ships on this board.
	private final ArrayList<Ship> shipList;
	
	/**
	 * Instantiates a new empty board with the given dimensions.
	 * 
	 * @param width The width (number of columns) on the board.
	 * @param height The height (number rows) on the board.
	 */
	public Board(int width, int height) 
	{
		this.width = width;
		this.height = height;
		this.ships = new Ship[height][width];
		this.shipList = new ArrayList<Ship>();
		this.state = new State[height][width];
		// Initialize initial states.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				state[y][x] = State.Empty;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onField(int x, int y)
	{
		return !(x < 0 || x >= width || y < 0 || y >= height);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public State getState(int x, int y) 
	{
		if (onField(x, y)) {
			return state[y][x];
		}
		return null;
	}
	
	/**
	 * Sets the state of the given cell.
	 * 
	 * @param x The column index of the cell.
	 * @param y The row index of the cell.
	 * @param newState The new state of the cell.
	 * @return True if the new state was set.
	 */
	protected boolean setState(int x, int y, State newState)
	{
		boolean on = onField(x, y); 
		if (on) {
			state[y][x] = newState;
		}
		return on;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getStateCount(State s) 
	{
		int count = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				count += (state[y][x] == s ? 1 : 0);
			}
		}
		return count;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getColumns()
	{
		return width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getRows()
	{
		return height;
	}
	
	/**
	 * Returns the ship (if any) at the given coordinate.
	 * 
	 * @param x The column index of the ship.
	 * @param y The row index of the ship.
	 * @return A clone of the ship at that position.
	 */
	public Ship getShip(int x, int y)
	{
		if (!onField(x, y)) {
			return null;
		}
		return (ships[y][x] == null ? null : ships[y][x].clone());
	}
	
	/**
	 * Gets the ship added in the given index. (0 = first).
	 * 
	 * @param addIndex The index of the ship added.
	 * @return The ship at the given index, or null if none exists.
	 */
	public Ship getShip(int addIndex)
	{
		if (addIndex < 0 || addIndex >= shipList.size()) {
			return null;
		}
		return shipList.get(addIndex).clone();
	}
	
	/**
	 * Checks if the given ship on this board is sunk. If the ship is invalid
	 * (has a null coordinate, coordinate not on the board) or if the ship just
	 * doesn't exist at its given position false is returned. If the ship exists
	 * at its said placement then true is returned if all cells beneath it are
	 * either Sunk or Hit.
	 * 
	 * @param s The ship to check for sunkeness.
	 * @return True if the ship is sinkable.
	 */
	protected boolean isSunk(Ship s) 
	{
		// Make sure the ship exists here
		if (!shipList.contains(s)) {
			return false;
		}
		// Check all cells are sunk
		for (Coord c : s.coords) {
			if (!state[c.y][c.x].isFatal()) {
				return false;
			}
		}
		// All are sunk!
		return true;
	}
	
	/**
	 * Tries to sink the given ship on this board. If the ship is not on this
	 * board at the right orientation and position then false is returned.
	 * This will return true if the ship given is sunk.
	 * 
	 * @param s The ship to sink.
	 * @return Whether the ship is now sunk.
	 */
	protected boolean sink(Ship s)
	{
		// Make sure the ship exists here
		if (!shipList.contains(s)) {
			return false;
		}
		// Sink all coords
		for (Coord c : s.coords) {
			state[c.y][c.x] = State.Sunk;
		}
		// Sunk!
		return true;
	}
	
	/**
	 * Returns the number of ships currently on this board.
	 */
	public int getShipCount()
	{
		return shipList.size();
	}
	
	/**
	 * Returns true if the given ship can be added. The Ship.place() method
	 * should be called before hand setting the ships position and orientation.
	 * 
	 * @param s The ship to test for placement.
	 * @return True if the ship can be placed on the board.
	 */
	public boolean canAddShip(Ship s) 
	{
		// Make sure all coords were specified, their on the board, and the cell
		// is null.
		for (Coord c : s.coords) {
			if (c == null) {
				return false;
			}
			if (!onField(c.x, c.y)) {
				return false;
			}
			if (ships[c.y][c.x] != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds the given ship to this board in its current position and orientation.
	 * If the ship cannot be placed at its desired position and orientation then
	 * false is returned. If the board already contains the ship then false is
	 * returned. Only when the ship is successfully added to the board is then
	 * true is returned.
	 * 
	 * @param s The ship to add.
	 * @return True if the ship was added, false if it could not be.
	 */
	public boolean addShip(Ship s) 
	{
		// Get a clone so it cannot be modified after its added.
		s = s.clone();
		// If the ship cannot fit in its given place or this board already is
		// holding the ship then return false.
		if (!canAddShip(s) || shipList.contains(s)) {
			return false;
		}
		// Set the cells to reference the given ship
		for (Coord c : s.coords) {
			ships[c.y][c.x] = s;
		}
		// Add the ship to the board list.
		shipList.add(s);
		// Success!
		return true;
	}
	
	/**
	 * Returns true if the ship can be removed from its current position and
	 * orientation. If the board does not contain the ship then false is 
	 * returned, else true is returned.
	 * 
	 * @param s The ship to test for removal.
	 * @return True if the ship can be removed in its current placement.
	 */
	public boolean canRemoveShip(Ship s) 
	{
		// All cells under the ship should exist and point to it.
		for (Coord c : s.coords) {
			if (c == null) {
				return false;
			}
			if (!onField(c.x, c.y)) {
				return false;
			}
			if (!ships[c.y][c.x].equals(s)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Removes the given ship from the board. If the ship cannot be removed from
	 * its current placement or if the board doesn't even have the ship on it
	 * then false is returned. True is returned once the ship has been removed
	 * from this board.
	 * 
	 * @param s The ship to remove.
	 * @return True if the ship was successfully removed.
	 */
	public boolean removeShip(Ship s) 
	{
		// If the ship does not exist at its current position and orientation or
		// the ship doesn't exist in the boards ship list then return false.
		if (!canRemoveShip(s) || !shipList.contains(s)) {
			return false;
		}
		// Clear all cells of the reference.
		for (Coord c : s.coords) {
			ships[c.y][c.x] = null;
		}
		// Remove it from the list.
		shipList.remove(s);
		// Success!
		return true;
	}
	
	/**
	 * Returns true if this board contains the given ship on it.
	 * 
	 * @param s The ship to test for existence.
	 * @return True if the ship has been added to the board.
	 */
	public boolean hasShip(Ship s) 
	{
		return shipList.contains(s);
	}
	
	/**
	 * Removes all ships placed on the board.
	 */
	public void clearShips()
	{
		shipList.clear();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ships[y][x] = null;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder((width + 1) * height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sb.append(state[y][x].getToken());
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
}