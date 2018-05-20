package shipgames.tetris;

import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import shipgames.GameScreen;
import shipgames.Util;

/**
 * 
 * @author Phil
 *
 */
@SuppressWarnings("serial")
public class TetrisGame extends GameScreen implements KeyListener
{

	// Entry point into application.
	public static void main(String[] args)
	{
		showWindow(new TetrisGame(), "Tetris Game");
	}
	
	
	/** This is how many pixels wide the TetrisField is. */
	public final static int WIDE = TetrisField.WIDTH * Square.SIZE;
	
	/** This is how many pixels high the TetrisField is. */
	public final static int HIGH = TetrisField.HEIGHT * Square.SIZE;
	
	/** This is how many preview blocks are available. */
	public final static int PREVIEW_BLOCKS = 3;
	
	/** This is the speed in seconds the game field fills up at game over. */
	public final static float GAME_OVER_SPEED = 0.2f;
	
	public final static float PREVIEW_SCALE = 0.75f;
	
	public final static float FALLING_MULTIPLIER = 0.1f;
	
	public final static float TITLE_FONT_SIZE = 100f;
	
	public final static float GAME_FONT_SIZE = 24f;
	
	public final static float SHADOW_TRANSPARENCY = 0.3f;
	
	public final static int PANEL_WIDTH = 100;
	
	public final static int PANEL_PADDING = 20;
	
	public final static boolean SMOOTH_FALLING = true;
	
	public final static String STRING_CONTROLS = 
		"P = Play/Pause\n" +
		"N = New Game\n" +
		"M = Change Music\n" +
		"Up = Rotate Clockwise\n" +
		"Down = Move Down\n" +
		"Left = Move Left\n" +
		"Right = Move Right\n" +
		"Spacebar = Fast Fall\n" + 
		"D = Change Difficulty";
	
	public final static String STRING_STATS =
		"%s\n" + 
		"Lines\n" +
		"%d\n" +
		"Score\n" + 
		"%d\n" +
		"Level\n" +
		"%d";
	
	public final static String RESOURCE_PATH = "shipgames/tetris/data/";
	
	
	// This is the field of squares from blocks falling. 
	// This keeps track of the squares and also removes
	// full lines from the field for more playing.
	private TetrisField field;
	// This is the current falling block above the field
	// As soon as this hits the field, a block is taken
	// from the preview array and set as the current.
	private Block current;
	// This is an array of the blocks to come.
	private Block[] preview;
	// The dificulty level.
	private Difficulty difficulty;
	// This is the how often in seconds the current block
	// falls on the field. As the level increases the interval
	// shortens which makes the game go faster.
	private float interval;
	// This is the current time of the game in seconds since
	// the last time the current block was moved down.
	private float time;
	// This is a flag used when the player wants the block
	// to drop quickly. When this flag is true all input
	// used to manuever the current block is disabled until
	// the current block hits the tetris field.
	private boolean falling;
	
	// This is the current state of the game. This decides
	// when and what is updated and drawn as well as what
	// input from the user is accepted.
	private GameState gameState;
	

	// These flags are to enable better control over the falling
	// block. These will enable the player to hold down the
	// manuever keys to make the falling block move rather then
	// press one.
	private boolean movingRight;
	private boolean movingLeft;
	private float manueverTime;

	
	// These are the stats of the game. These keeps
	// track of how many lines have been removed, what
	// level this is based on how many lines have been
	// removed, and then the score which is a culmination
	// of points based on how many lines where removed at once.
	private int lines;
	private int score;
	private int level;
	
	// These are the audo clips played at the title,
	// during game play, and when the field is filled
	// to the top.
	private AudioClip clipTitle;
	private AudioClip clipMusicA;
	private AudioClip clipMusicB;
	private AudioClip clipMusicC;
	private AudioClip clipGameOver;
	private AudioClip clipCurrent;
	private AudioClip clipClick;
	
	// These are the fonts loaded from TTF files.
	private Font titleFont;
	private Font gameFont;

	// This is the background image for the game field.
	private BufferedImage background;
	
	/**
	 * This initializes a Tetris game creating the Tetris Field,
	 * initiliazing the preview blocks, loading the bitmap used
	 * for drawing the squares, and loading all the music used
	 * in the game. After everything is loaded the game starts
	 * with the title screen.
	 */
	public TetrisGame()
	{
		super(WIDE + PANEL_WIDTH, HIGH, true);
		
		// Listen to the keyboard
		addKeyListener(this);
		setBackground(Color.black);
		
		// Create the field of squares.
		field = new TetrisField();
		
		// Intialize the preview blocks
		preview = new Block[PREVIEW_BLOCKS];
		// Set the initial dificulty.
		difficulty = Difficulty.Easy;
		
		// Try loading all resources.
		try 
		{
			Util.setResourceDirectory("shipgames/tetris/data/");
			
			// Load the square Image
			Square.SQUARE_SET = Util.loadImage("Blocks.bmp");
			Square.SQUARE_SET_SIZE = Square.SQUARE_SET.getHeight();

			// Load the music clips
			clipTitle = Util.loadClip("Title Screen.wav");
			clipMusicA = Util.loadClip("Music A.wav");
			clipMusicB = Util.loadClip("Music B.wav");
			clipMusicC = Util.loadClip("Music C.wav");
			clipGameOver = Util.loadClip("Game Over.wav");
			clipClick = Util.loadClip("Block.wav");
			
			// Load the fonts
			titleFont = Util.loadFont("GameFontBold.ttf", TITLE_FONT_SIZE);
			gameFont = Util.loadFont("GameFont.ttf", GAME_FONT_SIZE);
			
			// Load the background
			background = Util.loadImage("Background.jpg");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			// Start a new game.
			newGame();
		}
	}

	/**
	 * Starts a new game setting the game state, setting
	 * the new preview blocks, grabbing the initial falling
	 * block, clearing the field of all squares, reseting
	 * the stats, and then finally plays the title screen music.
	 */
	public void newGame()
	{
		gameState = GameState.TitleScreen;
		
		// Clear the field BEFORE the next block is added.
		field.clear();
		
		// Set the new preview blocks and grab the next block.
		for (int i = 0; i < PREVIEW_BLOCKS; i++)
			preview[i] = new Block(field, 0, 0, null);
		
		current = getNextBlock();

		// Reset the interval
		interval = difficulty.getInitialSpeed();
		
		// Reset the stats		
		lines = score = level = 0;
		
		// Make sure theres no falling block
		falling = false;
		
		time = manueverTime = 0f;
		
		if (clipCurrent != null)
			clipCurrent.stop();
		clipCurrent = clipTitle;
		clipCurrent.loop();
	}
	
	/**
	 * This occurs at every frame and draws everything on 
	 * the screen. The first things drawn will be in the back
	 * and the last things drawn are on top.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	@Override
	public void draw(Graphics2D gr) 
	{		
		// Draw the background always
		gr.drawImage(background, 0, 0, WIDE, HIGH, null);
		
		// Draw the field of squares always
		field.draw(gr);
		
		// Drawing the block and its shadow
		if (gameState != GameState.GameOver && gameState != GameState.TitleScreen)
		{
			// Set the transparency for the shadow
			gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SHADOW_TRANSPARENCY));
			// Draw the current blocks shadow
			current.getShadow().draw(gr);
			
			// Reset the transparency to opaque
			gr.setComposite(AlphaComposite.SrcOver);
			
			// Draw the current falling block
			if (SMOOTH_FALLING && gameState != GameState.GameOver)
			{
				float wait = (falling ? interval * FALLING_MULTIPLIER :interval);
				float delta = (time / wait) * Square.SIZE;
				gr.translate(0f, delta - Square.SIZE);
				if (falling && gameState != GameState.TitleScreen) 
					drawMotion(gr);
				current.draw(gr);
				gr.translate(0f, Square.SIZE - delta);
			}
			else
			{
				if (falling && gameState != GameState.TitleScreen) 
					drawMotion(gr);
				current.draw(gr);
			}

			// Draw the preview blocks
			drawPreviews(gr);
		}
		
		// Draw the border always
		gr.setColor(Color.white);
		gr.drawRect(0, 0, WIDE - 1, HIGH - 1);
		
		// Draw the stats always
		gr.setFont(gameFont);
		gr.setColor(Color.white);
		String stats = String.format(STRING_STATS, difficulty, lines, score, level);
		drawString(gr, stats, WIDE, 0, WIDE + PANEL_WIDTH, HIGH - PANEL_PADDING, false);

		// Draw any text on the screen
		float bottom = 0f;
		switch (gameState)
		{
		// At the title screen show the title and the controls.
		case TitleScreen:
			gr.setFont(titleFont);
			gr.setColor(Color.white);
			bottom = drawString(gr, "Tetris", 0, HIGH * 0.1f, WIDE, HIGH, true);
			gr.setFont(gameFont);
			drawString(gr, STRING_CONTROLS, 0, bottom, WIDE, HIGH, true);
			break;
		// At the pause screen show "PAUSED" and the controls
		case Paused:
			gr.setFont(titleFont);
			gr.setColor(Color.white);
			bottom = drawString(gr, "paused", 0, HIGH * 0.1f, WIDE, HIGH, true);
			gr.setFont(gameFont);
			drawString(gr, STRING_CONTROLS, 0, bottom, WIDE, HIGH, true);
			break;
		// At the GameOver just show "GAME SHOW"
		case GameOver:
			gr.setFont(titleFont);
			gr.setColor(Color.white);
			drawString(gr, "Game\nOver", 0, HIGH * 0.1f, WIDE, HIGH, true);
			break;
		}
	}
	
	/**
	 * Draws the preview blocks on the panel. This
	 * will drawn them center aligned in the panel
	 * and they will be separated vertically by so
	 * many pixels.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void drawPreviews(Graphics2D gr)
	{
		final float spacing = 20;		
		float cx = (PANEL_WIDTH - PANEL_PADDING * 2) / 2;
		float x, y;
		
		// Translate to the panel
		gr.translate(WIDE + PANEL_PADDING, PANEL_PADDING);
		// Scale the blocks
		gr.scale(PREVIEW_SCALE, PREVIEW_SCALE);
		
		for (int i = 0; i < preview.length; i++)
		{
			// This is the left side in pixels of the
			// current preview block
			x = cx - (Square.SIZE * preview[i].getWidth() * PREVIEW_SCALE) * 0.5f;
			// This is the total vertical height of 
			// the current preview block.
			y = Square.SIZE * preview[i].getHeight() + spacing;
			// Translate it by x to center it.
			gr.translate(x, 0);
			// Finally draw the current preview block.
			preview[i].draw(gr);
			// Translate it back to the default x
			// and translate downwards by the total
			// vertical height of this preview block.
			gr.translate(-x, y);
		}
		
		// Clear the transformation
		gr.setTransform(new AffineTransform());
	}
	
	/**
	 * This will draw the motion lines on a falling block.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void drawMotion(Graphics2D gr)
	{
		final Color transparent = new Color(0, 0, 0, 0);
		final int motionLength = Square.SIZE * 3;
		final int trailLength = Square.SIZE * 6;
		
		Square s;
		int x, y;
		for (int i = 0; i < 4; i++)
		{
			s = current.getSquare(i);
			// This is the bottom of the motion gradient (white)
			y = s.getY() * Square.SIZE + 1;
			// This is the left side of the motion gradient
			x = s.getX() * Square.SIZE;
			// Draw the main motion gradient.
			// Start up at motionLength (transparent) down to y (gray)
			gr.setPaint(new GradientPaint(0f, y - motionLength, transparent, 0f, y, Color.gray));
			gr.fillRect(x, y - motionLength, Square.SIZE, motionLength);
			// Draw the trail gradients
			// Start up at trailLength (transparent) down to y (white)
			gr.setPaint(new GradientPaint(0f, y - trailLength, transparent, 0f, y, Color.white));
			// The left trail
			gr.fillRect(x, y - trailLength, 1, trailLength);
			// The right trail
			gr.fillRect(x + Square.SIZE - 1, y - trailLength, 1, trailLength);
		}
	}
	
	/**
	 * This will draw a paragraph centered in a box where
	 * drawing each line either starts at the bottom or top.
	 * 
	 * @param gr => The graphics object to draw on.
	 * @param s => The paragraph to draw.
	 * @param left => The left side of the box.
	 * @param top => The top side of the box.
	 * @param right => The right side of the box.
	 * @param bottom => The bottom side of the box.
	 * @param startAtTop => Whether to start drawing each line
	 * 		at the top of the box or at the bottom.
	 * 
	 * @return The bottom coodinate of the paragraph drawn.
	 */
	public float drawString(Graphics2D gr, String s, float left, float top, float right, float bottom, boolean startAtTop)
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

	/**
	 * This occurs at every frame and handles all updating
	 * of the game. If the game is paused or at the title
	 * screen no updating of the game will occur. This will
	 * apply gravity to the current block and make it fall.
	 * Also if the game state is GameOver then this will fill
	 * up the screen with blocks.
	 * 
	 * @param deltatime => The time in seconds since the last update.
	 */
	@Override
	public void update(float deltatime) 
	{
		// The the game is currently paused then don't update.
		if (gameState == GameState.Paused ||
			gameState == GameState.TitleScreen)
			return;
		
		time += deltatime;
		manueverTime += deltatime;
		
		float wait = interval;
		// If its falling increase the speed of falling.
		if (falling)
			wait = interval * FALLING_MULTIPLIER;
		if (gameState == GameState.GameOver)
			wait = GAME_OVER_SPEED;
		
		if (time >= wait)
		{
			time -= wait;
			// If its game over fill up the field line by line
			// until its full.
			if (gameState == GameState.GameOver)
			{
				int y = TetrisField.HEIGHT - 1;
				while (y > 0 && field.countEmptySquares(y) == 0)
					y--;
				// Fill in this row with squares
				field.fillInSquares(y);
				// If the top row is filled then change the game state.
				if (field.countEmptySquares(0) == 0)
					togglePlay();
			}
			else
			{				
				// Proceed with playing the game.
				moveBlockDown();	
			}
		}

		wait = interval * 4;
		// If the player is holding down any of the
		// manuever keys then use them.
		if (manueverTime >= wait && gameState == GameState.Playing)
		{
			manueverTime -= wait;
			
			if (movingLeft)
				current.left();
			if (movingRight)
				current.right();
		}
		
	}

	/**
	 * This method is to get the next block from the
	 * preview block array, set it up on the gamefield,
	 * check to see if it intersects with the gamefield
	 * already (if it does its GameOver), and then return it.
	 */
	public Block getNextBlock()
	{
		// Get the next block
		Block b = preview[0];
		
		// Shift all the preview blocks up
		for (int i = 1; i < preview.length; i++)
			preview[i - 1] = preview[i];
		// Set the last block as a new random block
		preview[preview.length - 1] = new Block(field, 0, 0, null);
		
		// Set the new blocks location
		b.initialize(TetrisField.WIDTH / 2 - 1, 1);
		// Check to see if it intersects the field as soon as its added.
		b.updateNext();
		
		if (b.intersectsField())
		{
			clipCurrent.stop();
			clipCurrent = clipGameOver;
			clipCurrent.play();
			gameState = GameState.GameOver;
			falling = false;
		}
		
		// Return it.
		return b;
	}
	
	/**
	 * This applies gravity to the block. When the block
	 * tries to move down but is stopped several things occur.
	 * The collision sound will play, the block will be placed 
	 * on the field, the next block will be gotten, and it
	 * will finally check if the block that landed created 
	 * any lines. If any lines were created they will be removed
	 * and the lines, score, and level stats will be updated.
	 */
	public void moveBlockDown()
	{
		// If the current block failed to move down
		if(!current.down())
		{
			// Play the sound
			clipClick.play();
			// Place the block on the field
			field.placeBlock(current);
			// Grab the next block from the preview
			current = getNextBlock();			
			// If this block was set to fall then reset it.
			if (falling)
			{
				falling = false;
				time = 0f;
			}
			// Check how many lines if any where removed.
			int removed = field.checkLines();
			if (removed != 0)
			{
				lines += removed;
				// The level increases every 10 lines
				level = lines / 10;
				// The adjusted interval based on the level
				interval = difficulty.getInterval(level);
				// The original scalars for adding to the
				// score based on how many lines removed.
				int scalars[] = {40, 100, 300, 1200};
				score += (int)((level + 1) * scalars[removed - 1] * difficulty.getBonus());
			}
			
		}
	}
	
	/**
	 * This will toggle the gameplay depending on the
	 * current Game State. If its game over a new game
	 * will start, if its at the title screen you will
	 * start playing, and if its playing the game will
	 * pause.
	 */
	public void togglePlay()
	{
		switch(gameState)
		{
		case GameOver: 
			newGame();
			break;
		case TitleScreen:
			clipCurrent.stop();
			clipCurrent = clipMusicA;
		case Paused: 
			gameState = GameState.Playing;
			clipCurrent.loop();
			break;
		case Playing: 
			gameState = GameState.Paused;
			clipCurrent.stop();
			break;
		}
	}
	
	/**
	 * This will toggle the current music playing
	 * as long as the Game State is Playing.
	 */
	public void toggleMusic()
	{
		if (gameState != GameState.Playing)
			return;
		
		clipCurrent.stop();
		// Select the next one
		if (clipCurrent == clipMusicA)
			clipCurrent = clipMusicB;
		else if (clipCurrent == clipMusicB)
			clipCurrent = clipMusicC;
		else if (clipCurrent == clipMusicC)
			clipCurrent = clipMusicA;
		// Loop!
		clipCurrent.loop();
	}
	
	/**
	 * This occurs each time a key is pressed on the keyboard.
	 */
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		// The D key increases the dificulty.
		if (key == KeyEvent.VK_D && gameState == GameState.TitleScreen)
			difficulty = Difficulty.next(difficulty);

		// The P key pauses/unpauses the game.
		if (key == KeyEvent.VK_P)
			togglePlay();

		// The N key starts a new game only if the player is currently playing a game.
		boolean playing = gameState != GameState.TitleScreen &&	
						  gameState != GameState.GameOver;
		if (key == KeyEvent.VK_N && playing)
			newGame();
		
		// The M key plays a new song.
		if (key == KeyEvent.VK_M)
			toggleMusic();
		
		// If the block is in freefall mode or the game
		// is paused ignore any input to move or rotate it.
		if (falling || gameState != GameState.Playing)
			return;
		
		// Manuever the current block based on input
		switch (key)
		{
		case KeyEvent.VK_LEFT:
			current.left();
			movingLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			current.right();
			movingRight = true;
			break;
		case KeyEvent.VK_DOWN:
			moveBlockDown();
			break;
		case KeyEvent.VK_UP:
			current.rotateRight();
			break;
		case KeyEvent.VK_ENTER:
			current.rotateLeft();
			break;
		case KeyEvent.VK_SPACE:
			if (current.getTop() > 0)
				falling = true;
			break;
		}
	}
	

	public void keyReleased(KeyEvent e) 
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			movingLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			movingRight = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) 
	{
	}
	
}
