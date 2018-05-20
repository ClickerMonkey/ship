package finalproject.animation;

/**
 * A single animation that is ran, then disposed of.
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class Anim
{

	/**
	 * Runs the animation given the animator thats executing it.
	 */
	public abstract void run(Animator a);
	
}
