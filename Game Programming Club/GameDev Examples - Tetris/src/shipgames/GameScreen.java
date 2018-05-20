package shipgames;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Philip Diffenderfer
 *
 */
@SuppressWarnings("serial")
public abstract class GameScreen extends JPanel
{
	
	// The double buffer used to eliminate flickering.
	private BufferedImage buffer;
	// The flag to have antialising for drawing (smoother drawing).
	private boolean antialisingOn;
	// The desired frames per second to run, if set to 0 then it will run at top speed.
	private float frameInterval;
	// The time in seconds since the last update and draw call.
	private float deltatime;
	// The time in seconds since last frame draw and update.
	private float time;
	// How many frames occured since the last FPS calculation.
	private int frames;
	// The time in seconds since the last FPS calculation.
	private float frameTime;
	// The last calculation for frames per second.
	private int framesPerSecond;
	// How often in seconds to calculate the frames per second.
	private float updateFPS = 0.25f;
	// The transformation on all the graphics drawn.
	protected AffineTransform transform;
	// The graphics object used for drawing.
	protected Graphics2D graphics;
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public GameScreen(int width, int height)
	{
		this(width, height, 0f, true);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param desiredFPS
	 */
	public GameScreen(int width, int height, float desiredFPS)
	{
		this(width, height, desiredFPS, true);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param antialising
	 */
	public GameScreen(int width, int height, boolean antialising)
	{
		this(width, height, 0f, antialising);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param desiredFPS
	 * @param antialising
	 */
	public GameScreen(int width, int height, float desiredFPS, boolean antialising)
	{
		if (desiredFPS > 0.0)
			frameInterval = 1f / desiredFPS;
		
		antialisingOn = antialising;
		
		Dimension d = new Dimension(width, height);
		setSize(d);
		setPreferredSize(d);
		setFocusable(true);
		setDoubleBuffered(true);
		
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		transform = new AffineTransform();
	}
	
	/**
	 * 
	 * @param gr => The graphics object used to render objects.
	 */
	public abstract void draw(Graphics2D gr);
	
	/**
	 * 
	 * @param deltatime => The time in seconds since the last time update was called.
	 */
	public abstract void update(float deltatime);
	
	/**
	 * Starts the game loop until the game is exited.
	 */
	public void start()
	{
		float deltaLast = 0f;
		long last = 0;
		long start = System.currentTimeMillis();

		// While the window is visible
		while (true)
		{
			// Calculate the deltatime
			last = start;
			start = System.currentTimeMillis();
			
			deltaLast = (start - last) * 0.001f;
			
			// Check whether this has a max allowed FPS.
			if (frameInterval != 0.0)
			{
				time += deltaLast;
				if (time >= frameInterval)
				{
					gameLoop(time);
					time -= frameInterval;
				}
			}
			else
			{
				gameLoop(deltaLast);
			}
		}
	}
	
	/**
	 * This updates the frames per second and updates and draws the game.
	 */
	public void gameLoop(float deltatime)
	{
		this.deltatime = deltatime;
		// Calculate the FPS
		frameTime += deltatime;
		frames++;
		
		if (frameTime >= updateFPS)
		{
			framesPerSecond = (int)(frames / frameTime);
			frameTime -= updateFPS;
			frames = 0;
		}
		
		// Update the game
		update(deltatime);
		// Draw the game
		repaint();
	}

	/**
	 * Overrides the update method to enable double buffering.
	 */
	@Override
	public final void update(Graphics g)
	{
		paint(g);
	}
	
	/**
	 * Overrides the Panel's paint method to render the game.
	 */
	@Override
	public final void paint(Graphics g)
	{
		if (g == null || buffer == null || transform == null)
			return;
		
		// Attempt to draw this frame. Occasionally
		// there are random errors with java.
		try 
		{
			// Get the graphics of the buffer
			graphics = (Graphics2D)g;
			// Clear the buffer with the background color
			graphics.setColor(getBackground());
			graphics.fillRect(0, 0, getWidth(), getHeight());
			// If antialising is turned on enable it.
			if (antialisingOn)
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// Apply the world transformation.
			graphics.setTransform(transform);
			// Draw the game objects to the buffers graphics.
			draw(graphics);
			// graphics = null;
			// Draw the buffer to the main graphics
			// g.drawImage(buffer, 0, 0, this);
			// g.dispose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the number of frames per second this Game is currently running.
	 */
	public int getFPS()
	{
		return framesPerSecond;
	}

	/**
	 * Returns the time in seconds since the last update and draw.
	 */
	public float getDeltatime()
	{
		return deltatime;
	}
	
	/**
	 * Sets how many times per second the frames per second is calculated. 
	 * 
	 * @param updates => The number of times per second to update the FPS.
	 */
	public void setUpdatesPerSecond(float updates)
	{
		if (updates == 0.0)
			return;
		updateFPS = 1f / updates;
	}
	
	/**
	 * Sets the flag for antialiasing turned on or off. Antialiasing on results in
	 * smoother drawing and nicer edges but takes more time to perform.
	 * 
	 * @param on => The state of antialiasing.
	 */
	public void setAntialiasing(boolean on)
	{
		antialisingOn = on;
	}
	
	/**
	 * 
	 */
	public void setupMathCoordinateSystem()
	{
		transform.setToScale(1f, -1f);
		transform.translate(0f, -getHeight());
		if (graphics != null)
			graphics.setTransform(transform);
	}
	
	public void setupDefaultCoordinateSystem()
	{
		transform.setToIdentity();
		if (graphics != null)
			graphics.setTransform(transform);
	}
	
	/**
	 * Displays a GameScreen as a window and sets its initial title.
	 * 
	 * @param gameScreen => The game screen contained in this Window.
	 * @param title => The text at the top of the window.
	 */
	public static void showWindow(GameScreen gameScreen, String title)
	{
		if (gameScreen == null)
			return;
		
		JFrame window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(gameScreen);
		window.pack();
		window.setVisible(true);
		
		gameScreen.start();
	}
	
}
