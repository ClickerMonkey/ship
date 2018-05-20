package shipgames.pongbasic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import shipgames.GameScreen;
import shipgames.Util;

@SuppressWarnings("serial")
public class QuadPongGame extends GameScreen implements MouseMotionListener, KeyListener
{
	
	public static void main(String[] args)
	{
		showWindow(new QuadPongGame(), "Pong");
	}

	// The width and height of the game in pixels
	public static final int HEIGHT = 512;
	public static final int WIDTH = 512;
	
	// The radius of the ball in pixels
	public static final float BALL_RADIUS = 10f;
	// The color of the ball
	public static final Color BALL_COLOR = Color.blue;
	// The maximum speed of the ball in pixels-per-second.
	public static final float BALL_MAX_SPEED = 1000f;
	// The initial speed of the ball in pixels-per-second.
	public static final float BALL_INITIAL_SPEED = 200f;
	// The amount to increase the ball's speed by each time it hits a bar in
	// pixels-per-second.
	public static final float BALL_SPEED_INCREASE = 20f;
	
	// The number of pixels the bar's center x is from the sides.
	public static final float BAR_PADDING = 20f;
	// The width of the bars in pixels.
	public static final float BAR_WIDTH = 18f;
	// The height of the bars in pixels.
	public static final float BAR_HEIGHT = 80f;
	
	// The color of player's bar.
	public static final Color PLAYER_COLOR = Color.green;
	// The initial number of lives a bar can have.
	public static final int INITIAL_LIVES = 5;
	
	// The amount of time after a player has scored to wait for the next round.
	public static final float WAIT_TIME = 1f;
	
	// The size of the title font in pixels.
	public final static int TITLE_FONT_SIZE = 60;
	// The size of the normal game font in pixels.
	public final static int GAME_FONT_SIZE = 20;
	
	// The title string to display for this game.
	public final static String TITLE_STRING = "QUAD-PONG";
	// The paused string to display for the Paused game state.
	public final static String PAUSE_STRING = "PAUSED";

	// The list of controls for the game.
	public final static String STRING_CONTROLS = 
		"P = Play/Pause\n" +
		"N = New Game\n" +
		"Bar Control = Mouse";
	
	// The current state of the game.
	private GameState state;
	
	// The ball in play.
	private Ball ball;
	
	// Player1 (user) and Player2 (ai) bars.
	private Bar leftBar;
	private Bar topBar;
	private Bar rightBar;
	private Bar bottomBar;
	
	// Score for the player. For each second the player is alive he gets 10 points.
	private float playerScore;
	private int playerLives;
	
	// The fonts used to draw any text for the title screen or normal game font.
	private Font titleFont;
	private Font gameFont;
	
	// The amount of time in seconds we've been in the Waiting game state.
	private float waiting;
	
	/**
	 * Initializes a new Quad Pong Game creating the balls and bars.
	 */
	public QuadPongGame()
	{
		super(WIDTH, HEIGHT, true);
		
		setBackground(Color.black);
		
		// Listen for mouse movement and keyboard input.
		addMouseMotionListener(this);
		addKeyListener(this);
		
		// Initialize it with all ball constants.
		ball = new Ball(0f, 0f, BALL_RADIUS, 0f, BALL_INITIAL_SPEED, BALL_MAX_SPEED, BALL_COLOR);

		// Initialize both bars with its constants
		leftBar = new Bar(BAR_PADDING, 0f, BAR_WIDTH, BAR_HEIGHT, PLAYER_COLOR);
		topBar = new Bar(0f, BAR_PADDING, BAR_HEIGHT, BAR_WIDTH, PLAYER_COLOR);
		rightBar = new Bar(WIDTH - BAR_PADDING, 0f, BAR_WIDTH, BAR_HEIGHT, PLAYER_COLOR);
		bottomBar = new Bar(0f, HEIGHT - BAR_PADDING, BAR_HEIGHT, BAR_WIDTH, PLAYER_COLOR);
		
		// Create the fonts
		titleFont = new Font("Arial", Font.BOLD, TITLE_FONT_SIZE);
		gameFont = new Font("Arial", Font.BOLD, GAME_FONT_SIZE);
		
		// Start as new game.
		newGame();
	}
	
	/**
	 * Starts a new game reseting the ball to the center and the bars to the middle.
	 */
	private void newGame()
	{
		resetBall();

		float centerY = HEIGHT * 0.5f;
		float centerX = WIDTH * 0.5f;
		leftBar.setY(centerY);
		rightBar.setY(centerY);
		topBar.setX(centerX);
		bottomBar.setX(centerX);
		
		// Clear the player's scores
		playerScore = 0;
		waiting = 0f;
		playerLives = INITIAL_LIVES;
		
		// Set the state as title screen
		state = GameState.TitleScreen;
	}
	
	/**
	 * Resets the ball to the center of the screen and randomizes its direction.
	 */
	private void resetBall()
	{
		float centerX = WIDTH * 0.5f;
		float centerY = HEIGHT * 0.5f;
		// Initial angle is always a 30 degree from any axis.
		float angle = Util.random(0, 3) *  90 + 30;

		ball.reset(centerX, centerY, angle, BALL_INITIAL_SPEED);
	}
	
	/**
	 * Draws the bars and the ball.
	 */
	@Override
	public void draw(Graphics2D gr)
	{
		leftBar.draw(gr);
		topBar.draw(gr);
		rightBar.draw(gr);
		bottomBar.draw(gr);

		// Draw the scores below the ball if the game is playing.
		if (isInPlay())
		{
			gr.setColor(Color.gray);
			gr.setFont(gameFont);
			String scores = String.format("Lives: %d\nScore: %d", playerLives, (int)playerScore);
			Util.drawString(gr, scores, 0f, HEIGHT * 0.45f, WIDTH, HEIGHT, true);
		}
		
		ball.draw(gr);

		
		// Don't display any text if the game state is waiting or playing.
		if (isInPlay())
			return;
		
		float top = HEIGHT * 0.2f;
		float bottom = 0f;
		// At the title screen print the title, else print paused.
		gr.setColor(Color.white);
		gr.setFont(titleFont);
		if (state == GameState.TitleScreen)
			bottom = Util.drawString(gr, TITLE_STRING, 0f, top, WIDTH, HEIGHT, true);
		else
			bottom = Util.drawString(gr, PAUSE_STRING, 0f, top, WIDTH, HEIGHT, true);
		
		// Print the controls
		gr.setFont(gameFont);
		Util.drawString(gr, STRING_CONTROLS, 0f, bottom, WIDTH, HEIGHT, true);
	}

	/**
	 * Updates the position of the ball. This also handles collisions between 
	 * the bars and the walls and the bars and the ball. 
	 */
	@Override
	public void update(float deltatime)
	{
		if (state == GameState.Waiting)
		{
			waiting += deltatime;
			if (waiting >= WAIT_TIME)
			{
				waiting = 0f;
				state = GameState.Playing;
			}
		}
		
		if (state == GameState.Playing)
		{
			// Update the player's score	
			playerScore += deltatime * 100;

			ball.update(deltatime);
			
			// Handle any collisions between the ball and any bars.
			Collisions.handleCollision(leftBar, ball, BALL_SPEED_INCREASE, false);
			Collisions.handleCollision(topBar, ball, BALL_SPEED_INCREASE, false);
			Collisions.handleCollision(rightBar, ball, BALL_SPEED_INCREASE, false);
			Collisions.handleCollision(bottomBar, ball, BALL_SPEED_INCREASE, false);
			
			// Check for missing the ball
			boolean pastLeft = ball.getRight() < 0;
			boolean pastRight = ball.getLeft() > WIDTH;
			boolean pastTop = ball.getBottom() < 0;
			boolean pastBottom = ball.getTop() > HEIGHT;
			
			if (pastLeft || pastRight || pastTop || pastBottom)
			{
				playerLives--;
				resetBall();
				
				if (playerLives < 0)
					newGame();
				else
					state = GameState.Waiting;
			}
		}
	}
	
	/**
	 * Handles mouse movement. The top and bottom bars are centered on the x-axis
	 * of the mouse while the right and left bars are centered on the y axis.
	 */
	public void mouseMoved(MouseEvent e) 
	{
		if (isInPlay())
		{
			leftBar.setY(e.getY());
			rightBar.setY(e.getY());
			topBar.setX(e.getX());
			bottomBar.setX(e.getX());
			// Clip the bounds where the bars can move
			leftBar.clipBottom(HEIGHT - BAR_PADDING);
			leftBar.clipTop(BAR_PADDING);
			rightBar.clipBottom(HEIGHT - BAR_PADDING);
			rightBar.clipTop(BAR_PADDING);
			topBar.clipLeft(BAR_PADDING);
			topBar.clipRight(WIDTH - BAR_PADDING);
			bottomBar.clipLeft(BAR_PADDING);
			bottomBar.clipRight(WIDTH - BAR_PADDING);
		}
	}

	/**
	 * This will toggle the gameplay depending on the current Game State. 
	 * If the game state is at the titlescreen or paused the game will play, 
	 * and if the state is playing or waiting then the game pauses.
	 */
	public void togglePlay()
	{
		if (isInPlay())
			state = GameState.Paused;
		else
			state = GameState.Playing;
	}
	
	/**
	 * Returns true if the state of the game is currently playing (and not
	 * the title or pause screen).
	 */
	public boolean isInPlay()
	{
		return (state == GameState.Playing || state == GameState.Waiting);
	}
	
	/**
	 * Handles any key presses.
	 */
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch (key)
		{
		case KeyEvent.VK_N:
			newGame();
			break;
		case KeyEvent.VK_P:
			togglePlay();
			break;
		}
	}
	
	
	
	public void mouseDragged(MouseEvent e) 
	{
	}
	
	public void keyReleased(KeyEvent e) 
	{
	}

	public void keyTyped(KeyEvent e) 
	{
	}

}
