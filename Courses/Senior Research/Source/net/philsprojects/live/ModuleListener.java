package net.philsprojects.live;

/**
 * A listener invoked when module activity has occurred. The listener is 
 * notified when a strategy swap has occurred, when an error has occurred during
 * a strategy swap, when a strategy has been added, and when a strategy has been
 * removed.
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 * 		The strategy type. This is typically an interface and each strategy
 * 		holds its own implementation.
 */
public interface ModuleListener<S> 
{
	
	/**
	 * Invoked when a module has successfully swapped its active strategy with
	 * another strategy.
	 * 
	 * @param module
	 * 		The module the swap has occurred on.
	 * @param from
	 * 		The previously active strategy.
	 * @param to
	 * 		The new active strategy.
	 */
	public void onStrategySwap(Module<S> module, Strategy<S> from, Strategy<S> to);
	
	/**
	 * Invoked when a module had errors swapping the active strategy.
	 * 
	 * @param module
	 * 		The module the swap failed to occur on.
	 * @param from
	 * 		The active strategy.
	 * @param to
	 * 		The strategy which failed to be swapped.
	 * @param e
	 * 		The exception thrown.
	 */
	public void onStrategyError(Module<S> module, Strategy<S> from, Strategy<S> to, Exception e);
	
	/**
	 * Invoked when a strategy has been added to a module.
	 * 
	 * @param module
	 * 		The module the strategy has been added to.
	 * @param added
	 * 		The strategy added to the module.
	 */
	public void onStrategyAdd(Module<S> module, Strategy<S> added);
	
	/**
	 * Invoked when a strategy has been remove from a module.
	 * 
	 * @param module
	 * 		The module the strategy has been removed from.
	 * @param removed
	 * 		The strategy removed from the module.
	 */
	public void onStrategyRemove(Module<S> module, Strategy<S> removed);
	
}
