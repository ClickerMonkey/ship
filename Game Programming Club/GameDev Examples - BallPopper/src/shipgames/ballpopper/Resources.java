package shipgames.ballpopper;

import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import shipgames.Motion;
import shipgames.Util;

/**
 * Contains all constants and resources required by the games logic
 * and graphics.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Resources 
{

	// The number of high scores to keep track of during the current
	// session.
	public static final int HIGH_SCORES = 5;


	// The size of the balls on the field in pixels.
	public static final int BALL_SIZE = 32;

	// The time in seconds it takes a ball to move down to its position
	// on the row below.
	public static final float BALL_DOWN_DURATION = 0.5f;

	// The time in seconds it takes a ball to move left to its position
	// on the column to the left.
	public static final float BALL_LEFT_DURATION = 0.5f;

	// The motion used for moving balls to the left.
	public static final Motion BALL_MOTION_LEFT = new Motion(Motion.In, Motion.Log10);

	// The motion used for moving the balls down.
	public static final Motion BALL_MOTION_DOWN = new Motion(Motion.In, Motion.TinyBounce);


	// The total number of different ball colors on the field.
	public static final int BALL_COLORS = 3;


	// The number of rows of balls on the field.
	public static final int FIELD_ROWS = 12;
	// The number of columns of balls on the field.
	public static final int FIELD_COLUMNS = 12;
	// The width of the field in pixels. Scaled proportionately to the ball size.
	public static final int FIELD_WIDTH = FIELD_COLUMNS * BALL_SIZE;
	// The height of the field in pixels. Scaled proportionately to the ball size.
	public static final int FIELD_HEIGHT = FIELD_ROWS * BALL_SIZE;

	// The padding on the left side of the field. Scaled proportionately to the ball size.
	public static final int GAME_PAD_LEFT = BALL_SIZE * 4;
	// The padding on the right side of the field. Scaled proportionately to the ball size.
	public static final int GAME_PAD_RIGHT = BALL_SIZE;
	// The padding on the top side of the field. Scaled proportionately to the ball size.
	public static final int GAME_PAD_TOP = BALL_SIZE;
	// The padding on the bottom side of the field. Scaled proportionately to the ball size.
	public static final int GAME_PAD_BOTTOM = BALL_SIZE;
	// The total width of the game screen in pixels. Scaled proportionately to the ball size.
	public static final int GAME_WIDTH = GAME_PAD_LEFT + FIELD_WIDTH + GAME_PAD_RIGHT;
	// The total height of the game screen in pixels. Scaled proportionately to the ball size.
	public static final int GAME_HEIGHT = GAME_PAD_TOP + FIELD_HEIGHT + GAME_PAD_BOTTOM;

	// The color of the game screen background.
	public static final Color GAME_BACKGROUND = Color.black;

	// The font to use for scores.
	//  @Resource(type=ResourceType.FILE_FONT)
	public static final Font SCORE_FONT;

	// The size of the font used for scores in pixels. Scaled proportionately to the ball size.
	public static final int SCORE_FONT_SIZE = BALL_SIZE;

	// The color of the text used for the scores.
	public static final Color SCORE_FONT_COLOR = Color.white;


	// The font of the text on the field.
	public static final Font FIELD_FONT;
	// The size of the text on the field in pixels.
	public static final int FIELD_FONT_SIZE = BALL_SIZE * 4;
	// The color of the text on the field
	public static final Color FIELD_FONT_COLOR = new Color(200, 200, 200);

	// The paint for the background of the field.
	public static final GradientPaint FIELD_PAINT = new GradientPaint(0, 0, new Color(216, 216, 216), 0, GAME_HEIGHT, new Color(40, 40, 40));
	// The Color of the outline of the field.
	public static final Color FIELD_OUTLINE_COLOR = Color.gray;
	// The stroke of the outline of the field.
	public static final Stroke FIELD_OUTLINE = new BasicStroke(4);


	// The tile sheet (single row of ball images 'tiles')
	public static final BufferedImage BALL_TILE;
	// The size of the balls on the tile sheet in pixels.
	public static final int BALL_TILE_SIZE;


	// The sound effect played when balls are removed from the field.
	public static final AudioClip BALL_REMOVE;


	// The tile sheet for the pop animation
	public static final BufferedImage POP_TILE;
	// The number of tiles across the tile sheet.
	public static final int POP_TILE_COLUMNS = 4;
	// The total number of tiles on the tile sheet.
	public static final int POP_TILE_FRAMES = 16;
	// The width of the tiles in pixels.
	public static final int POP_TILE_FRAME_WIDTH = 32;
	// The height of the tiles in pixels.
	public static final int POP_TILE_FRAME_HEIGHT = 32;
	// The lifetime of an pop in seconds.
	public static final float POP_DURATION = 0.6f;
	// The interval between each pop frame in seconds.
	public static final float POP_INTERVAL = POP_DURATION / POP_TILE_FRAMES;


	/**
	 * Loads the physical resources from the file system as soon as this 
	 * Resources class is referenced by an object for the first time.
	 */
	static
	{
		BufferedImage ballsSheet = null, popSheet = null;
		AudioClip audio = null;
		Font smallFont = null, largeFont = null;

		// Try to load all resources, on error exit.
		try
		{
			Util.setResourceDirectory("shipgames/ballpopper/");

			ballsSheet = Util.loadImage("Balls.png");
			popSheet = Util.loadImage("Explosion.png");
			audio = Util.loadClip("Pop.wav");
			smallFont = Util.loadFont("GameFont.ttf", SCORE_FONT_SIZE);
			largeFont = Util.loadFont("GameFont.ttf", FIELD_FONT_SIZE);
		}
		catch (Exception e)
		{
			// Uh-oh! Run!!!!
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error loading resources:\n" + e.toString());
			System.exit(0);
		}

		BALL_TILE = ballsSheet;
		BALL_TILE_SIZE = ballsSheet.getHeight();

		BALL_REMOVE = audio;

		SCORE_FONT = smallFont;
		FIELD_FONT = largeFont;

		POP_TILE = popSheet;
	}

}
