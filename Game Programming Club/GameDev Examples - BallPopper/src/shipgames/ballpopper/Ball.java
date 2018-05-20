package shipgames.ballpopper;

import static shipgames.ballpopper.Resources.*;

import java.awt.Graphics2D;


/**
 * A Ball is an object on a Ball Field which has an index on the x and y axis
 * as well as its actual (physical) coordinates on the field. If the ball's
 * x and y indices correlate to its actual x and y coordinates then its
 * considered at rest and doesn't need to be updated to move anywhere. When the
 * setX and setY methods are called it forces the ball to move to those new 
 * indices, those indices are considered it's "desired position" on the field.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Ball 
{

	// The distance from the desired position on the field that
	// signals this Ball as resting.
	public static final float EPSILON = 0.0001f;

	// The index on the x-axis of this ball on the field. An index of
	// 0 makes this ball at the bottom row on the screen.
	public int indexX;

	// The index on the y-axis of this ball on the field. An index of
	// 0 makes this ball on the left column on the screen.
	public int indexY;

	// The actual x-coordinate of this ball which delegates where it's drawn.
	public float actualX;

	// The actual y-coordinate of this ball which delegates where it's drawn.
	public float actualY;

	// The last actual x-coordinate of this ball.
	public float lastX;

	// The last actual y-coordinate of this ball.
	public float lastY;

	// Whether or not this ball is resting at its destination position 
	// (normally against the right side of another ball).
	public boolean restingX = true;

	// Whether or not this ball is resting at its destination position 
	// (normally on top of another ball).
	public boolean restingY = true;

	// The type (color) of this ball which defines what is drawn and which
	// surrounding balls are removed simultaneously.
	public BallType type;

	// The time in seconds since this ball was last resting.
	private float time;


	/**
	 * Initializes a Ball given its x and y indices on the field and the
	 * type (color) of this ball.
	 * 
	 * @param indexX => The index on the x-axis of this ball on the field. 
	 * 		An index of 0 makes this ball at the bottom row on the screen.
	 * @param indexY =>  The index on the y-axis of this ball on the field. 
	 *		An index of 0 makes this ball on the left column on the screen.
	 * @param type => The type (color) of this ball which defines what is 
	 * 		drawn and which surrounding balls are removed simultaneously.
	 */
	public Ball(int indexX, int indexY, BallType type) {
		this.indexX = indexX;
		this.indexY = indexY;
		this.type = type;
		this.lastX = this.actualX = indexX * BALL_SIZE;
		this.lastY = this.actualY = indexY * BALL_SIZE;
	}

	/**
	 * Draws the ball on the graphics object based on its type and actual
	 * x and y coordinates in the field.
	 * 
	 * @param gr => The graphics object to draw on.
	 */
	public void draw(Graphics2D gr) {
		type.draw(gr, (int)actualX, (int)actualY);
	}

	/**
	 * Updates the position of the ball if its not in a resting state.
	 * 
	 * @param deltatime => The time in seconds since the last update.
	 */
	public void update(float deltatime)
	{
		// If the ball is currently resting then don't move it.
		if ((restingX && restingY) || deltatime == 0f) {
			return;
		}

		// Update the time...
		time += deltatime;

		// If the ball is not at its resting position on the x-axis...
		if (!restingX) {
			// Normalized time from previous position to target position
			float leftDelta = time / BALL_LEFT_DURATION;
			// Based on the actual motion compute the new delta
			float deltaX = BALL_MOTION_LEFT.getDelta(leftDelta);

			// The target ball x-coordinate
			int desiredX = indexX * BALL_SIZE;

			// The current ball x-coordinate
			actualX = (desiredX - lastX) * deltaX + lastX;

			// If the delta went beyond 1 then the ball is now resting on x-axis
			if (leftDelta >= 1f) {
				restingX = true;
				actualX = desiredX;
				lastX = actualX;
			}
		}

		// If the ball is not at its resting position on the y-axis...
		if (!restingY) {
			// Normalized time from previous position to target position
			float downDelta = time / BALL_DOWN_DURATION;
			// Based on the actual motion compute the new delta
			float deltaY = BALL_MOTION_DOWN.getDelta(downDelta);

			// The target ball y-coordinate
			int desiredY = indexY * BALL_SIZE;

			// The current ball y-coordinate
			actualY = (desiredY - lastY) * deltaY + lastY;

			// If the delta went beyond 1 then the ball is now resting on y-axis
			if (downDelta >= 1f) {
				restingY = true;
				actualY = desiredY;
				lastY = actualY;
			}
		}

		// If the ball is at a complete rest then reset the time.
		if (restingX && restingY) {
			time = 0f;
		}
	}

	/**
	 * Sets the y index of this ball on the field. This forces the ball out of
	 * its resting state if its in one and causes it to update its position 
	 * until it's at its desired position.
	 * 
	 * @param y => The new index along the y-axis of this ball.
	 */
	public void setY(int y) {
		if (y != indexY) {
			indexY = y;
			restingY = false;
		}
	}

	/**
	 * Sets the x index of this ball on the field. This forces the ball out of
	 * its resting state (if its in one) and causes it to update its position
	 * until it's at its desired position.
	 * 
	 * @param x => The new index along the x-axis of this ball.
	 */
	public void setX(int x) {
		if (x != indexX) {
			indexX = x;
			restingX = false;
		}
	}

	/**
	 * Returns whether this ball is at rest. This ball is at rest if the ball's
	 * x and y indices correlate to its actual x and y coordinates on the field.
	 */
	public boolean isResting() {
		return (restingX && restingY);
	}

}
