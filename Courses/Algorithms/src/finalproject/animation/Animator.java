package finalproject.animation;

import java.awt.Graphics2D;

import finalproject.Queue;

public class Animator extends Thread 
{

	// The queue of animations.
	private Queue<Anim> animations;
	// The scale of the animation speed, used to speed up or slow animation speed.
	private double speedScale;
	// Whether this animator can continue to animate.
	private boolean enabled;
	// The animation screen running this animator.
	private AnimScreen screen;
	
	/**
	 * Initializes an animator with a maximum number of animations.
	 * 
	 * @param maxAnimations => The max number of animations this can contain.
	 */
	public Animator(AnimScreen parent, int maxAnimations)
	{
		screen = parent;
		animations = new Queue<Anim>(maxAnimations);
		speedScale = 1.0;
		enabled = true;
	}
	
	/**
	 * Adds an animation to run at the end of the queue.
	 */
	public void addAnimation(Anim a)
	{
		animations.offer(a);
	}
	
	/**
	 * Executes all animations one at a time. If the animator is paused by
	 * being disabled then it waits every 10 milliseconds to determine if it
	 * can continue running animations.
	 */
	@Override
	public void run()
	{
		while (animations.hasElements())
		{
			// Wait while its disabled
			while (!enabled)
				wait(10);
			
			// Pop off the next animation and run it.
			animations.poll().run(this);
		}
	}
	
	/**
	 * Pauses the animation screen for so many milliseconds.
	 * 
	 * @param millis => The number of milliseconds to pause.
	 */
	public void wait(int millis)
	{
		try {
			sleep((long)(millis * speedScale));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets whether this animator is enabled to run animations.
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Sets the scale of the animation speed, used to speed up or slow animation speed.
	 */
	public void setSpeedScale(double speedScale)
	{
		this.speedScale = speedScale;
	}
	
	/**
	 * Returns whether this animator is enabled to run animations.
	 */
	public boolean isEnabled()
	{
		return enabled;
	}
	
	/**
	 * The scale of the animation speed, used to speed up or slow animation speed.
	 */
	public double getSpeedScale()
	{
		return speedScale;
	}

	/**
	 * Returns the graphics object associated with the screen animating. 
	 */
	public Graphics2D getGraphics2D()
	{
		return screen.getGraphics2D();
	}
	
	/**
	 * Returns the animation screen this animator draws on (Panel). 
	 */
	public AnimScreen getScreen()
	{
		return screen;
	}
	
}
