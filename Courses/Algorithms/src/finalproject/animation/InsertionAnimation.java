package finalproject.animation;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Scanner;

import finalproject.BSPNode;
import finalproject.Line;
import finalproject.Plane;
import finalproject.Polygon;
import finalproject.Queue;
import finalproject.Stack;
import finalproject.Vector;

/**
 * Steps for building the animation:
 * 
 * #1. Blink the current line, then undraw it.
 * #2. Draw the splitter for the line's space
 * #3. Blink all the lines on that plane
 * #4. Goto the first line on the front plane, then back plane and repeat steps.
 * 
 * @author Philip Diffenderfer
 */
@SuppressWarnings("serial")
public class InsertionAnimation extends AnimScreen implements KeyListener
{
	
	public static void main(String[] args)
	{
		createWindow(new InsertionAnimation(), "Animation");
	}

	public static final int WIDTH = 512;
	public static final int HEIGHT = 512;
	
	private Animator animator;
	private Polygon world;
	
	public InsertionAnimation()
	{
		super(WIDTH, HEIGHT, Color.white, 5.0);

		addKeyListener(this);
	}
	
	public void loadAnimation(String filename)
	{
		animator = new Animator(this, 1024);
		// Clear the screen
		print(getGraphics());
		
		// Create the world polygon
		world = new Polygon(4);
		world.add(0, 0);
		world.add(WIDTH, 0);
		world.add(WIDTH, HEIGHT);
		world.add(0, HEIGHT);
		
		// Read the lines used to build the tree from the file.
		Queue<Line> lines = linesFromFile(filename);
		
		// Build the animation now.
		buildAnimation(lines);
	}
	
	/**
	 * Given a filename this will read a file for lines for building a BSP Tree. 
	 * 
	 * @param filename => The location of the file to load lines from.
	 */
	public Queue<Line> linesFromFile(String filename)
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
			return null;
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
		
		return lines;
	}
	
	/**
	 * Builds the animation for construction of a BSP Tree.
	 * 
	 * @param lines => The initial lines in the world.
	 */
	public void buildAnimation(Queue<Line> lines)
	{
		int totalLines = lines.getSize();
		
		// Create the root node initially with all of the lines
		AnimBSPNode root = new AnimBSPNode();
		root.setLines(lines);
		root.setSpace(world);
		
		// Create the stack that processes the nodes and start off with the root.
		Stack<AnimBSPNode> nodes = new Stack<AnimBSPNode>(totalLines);
		nodes.push(root);
		
		// The current line, plane, and node we're splitting up.
		Line currentLine;
		Plane currentPlane;
		AnimBSPNode currentNode;
		
		// The Queues of the lines currently on the BSP node
		Queue<Line> frontLines, backLines, nodeLines;
		Line line;
		int result;
		
		// Traverse through each node in the BSP tree and split the space up
		// into a front half and back half. Continue branching until all lines
		// on the same plane have their own node.
		while (nodes.hasElements())
		{
			// Grab the next node to process
			currentNode = nodes.pop();
			// Get the lines that are currently in the nodes space
			nodeLines = currentNode.getLines();
			// Pick the first one as the plane that designates the separation of
			// space for this node.
			currentLine = nodeLines.poll();
			currentPlane = new Plane(currentLine);
			// Set the partition of the current node to the plane from the line.
			currentNode.setPartition(currentPlane);
			
			// Add in the animation to draw the entire line
			animator.addAnimation(new LineAnim(currentLine));

			// Update nodeLines with lines not on the node's plane
			nodeLines = buildNode(currentNode);
			// Add the original line back to the node.
			currentNode.getLines().offer(currentLine);
			
			// Add in the animation to draw the plane, and the lines on it.
			animator.addAnimation(new NodeAnim(currentNode));
			
			// Update the nodes splitter based on its space
			currentNode.updateSplitter();
			
			// Create the queues that contain the lines in the current space
			frontLines = new Queue<Line>(totalLines);
			backLines = new Queue<Line>(totalLines);

			// For each line in this nodes space...
			while (nodeLines.hasElements())
			{
				line = nodeLines.poll();
				result = currentPlane.eval(line);
				
				if (result == Plane.IN_FRONT)
					frontLines.offer(line);
				else if (result == Plane.IN_BACK)
					backLines.offer(line);
				else if (result == Plane.OVERLAP)
				{
					// Add in the animation to draw the entire line
					animator.addAnimation(new LineAnim(line));
					
					Line[] split = currentPlane.split(line);
					frontLines.offer(split[Plane.IN_FRONT]);
					backLines.offer(split[Plane.IN_BACK]);
				}
			}
			
			// Split the current nodes space by its plane
			Polygon[] splitSpace = currentPlane.split(currentNode.getSpace());
			Polygon frontSpace = splitSpace[Plane.IN_FRONT];
			Polygon backSpace = splitSpace[Plane.IN_BACK];
			
			// If there were lines in front of the current node then add the
			// lines to the front node of the current BSP node.
			if (frontLines.hasElements())
			{
				AnimBSPNode front = new AnimBSPNode();
				front.setLines(frontLines);
				front.setSpace(frontSpace);
				
				currentNode.setFront(front);
				// Traverse this space next.
				nodes.push(front);
			}			
			
			// If there were lines in back of the current node then add the
			// lines to the back node of the current BSP node.
			if (backLines.hasElements())
			{
				AnimBSPNode back = new AnimBSPNode();
				back.setLines(backLines);
				back.setSpace(backSpace);

				currentNode.setBack(back);
				// Traverse this space next.
				nodes.push(back);
			}
		}
	}

	/**
	 * This will build a node containing all the lines in the nodes queue that
	 * lie on the plane of the node. All other lines that don't lie on the node's
	 * plane will be returned as a queue.
	 * 
	 * @param node => The node to build.
	 */
	public Queue<Line> buildNode(BSPNode node)
	{
		int total = node.getLines().getSize();
		Queue<Line> on = node.getLines();
		Queue<Line> off = new Queue<Line>(total);
		Plane plane = node.getPartition();
		Line line;
		int eval;
		
		while (--total >= 0)
		{
			line = on.poll();
			eval = plane.eval(line);
			// If the line is directly on the plane then add it to the node.
			if (eval == Plane.ON_TOP)
				on.offer(line);
			else
				off.offer(line);
		}
		
		return off;
	}
	
	/**
	 * Handles when keys on the keyboard are pressed.
	 */
	public void keyPressed(KeyEvent e) 
	{
		char c = e.getKeyChar();
		
		// Load 'level#.dat' according to what key was pressed.
		if (c >= '1' && c <= '9')
		{
			loadAnimation("level" + c + ".dat");
			animator.start();
		}
		
		// Toggle between playing and pausing.
		if (c == 'p')
		{
			animator.setEnabled(!animator.isEnabled());
		}
		
	}

	/**
	 * Handles when keys on the keyboard are released.
	 */
	public void keyReleased(KeyEvent e) 
	{
		
	}

	/**
	 * Handles when the keys on the keyboard are triggered as typed.
	 */
	public void keyTyped(KeyEvent e) 
	{
		
	}
	
}
