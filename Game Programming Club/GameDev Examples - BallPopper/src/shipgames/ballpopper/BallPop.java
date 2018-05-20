package shipgames.ballpopper;

import static shipgames.ballpopper.Resources.*;

import java.awt.Graphics2D;

/**
 * An explosion effect that occurs after a ball has been popped.
 * 
 * @author Philip Diffenderfer
 *
 */
public class BallPop
{

	// The time since the last frame was advanced, in seconds.
	private float time;
	
	// The index of the current frame on the tile sheet.
	private int frameIndex = 0;
	
	// The x-coordinate of the current tile frame on the tile sheet.
	private int frameLeft;
	
	// The y-coordinate of the current tile frame on the tile sheet.
	private int frameTop;
	
	// The actual x-coordinate of this explosion on the screen.
	private final int x;
	
	// The actual y-coordinate of this explosion on the screen.
	private final int y;
	
	
	/**
	 * Initializes a pop effect given its position on the screen (as indices).
	 * 
	 * @param indexX => The x index of the ball that popped.
	 * @param indexY => The y index of the ball that popped.
	 */
	public BallPop(int indexX, int indexY)
	{
		this.x = indexX * BALL_SIZE;
		this.y = indexY * BALL_SIZE;
	}
	
	/**
	 * Draws this BallPop on given graphics object.
	 * 
	 * @param gr => The graphics object to draw the BallPop on.
	 */
	public void draw(Graphics2D gr)
	{
		gr.drawImage(POP_TILE, 
				x, y, x + BALL_SIZE, y + BALL_SIZE, 
				frameLeft, frameTop,
				frameLeft + POP_TILE_FRAME_WIDTH, 
				frameTop + POP_TILE_FRAME_HEIGHT, null);
	}
	
	/**
	 * Updates this BallPop.
	 * 
	 * @param deltatime => The time in seconds since the last update.
	 */
	public void update(float deltatime)
	{
		// Update the time on the current frame.
		time += deltatime;
		
		if (time >= POP_INTERVAL) {
			frameIndex++;
			frameLeft = (frameIndex % POP_TILE_COLUMNS) * POP_TILE_FRAME_WIDTH;
			frameTop = (frameIndex / POP_TILE_COLUMNS) * POP_TILE_FRAME_HEIGHT;
			time -= POP_INTERVAL;
		}
	}
	
	/**
	 * Returns whether this BallPop is dead.
	 */
	public boolean isDead()
	{
		return (frameIndex >= POP_TILE_FRAMES);
	}
	
}
