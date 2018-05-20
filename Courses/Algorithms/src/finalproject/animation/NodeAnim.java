package finalproject.animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import finalproject.Line;
import finalproject.Queue;
import finalproject.Vector;


public class NodeAnim extends Anim
{

	public final static Color NORMAL_COLOR = Color.blue;
	public final static Color BLINK_COLOR = Color.red;
	public final static Color SPLITTER_COLOR = Color.lightGray;
	public final static Stroke LINE_STROKE = new BasicStroke(0.4f);
	public final static Stroke SPLITTER_STROKE = new BasicStroke(0.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[] {1.0f, 0.5f}, 1.0f);
	
	private AnimBSPNode node;
	
	public NodeAnim(AnimBSPNode node)
	{
		this.node = node;
	}
	
	
	@Override
	public void run(Animator a) 
	{
		Queue<Line> nodeLines = node.getLines();
		int total = nodeLines.getSize();
		Line lines[] = new Line[total];
		
		// Pop off each animation, put it into the array, then put it back on.
		for (int i = 0; i < total; i++)
			nodeLines.offer(lines[i] = nodeLines.poll());
		
		Graphics2D gr = a.getScreen().getGraphics2D();
		
		// Draw the splitter
		gr.setStroke(SPLITTER_STROKE);
		draw(gr, node.getSplitter(), SPLITTER_COLOR);
		a.wait(300);
		
		// Blink the lines on the node.
		gr.setStroke(LINE_STROKE);
		draw(gr, lines, NORMAL_COLOR);
		a.wait(300);
		draw(gr, lines, BLINK_COLOR);
		a.wait(300);
		draw(gr, lines, NORMAL_COLOR);
		a.wait(300);
		draw(gr, lines, BLINK_COLOR);
		a.wait(300);
		draw(gr, lines, NORMAL_COLOR);
		a.wait(1000);
	}
	
	/**
	 * Draws the set of lines with a specific color.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param lines => The array of lines to draw.
	 * @param color => The color of the lines to draw.
	 */
	public void draw(Graphics2D gr, Line[] lines, Color color)
	{
		gr.setColor(color);
		
		Vector s, e, n = new Vector();
		for (int i = 0; i < lines.length; i++)
		{
			s = lines[i].getStart();
			e = lines[i].getEnd();
			gr.draw(new Line2D.Double(s.x, s.y, e.x, e.y));
			
			n.set(s);
			n.subtract(e);
			n.normalize();
			n.tangent();
			
			double mx = (s.x + e.x) * 0.5;
			double my = (s.y + e.y) * 0.5;
			double cx = (mx + n.x);
			double cy = (my + n.y);
			gr.draw(new Line2D.Double(cx, cy, mx, my));
		}
	}
	
	/**
	 * Draws a line with a specific color.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param lines => The line to draw.
	 * @param color => The color of the line to draw.
	 */
	public void draw(Graphics2D gr, Line line, Color color)
	{
		gr.setColor(color);
		
		Vector s, e;
		s = line.getStart();
		e = line.getEnd();
		gr.draw(new Line2D.Double(s.x, s.y, e.x, e.y));
	}

}
