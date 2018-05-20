package finalproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Scanner;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

@SuppressWarnings("serial")
public class BSPScreen extends GameScreen implements MouseMotionListener
{

	public static void main(String[] args)
	{
		showWindow(new BSPScreen(), "Final Project - Binary Space Partitioning");
	}

	private static final int WIDTH = 512;
	private static final int HEIGHT = 512;
	
	private static final int SCALE = 5;
	
	private static final BasicStroke STROKE_LINE = new BasicStroke(2);
	private static final BasicStroke STROKE_NORM = new BasicStroke(2);
	
	private static final Color COLOR_LINE = Color.green;
	private static final Color COLOR_NORM = Color.blue;
	
	private BSPTree tree;
	private Vector eye;
	
	public BSPScreen()
	{
		super(WIDTH, HEIGHT, true);
		
		setBackground(Color.black);
		addMouseMotionListener(this);
		
		loadLevel("level2.dat");

		eye = new Vector(240, 180);
	}
	
	public void loadLevel(String filename)
	{
		tree = new BSPTree();

		Queue<Line> lines = null;
		
		try
		{
			lines = readLevelFromFile(filename);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Lines: " + lines.getSize());
		
		tree.buildTree(lines);
		
		System.out.println("Nodes: " + tree.getSize());
	}
	
	public Queue<Line> readLevelFromFile(String filename) throws Exception
	{
		Scanner input = new Scanner(new File(filename));
		
		int total = input.nextInt();
		
		Queue<Line> lines = new Queue<Line>(total);
		Vector start,end;
		
		for (int i = 0; i < total; i++)
		{
			start = new Vector(input.nextInt(), input.nextInt());
			end = new Vector(input.nextInt(), input.nextInt());
			lines.offer(new Line(start, end));
		}
		
		return lines;
	}
	
	@Override
	public void draw(Graphics2D gr) 
	{
		tree.setEye(eye);
		
		Queue<Line> lines = tree.getSortedLines();
		Point mid = new Point();
		Line current;
		
		int index = 0;
		
		while (lines.hasElements())
		{
			current = lines.poll();
			
			if (current == null) {
				break;
			}
			
			mid.x = (int)(current.getStart().x + current.getEnd().x) >> 1;
			mid.y = (int)(current.getStart().y + current.getEnd().y) >> 1;
			
			gr.setColor(Color.white);
			gr.drawString(String.valueOf(index), mid.x * SCALE, mid.y * SCALE);
			
			drawLine(gr, current);
			index++;
		}
		
		gr.setColor(Color.white);
		gr.drawOval((int)(eye.x * SCALE - 4), (int)(eye.y * SCALE - 4), 8, 8);
	}	
	
	public void drawLine(Graphics2D gr, Line l)
	{
		Vector n = new Vector();
		int sx, sy, ex, ey, mx, my, size;
		
		sx = (int)(l.getStart().x * SCALE);
		sy = (int)(l.getStart().y * SCALE);
		ex = (int)(l.getEnd().x * SCALE);
		ey = (int)(l.getEnd().y * SCALE);
		size = SCALE << 1;
		
		gr.setColor(COLOR_LINE);
		gr.setStroke(STROKE_LINE);
		gr.drawLine(sx, sy, ex, ey);

		n.set(l.getStart());
		n.subtract(l.getEnd());
		n.normalize();
		n.tangent();
		n.multiply(size);
		
		mx = (sx + ex) >> 1;
		my = (sy + ey) >> 1;
		sx = (int)(mx + n.x);
		sy = (int)(my + n.y);
		
		gr.setColor(COLOR_NORM);
		gr.setStroke(STROKE_NORM);
		gr.drawLine(mx, my, sx, sy);	
	}

	public void drawPolygon(Graphics2D gr, Polygon p)
	{
		int total = p.getSize();
		Vector a, b;
		
		for (int i = 0; i < total; i++)
		{
			a = p.get(i);
			b = p.get(i + 1);
			
			gr.draw(new Line2D.Double(a.x, a.y, b.x, b.y));
		}
	}
	
	@Override
	public void update(float deltatime) 
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		eye.set(e.getX() / SCALE, e.getY() / SCALE);
	}
	

}
