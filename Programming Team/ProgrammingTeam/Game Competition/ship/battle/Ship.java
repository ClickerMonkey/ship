package ship.battle;

public class Ship 
{
	
	/**
	 * The type of this ship.
	 */
	public final ShipType type;
	
	/**
	 * The coordinates of this ships pieces. Do not directly modify these 
	 * coordinates, only through the place() method. If you do you will be 
	 * caught and lose!
	 */
	public final Coord[] coords;
	
	/**
	 * The column index of the head of this ship.
	 */
	public int x;
	
	/**
	 * The row index of the head of this ship.
	 */
	public int y;
	
	/**
	 * The direction this ship is currently in on the board.
	 */
	public Direction direction;
	
	/**
	 * Instantiates a new ship of the given type.
	 * 
	 * @param type This ships type.
	 */
	public Ship(ShipType type) 
	{
		this.type = type;
		this.coords = new Coord[type.length];
	}
	
	/**
	 * Sets the position and orientation of this ship.
	 * 
	 * @param x The column index of the head of this ship.
	 * @param y The row index of the head of this ship.
	 * @param dir The orientation of this ship with-respect-to its head.
	 */
	public void place(int x, int y, Direction dir) 
	{
		this.x = x;
		this.y = y;
		this.direction = dir;
		for (int i = 0; i < type.length; i++) {
			this.coords[i] = new Coord(x, y, i, dir);
		}
	}
	
	/**
	 * Returns a clone of this ship. This is used to keep a copy of a ship that
	 * the player cannot later modify.
	 */
	public Ship clone()
	{
		Ship s = new Ship(type);
		s.place(x, y, direction);
		return s;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Ship)
		{
			Ship s = (Ship)o;
			if (type != s.type) {
				return false;
			}
			for (int i = 0; i < type.length; i++) {
				if (coords[i] == null || s.coords[i] == null) {
					return false;
				}
				if (coords[i].x != s.coords[i].x || coords[i].y != s.coords[i].y) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}