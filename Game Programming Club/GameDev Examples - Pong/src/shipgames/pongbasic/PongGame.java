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
public class PongGame extends GameScreen implements MouseMotionListener, KeyListener
{
	
	public static void main(String[] args)
	{
		showWindow(new PongGame(), "Pong");
	}

	// The width and height of the game in pixels
	public static final int HEIGHT = 400;
	public static final int WIDTH = 600;
	
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
	public static final float BALL_SPEED_INCREASE = 50f;
	
	// The number of pixels the bar's center x is from the sides.
	public static final float BAR_PADDING = 20f;
	// The width of the bars in pixels.
	public static final float BAR_WIDTH = 18f;
	// The height of the bars in pixels.
	public static final float BAR_HEIGHT = 80f;
	
	// The color of player1's bar.
	public static final Color PLAYER1_COLOR = Color.green;
	// The color of player2's bar.
	public static final Color PLAYER2_COLOR = Color.red;
	
	// The max speed the AI bar can go in pixels-per-second.
	public static final float AI_MAX_SPEED = 400f;
	
	// The amount of time after a player has scored to wait for the next round.
	public static final float WAIT_TIME = 1f;
	
	// The size of the title font in pixels.
	public final static int TITLE_FONT_SIZE = 90;
	// The size of the normal game font in pixels.
	public final static int GAME_FONT_SIZE = 24;
	// The title string to display for this game.
	public final static String TITLE_STRING = "PONG";
	// The paused string to display for the Paused game state.
	public final static String PAUSE_STRING = "PAUSED";

	// The list of controls for the game.
	public final static String STRING_CONTROLS = 
		"P = Play/Pause\n" +
		"N = New Game\n" +
		"Bar Control = Mouse";
	
	// The current state of the game.
	public GameState state;
	
	// The ball in play.
	public Ball ball;
	
	// Player1 (user) and Player2 (ai) bars.
	public Bar player1;
	public Bar player2;
	
	// Score for each player.
	public int player1Score;
	public int player2Score;
	
	// The fonts used to draw any text for the title screen or normal game font.
	private Font titleFont;
	private Font gameFont;
	
	// The amount of time in seconds we've been in the Waiting game state.
	private float waiting;
	
	/**
	 * Initializes a new Pong Game creating the balls and bars.
	 */
	public PongGame()
	{
		super(WIDTH, HEIGHT, true);
		
		setBackground(Color.black);
		
		// Listen for mouse movement and keyboard input.
		addMouseMotionListener(this);
		addKeyListener(this);
		
		// Initialize it with all ball constants.
		ball = new Ball(0f, 0f, BALL_RADIUS, 0f, BALL_INITIAL_SPEED, BALL_MAX_SPEED, BALL_COLOR);

		// Initialize both bars with its constants
		player1 = new Bar(BAR_PADDING, 0f, BAR_WIDTH, BAR_HEIGHT, PLAYER1_COLOR);
		player2 = new Bar(WIDTH - BAR_PADDING, 0f, BAR_WIDTH, BAR_HEIGHT, PLAYER2_COLOR);
		
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
		player1.setY(centerY);
		player2.setY(centerY);
		
		// Clear the player's scores
		player1Score = player2Score = 0;
		
		waiting = 0f;
		
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
		// Initial angle is always a 45 degree from any axis.
		float angle = Util.random(0, 3) *  90 + 45;

		ball.reset(centerX, centerY, angle, BALL_INITIAL_SPEED);
	}
	
	/**
	 * Draws the bars and the ball.
	 */
	@Override
	public void draw(Graphics2D gr)
	{
		player1.draw(gr);
		player2.draw(gr);
		ball.draw(gr);

		// Draw the scores
		gr.setColor(Color.white);
		gr.setFont(gameFont);
		String scores = player1Score + " | " + player2Score;
		Util.drawString(gr, scores, 0f, 0f, WIDTH, HEIGHT, true);
		
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
	 * Updates the position of the ball and the AI's bar. This also handles
	 * collisions between the bars and the walls, bars and the ball, and the 
	 * ball and the wall. 
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
			// Don't let the player's bar pass the top or bottom sides.
			player1.clipBottom(HEIGHT);
			player1.clipTop(0);
			
			// Update the position of the AI bar
			updateAI(deltatime);
			player2.clipBottom(HEIGHT);
			player2.clipTop(0);

			ball.update(deltatime);
			
			// If the ball hits the bottom or top 'bounce' it off.
			ball.checkBottom(HEIGHT);
			ball.checkTop(0);
			
			// Handle any collisions between the ball and any bars.
			Collisions.handleCollision(player1, ball, BALL_SPEED_INCREASE, true);
			Collisions.handleCollision(player2, ball, BALL_SPEED_INCREASE, true);
			
			// Check for scoring 
			if (ball.pastLeft(0))
			{
				player2Score++;
				resetBall();
				state = GameState.Waiting;
			}
			if (ball.pastRight(WIDTH))
			{
				player1Score++;
				resetBall();
				state = GameState.Waiting;
			}
		}
	}
	
	/**
	 * Update the AI bar based on the position of the ball. Move the AI bar 
	 * center y coordinate to the ball's center y coordinate as close as
	 * possible depending on the AI_MAX_SPEED.
	 * 
	 * @param deltatime => The time in seconds since the last update.
	 */
	public void updateAI(float deltatime)
	{
		// Move the player2 bar closer to the ball
		float speed = AI_MAX_SPEED * deltatime;
		float diff = ball.getY() - player2.getY();
		int dir = (int)Math.signum(diff);
		
		// If the possible speed of the ball is greater then the difference
		// then only move the difference
		if (speed > Math.abs(diff))
			player2.setY(ball.getY());
		else
			player2.setY(player2.getY() + speed * dir);
	}

	/**
	 * Handles mouse movement. The player's bar is set to the mouse's y.
	 */
	public void mouseMoved(MouseEvent e) 
	{
		if (isInPlay())
			player1.setY(e.getY());
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
