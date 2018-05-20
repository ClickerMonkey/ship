package shipgames;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * The Util class is a helper class with random (literally) functions and the
 * loading of media files from a resource directory.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Util
{

	// The source directory to currently load
	// resources from.
	private static String resources = "";
	// The classloader used to determine the classpath
	// of the game. Used to load resources.
	private static ClassLoader loader;
	
	// This occurs as soon as the Util class is initialized
	static 
	{
		loader = Util.class.getClassLoader();
	}
	
	/**
	 * Sets the directory containing all the resources to load.
	 * 
	 * @param directory => The directory containing resources.
	 */
	public static void setResourceDirectory(String directory)
	{
		resources = directory;
	}
	
	/**
	 * Loads a clip from the resource directory.
	 * 
	 * @param filename => The name of the file in the resource directory.
	 */
	public static AudioClip loadClip(String filename) throws Exception
	{
		URL url = loader.getResource(resources + filename);
		return Applet.newAudioClip(url);
	}
	
	/**
	 * Loads a font from the resource directory.
	 * 
	 * @param filename => The name of the file in the resource directory.
	 * @param size => The size in pixels of the font to load.
	 */
	public static Font loadFont(String filename, float size) throws Exception
	{
		InputStream stream = loader.getResource(resources + filename).openStream();
		Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
		return font.deriveFont(size);
	}
	
	/**
	 * Loads an image from the resource directory.
	 * 
	 * @param filename => The name of the file in the resource directory.
	 */
	public static BufferedImage loadImage(String filename) throws Exception
	{
		InputStream stream = loader.getResource(resources + filename).openStream();
		return ImageIO.read(stream);
	}
	
	/**
	 * Loads a file from the resource directory.
	 * 
	 * @param filename => The name of the file in the resource directory.
	 */
	public static File loadFile(String filename) throws Exception
	{
		return new File(loader.getResource(resources + filename).getFile());
	}
	
	/**
	 * Returns a random number inclusively between min and max.
	 */
	public static int random(int min, int max)
	{
		return (int)((max - min) * Math.random()) + min;
	}

	/**
	 * Returns a random number inclusively between min and max.
	 */
	public static float random(float min, float max)
	{
		return (float)((max - min) * Math.random()) + min;
	}
	
	/**
	 * Returns a value that isnt larger then the max and smaller then the min.
	 * The value passed in is clamped to this range and can only be equal to the
	 * min and the max or between them.
	 * 
	 * @param value => The value to clamp
	 * @param min => The minimum value.
	 * @param max => The maximum value.
	 */
	public static float clamp(float value, float min, float max)
	{
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}
	
	/**
	 * This will draw a paragraph centered in a box where drawing each line 
	 * either starts at the bottom or top.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param s => The paragraph to draw.
	 * @param left => The left side of the box.
	 * @param top => The top side of the box.
	 * @param right => The right side of the box.
	 * @param bottom => The bottom side of the box.
	 * @param startAtTop => Whether to start drawing each line at the top of 
	 * 		the box or at the bottom.
	 * @return The bottom coodinate of the paragraph drawn.
	 */
	public static float drawString(Graphics2D gr, String s, float left, float top, float right, float bottom, boolean startAtTop)
	{
		String[] lines = s.split("\n");
		FontMetrics metrics = gr.getFontMetrics();

		final float cushion = 4f;
		float fontHeight = metrics.getHeight();
		float totalHeight = (fontHeight + cushion);
		float paraBottom = top + (lines.length * totalHeight);
		float y = (startAtTop ? top + fontHeight : bottom);
		float cx = (left + right) * 0.5f;
		float width, x;
		int line;
		
		for (int i = 0; i < lines.length; i++)
		{
			// The current line to draw
			line = (startAtTop ? i : lines.length - i - 1);
			// The width of the line in pixels
			width = metrics.stringWidth(lines[line]);
			// The starting x centered in the bounds.
			x = cx - (width * 0.5f);
			// Draw the line
			gr.drawString(lines[line], x, y);
			// Increase/Decrease y based on whether it started at the top.
			y += (startAtTop ? totalHeight : -totalHeight);
		}
		
		// Return the bottom of the paragraph 
		//mbased on whether it started at the top.
		return (startAtTop ? paraBottom : bottom);
	}
	
}
