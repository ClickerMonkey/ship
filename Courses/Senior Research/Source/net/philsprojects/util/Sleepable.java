package net.philsprojects.util;

/**
 * Any entity which blocks its thread for some period of time or until some
 * event occurs. 
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Sleepable 
{
	
	/**
	 * This must interrupt and awake any blocking calls in this Sleepable.
	 */
	public void awake();
	
}
