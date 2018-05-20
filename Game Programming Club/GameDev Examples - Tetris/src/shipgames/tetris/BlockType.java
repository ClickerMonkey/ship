package shipgames.tetris;

/**
 * The different types of block in a tetris game.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum BlockType
{
	// [][]
	// [][]
	Square(0),
	// [][][][]
	Line(1),
	//   []
	//   []
	// [][]
	J(2),
	// []
	// []
	// [][]
	L(3), 
	// [][][]
	//   []
	T(4),
	// [][]
	//   [][]
	Z(5),
	//   [][]
	// [][]
	S(6);

	private int source;
	
	private BlockType(int source)
	{
		this.source = source;
	}
	
	public int getSource()
	{
		return source;
	}
	
	/**
	 * Gets a random block type.
	 */
	public static BlockType getRandom()
	{
		BlockType[] types = {Square, Line, J, L, T, Z, S};
		
		return types[(int)(Math.random() * types.length)];
	}

}
