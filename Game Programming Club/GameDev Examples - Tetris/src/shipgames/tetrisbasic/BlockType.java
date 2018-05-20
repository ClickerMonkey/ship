package shipgames.tetrisbasic;

import java.awt.Color;

/**
 * The different types of block in a tetris game. Each type has its own color.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum BlockType
{
	// Traditional colors
	
	Square	(0, 240, 240),
	Line	(255, 0, 0),
	J		(240, 0, 240),
	L 		(240, 240, 0), 
	T		(120, 120, 120),
	Z		(0, 0, 240),
	S		(0, 240, 0);

	// The color of this block type
	private Color color;
	
	/**
	 * Initialize block color based on its type.
	 * 
	 * @param r => Red component of the block color.
	 * @param g => Green component of the block color.
	 * @param b => Blue component of the block color.
	 */
	private BlockType(int r, int g, int b)
	{
		color = new Color(r, g, b);
	}
	
	/**
	 * Returns the color associated with the color of this type.
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Gets a random block type.
	 */
	public static BlockType getRandom()
	{
		BlockType[] types = {Square, Line, J, L, T, Z, S};
		
		return types[(int)(Math.random() * 7)];
	}

}
