package net.philsprojects.live;

/**
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 * 		The strategy type. This is typically an interface and each strategy
 * 		holds its own implementation.
 */
public class ModuleListenerAdapter<S> implements ModuleListener<S> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStrategyAdd(Module<S> module, Strategy<S> added) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStrategyError(Module<S> module, Strategy<S> from, Strategy<S> to, Exception e) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStrategyRemove(Module<S> module, Strategy<S> removed) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStrategySwap(Module<S> module, Strategy<S> from, Strategy<S> to) 
	{
	}

}
