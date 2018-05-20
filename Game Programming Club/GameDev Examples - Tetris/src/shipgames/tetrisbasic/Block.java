package shipgames.tetrisbasic;

import java.awt.Graphics2D;

/**
 * A block is a 'Tetromino' consisting of four squares arranged in a specific 
 * order based on its BlockType. The BlockTypes are all the possible combinatons 
 * of putting four blocks where every block is connected by the side to another 
 * block.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Block 
{
	
	// This blocks type.
	private BlockType type;
	// The 4 squares that make up this block.
	private Square[] squares = new Square[4];
	// The gamefield of squares.
	private TetrisField field;
	// The next square positions
	private Point[] next;
	
	/**
	 * Initializes a Block based on the GameField containing it, its initial
	 * x and y position, and its block type.
	 * 
	 * @param field => The GameField its contained on.
	 * @param x => The x value, or column this Block is on.
	 * @param y => The y value, or row this Block is.
	 * @param type => This Blocks type.
	 */
	public Block(TetrisField field, int x, int y, BlockType type)
	{
		this.field = field;
		this.type = (type != null ? type : BlockType.getRandom());
		
		this.next = new Point[] {
				new Point(), new Point(), 
				new Point(), new Point()};
		
		this.initialize(x, y);
	}
	
	/**
	 * This initializes this block at a position setting its squares positions 
	 * based on this blocks type. If this block's type is set to Random it is 
	 * reset as one of the predefined blocks.
	 * 
	 * @param x => The x value, or column this Block is on.
	 * @param y => The y value, or row this Block is on.
	 */
	public void initialize(int x, int y)
	{
		switch (type)
		{
		case Square:
			squares[0] = new Square(x, y, type);
			squares[1] = new Square(x + 1, y, type);
			squares[2] = new Square(x, y + 1, type);
			squares[3] = new Square(x + 1, y + 1, type);
			break;
		case Line:
			squares[0] = new Square(x, y, type);
			squares[1] = new Square(x, y + 1, type);
			squares[2] = new Square(x, y + 2, type);
			squares[3] = new Square(x, y + 3, type);			
			break;
		case J:
			squares[0] = new Square(x + 1, y, type);
			squares[1] = new Square(x + 1, y + 1, type);
			squares[2] = new Square(x + 1, y + 2, type);
			squares[3] = new Square(x, y + 2, type);
			break;
		case L:
			squares[0] = new Square(x, y, type);
			squares[1] = new Square(x, y + 1, type);
			squares[2] = new Square(x, y + 2, type);
			squares[3] = new Square(x + 1, y + 2, type);
			break;
		case T:
			squares[0] = new Square(x, y, type);
			squares[1] = new Square(x + 1, y, type);
			squares[2] = new Square(x + 2, y, type);
			squares[3] = new Square(x + 1, y + 1, type);
			break;
		case Z:
			squares[0] = new Square(x, y, type);
			squares[1] = new Square(x + 1, y, type);
			squares[2] = new Square(x + 1, y + 1, type);
			squares[3] = new Square(x + 2, y + 1, type);			
			break;
		case S:
			squares[0] = new Square(x, y + 1, type);
			squares[1] = new Square(x + 1, y + 1, type);
			squares[2] = new Square(x + 1, y, type);
			squares[3] = new Square(x + 2, y, type);			
			break;
		}
	}
	
	/**
	 * Draws this block to the Graphics object.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void draw(Graphics2D gr)
	{
		squares[0].draw(gr);
		squares[1].draw(gr);
		squares[2].draw(gr);
		squares[3].draw(gr);
	}
	
	/**
	 * This will attempt to rotate this Block clockwise but if the squares on 
	 * the game field interfere, or a square ends up out of bounds, its set back
	 *  to its original position.
	 * 
	 * @return True if this Block has successfully rotated.
	 */
	public boolean rotateRight()
	{
		if (type == BlockType.Square)
			return true;

		// Setup the next locations.
		updateNext();
		// Rotate the squares now based on the center
		Point c = getCenter();
		next[0].set(-(next[0].y - c.y) + c.x, (next[0].x - c.x) + c.y);
		next[1].set(-(next[1].y - c.y) + c.x, (next[1].x - c.x) + c.y);
		next[2].set(-(next[2].y - c.y) + c.x, (next[2].x - c.x) + c.y);
		next[3].set(-(next[3].y - c.y) + c.x, (next[3].x - c.x) + c.y);
		// Try to move to the next positions
		return attemptMove();
	}
	
	/**
	 * This will attempt to rotate this Block counter-clockwise but if the 
	 * squares on the game field interfere, or a square ends up out of bounds, 
	 * its set back to its original position.
	 * 
	 * @return True if this Block has successfully rotated.
	 */
	public boolean rotateLeft()
	{
		if (type == BlockType.Square)
			return true;

		// Setup the next locations.
		updateNext();
		// Rotate the squares now based on the center
		Point c = getCenter();
		next[0].set((next[0].y - c.y) + c.x, -(next[0].x - c.x) + c.y);
		next[1].set((next[1].y - c.y) + c.x, -(next[1].x - c.x) + c.y);
		next[2].set((next[2].y - c.y) + c.x, -(next[2].x - c.x) + c.y);
		next[3].set((next[3].y - c.y) + c.x, -(next[3].x - c.x) + c.y);
		// Try to move to the next positions
		return attemptMove();
	}
	
	/**
	 * This will attempt to move this Block downwards but if the squares on the 
	 * game field interfere, or a square ends up out of bounds, its set back to 
	 * its original position.
	 * 
	 * @return True if this Block has successfully moved down.
	 */
	public boolean down()
	{
		// Setup the next locations
		updateNext();
		// Move every square down
		next[0].y++;
		next[1].y++;
		next[2].y++;
		next[3].y++;
		// Try to move to the next positions
		return attemptMove();
	}
	
	/**
	 * This will attempt to move this Block to the right but if the squares on
	 * the game field interfere, or a square ends up out of bounds, its set 
	 * back to its original position.
	 * 
	 * @return True if this Block has successfully moved right.
	 */
	public boolean right()
	{
		// Setup the next locations.
		updateNext();
		// Move every square to the right
		next[0].x++;
		next[1].x++;
		next[2].x++;
		next[3].x++;
		// Try to move to the next positions
		return attemptMove();
	}
	
	/**
	 * This will attempt to move this Block to the left but if the squares on 
	 * the game field interfere, or a square ends up out of bounds, its set 
	 * back to its original position.
	 * 
	 * @return True if this Block has successfully moved left.
	 */
	public boolean left()
	{
		// Setup the next locations.
		updateNext();
		// Move every square to the left
		next[0].x--;
		next[1].x--;
		next[2].x--;
		next[3].x--;
		// Try to move to the next positions
		return attemptMove();
	}
	
	/**
	 * Attempt to move this block to the next positions by checking if the next
	 * posisions intersect any blocks on the field or if any are off the field. 
	 * If the next block position is free then move to it and return success.
	 * 
	 * @return True if move to the next positions was successful.
	 */
	private boolean attemptMove()
	{
		// Check to see if the next locations intersected,
		// if they didnt then move to those positions.
		if (!intersectsField())
		{
			moveToNext();
			return true;
		}
		return false;
	}
	
	/**
	 * This will return whether the next positions computed will intersect with
	 *  any existing square on the field.
	 * 
	 * @return True if the next positions intersected with the field.
	 */
	public boolean intersectsField()
	{
		return (!field.isEmpty(next[0]) || 
				!field.isEmpty(next[1]) ||
				!field.isEmpty(next[2]) || 
				!field.isEmpty(next[3]));
	}

	/**
	 * This will reset the positions of the next points to the positions of the
	 * current blocks
	 */
	public void updateNext()
	{
		next[0].set(squares[0].getLocation());
		next[1].set(squares[1].getLocation());
		next[2].set(squares[2].getLocation());
		next[3].set(squares[3].getLocation());
	}
	
	/**
	 * This will move the squares of the block to the next points on the field.
	 */
	private void moveToNext()
	{
		squares[0].getLocation().set(next[0]);
		squares[1].getLocation().set(next[1]);
		squares[2].getLocation().set(next[2]);
		squares[3].getLocation().set(next[3]);
	}
	
	/**
	 * This will return the center of this block which is used for rotation 
	 * and is the second block.
	 */
	private Point getCenter()
	{
		return squares[1].getLocation();
	}
	
	/**
	 * This will return a square from this block based on the index of the square.
	 * 
	 * @param i => The index from 0 to 3.
	 */
	public Square getSquare(int i)
	{
		if (i < 0 || i > 3)
			return null;
		
		return squares[i];
	}
	
	/**
	 * Gets the row of the top most square in this block.
	 */
	public int getTop()
	{
		return Math.min(squares[0].getY(), 
			   Math.min(squares[1].getY(), 
			   Math.min(squares[2].getY(), squares[3].getY())));
	}
		
}
