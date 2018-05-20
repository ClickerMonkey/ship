package shipgames.ballpopper;

import static shipgames.ballpopper.Resources.*;

import java.awt.Graphics2D;

/**
 * The different types of balls that lie on the field. The type
 * of ball designates which balls are the same and are considered
 * for removal if a neighboring ball has the same type. Each
 * type has a left and right coordinate on an image of ball's
 * where the image is a single row of balls of equal sizes.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum BallType 
{

	Red(0),
	Orange(1),
	Yellow(2),
	Green(3),
	Blue(4),
	Purple(5);
	
	// The left x-coordinate of the ball on the tile sheet.
	private int left;
	
	// The right x-coordinate of the ball on the tile sheet.
	private int right;
	
	
	/**
	 * Initializes a ball type given its index (column) on the tile sheet.
	 *  
	 * @param index => The index of this type on the tile sheet.
	 */
	private BallType(int index)
	{
		left = index * BALL_TILE_SIZE;
		right = left + BALL_TILE_SIZE;
	}

	/**
	 * Draws this type at the given x and y coordinate based on the
	 * BALL_SIZE in the resources. The left and right coordinates of this
	 * type designate which tile from the tile sheet is drawn for this type.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param x => The actual x coordinate to draw the ball at.
	 * @param y => The actual y coordinate to draw the ball at.
	 */
	public void draw(Graphics2D gr, int x, int y)
	{
		gr.drawImage(BALL_TILE, 
				x, y, x + BALL_SIZE, y + BALL_SIZE, 
				left, 0, right, BALL_TILE_SIZE, null);
	}
	
	/**
	 * Generates a random ball given a range of ball types. The range
	 * decides the maximum number of different ball types this generates.
	 * 
	 * @param range => The maximum number of different ball types.
	 */
	public static BallType getRandom(int range)
	{
		int type = (int)(Math.random() * range) % 6;

		// To change what color the balls are then change which ones
		// are the beginning colors (0, 1, 2) depending on how many different
		// colors your playing in the game (default 3).
		switch (type)
		{
		case 0: 	return Blue;
		case 1: 	return Green;
		case 2: 	return Yellow;
		case 3: 	return Red;
		case 4: 	return Orange;
		case 5: 	return Purple;
		}
		
		return null;
	}
	
}
