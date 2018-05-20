package shipgames.tetrisbasic;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

/**
 * A square on the tetris field. Every square is the same size and their
 * appearance depends on their block type. Each square contains its own location
 * on the tetris field even though they are put into a 2-dimensional grid.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Square
{
	
	// The size of a single square in pixels.
	public final static int SIZE =  24;
	
	// The type of this square.
	private BlockType type;
	
	// The row and column index this square is at.
	private Point location;
	
	/**
	 * Initializes a new Square with its row and column index as well as its color.
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
		final Color TRANS = new Color(255, 255, 255, 0);
		final Color GLARE = new Color(255, 255, 255, 255);
		final Color SHADE = new Color(0, 0, 0, 255);
		
		// Compute the bounds of this square.
		int left = location.x * SIZE;
		int top = location.y * SIZE;
		int bottom = (location.y + 1) * SIZE;
		// The center x coordinate of the square
		int cx = location.x * SIZE + SIZE / 2;
		// The center y coordinate of the square
		int cy = location.y * SIZE + SIZE / 2;
		// The width of the bevel in pixels
		int bevel = SIZE >> 3;
		
		// Draw the initial square color.
		gr.setColor(type.getColor());
		gr.fillRect(left, top, SIZE, SIZE);
		// Draw the glare from the upper left corner to the center
		gr.setPaint(new GradientPaint(left, top, GLARE, cx, cy, TRANS));
		gr.fillRect(left, top, SIZE, SIZE);
		// Draw the shadow from the center to the bottom
		gr.setPaint(new GradientPaint(left, cy, TRANS, left, bottom, SHADE));
		gr.fillRect(left, top, SIZE, SIZE);
		// Draw the inset
		gr.setColor(type.getColor());
		gr.fillRect(left + bevel, top + bevel, SIZE - bevel * 2, SIZE - bevel * 2);
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
