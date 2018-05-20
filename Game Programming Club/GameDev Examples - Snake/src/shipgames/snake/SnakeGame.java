package shipgames.snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import shipgames.GameScreen;
import shipgames.Util;

@SuppressWarnings("serial")
public class SnakeGame extends GameScreen implements MouseMotionListener
{

	public static void main(String[] args)
	{
		showWindow(new SnakeGame(), "Snake Game");
	}
	
	public static final int HEIGHT = 320;
	public static final int WIDTH = 480;
	
	private float time;
	private Snake snake;
	
	private TexturePaint grass;
	private BufferedImage tilesheet;
	private Rectangle linkSource;
	
	public SnakeGame()
	{
		super(WIDTH, HEIGHT, false);
		
		setBackground(Color.black);
		addMouseMotionListener(this);
		
		try
		{
			Util.setResourceDirectory("shipgames/snake/");
			Rectangle2D.Float anchor = new Rectangle2D.Float(0, 0, 139, 138);
			grass = new TexturePaint(Util.loadImage("Grass.jpg"), anchor);
			
			tilesheet = Util.loadImage("Body2.png");
		}
		catch (Exception e)
		{
			System.out.println("Error loading resources");
			System.exit(1);
		}
		linkSource = new Rectangle(0, 0, 24, 24);
		
		snake = new Snake(tilesheet, linkSource, WIDTH * 0.5f, HEIGHT * 0.5f, 120, 20, 12, 60f);
	}
	
	@Override
	public void draw(Graphics2D gr)
	{
		gr.setPaint(grass);
		gr.fillRect(0, 0, WIDTH, HEIGHT);
		
		gr.setColor(Color.green);
		snake.draw(gr);
	}

	@Override
	public void update(float deltatime) 
	{
		if (deltatime == 0f)
			return;
		
		snake.update(deltatime);
		
		time += deltatime;

		if (time >= 2)
		{
			snake.addLinks(3);
			time -= 2;
		}
	}

	public void mouseDragged(MouseEvent e) 
	{
		snake.setTarget(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) 
	{
		snake.setTarget(e.getX(), e.getY());
	}

}
