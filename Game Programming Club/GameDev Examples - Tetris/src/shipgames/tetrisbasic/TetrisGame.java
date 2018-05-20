package shipgames.tetrisbasic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

import shipgames.GameScreen;
import shipgames.Util;

/**
 * The Tetris Game handles keyboard input, drawing the game objects, and
 * updating the game. At the start of the game the title screen comes up and
 * notifies the user of the commands for manuevering the blocks. During the game
 * play the current block will attempt to move down one (gravity). If it cannot
 * move down one then it is stuck and then added to the tetris field. The user
 * can also use the keys to manuever the blocks and they only move if they have 
 * the room to without intersecting the tetris field. During the game a preview
 * is kept in the upper right corner, which is the next block to start falling.
 * 
 * 
 * @author Philip Diffenderfer
 *
 */
@SuppressWarnings("serial")
public class TetrisGame extends GameScreen implements KeyListener
{
	// Show the game window.
	public static void main(String[] args)
	{
		showWindow(new TetrisGame(), "Tetris");
	}
	
	
	/** 
	 * This is how many pixels wide the TetrisField is.
	 */
	public final static int WIDE = TetrisField.WIDTH * Square.SIZE;
	
	/** 
	 * This is how many pixels high the TetrisField is.
	 */
	public final static int HIGH = TetrisField.HEIGHT * Square.SIZE;
	
	/** 
	 * This is how many preview blocks are available.
	 */
	public final static int PREVIEW_BLOCKS = 3;
	
	/** 
	 * This is the speed in seconds the game field fills up at game over.
	 */
	public final static float GAME_OVER_SPEED = 0.2f;
	
	
	/** 
	 * The initial game speed for level 0. This is the interval in seconds 
	 * between each fall (gravity) of the block.
	 */
	public final static float INITIAL_GAME_SPEED = 0.40f;
	/**
	 * This is the amount of seconds that is removed from the interval between
	 * block falls. Each level this amount is removed from the waiting interval.
	 */
	public final static float LEVEL_SPEED_DECREASE = 0.015f;

	/** 
	 * Original score scalars for adding score. These determines how much score
	 * to add based on how many lines were removed from the last move.
	 */
	public final static int scalars[] = {40, 100, 300, 1200};
	
	
	/**
	 * This is the scale to draw the preview block on the right side.
	 */
	public final static float PREVIEW_SCALE = 0.8f;
	
	/**
	 * This is the multiplier for the fall speed of the block if the block
	 * is in fast fall mode.
	 */
	public final static float FALLING_MULTIPLIER = 0.1f;
	
	/**
	 * This is the size of the title font in pixels.
	 */
	public final static int TITLE_FONT_SIZE = 60;
	/**
	 * This is the size of the regular font in pixels.
	 */
	public final static int GAME_FONT_SIZE = 16;
	
	/**
	 * This is the width of the panel on the right in pixels.
	 */
	public final static int PANEL_WIDTH = 100;
	/**
	 * This is how much padding there is around the panel, padding the preview
	 * block as well as the current scores.
	 */
	public final static int PANEL_PADDING = 20;
	
	/**
	 * This is the string that displays the controls to play the game.
	 */
	public final static String STRING_CONTROLS = 
		"P = Play/Pause\n" +
		"N = New Game\n" +
		"Up = Rotate Clockwise\n" +
		"Enter = Rotate Counter-Clockwise\n" +
		"Down = Move Down\n" +
		"Left = Move Left\n" +
		"Right = Move Right\n" +
		"Spacebar = Fast Fall";
	
	/**
	 * This is the format string that displays the current game statistics.
	 */
	public final static String STRING_STATS =
		"Lines\n" +
		"%d\n" +
		"Score\n" + 
		"%d\n" +
		"Level\n" +
		"%d";
	
	// This is the field of squares built from blocks falling. This keeps track 
	// of the squares and also removes full lines from the field for more playing.
	private TetrisField field;
	
	// This is the current falling block above the field As soon as this hits 
	// the field, a block is taken from the preview array and set as the current.
	private Block current;
	
	// This is the next block to come.
	private Block next;
	
	// This is the how often in seconds the current block falls on the field. As
	// the level increases the interval shortens which makes the game go faster.
	private float interval;
	
	// This is the current time of the game in seconds since the last time the 
	// current block was moved down.
	private float time;
	
	// This is a flag used when the player wants the block to drop quickly in 
	// fast fall mode. When this flag is true all input used to manuever the 
	// current block is disabled until the current block hits the tetris field 
	// and is placed.
	private boolean falling;
	
	// This is the current state of the game. This decides when and what is 
	// updated and drawn as well as what input from the user is accepted.
	private GameState gameState;
	
	// These are the statistics of the game. These keeps track of how many lines
	// have been removed, what level this is based on how many lines have been
	// removed, and then the score which is a culmination of points based on how
	// many lines where removed at once.
	private int lines;
	private int score;
	private int level;
	
	// The fonts used to draw any text for the title screen or normal game font.
	private Font titleFont;
	private Font gameFont;
	
	
	/**
	 * This initializes a Tetris game creating the Tetris Field and setting
	 * the game fonts. After everything is loaded the game starts with the 
	 * title screen.
	 */
	public TetrisGame()
	{
		super(WIDE + PANEL_WIDTH, HIGH, true);
		
		// Listen to the keyboard
		addKeyListener(this);
		
		// Set the background color
		setBackground(Color.black);
		
		// Create the field of squares.
		field = new TetrisField();
		
		// Create the fonts
		titleFont = new Font("Arial", Font.BOLD, TITLE_FONT_SIZE);
		gameFont = new Font("Arial", Font.BOLD, GAME_FONT_SIZE);
		
		// Start a new game.
		newGame();
	}
	
	/**
	 * Starts a new game setting the game state, setting the next block, 
	 * grabbing the initial falling block, clearing the field of all squares, 
	 * and reseting the stats.
	 */
	public void newGame()
	{
		gameState = GameState.TitleScreen;
		
		// Clear the field BEFORE the next block is added.
		field.clear();

		// Intialize the preview blocks
		next = new Block(field, 0, 0, null);
		
		current = getNextBlock();

		// Reset the interval
		interval = INITIAL_GAME_SPEED;
		// Reset the stats		
		lines = score = level = 0;
		// Make sure theres no falling block
		falling = false;
		
		time = 0f;
	}
	
	/**
	 * This occurs at every frame and draws everything on the screen. The first
	 * things drawn will be in the back and the last things drawn are on top.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	@Override
	public void draw(Graphics2D gr) 
	{		
		// Draw the field of squares always
		field.draw(gr);
		
		// Drawing the block and its shadow
		if (gameState != GameState.GameOver && gameState != GameState.TitleScreen)
		{
			current.draw(gr);

			// Draw the preview blocks
			drawPreview(gr);
		}
		
		// Draw the border always
		gr.setColor(Color.white);
		gr.drawRect(0, 0, WIDE - 1, HIGH - 1);
		
		// Draw the stats always
		gr.setFont(gameFont);
		gr.setColor(Color.white);
		String stats = String.format(STRING_STATS, lines, score, level);
		Util.drawString(gr, stats, WIDE, 0, WIDE + PANEL_WIDTH, HIGH - PANEL_PADDING, false);

		// Draw any text on the screen
		float bottom = 0f;
		switch (gameState)
		{
		// At the title screen show the title and the controls.
		case TitleScreen:
			gr.setFont(titleFont);
			gr.setColor(Color.white);
			bottom = Util.drawString(gr, "TETRIS", 0, HIGH * 0.1f, WIDE, HIGH, true);
			
			gr.setFont(gameFont);
			Util.drawString(gr, STRING_CONTROLS, 0, bottom, WIDE, HIGH, true);
			break;
		// At the pause screen show "PAUSED" and the controls
		case Paused:
			gr.setFont(titleFont);
			gr.setColor(Color.white);
			bottom = Util.drawString(gr, "PAUSED", 0, HIGH * 0.1f, WIDE, HIGH, true);
			
			gr.setFont(gameFont);
			Util.drawString(gr, STRING_CONTROLS, 0, bottom, WIDE, HIGH, true);
			break;
		// At the GameOver just show "GAME SHOW"
		case GameOver:
			gr.setFont(titleFont);
			gr.setColor(Color.white);
			Util.drawString(gr, "Game\nOver", 0, HIGH * 0.1f, WIDE, HIGH, true);
			break;
		}
	}
	
	/**
	 * Draws the next block that will be falling.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void drawPreview(Graphics2D gr)
	{
		// Translate to the panel
		gr.translate(WIDE + PANEL_PADDING, PANEL_PADDING);
		// Scale the blocks
		gr.scale(PREVIEW_SCALE, PREVIEW_SCALE);
		// Draw it
		next.draw(gr);
		// Clear the transformation
		gr.setTransform(new AffineTransform());
	}
	
	/**
	 * This occurs at every frame and handles all updating of the game. If the 
	 * game is paused or at the title screen no updating of the game will occur. 
	 * This will apply gravity to the current block and make it fall. Also if 
	 * the game state is GameOver then this will fill up the screen with blocks.
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
		
	}

	/**
	 * This method is to get the next block, set it up on the gamefield, check 
	 * to see if it intersects with the gamefield already (if it does it's 
	 * GameOver), and then return it.
	 */
	public Block getNextBlock()
	{
		// Get the next block
		Block b = next;
		
		next = new Block(field, 0, 0, null);
		
		// Set the new blocks location
		b.initialize(TetrisField.WIDTH / 2 - 1, 1);
		
		// Check to see if it intersects the field as soon as its added.
		b.updateNext();
		
		// If the block already intersects the field its game over!
		if (b.intersectsField())
		{
			gameState = GameState.GameOver;
			falling = false;
		}
		
		// Return it.
		return b;
	}
	
	/**
	 * This applies gravity to the block. When the block tries to move down but 
	 * is stopped several things occur. The block will be placed on the field, 
	 * the next block will be gotten, and it will finally check if the block 
	 * that landed created any lines. If any lines were created they will be 
	 * removed and the lines, score, and level stats will be updated.
	 */
	private void moveBlockDown()
	{
		// If the current block failed to move down
		if(!current.down())
		{
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

			// Update the statistics each time a block lands
			updateStats();
			
		}
	}
	
	/**
	 * This updates the stats based on the removed amount of lines from the
	 * tetris field.
	 */
	private void updateStats()
	{				
		int removed = field.checkLines();
		
		if (removed == 0)
			return;
		
		lines += removed;
	
		// The level increases every 10 lines
		level = lines / 10;
		
		// The adjusted interval based on the level
		interval = INITIAL_GAME_SPEED - (LEVEL_SPEED_DECREASE * level);
		
		score += ((level + 1) * scalars[removed - 1]);
	}
	
	/**
	 * This will toggle the gameplay depending on the current Game State. 
	 * If it's game over a new game will start, if its at the title screen you 
	 * will start playing, and if its playing the game will pause.
	 */
	public void togglePlay()
	{
		switch(gameState)
		{
		case TitleScreen:
		case Paused:
			gameState = GameState.Playing;
			break;
		case Playing: 
			gameState = GameState.Paused;
			break;
		case GameOver: 
			newGame();
			break;
		}
	}
	
	/**
	 * This occurs each time a key is pressed on the keyboard.
	 */
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();

		// The P key pauses/unpauses the game.
		if (key == KeyEvent.VK_P)
			togglePlay();

		boolean playing = gameState != GameState.TitleScreen &&	
						  gameState != GameState.GameOver;

		// The N key starts a new game only if the player is currently playing a game.
		if (key == KeyEvent.VK_N && playing)
			newGame();
		
		// If the block is in freefall mode or the game
		// is paused ignore any input to move or rotate it.
		if (falling || gameState != GameState.Playing)
			return;
		
		// Manuever the current block based on input
		switch (key)
		{
		case KeyEvent.VK_LEFT:
			current.left();
			break;
		case KeyEvent.VK_RIGHT:
			current.right();
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
	}
	
	public void keyTyped(KeyEvent e) 
	{
	}
	
}