package net.philsprojects.live;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.philsprojects.stat.StatGroup;
import net.philsprojects.util.LockRef;
import net.philsprojects.util.Notifier;
import net.philsprojects.util.Ref;

/**
 * An abstract implementation of a module with only strategy swapping logic
 * unimplemented.
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 * 		The strategy type. This is typically an interface and each strategy
 * 		holds its own implementation.
 */
public abstract class AbstractModule<S> implements Module<S> 
{

	// The active strategy in the module.
	private final LockRef<Strategy<S>> active;
	
	// The name of the module.
	protected final String name;
	
	// The map of strategies to their name.
	private final Map<String, Strategy<S>> strategyMap;
	
	// The list of strategies in this module.
	private final List<Strategy<S>> strategyList;
	
	// The group of statistics for the module.
	private StatGroup statistics;
	
	// The parent statistic group
	private StatGroup parent;
	
	// The notifier which contains the listeners and proxy listener.
	protected final Notifier<ModuleListener<S>> notifier;
	
	
	/**
	 * Instantiates an AbstractModule given a unique name of the module.
	 * 
	 * @param name
	 * 		The name of the module.
	 */
	public AbstractModule(String name) 
	{
		this.name = name;
		this.active = new LockRef<Strategy<S>>();
		this.strategyMap = new HashMap<String, Strategy<S>>();
		this.strategyList = new ArrayList<Strategy<S>>();
		this.notifier = Notifier.create(ModuleListener.class);
	}

	
	/**
	 * Handles the swapping from one strategy to another.
	 * 
	 * @param current
	 * 		The current strategy.
	 * @param next
	 * 		The strategy to swap to.
	 * @return
	 * 		True if the swap was successful.
	 */
	protected abstract boolean onSwap(Strategy<S> current, Strategy<S> next);
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public S get() 
	{
		return getActive().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(S value)
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ref<S> ref() 
	{
		return getActive().ref();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Strategy<S> getActive() 
	{
		return active.get();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() 
	{
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addStrategy(Strategy<S> newStrategy) 
	{
		// If the strategy given is null, return false.
		if (newStrategy == null) {
			return false;
		}
		
		strategyMap.put(newStrategy.getName(), newStrategy);
		strategyList.add(newStrategy);
		
		// Set the parent of the strategy to this module's group.
		newStrategy.setStatisticsParent(getStatistics());
		
		// Notify listeners that the strategy has been added.
		notifier.proxy().onStrategyAdd(this, newStrategy);
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Strategy<S>> getStrategies() 
	{
		return strategyList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Strategy<S> getStrategy(String name) 
	{
		return strategyMap.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getStrategyCount() 
	{
		return strategyList.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Strategy<S> removeStrategy(String name) 
	{
		Strategy<S> removed = strategyMap.remove(name);
		
		// Only can remove from list if it exists in the module.
		if (removed != null) 
		{
			strategyList.remove(removed);
			
			// Notify the listeners that the strategy has been removed
			notifier.proxy().onStrategyRemove(this, removed);
		}
		
		return removed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setActive(String name) 
	{
		return setActive(strategyMap.get(name));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setActive(Strategy<S> newActive) 
	{
		// Lock and get the current strategy.
		Strategy<S> old = active.lock();
		
		// If its the same strategy, unlock the reference and return success.
		if (old == newActive) {
			active.unlock();
			return true;
		}
		
		try {
			// newActive must not be null, and only update active if active is null 
			// or all data from active was successfully transfered to newActive.
			if (newActive != null && (old == null || onSwap(old, newActive))) 
			{
				active.set(newActive);
			}			
			
			// Notify listeners of successful swap.
			notifier.proxy().onStrategySwap(this, old, newActive);
		}
		catch (Exception e) {
			// Notify listeners of an error during swap.
			notifier.proxy().onStrategyError(this, old, newActive, e);
		}
		finally {
			// Unlock the active strategy.
			active.unlock();	
		}
		
		// True if strategies have been swapped.
		return (old != active);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatGroup getStatistics()
	{
		if (statistics == null) {
			if (parent == null) {
				parent = StatGroup.getRoot();
			}
			statistics = parent.getChild(name);
		}
		return statistics;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override 
	public void setStatisticsParent(StatGroup parent)
	{
		this.parent = parent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Notifier<ModuleListener<S>> getListeners()
	{
		return notifier;
	}

}
