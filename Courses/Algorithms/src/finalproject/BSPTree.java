package finalproject;

import java.io.File;
import java.util.Scanner;

/**
 * A Binary Space Partitioning Tree implementation with construction and 
 * back-to-front traversing. The tree is built with a Queue of lines and the
 * traversal also returns a Queue of lines.
 * 
 * @author Philip Diffenderfer
 *
 */
public class BSPTree 
{

	// The root node for the entire tree.
	private BSPNode root;
	
	// The position of the eye in space.
	private Vector eye;
	
	// The total number of nodes in this tree.
	private int size;
	
	// A queue of lines sorted from back to front based on the eye position.
	private Queue<Line> sortedLines;
	
	// A temporary stack used each time a tree is built or sorted lines is calculated.
	private Stack<BSPNode> nodes;

	
	/**
	 * Initializes an empty Binary Space Partitioning Tree.
	 */
	public BSPTree()
	{
		eye = new Vector();
	}
	
	/**
	 * This will build-up the Binary Space Partitioning Tree based on the queue
	 * of lines contained in this tree. All previous tree data will be erased.
	 */
	public void buildTree(Queue<Line> lines)
	{
		int totalLines = lines.getSize();
		
		size = 0;
		// Create the root node initially with all of the lines
		root = new BSPNode(lines);
		
		// Create the stack that processes the nodes and start off with the root.
		nodes = new Stack<BSPNode>(totalLines);
		nodes.push(root);
		
		// The current line, plane, and node we're splitting up.
		Line currentLine;
		Plane currentPlane;
		BSPNode currentNode;
		
		// The Queues of the lines currently on the BSP node
		Queue<Line> frontLines, backLines, nodeLines, onLines;
		Line line;
		int result;
		
		onLines = new Queue<Line>(totalLines);
		
		// Traverse through each node in the BSP tree and split the space up
		// into a front half and back half. Continue branching until all lines
		// on the same plane have their own node.
		while (nodes.hasElements())
		{
			// Grab the next node to process
			currentNode = nodes.pop();
			size++;
			// Get the lines that are currently in the nodes space
			nodeLines = currentNode.getLines();
			// Pick the first one as the plane that designates the separation of
			// space for this node.
			currentLine = nodeLines.poll();
			currentPlane = new Plane(currentLine);

			// Create the queues that contain the lines in the current space
			frontLines = new Queue<Line>(totalLines);
			backLines = new Queue<Line>(totalLines);
			onLines.clear();
			
			// For each line in this nodes space...
			while (nodeLines.hasElements())
			{
				line = nodeLines.poll();
				result = currentPlane.eval(line);
				
				if (result == Plane.IN_FRONT)
					frontLines.offer(line);
				else if (result == Plane.IN_BACK)
					backLines.offer(line);
				else if (result == Plane.ON_TOP)
					onLines.offer(line);
				else if (result == Plane.OVERLAP)
				{
					Line[] split = currentPlane.split(line);
					frontLines.offer(split[Plane.IN_FRONT]);
					backLines.offer(split[Plane.IN_BACK]);
				}
			}
			
			// If there were lines in front of the current node then add the
			// lines to the front node of the current BSP node.
			if (frontLines.hasElements())
			{
				BSPNode node = new BSPNode(frontLines);
				currentNode.setFront(node);
				nodes.push(node);
			}			
			
			// If there were lines in back of the current node then add the
			// lines to the back node of the current BSP node.
			if (backLines.hasElements())
			{
				BSPNode node = new BSPNode(backLines);
				currentNode.setBack(node);
				nodes.push(node);
			}
			
			// For every line on this node's plane put it on this node
			while (onLines.hasElements())
				nodeLines.offer(onLines.poll());
			
			// Add the original line back to the node.
			currentNode.getLines().offer(currentLine);
			// Set the partition of the current node to the plane from the line.
			currentNode.setPartition(currentPlane);
		}
		
		// Create the queue for the sorted lines each time the tree is built.
		sortedLines = new Queue<Line>(size);
		nodes = new Stack<BSPNode>(size);
	}

	/**
	 * A recursive method for returning the sorted lines based on the eye in the
	 * world in back-to-front order.
	 * 
	 * @param node => The current node being traversed.
	 */
	private void buildSortedLines(BSPNode node)
	{
		if (node == null)
			return;
		
		// Determine which side the eye is on of the current plane
		int result = node.getPartition().eval(eye);
		Queue<Line> lines = node.getLines();
		
		if (result == Plane.IN_FRONT)
		{
			buildSortedLines(node.getBack());
			for (int i = 0; i < lines.getSize(); i++)
				sortedLines.offer(lines.peek(i));
			buildSortedLines(node.getFront());
		}			
		else if (result == Plane.IN_BACK)
		{
			buildSortedLines(node.getFront());
			for (int i = 0; i < lines.getSize(); i++)
				sortedLines.offer(lines.peek(i));
			buildSortedLines(node.getBack());
		}
		else
		{
			buildSortedLines(node.getFront());
			buildSortedLines(node.getBack());
		}
	}
	
	/**
	 * Returns a queue of sorted lines based on the eye in space.
	 */
	public Queue<Line> getSortedLines()
	{
		sortedLines.clear();
		
		buildSortedLines(root);
		
		return sortedLines;
	}
	
	/**
	 * Tries to read a binary tree from a file containing the lines. The tree is
	 * built from the file of lines. This will return true upon successful file
	 * reading and tree building.
	 * 
	 * @param filename => The filepath to the line file.
	 */
	public boolean readTree(String filename)
	{
		Scanner input = null;
		
		// Try to open up the file for reading, return false if it failed.
		try
		{
			input = new Scanner(new File(filename));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		// The total number of lines/planes in the file
		int total = input.nextInt();
		
		Queue<Line> lines = new Queue<Line>(total);
		Vector start,end;
		
		// Read each line in the format of starting point and ending point.
		for (int i = 0; i < total; i++)
		{
			start = new Vector(input.nextInt(), input.nextInt());
			end = new Vector(input.nextInt(), input.nextInt());
			lines.offer(new Line(start, end));
		}
		
		// Build the tree based on the read in lines.
		buildTree(lines);
		
		return true;
	}
	
	/**
	 * Sets the view point of the eye in space.
	 * 
	 * @param eye => The point in space
	 */
	public void setEye(Vector eye)
	{
		this.eye.set(eye);
	}
	
	/**
	 * Returns the root node of the BSP tree.
	 */
	public BSPNode getRoot()
	{
		return root;
	}

	/**
	 * Returns the number of nodes in this tree.
	 */
	public int getSize() 
	{
		return size;
	}

	/**
	 * Returns the stack used for traversing through the nodes.
	 */
	public Stack<BSPNode> getNodes() 
	{
		return nodes;
	}
	
}
