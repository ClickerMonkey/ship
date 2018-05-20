package shipgames.tetris;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A square on the tetris field. Every square is the same
 * size and they're appearance depends on their block type.
 * The image per block type is taken from the preloaded
 * SQUARE_SET BufferedImage. Once the SQUARE_SET is loaded
 * SQUARE_SET_SIZE should be set to the number of pixels
 * wide the squares on the source image is. Each square
 * contains its own location on the tetris field as well
 * even though they are put into a 2-dimensional grid.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Square
{
	
	// The size of a single square in pixels.
	public final static int SIZE =  24;
	// The set of squares used to draw a block.
	public static BufferedImage SQUARE_SET;
	// The size in pixels of each square set.
	public static int SQUARE_SET_SIZE;
	// The type of this square.
	private BlockType type;
	// The row and column index this square is at.
	private Point location;
	
	
	/**
	 * Initializes a new Square with its row and column index 
	 * as well as its color.
	 * 
	 * @param x => The initial column index for this square.
	 * @param y => The initial row index for this square.
	 * @param type => The initial block type of this square.
	 */
	public Square(int x, int y, BlockType type)
	{
		this.location = new Point(x, y);
		this.type = (type != null ? type : BlockType.getRandom());
	}
	
	/**
	 * Draws this square on the screen.
	 */
	public void draw(Graphics2D gr)
	{
		if (SQUARE_SET == null || SQUARE_SET_SIZE < 1)
			return;
		
		// Get the index of the frame on the source image
		// based on this block type.
		int index = type.getSource();
		
		// Compute the bounds of this square.
		int left = location.x * SIZE;
		int right = (location.x + 1) * SIZE;
		int top = location.y * SIZE;
		int bottom = (location.y + 1) * SIZE;
		int sourceLeft = index * SQUARE_SET_SIZE;
		int sourceRight = (index + 1) * SQUARE_SET_SIZE;
		
		// Draw the image based on the bounds and the source
		// rectangle from the index.
		gr.drawImage(SQUARE_SET, left, top, right, bottom, 
				sourceLeft, 0, sourceRight, SQUARE_SET_SIZE, null);
	}
	
	/**
	 * Sets this square's type based on another's.
	 * 
	 * @param b => The square to copy from.
	 */
	public void setType(Square b)
	{
		type = b.type;
	}
	
	/**
	 * Sets the column index of this square.
	 * 
	 * @param x => The new column index.
	 */
	public void setX(int x)
	{
		location.x = x;
	}
	
	/**
	 * Sets the row index of this square.
	 * 
	 * @param y => The new row index.
	 */
	public void setY(int y)
	{
		location.y = y;
	}

	/**
	 * Returns the row and column index of this Square.
	 */
	public Point getLocation()
	{
		return location;
	}
	
	/**
	 * Returns the row index of this square.
	 */
	public int getY()
	{
		return location.y;
	}
	
	/**
	 * Returns the column index of this square.
	 */
	public int getX()
	{
		return location.x;
	}
	
}
