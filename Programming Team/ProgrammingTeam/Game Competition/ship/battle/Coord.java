package ship.battle;

/**
 * A coordinate in the battlefield.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Coord 
{
	
	/**
	 * The x-component of the coordinate (column)
	 */
	public final int x;
	
	/**
	 * The y-component of the coordinate (row)
	 */
	public final int y;
	
	/**
	 * Instantiates a new Coord.
	 * 
	 * @param x The x-component of the coordinate (column).
	 * @param y The y-component of the coordinate (row).
	 */
	public Coord(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Instantiates a new Coord given a starting (reference) point, a distance
	 * from that point, and the direction from the point.
	 * 
	 * @param startx The x-coord of the reference point.
	 * @param starty The y-coord of the reference point.
	 * @param dist The distance from the reference point.
	 * @param dir The direction from the reference point.
	 */
	public Coord(int startx, int starty, int dist, Direction dir) 
	{
		this.x = startx + (dir.x * dist);
		this.y = starty + (dir.y * dist);
	}
	
}