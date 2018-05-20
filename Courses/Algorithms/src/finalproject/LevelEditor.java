package finalproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class LevelEditor extends GameScreen implements KeyListener, MouseMotionListener, MouseListener
{

	public static void main(String[] args)
	{
		showWindow(new LevelEditor(), "BSP Level Editor");
	}

	private static final int SCALE = 5;
	
	private static final int NONE 	= 1 << 0;
	private static final int START 	= 1 << 1;
	private static final int END 	= 1 << 2;
	private static final int LINE 	= 1 << 3;

	private static final BasicStroke STROKE_LINE = new BasicStroke(2);
	private static final BasicStroke STROKE_NORM = new BasicStroke(2);
	private static final BasicStroke STROKE_HAND = new BasicStroke(1);

	private static final Color COLOR_LINE = Color.green;
	private static final Color COLOR_NORM = Color.blue;
	private static final Color COLOR_HAND = Color.black;
	private static final Color COLOR_GRID = new Color(220, 220, 255);
	
	private ArrayList<Line> lines;
	private Line current;
	private Vector mouse;
	private Vector previous;
	private int point;
	
	public LevelEditor()
	{
		super(512, 512);
		
		setFocusable(true);
		setBackground(Color.white);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		lines = new ArrayList<Line>(64);
		mouse = new Vector();
		previous = new Vector();
		current = null;
		point = NONE;
	}
	
	@Override
	public void draw(Graphics2D gr) 
	{
		// Draw the grid
		int w = getWidth() / SCALE;
		int h = getHeight() / SCALE;
		
		gr.setColor(COLOR_GRID);
		for (int i = 0; i < w; i++)
			gr.drawLine(i * SCALE, 0, i * SCALE, getHeight());
		for (int i = 0; i < h; i++)
			gr.drawLine(0, i * SCALE, getWidth(), i * SCALE);
		
		// Draw the lines
		for (int i = 0; i < lines.size(); i++)
			if (lines.get(i) != current)
				drawLine(gr, lines.get(i), COLOR_HAND, COLOR_HAND, COLOR_LINE);

		// Draw the selected line
		if (current != null)
		{
			Color line, start, end;
			start = (point == START ? Color.red : COLOR_HAND);
			end = (point == END ? Color.red : COLOR_HAND);
			line = (point == LINE ? Color.red : COLOR_LINE);
			drawLine(gr, current, start, end, line);
		}
	}
	
	public void drawLine(Graphics2D gr, Line l, Color start, Color end, Color line)
	{
		Vector n = new Vector();
		int sx, sy, ex, ey, mx, my, size;
		
		sx = (int)(l.getStart().x * SCALE);
		sy = (int)(l.getStart().y * SCALE);
		ex = (int)(l.getEnd().x * SCALE);
		ey = (int)(l.getEnd().y * SCALE);
		size = SCALE << 1;
		
		gr.setColor(line);
		gr.setStroke(STROKE_LINE);
		gr.drawLine(sx, sy, ex, ey);

		gr.setStroke(STROKE_HAND);
		gr.setColor(start);
		gr.drawOval(sx - SCALE, sy - SCALE, size, size);
		gr.setColor(end);
		gr.drawOval(ex - SCALE, ey - SCALE, size, size);

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

	@Override
	public void update(float deltatime) 
	{

	}

	public void keyPressed(KeyEvent e) 
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_C:
			Line newLine = new Line(new Vector(-2, 0), new Vector(2, 0));
			setMidPoint(newLine, mouse);
			lines.add(newLine);
			break;
		case KeyEvent.VK_S:
			try
			{
				saveToFile("level1.dat");
				System.out.println("Level saved to 'level1.dat'");
			}
			catch (Exception ex)
			{
				System.out.println("Error saving level to 'level1.dat'");
				ex.printStackTrace();
			}
			break;
		case KeyEvent.VK_L:
			try
			{
				readFromFile("level1.dat");
				System.out.println("Level loaded from 'level1.dat'");
			}
			catch (Exception ex)
			{
				System.out.println("Error loading level from 'level1.dat'");
				ex.printStackTrace();
			}
			break;
		case KeyEvent.VK_DELETE:
			if (current != null)
			{
				lines.remove(current);
				current = null;
			}
			break;
		}		
	}
	
	public void saveToFile(String filename) throws Exception
	{
		PrintStream writer = new PrintStream("level1.dat");
		
		writer.println(lines.size());
		Line l;
		
		for (int i = 0; i < lines.size(); i++)
		{
			l = lines.get(i);
			writer.format("%d %d %d %d\n", 
					(int)l.getStart().x, (int)l.getStart().y,
					(int)l.getEnd().x, (int)l.getEnd().y);
		}
	}

	public void readFromFile(String filename) throws Exception
	{
		Scanner input = new Scanner(new File(filename));
		
		int total = input.nextInt();
		
		lines = new ArrayList<Line>(total);
		Vector start,end;
		
		for (int i = 0; i < total; i++)
		{
			start = new Vector(input.nextInt(), input.nextInt());
			end = new Vector(input.nextInt(), input.nextInt());
			lines.add(new Line(start, end));
		}
	}
	
	public void keyReleased(KeyEvent e) 
	{
	}

	public void keyTyped(KeyEvent e) 
	{
	}

	public void mouseDragged(MouseEvent e) 
	{
		if (current != null)
		{
			previous.set(mouse);
			mouse.x = Math.floor((double)e.getX() / SCALE);
			mouse.y = Math.floor((double)e.getY() / SCALE);
			
			if (point == START || point == LINE)
				update(current.getStart());
			if (point == END || point == LINE)
				update(current.getEnd());
		}
	}
	
	public void update(Vector v)
	{
		v.x -= (previous.x - mouse.x);
		v.y -= (previous.y - mouse.y);
	}
	
	public void setMidPoint(Line l, Vector v)
	{
		int mx = (int)(l.getStart().x + l.getEnd().x) >> 1;
		int my = (int)(l.getStart().y + l.getEnd().y) >> 1;
		int dx = (int)(v.x - mx);
		int dy = (int)(v.y - my);
		l.getStart().x += dx;
		l.getStart().y += dy;
		l.getEnd().x += dx;
		l.getEnd().y += dy;
	}

	public void mouseMoved(MouseEvent e) 
	{
		previous.set(mouse);
		mouse.x = Math.floor((double)e.getX() / SCALE);
		mouse.y = Math.floor((double)e.getY() / SCALE);
	}

	public void mouseClicked(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e) 
	{
		
	}

	public void mouseExited(MouseEvent e) 
	{
		
	}

	public void mousePressed(MouseEvent e) 
	{	
		previous.set(mouse);
		mouse.x = (double)e.getX() / SCALE;
		mouse.y = (double)e.getY() / SCALE;
		
		double dx, dy, d, sd, delta, dsq;
		dx = dy = d = sd = 0.0;
		
		Line l = null;
		current = null;
		point = NONE;
		
		for (int i = 0; i < lines.size(); i++)
		{
			l = lines.get(i);

			dx = l.getStart().x - mouse.x;
			dy = l.getStart().y - mouse.y;
			dsq = dx * dx + dy * dy;
			
			if (dsq < 1.0)
			{
				current = l;
				point = START;
				break;
			}
			
			dx = l.getEnd().x - mouse.x;
			dy = l.getEnd().y - mouse.y;
			dsq = dx * dx + dy * dy;
			
			if (dsq < 1.0)
			{
				current = l;
				point = END;
				break;
			}

			dx = l.getEnd().x - l.getStart().x;
			dy = l.getEnd().y - l.getStart().y;
			dsq = dx * dx + dy * dy;
			
			if (dsq == 0.0) continue;
			
			// The distance between the starting and ending point of the line
			d = Math.sqrt(dsq);
			
			// The signed distance from the mouse to this line
			sd = (dy * (l.getStart().x - mouse.x) - dx * (l.getStart().y - mouse.y)) / d;
			
			// The linear distance from the starting point
			delta = ((mouse.x - l.getStart().x) * dx + (mouse.y - l.getStart().y) * dy);
			
			// If its within one unit AND the mouse is within the segment then drag it
			if (sd < 0.5 && sd > -0.5 && delta > 0.0 && delta < dsq)
			{
				current = l;
				point = LINE;
				break;
			}
		}
		mouse.x = Math.floor(mouse.x);
		mouse.y = Math.floor(mouse.y);
	}

	public void mouseReleased(MouseEvent e) 
	{	
		current = null;
	}

}
