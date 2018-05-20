package finalproject;

/**
 * A Node in a binary space partitioning tree which contains the partitioning
 * plane, the lines contained on the plane, and a front and back subtree node.
 * 
 * @author Philip Diffenderfer
 *
 */
public class BSPNode 
{

	// The plane in this node that partitions the space
	private Plane partition;
	// A Queue of lines that are on the partition plane
	private Queue<Line> lines;
	// The child node in the front
	private BSPNode front;
	// The child node in the back
	private BSPNode back;

	/**
	 * Initializes an empty Binary-Space-Partitioning Node.
	 */
	public BSPNode()
	{
	}
	
	/**
	 * Initializes an empty Binary-Space-Partitioning Node.
	 */
	public BSPNode(Queue<Line> lines)
	{
		this.lines = lines;
	}
	
	/**
	 * Sets the partitioning plane of this node.
	 * 
	 * @param partition => The plane that divides this node.
	 */
	public void setPartition(Plane partition)
	{
		this.partition = partition;
	}
	
	/**
	 * Sets the lines that are on this node.
	 * 
	 * @param lines => The lines on this node's partitioning plane.
	 */
	public void setLines(Queue<Line> lines)
	{
		this.lines = lines;
	}
	
	/**
	 * Sets the child node in the front.
	 * 
	 * @param front => The node in front.
	 */
	public void setFront(BSPNode front)
	{
		this.front = front;
	}
	
	/**
	 * Sets the child node in the back.
	 * 
	 * @param back => The node in back.
	 */
	public void setBack(BSPNode back)
	{
		this.back = back;
	}
	
	/**
	 * Returns this node's partitioning plane.
	 */
	public Plane getPartition()
	{
		return partition;
	}
	
	/**
	 * Returns a queue of lines that are on this plane.
	 */
	public Queue<Line> getLines()
	{
		return lines;
	}
	
	/**
	 * Gets the child that is in the front.
	 */
	public BSPNode getFront()
	{
		return front;
	}
	
	/**
	 * Gets the child that is in the back.
	 */
	public BSPNode getBack()
	{
		return back;
	}
	
}
