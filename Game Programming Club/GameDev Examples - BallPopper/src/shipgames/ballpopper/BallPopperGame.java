package shipgames.ballpopper;

import static shipgames.ballpopper.Resources.*;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import shipgames.GameScreen;
import shipgames.Util;

/**
 * The panel which handles drawing and updating the ball field, listening for
 * user input, and drawing the current score and the highest 5 scores in the
 * current session.
 * 
 * @author Philip Diffenderfer
 *
 */
public class BallPopperGame extends GameScreen implements MouseListener, MouseMotionListener
{
	
	private static final long serialVersionUID = 4252910501612323848L;

	
	/**
	 * Creates and shows a window with an instance of this panel in it.
	 */
	public static void main(String[] args)
	{
		showWindow(new BallPopperGame(), "Ball Popper! (by Philip Diffenderfer)");
	}

	// The field of balls.
	private BallField field;
	
	// The current score of the game.
	private long score = 0;
	
	// The list of high scores in this game session.
	private long[] highScores = new long[HIGH_SCORES];

	
	/**
	 * Initializes a BallPopperGame.
	 */
	public BallPopperGame()
	{
		super(GAME_WIDTH, GAME_HEIGHT, true);

		// Listen for input!
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(GAME_BACKGROUND);

		// Create the field
		field = new BallField(
				FIELD_ROWS, 
				FIELD_COLUMNS, 
				GAME_PAD_LEFT, 
				GAME_PAD_TOP, 
				BALL_COLORS);

		// Fill the field with balls.
		field.fill();
	}


	/**
	 * Draws the game screen and the field to the graphics object.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	@Override
	public synchronized void draw(Graphics2D gr) 
	{
		synchronized (this) 
		{
			// Draw the field background.
			gr.setPaint(FIELD_PAINT);
			gr.fillRect(GAME_PAD_LEFT, GAME_PAD_TOP, FIELD_WIDTH, FIELD_HEIGHT);

			// Draw the field outline.
			gr.setColor(FIELD_OUTLINE_COLOR);
			gr.setStroke(FIELD_OUTLINE);
			gr.drawRect(GAME_PAD_LEFT, GAME_PAD_TOP, FIELD_WIDTH, FIELD_HEIGHT);

			// Draw the field background text.
			gr.setColor(FIELD_FONT_COLOR);
			gr.setFont(FIELD_FONT);
			Util.drawString(gr, "Ball\nPopper", 
					GAME_PAD_LEFT, 
					GAME_PAD_TOP, 
					GAME_WIDTH - GAME_PAD_RIGHT, 
					GAME_HEIGHT - GAME_PAD_BOTTOM, true);

			// Draws the score and the high scores board.
			String board = score + "\n\n" + buildHighScores();

			gr.setColor(SCORE_FONT_COLOR);
			gr.setFont(SCORE_FONT);
			Util.drawString(gr, board, 0, GAME_PAD_TOP, GAME_PAD_LEFT, GAME_HEIGHT, true);
			
			// Draws the field of balls.
			setupMathCoordinateSystem();
			field.draw(gr);
			setupDefaultCoordinateSystem();
		}
	}

	/**
	 * Updates the field and it's balls.
	 */
	@Override
	public void update(float deltatime) 
	{
		synchronized (this) 
		{
			field.update(deltatime);
		}
	}

	
	/**
	 * Tries to remove balls from the field when the mouse is clicked on the
	 * screen.
	 */
	public void mousePressed(MouseEvent e) 
	{
		// Try to remove balls from the field.
		int removed = field.removeBalls(e.getX(), GAME_HEIGHT - e.getY());

		if (removed == 0)
			return;

		// Play the ball remove sound effect.
		BALL_REMOVE.play();

		// Increase the score by the number of balls removed squared scaled
		// by the total range of ball colors on the field.
		score += (removed * removed) * BALL_COLORS;

		// If the field is now empty the player has succeeded so update the
		// high scores and refill the field.
		if (field.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "You WON!!!");
			updateHighScores(score);
			score = 0;
			field.fill();
		}
		// If the field is stuck and no more balls can be removed then
		// let the player no they lost and refill the field.
		else if (field.isStuck())
		{
			JOptionPane.showMessageDialog(this, "You Lost...");
			score = 0;
			field.fill();
		}
	}

	/**
	 * Given a score this method updates the high score list shifting
	 * scores or removing them as necessary.
	 * 
	 * @param score => The score to try to add.
	 */
	public void updateHighScores(long score)
	{
		for (int i = 0; i < HIGH_SCORES; i++)
		{
			if (highScores[i] < score)
			{
				for (int j = HIGH_SCORES - 1; j > i; j--)
					highScores[j] = highScores[j - 1];
				highScores[i] = score;
				break;
			}
		}
	}

	/**
	 * Builds the high scores as a line-delimited string.
	 */
	public String buildHighScores()
	{
		StringBuilder sb = new StringBuilder(128);

		for (int i = 0; i < HIGH_SCORES; i++)
			sb.append(highScores[i]).append('\n');

		return sb.toString();
	}

	// We don't care about these methods.
	public void mouseClicked(MouseEvent e) { }
	public void mouseDragged(MouseEvent e) { }
	public void mouseMoved(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }

}
