package net.philsprojects.live;

/**
 * A listener invoked when a strategy update has occurred. 
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 * 		The strategy type. This is typically an interface and each strategy
 * 		holds its own implementation.
 */
public interface StrategyListener<S> 
{
	
	/**
	 * Invoked when a strategy has successfully been updated.
	 * 
	 * @param strategy
	 * 		The strategy successfully updated.
	 */
	public void onStrategyUpdate(Strategy<S> strategy);
	
	/**
	 * Invoked when a strategy has thrown an exception while updating.
	 *  
	 * @param strategy
	 * 		The strategy that failed to update.
	 * @param e
	 * 		The exception that was thrown.
	 */
	public void onStrategyError(Strategy<S> strategy, Exception e);
	
}
