package net.philsprojects.live;

/**
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 * 		The strategy type. This is typically an interface and each strategy
 * 		holds its own implementation.
 */
public class StrategyListenerAdapter<S> implements StrategyListener<S> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStrategyError(Strategy<S> strategy, Exception e) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStrategyUpdate(Strategy<S> strategy) 
	{
	}

}
