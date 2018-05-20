package shipgames.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import shipgames.Util;
import shipgames.Vector;

import shipgames.GameScreen;

import static shipgames.pongbasic.Collisions.*;

/**
 * This is an example of a pong game with special effects and a predictive-AI.
 * Some of this is bad programming practice (The state handling is messy and
 * hard to understand). All of the programming has been done by me alone, if you
 * would like a diagram to explain the calculateAIPosition() algorithm then
 * send me an email at pdiffenderfer@gmail.com!
 * 
 * @author Philip Diffenderfer
 * 
 */
@SuppressWarnings("serial")
public class PongGame extends GameScreen implements MouseMotionListener, KeyListener
{

	public static void main(String[] args)
	{
		showWindow(new PongGame(), "Phil's Pong Game");
	}

	/** The width of the game screen in pixels. */
	public static final int WIDTH = 640;
	/** The height of the game screen in pixels. */
	public static final int HEIGHT = 480;
	
	/** The state where the ball is in motion. */
	public static final int STATE_PLAYING = 1;
	/** The state where a bar missed the ball and now is imploding. */
	public static final int STATE_IMPLODING = 2;
	/** The state where a bar missed the ball and is not exploding. */
	public static final int STATE_EXPLODING = 4;
	/** The state where a bar missed the ball and the ball is about to be served.*/
	public static final int STATE_RESERVING = 8;
	/** The number of times to 'blink' the bar that missed the ball during the re-serving state. */
	public static final int STATE_RESERVING_PULSES = 3;
	/** The time in seconds the imploding state will last. */
	public static final float STATE_IMPLODING_TIME = 0.25f;
	/** The time in seconds the exploding state will last. */
	public static final float STATE_EXPLODING_TIME = 0.75f;
	/** The time in seconds the re-serving state will last. */
	public static final float STATE_RESERVING_TIME = 2f;
	
	/** The width of a bar in pixels. */
	public static final float BAR_WIDTH = 20f;
	/** The height of the bar in pixels. */
	public static final float BAR_HEIGHT = 100f;
	/** The distance in pixels the bar's center is away from the sides. */
	public static final float BAR_OFFSET = 20f;
	
	/** The radius of the ball. */
	public static final float BALL_RADIUS = 12f;
	/** The initial velocity of the ball (in pixels-per-second). */
	public static final float BALL_VELOCITY = 450f;
	/** The maximum velocity of the ball (in pixels-per-second). */
	public static final float BALL_MAXVELOCITY = 900f;
	/** The intial direction of the ball at game start. */
	public static final float BALL_INITIAL_ANGLE = 45f;

	/** The max velocity the AI bar can move (in pixels-per-second). */
	public static final float AI_MAXVELOCITY = 400f;
	
	
	
	/** The ball on the Pong field. */
	private Ball ball;
	
	/** The player's bar (left). */
	private Bar playerBar;
	/** The player's score (times the AI missed the ball). */
	private int playerScore;
	
	
	/** The AI's bar (right). */
	private Bar aiBar;
	/** The AI's score (times the player missed the ball). */
	private int aiScore;
	/** The calculated intersection point of the ball and the AI's bar. */
	private float desiredAIPosition;
	
	
	/** During a re-serve, this is a pointer to the bar that missed the ball. */
	private Bar missedBar = null;
	/** During a re-serve, this is a pointer to the die effect for the missed bar. */
	private BurstParticleSystem missedDie = null;
	
	
	/** The effect triggered when the ball hits the AI bar. */
	private BurstParticleSystem aiSpark;
	/** The effect triggered when the AI bar misses the ball. */
	private BurstParticleSystem aiDie;
	/** The effect triggered when the ball hits the player bar. */
	private BurstParticleSystem playerSpark;
	/** The effect triggered when the player bar misses the ball. */
	private BurstParticleSystem playerDie;

	/** The effect triggered when the ball hits the walls. */
	private BurstParticleSystem wallSpark;
	/** The effect triggered when the ball is about to be served. */
	private BurstParticleSystem reServeEffect;
	/** The effect that trails after the ball during entire game-play.  */
	private BurstParticleSystem ballTrail;
	
	/** The current game state. */
	private int state = STATE_RESERVING;
	/** The time in seconds that the current state has been running. */
	private float stateTime = 0f;
	
	private boolean paused = false;
	
	/** The font used for drawing all game text. */
	private Font gameFont;
	
	
	/**
	 * Initializes the pong game creating the player bar, AI bar,
	 * and all special effects for the game.
	 */
	public PongGame()
	{
		super(WIDTH, HEIGHT, true);
		setBackground(Color.black);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		// Center x and y coordinates.
		int cx = WIDTH / 2;
		int cy = HEIGHT / 2;
		// Initialize the ball set at the center
		ball = new Ball(cx, cy, BALL_RADIUS, BALL_INITIAL_ANGLE, 
				BALL_VELOCITY, BALL_MAXVELOCITY, Color.orange);

		// Initialize the player bar on the left side as blue.
		playerBar = new Bar(BAR_OFFSET, cy, BAR_WIDTH, BAR_HEIGHT, Color.blue);
		
		// Initialize the ai bar on the right side as red.
		aiBar = new Bar(WIDTH - BAR_OFFSET, cy, BAR_WIDTH, BAR_HEIGHT, Color.red);
		// The desired position for the ai bar is at the center
		calculateAIPosition();
		
		// Attempt to load the desired game font for the game.
		try 
		{
			Util.setResourceDirectory("shipgames/pong/");
			gameFont = Util.loadFont("GameFont.ttf", 18f);
		} 
		catch (Exception e)
		{
			gameFont = new Font("Arial", Font.PLAIN, 18);
		}
		
		wallSpark = new BurstParticleSystem(
				20, 8, 0.01f, //Bursts, Particles-per-burst, Time between (seconds) 
				0f, // Gravity
				0.2f, 0.4f, // Min-Max lifetime
				80f, 120f, // Min-Max velocity
				6f, 4f, // Start-End size
				1f, 0.1f, // Start-End alpha
				Color.yellow, Color.red); // Start-End Color
		
		aiSpark = new BurstParticleSystem(
				1, 40, 0.01f, //Bursts, Particles-per-burst, Time between (seconds) 
				0f, // Gravity
				0.2f, 0.4f, // Min-Max lifetime
				120f, 120f, // Min-Max velocity
				6f, 6f, // Start-End size
				1f, 0f, // Start-End alpha
				Color.red, new Color(100, 0, 0)); // Start-End Color
		
		playerSpark = new BurstParticleSystem(
				1, 40, 0.01f, //Bursts, Particles-per-burst, Time between (seconds) 
				0f, // Gravity
				0.2f, 0.4f, // Min-Max lifetime
				120f, 120f, // Min-Max velocity
				6f, 6f, // Start-End size
				1f, 0f, // Start-End alpha
				Color.blue, new Color(0, 0, 100)); // Start-End Color
		
		reServeEffect = new BurstParticleSystem(
				100000, 80, 0.3f, //Bursts, Particles-per-burst, Time between (seconds) 
				0f, // Gravity
				0.5f, 1f, // Min-Max lifetime
				120f, 140f, // Min-Max velocity
				8f, 6f, // Start-End size
				1f, 0f, // Start-End alpha
				Color.white, Color.magenta); // Start-End Color
		
		aiDie = new BurstParticleSystem(
				6, 50, 0f, //Bursts, Particles-per-burst, Time between (seconds)  
				0f, // Gravity
				0.2f, 0f, // Min-Max lifetime
				400f, 500f, // Min-Max velocity
				10f, 8f, // Start-End size
				1f, 0f, // Start-End alpha
				Color.yellow, Color.red); // Start-End Color

		playerDie = new BurstParticleSystem(
				6, 50, 0.001f, //Bursts, Particles-per-burst, Time between (seconds) 
				0f, // Gravity
				0.2f, 0.3f, // Min-Max lifetime
				400f, 500f, // Min-Max velocity
				10f, 8f, // Start-End size
				1f, 0f, // Start-End alpha
				Color.white, Color.blue); // Start-End Color
		
		ballTrail = new BurstParticleSystem(
				-1, 2, 0.001f, //Bursts, Particles-per-burst, Time between (seconds)  
				0f, // Gravity
				0.2f, 0.2f, // Min-Max lifetime
				40f, 50f, // Min-Max velocity
				BALL_RADIUS * 2, 4f, // Start-End size
				1f, 0f, // Start-End alpha
				Color.yellow, Color.red); // Start-End Color
		
		// Start the particle effect trail behind the ball
		ballTrail.start(cx, cy);
		
		// This sets off the ball re-serve effect.
		postExploding();
	}
	
	/**
	 * Draws all of the objects on the screen.
	 */
	@Override
	public void draw(Graphics2D gr)
	{
		// The bars are drawn inthe back.
		playerBar.draw(gr);
		aiBar.draw(gr);
		
		// The re-serve effect is drawn second
		reServeEffect.draw(gr);
		
		// The ball trail must be drawn before the ball (if drawn)
		ballTrail.draw(gr);
		
		// Particle systems.
		wallSpark.draw(gr);
		aiSpark.draw(gr);
		playerSpark.draw(gr);
		aiDie.draw(gr);
		playerDie.draw(gr);
		
		// Scoreboard
		final int middle = WIDTH / 2;
		final Color transparent = new Color(64, 0, 64, 0);
		final Color blue = new Color(0, 0, 255, 180);
		final Color red = new Color(255, 0, 0, 180);
		final Paint bluePaint = new GradientPaint(middle, 0, blue, middle - 100, 0, transparent);
		final Paint redPaint = new GradientPaint(middle, 0, red, middle + 100, 0, transparent);
		final Paint blackPaint = new GradientPaint(middle, 28, Color.black, middle, 24, transparent);
		
		gr.setPaint(bluePaint);
		gr.fillRect(middle - 100, 0, 100, 28);
		gr.setPaint(redPaint);
		gr.fillRect(middle, 0, 100, 28);
		gr.setColor(Color.white);
		gr.drawLine(middle, 0, middle, 28);
		gr.setPaint(blackPaint);
		gr.fillRect(middle - 100, 24, 200, 5);
		
		String playerString = String.valueOf(playerScore);
		String aiString = String.valueOf(aiScore);

		gr.setFont(gameFont);
		int playerLength = gr.getFontMetrics().stringWidth(playerString);
		
		gr.setColor(Color.white);
		gr.drawString(playerString, middle - playerLength - 10, 20);
		gr.drawString(aiString, middle + 10, 20);
		
	}

	/**
	 * Updates the game based on the state: Playing, Imploding, 
	 * Exploding, or Re-serving.
	 */
	@Override
	public void update(float deltatime)
	{
		if (paused)
			return;
		
		// Update the current state.
		stateTime += deltatime;
		switch(state)
		{
		case STATE_PLAYING:
			updatePlaying(deltatime);
			break;
		case STATE_IMPLODING:
			updateImploding(deltatime);
			break;
		case STATE_EXPLODING:
			updateExploding(deltatime);
			break;
		case STATE_RESERVING:
			updateReServe(deltatime);
			break;
		}

		// Updates the particle systems.
		wallSpark.update(deltatime);
		aiSpark.update(deltatime);
		playerSpark.update(deltatime);
		reServeEffect.update(deltatime);
		aiDie.update(deltatime);
		playerDie.update(deltatime);
		
		// Updates the ball trail setting its location to the ball location.
		ballTrail.update(deltatime);
		ballTrail.setPosition(ball.getX(), ball.getY());
	}
	
	/**
	 * Updates the main game play. Collisions are checked with the ball 
	 * and the bars and walls. The AI's bar moves towards where the ball
	 * was calculated to end up if it isnt already there. If either bar
	 * misses the ball the ball is re-served.
	 */
	public void updatePlaying(float deltatime)
	{
		ball.update(deltatime);
		
		if (ball.checkTop(0) || ball.checkBottom(HEIGHT))
			wallSpark.start(ball.getX(), ball.getY());
		
		// BALL-BAR COLLISIONS
		// If the ball has hit the player's bar then calculate
		// where the ball will hit the AI's bar.
		if (handleCollision(playerBar, ball, 25f, true))
		{
			playerSpark.start(playerBar.getLeft(), ball.getY());
			calculateAIPosition();
		}
		
		// Do collision on ball and bar.
		if (handleCollision(aiBar, ball, 25f, true))
		{
			aiSpark.start(aiBar.getLeft(), ball.getY());
		}
		
		// Check if the ball passes the player bar.
		if (ball.getRight() < 0)
		{
			aiScore++;
			// The player missed the ball!
			reserveBall(playerBar, playerDie);
		}
		// Check the ball passing the AI bar.
		if (ball.getLeft() > WIDTH)
		{
			playerScore++;
			// The AI missed the ball!
			reserveBall(aiBar, aiDie);
		}
		
		// If the ball is headed towards the AI bar then move the bar
		// to the calculated place of intersection.
		if (ball.getVelocity().x > 0)
		{
			// Calculate the time it will take for the AI bar to move to its 
			// desired position.
			float diffBar = Math.abs(desiredAIPosition - aiBar.getY());
			float destBarTime = diffBar / AI_MAXVELOCITY;
			
			// Calculate the time it will take for the ball to get to the AI bar.
			float diffBall = (aiBar.getLeft() - ball.getRight());
			float destBallTime = diffBall / (ball.getVelocity().x * ball.getSpeed());
			
			// If the time it takes for the bar to move to its position
			// is less then the time it takes the ball to get to the AI's
			// bar then make the bar follow the ball, else make the bar
			// goto to the intersection point.
			float desiredPosition;
			if (destBarTime < destBallTime)
				desiredPosition = ball.getY();
			else
				desiredPosition = desiredAIPosition;

			float diff = desiredPosition - aiBar.getY();
			float velocity = AI_MAXVELOCITY * Math.signum(diff) * deltatime;
			
			if (Math.abs(diff) > Math.abs(velocity))
				aiBar.setY(aiBar.getY() + velocity);
			else
				aiBar.setY(desiredPosition);
			
			aiBar.clipTop(0f);
			aiBar.clipBottom(HEIGHT);
		}
	}
	
	/**
	 * Occurs after a bar has missed a ball. The bar that missed
	 * shrinks down making an 'imploding' effect. After the bar is
	 * done imploding then it explodes.
	 */
	public void updateImploding(float deltatime)
	{
		float delta = 1f - (stateTime / STATE_IMPLODING_TIME);
		// If the player missed shrink his bar
		if (missedBar != null)
		{
			missedBar.setHeight(delta * BAR_HEIGHT);
			missedBar.setWidth(delta * BAR_WIDTH);
		}
		
		// If the imploding is done move onto exploding
		if (stateTime >= STATE_IMPLODING_TIME)
		{
			postImploding();
			// Change state to exploding
			state = STATE_EXPLODING;
			stateTime = 0f;
		}
	}
	
	/**
	 * Occurs after the bar that missed the ball has been completely
	 * imploded, and right before the bar explodes.
	 */
	public void postImploding()
	{
		// Set the missed bars visibility and start the exploding
		if (missedBar != null)
		{
			missedBar.setHeight(BAR_HEIGHT);
			missedBar.setWidth(BAR_WIDTH);
			missedBar.setVisible(false);
			missedDie.start(missedBar.getX(), missedBar.getY());
		}
	}
	

	/**
	 * Occurs after the bar that missed is done imploding and
	 * it is currently exploding.
	 */
	public void updateExploding(float deltatime)
	{
		if (stateTime >= STATE_EXPLODING_TIME)
		{
			postExploding();
			// Change state to re-serve.
			state = STATE_RESERVING;
			stateTime = 0f;
		}
	}
	
	/**
	 * Occurs after the bar which missed the ball has exploded. This
	 * resets the visibility, y position, and alpha. The dying effect
	 * for the bar is stopped and the effect for re-serving is started.
	 */
	public void postExploding()
	{
		if (missedBar != null)
		{
			missedBar.setVisible(true);
			missedBar.setY(HEIGHT / 2);
			missedBar.setAlpha(0f);
			missedDie.stop();
		}
		// Start the reserving effect 
		reServeEffect.start(ball.getX(), ball.getY());
	}
	
	/**
	 * Occurs then the ball is paused waiting a certain number of
	 * seconds. If this was a reserve because a bar missed the ball
	 * then this will make the bar that missed blink.
	 */
	public void updateReServe(float deltatime)
	{		
		// The alpha based on the current time into the state
		// and the number of pulses (blinkings) the missed bar does.
		float delta = (stateTime * STATE_RESERVING_PULSES) / STATE_RESERVING_TIME;
		
		if (missedBar != null)
			missedBar.setAlpha(delta);
			
		if (stateTime >= STATE_RESERVING_TIME)
		{
			postReServe();
			// Change state to playing.
			state = STATE_PLAYING;
			stateTime = 0f;
		}
	}
	
	/**
	 * Occurs after the ball is done pausing. If the reserve was
	 * because of a missed ball the bar's alpha that missed is
	 * reset to opaque. Finally this calculates the AI position
	 * so the AI bar can move to the calculated spot of intersection.
	 */
	public void postReServe()
	{
		if (missedBar != null)
			missedBar.setAlpha(1f);
		
		// If the AI missed the bar then calculate its intersection point.
		if (missedBar == aiBar)
			calculateAIPosition();
		
		// Stop the re-serve effect and clear the missed bar and die effect.
		reServeEffect.stop();
		
		missedBar = null;
		missedDie = null;
	}
	
	
	/**
	 * This will reset the ball to the center and start the re-serving
	 * process. The ball's velocity will be reset and the current state
	 * to imploding. If it was re-served because a bar was missed the
	 * missed bar and its dying effect is set.
	 */
	public void reserveBall(Bar barMissed, BurstParticleSystem effectMissed)
	{
		ball.setX(WIDTH / 2);
		ball.setY(HEIGHT / 2);
		ball.setSpeed(BALL_VELOCITY);
		
		missedBar = barMissed;
		missedDie = effectMissed;
		
		state = STATE_IMPLODING;
		stateTime = 0f;
	}
	
	/**
	 * Clears the scores.
	 */
	public void resetGame()
	{
		aiScore = 0;
		playerScore = 0;
	}
	
	/**
	 * Calculates the position at which the ball will hit the AI bar barrier.
	 * This is only ran at a re-serve, and when the player hits the bar. This will
	 * set the position for where the center of the AI bar should be.
	 */
	public void calculateAIPosition()
	{
		// Calculate The balls bounds based on its center
		float ballX = ball.getX() - (playerBar.getRight() + ball.getRadius());
		float ballY = ball.getY() - ball.getRadius();
		float width = (aiBar.getLeft() - playerBar.getRight()) - ball.getDiameter();
		float height = HEIGHT - ball.getDiameter();
		
		// Calculate the wavelength for the velocity. The wavelength is the
		// the width in pixels it would take to return to its current position
		// after ricocheting off both sides.
		Vector theta = new Vector(ball.getVelocity());
		theta.normalize();
		theta.absolute();
		// A/sin(a) = B/sin(b)  ~  height/sin(a) = B/cos(a)   ~  (height*cos)/sin = B
		float wavelength = (height * theta.x) / theta.y * 2f;
		// Calculate how many waves
		float diffY = width - ballX;
		// The number of full waves
		int waves = (int)(diffY / wavelength);
		// The remaining length to finish the wave.
		float remaining = diffY - (wavelength * waves);
		
		// The delta the ball is between the top and bottom
		float heightDelta = ballY / height;
		// Adjust it if the ball is moving downwards.
		if (ball.getVelocity().y > 0)
			heightDelta = 1f - heightDelta;
		// The delta where the ball is on the wave.
		float waveDelta = remaining / wavelength;
		
		// The value at the top of the wall
		float top = heightDelta * 0.5f;
		// The value at the bottom of the wall
		float bottom = top + 0.5f;
		// The delta between the top and bottom of the screen where
		// the ball will end up.
		float delta = 0f;
		
		// If its inbetween the balls current position and the top
		// of the screen calculate the delta
		if (waveDelta >= 0f && waveDelta < top)
			delta = (top - waveDelta) * 2f;
		// If its inbetween the top and bottom part of the wave
		// calculate the delta.
		else if (waveDelta >= top && waveDelta < bottom)
			delta = (waveDelta - top) * 2f;
		// If its after the bottom calculate the delta.
		else if (waveDelta >= bottom)
			delta = 1f - ((waveDelta - bottom) * 2f);
		
		// Set the destination of the ball
		desiredAIPosition = delta * height + ball.getRadius();
		
		// Adjust it if the ball is moving downwards.
		if (ball.getVelocity().y > 0)
			desiredAIPosition = HEIGHT - desiredAIPosition;
	}
	
	/**
	 * Occurs when the mouse is moved over the game screen.
	 */
	public void mouseMoved(MouseEvent e)
	{
		if (paused)
			return;
		
		// If the ball is being re-served or we're playing
		// Then the players bar follows the mouse.
		if (state == STATE_RESERVING || state == STATE_PLAYING)
		{
			playerBar.setY(e.getY());
			playerBar.clipBottom(HEIGHT);
			playerBar.clipTop(0);
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
	}

	public void keyPressed(KeyEvent e)
	{	
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e) 
	{
		if (e.getKeyChar() == 'p')
		{
			paused = !paused;
		}
	}

}
