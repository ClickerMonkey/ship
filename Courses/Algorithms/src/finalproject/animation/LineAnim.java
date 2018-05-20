package finalproject.animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import finalproject.Line;
import finalproject.Vector;

public class LineAnim extends Anim
{

	public final static Color NORMAL_COLOR = Color.green;
	public final static Color BACK_COLOR = Color.lightGray;
	public final static Color INVISIBLE_COLOR = Color.white;
	public final static Stroke LINE_STROKE = new BasicStroke(0.4f);
	public final static Stroke COVER_STROKE = new BasicStroke(0.6f);
	
	private Line line;
	
	public LineAnim(Line line)
	{
		this.line = line;
	}
	
	
	@Override
	public void run(Animator a) 
	{
		Graphics2D gr = a.getScreen().getGraphics2D();
		
		// Blink the lines on the node.
		gr.setStroke(LINE_STROKE);
		draw(gr, line, NORMAL_COLOR, NORMAL_COLOR);
		a.wait(300);
		gr.setStroke(COVER_STROKE);
		draw(gr, line, BACK_COLOR, BACK_COLOR);
		a.wait(300);
		gr.setStroke(LINE_STROKE);
		draw(gr, line, NORMAL_COLOR, NORMAL_COLOR);
		a.wait(300);
		gr.setStroke(COVER_STROKE);
		draw(gr, line, BACK_COLOR, INVISIBLE_COLOR);
	}
	
	/**
	 * Draws a line with a specific color.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param lines => The line to draw.
	 * @param color => The color of the line to draw.
	 */
	public void draw(Graphics2D gr, Line line, Color color, Color normal)
	{
		gr.setColor(color);
		
		Vector s, e, n;
		n = new Vector();
		s = line.getStart();
		e = line.getEnd();
		gr.draw(new Line2D.Double(s.x, s.y, e.x, e.y));
		
		gr.setColor(normal);
		n.set(s);
		n.subtract(e);
		n.normalize();
		n.tangent();
		
		double mx = (s.x + e.x) * 0.5;
		double my = (s.y + e.y) * 0.5;
		double sx = (mx + n.x);
		double sy = (my + n.y);
		gr.draw(new Line2D.Double(sx, sy, mx, my));
	}

}
