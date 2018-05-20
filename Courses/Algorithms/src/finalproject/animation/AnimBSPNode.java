package finalproject.animation;

import finalproject.BSPNode;
import finalproject.Line;
import finalproject.Plane;
import finalproject.Polygon;
import finalproject.Vector;

/**
 * A BSP Node class that contains the line splitting the left and right 
 * subtrees as well as the polygon area that designates where the subtrees
 * exist.
 * 
 * @author Philip Diffenderfer
 */
public class AnimBSPNode extends BSPNode
{
	
	// The line that separates this nodes left and right subtrees.
	private Line splitter;
	// The space this node is contained in.
	private Polygon space;

	/**
	 * Updates the splitting line based on the partition plane and the space.
	 */
	public void updateSplitter()
	{
		Plane plane = getPartition();
		// The 2 points on the splitter line
		Vector intersects[] = new Vector[2];
		// The current line in the polygon
		Line line = new Line(new Vector(), new Vector());
		// The total number of lines in the polygon
		int lines = space.getSize();
		// The evaluation of the points on the current line.
		int evalS, evalE, x = 0;
		
		// Grab the 2 points where this plane splits up the space (polygon).
		for (int i = 0; i < lines; i++)
		{
			// Update the line to the next line in the polygon
			line.setStart(space.get(i));
			line.setEnd(space.get(i + 1));
			
			// Evaluate the points of the line to the partition.
			evalS = plane.eval(line.getStart());
			evalE = plane.eval(line.getEnd());
			
			// If both points are on the same side then goto the next line
			if (evalS == evalE)
				continue;
			
			// Add the point to splitter line.
			intersects[x++] = plane.intersection(line);
			
			// If the point is on the plane then skip the next line because
			// that one also has a point on the plane. This avoids the
			// splitter having the same starting and ending points.
			if (evalE == Plane.ON_TOP || evalS == Plane.ON_TOP)
				i++;
		}

		splitter = new Line(intersects[0], intersects[1]);
	}
	
	/**
	 * Sets the space this node is contained in.
	 */
	public void setSpace(Polygon space)
	{
		this.space = space;
	}
	
	/**
	 * Returns the line that separates this nodes left and right subtrees.
	 */
	public Line getSplitter()
	{
		return splitter;
	}
	
	/**
	 * Returns the space this node is contained in.
	 */
	public Polygon getSpace()
	{
		return space;
	}
	
}