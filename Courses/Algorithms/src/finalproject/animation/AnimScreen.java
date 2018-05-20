package finalproject.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AnimScreen extends JPanel
{

	/**
	 * Creates a window with the given animation screen contained in it.
	 * 
	 * @param screen => The animation screen in the window.
	 * @param title => The title of the window.
	 */
	public static void createWindow(AnimScreen screen, String title)
	{
		JFrame window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(screen);
		window.pack();
		window.setVisible(true);
	}
	
	private double scale = 1.0;
	
	/**
	 * Initializes an animation screen with its title, its inner size, and the
	 * background color of the inner panel.
	 * 
	 * @param title => The text of this window's title.
	 * @param width => The width in pixels of the inner panel.
	 * @param height => The height in pixels of the inner panel.
	 * @param background => The background color for the inner panel.
	 */
	public AnimScreen(int width, int height, Color background, double scale)
	{
		Dimension size = new Dimension(width, height);
		
		setSize(size);
		setPreferredSize(size);
		setBackground(background);
		setFocusable(true);
		
		this.scale = scale;
	}

	/**
	 * Gets the graphics object of the panel used to draw animations.
	 */
	public Graphics2D getGraphics2D()
	{
		// Grab the graphics object of this panel
		Graphics2D gr = (Graphics2D)getGraphics();
		// Turn on pretty antialiasing
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Scale all graphics drawn
		gr.scale(scale, scale);
		
		return gr;
	}
	
}
