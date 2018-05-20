package shipgames.tron;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import shipgames.GameScreen;

/**
 * This is the skeleton to a game like in the Movie Tron where bikes are racing
 * and trying not to hit each other's old path. This features only 2 bikes and
 * this has no game states so it begins at startup and every time a bike dies.
 * 
 * @author Philip Diffenderfer
 *
 */
@SuppressWarnings("serial")
public class TronGame extends GameScreen implements KeyListener 
{
	
	public static void main(String[] args)
	{
		showWindow(new TronGame(), "Tron");
	}
	
	// Width of the game screen in pixels.
	private static final int WIDTH = 640;
	// Height of the game screen in pixels.
	private static final int HEIGHT = 640;

	// Starting position and direction of bike1
	private static final int BIKE1_X = 0;
	private static final int BIKE1_Y = HEIGHT / 2;
	private static final int BIKE1_DIRECTION = Bike.EAST;
	
	// Starting position and direction of bike2
	private static final int BIKE2_X = WIDTH;
	private static final int BIKE2_Y = HEIGHT / 2;
	private static final int BIKE2_DIRECTION = Bike.WEST;
	
	// The respective bikes
	private Bike bike1;
	private Bike bike2;
	
	/**
	 * Initializes a Tron Game with 2 bikes.
	 */
	public TronGame()
	{
		super(WIDTH, HEIGHT, true);

		setBackground(Color.black);
		addKeyListener(this);
		
		// Create the empty bikes with their speed, color, and path width
		bike1 = new Bike(0, 0, 0, 100f, Color.blue, 3);
		bike2 = new Bike(0, 0, 0, 100f, Color.red, 3);
		// Set their positions
		resetBikes();
	}
	
	/**
	 * Draws the bikes on the field.
	 */
	@Override
	public void draw(Graphics2D gr)
	{
		bike1.draw(gr);
		bike2.draw(gr);
	}

	/**
	 * Reset the bikes to their starting positions and directions.
	 */
	public void resetBikes()
	{
		bike1.reset(BIKE1_X, BIKE1_Y, BIKE1_DIRECTION);
		bike2.reset(BIKE2_X, BIKE2_Y, BIKE2_DIRECTION);
	}
	
	/**
	 * Update the bikes positions and paths and check for collisions
	 */
	@Override
	public void update(float deltatime)
	{
		bike1.update(deltatime);
		bike2.update(deltatime);	

		// CHECK FOR BIKE-WALL COLLISION
		Point bike1Head = bike1.getPath().getLast();
		boolean bike1Out = (bike1Head.x < 0 || bike1Head.x > WIDTH ||
							bike1Head.y < 0 || bike1Head.y > HEIGHT);
		
		Point bike2Head = bike1.getPath().getLast();
		boolean bike2Out = (bike2Head.x < 0 || bike2Head.x > WIDTH ||
							bike2Head.y < 0 || bike2Head.y > HEIGHT);
		
		// CHECK FOR BIKE-PATH COLLISION
		boolean bike1Dies = bike1.hitsPath(bike2.getPath());
		boolean bike2Dies = bike2.hitsPath(bike1.getPath());
		
		// They both die at the same exact time from hitting each other or
		// both hitting a wall
		if ((bike1Dies && bike2Dies) || (bike1Out || bike2Out))
		{
			resetBikes();
		}
		// Bike 1 dies
		else if (bike1.hitsOwnPath() || bike1Dies || bike2Out)
		{
			resetBikes();
		}
		// Bike 2 dies
		else if (bike2.hitsOwnPath() || bike2Dies || bike2Out)
		{
			resetBikes();
		}
	}
	
	/**
	 * Updates the direction of the bikes based on key input.
	 */
	public void keyPressed(KeyEvent e) 
	{
		switch (e.getKeyCode())
		{
		// Bike1 Controls: [A]=>Left [W]=>Up [D]=>Right [S]=>Down
		case KeyEvent.VK_A:
			bike1.setDirection(Bike.WEST);
			break;
		case KeyEvent.VK_W:
			bike1.setDirection(Bike.NORTH);
			break;
		case KeyEvent.VK_D:
			bike1.setDirection(Bike.EAST);
			break;
		case KeyEvent.VK_S:
			bike1.setDirection(Bike.SOUTH);
			break;
		// Bike2 Controls: [J]=>Left [I]=>Up [L]=>Right [K]=>Down
		case KeyEvent.VK_J:
			bike2.setDirection(Bike.WEST);
			break;
		case KeyEvent.VK_I:
			bike2.setDirection(Bike.NORTH);
			break;
		case KeyEvent.VK_L:
			bike2.setDirection(Bike.EAST);
			break;
		case KeyEvent.VK_K:
			bike2.setDirection(Bike.SOUTH);
			break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e)
	{
		
	}

}
