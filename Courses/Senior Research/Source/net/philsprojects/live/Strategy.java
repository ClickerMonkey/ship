package net.philsprojects.live;

import java.util.List;

import net.philsprojects.stat.StatGroup;
import net.philsprojects.util.Notifier;
import net.philsprojects.util.Ref;

/**
 * A Strategy holds an implementation to some interface (S) and handles changing
 * properties that can affect the implementation live (immediately) or requires
 * the strategy to update the implementation.
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 * 		The strategy type. This is typically an interface and each strategy
 * 		holds its own implementation.
 */
public interface Strategy<S> extends Ref<S>
{
	
	/**
	 * Returns the name of the strategy. Every strategy should have its own 
	 * unique name.
	 * 
	 * @return
	 * 		The name of this strategy.
	 */
	public String getName();
	
	/**
	 * {@inheritDoc}
	 */
	public S get();
	
	/**
	 * Returns the reference of the implementation of this strategy. This can
	 * be used as a lock to the implementation if manual swapping is required.
	 * 
	 * @return
	 * 		The thread-safe reference to the implementation of this strategy.
	 */
	public Ref<S> ref();
	
	/**
	 * Returns a list of changable properties for this Strategy.
	 * 
	 * @return
	 * 		The list of properties for this Strategy.
	 */
	public List<Property<?>> getProperties();

	/**
	 * Returns the property with the given name.
	 * 
	 * @param <T>
	 * 		The property type.
	 * @param name
	 * 		The name of the property (case sensitive).
	 * @return
	 * 		The property with the given name.
	 */
	public <T extends Property<?>> T getProperty(String name);
	
	/**
	 * Adds a property to this strategy. This is called when a property is
	 * instantiated and a strategy is given. This will add the given (newly
	 * instantiated property) to this strategies internal structure.
	 * 
	 * @param <T>
	 * 		The type of value stored in the property.
	 * @param property
	 * 		The property to add to the strategy.
	 */
	public <T> void addProperty(Property<T> property);
	
	/**
	 * Returns the number of properties in this strategy.
	 * 
	 * @return
	 * 		The number of properties in this strategy.
	 */
	public int getPropertyCount();
	
	/**
	 * Returns true if atleast one property has been changed in this strategy
	 * and it requires the strategy to update for the change to take effect. If 
	 * only properties that are immediate are changed this will return false.  
	 * 
	 * @return
	 * 		True if a property has changed and an update is required to apply
	 * 		it, otherwise false.
	 */
	public boolean hasChanged();
	
	/**
	 * Applies the property changes to the strategy's implementation. If changes
	 * have been made to properties that could not change immediately this will
	 * lock the implementation, apply the changes, and unlock the implementation.
	 * 
	 * @return
	 * 		True if the changes applied occurred without error or if no changes
	 * 		needed to made, if an error occurs false will be returned.
	 */
	public boolean update();
	
	/**
	 * Returns the group of statistics for the strategy. This will be a 
	 * directory containing each statistic tracked by the strategy. The 
	 * StatGroup is only created the first time this method is invoked. This
	 * should only be invoked if this strategy is a part of a module, if it
	 * isn't then it will create its own directory and then this Strategy 
	 * should not be added to any module.
	 * 
	 * @return
	 * 		The reference to the strategy's group of statistics.
	 */
	public StatGroup getStatistics();
	
	/**
	 * Sets the parent group of this strategy's statistics. This will have no
	 * affect to this modules statistics if the getStatistics() method has
	 * already been invoked. If this isn't given the parent of the module is
	 * assumed to be root (the current working directory). This method is also
	 * automatically called when this strategy is added to a module.
	 * 
	 * @param parent
	 * 		The parent of this strategy.
	 */
	public void setStatisticsParent(StatGroup parent);
	
	/**
	 * Returns the notifier which manages the listeners to updates in the 
	 * strategy. StrategyListeners can be directly added and removed to the
	 * notifier. Avoid invoking the methods of the proxy object in the notifier
	 * since it may notify the listeners falsely when an update has not actually
	 * occurred with the strategy. 
	 * 
	 * @return
	 * 		The reference to the StrategyListener notifier.
	 */
	public Notifier<StrategyListener<S>> getListeners();
	
}
