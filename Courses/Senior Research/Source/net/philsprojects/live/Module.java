package net.philsprojects.live;

import java.util.List;

import net.philsprojects.stat.StatGroup;
import net.philsprojects.util.Notifier;
import net.philsprojects.util.Ref;

/**
 * A module is a reference to some strategy implementation where the module
 * can hold many strategies and performing swapping of strategies to active.
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 * 		The strategy type. This is typically an interface and each strategy
 * 		holds its own implementation.
 */
public interface Module<S> extends Ref<S>
{
	
	/**
	 * Returns the unique name of this module.
	 * 
	 * @return
	 * 		The name of this module.
	 */
	public String getName();
	
	/**
	 * {@inheritDoc}
	 */
	public S get();
	
	/**
	 * Returns the reference to the strategy implementation.
	 * 
	 * @return
	 * 		The reference to the strategy implementation.
	 */
	public Ref<S> ref();
	
	/**
	 * Returns the active strategy. This may block if the active strategy is
	 * being swapped out for another strategy.
	 * 
	 * @return
	 * 		The reference to the active strategy.
	 */
	public Strategy<S> getActive();

	/**
	 * Sets the active strategy of this module. If a strategy exists with the
	 * given name this will lock the active strategy reference until the
	 * previous active strategy has been completely swapped out.
	 * 
	 * @param name
	 * 		The name of the strategy to set as the active.
	 * @return
	 * 		True if setting the given strategy as the active one was a success. 
	 */
	public boolean setActive(String name);
	
	/**
	 * Sets the active strategy of this module. The given strategy does not have
	 * to exist in this module. This will lock the active strategy reference
	 * until the previous active strategy has been completely swapped out.
	 * 
	 * @param newActive
	 * 		The strategy to set as active. 
	 * @return
	 * 		True if setting the given strategy as the active one was a success.
	 */
	public boolean setActive(Strategy<S> newActive);
	
	
	/**
	 * Returns a list of all strategies that exist in this module.
	 * 
	 * @return
	 * 		The reference to the list of strategies.
	 */
	public List<Strategy<S>> getStrategies();

	/**
	 * Returns the number of strategies that have been added to the module.
	 * 
	 * @return
	 * 		The number of strategies in the module.		
	 */
	public int getStrategyCount();
	
	/**
	 * Adds the strategy to this module. If a strategy with the name of the
	 * given strategy already exists this will return false.
	 * 
	 * @param newStrategy
	 * 		The strategy to add.
	 * @return
	 * 		True if the strategy was added to the module, otherwise false.
	 */
	public boolean addStrategy(Strategy<S> newStrategy);
	
	/**
	 * Returns the strategy in this module with the given name.
	 * 
	 * @param name
	 * 		The name of the strategy.
	 * @return
	 * 		The strategy with the given name, or null if none existed.
	 */
	public Strategy<S> getStrategy(String name);
	
	/**
	 * Removes and returns the strategy from this module with the given name.
	 * 
	 * @param name
	 * 		The name of the strategy.
	 * @return
	 * 		The strategy removed, or null if none existed with the given name.
	 */
	public Strategy<S> removeStrategy(String name);

	/**
	 * Returns the group of statistics that exists for this module. The group
	 * returned will typically contain more directories for each of the
	 * strategies in the module. The StatGroup is only created the first time 
	 * this method is invoked. This method will be automatically invoked when
	 * a strategy is added to this module, so this modules parent should be set
	 * before strategys are added.
	 * 
	 * @return
	 * 		The reference to the statistics group for this module.
	 */
	public StatGroup getStatistics();
	
	/**
	 * Sets the parent group of this module's statistics. This will have no
	 * affect to this modules statistics if the getStatistics() method has
	 * already been invoked. If this isn't given the parent of the module is
	 * assumed to be root (the current working directory). This should also be
	 * set before any strategies are added to the module because all strategies
	 * of the module exist in this modules group.
	 * 
	 * @param parent
	 * 		The parent of this module.
	 */
	public void setStatisticsParent(StatGroup parent);
	
	/**
	 * Returns the notifier which manages the listeners to updates in the 
	 * module. ModuleListeners can be directly added and removed to the
	 * notifier. Avoid invoking the methods of the proxy object in the notifier
	 * since it may notify the listeners falsely when an event has not actually
	 * occurred with the module. 
	 * 
	 * @return
	 * 		The reference to the ModuleListener notifier.
	 */
	public Notifier<ModuleListener<S>> getListeners();
	
}
