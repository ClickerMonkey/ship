package net.philsprojects.live;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.philsprojects.stat.StatGroup;
import net.philsprojects.util.LockRef;
import net.philsprojects.util.Notifier;

/**
 * An abstract implementation of a Strategy.
 * 
 * @author Philip Diffenderfer
 *
 * @param <S>
 */
public abstract class AbstractStrategy<S> implements Strategy<S>
{

	// The name of the strategy unique in its module.
	protected final String name;
	
	// The read-write lock reference to the strategy implementation.
	protected final LockRef<S> ref;
	
	// A map of properties by their name.
	protected final Map<String, Property<?>> propertyMap;
	
	// A list of properties for this strategy.
	protected final List<Property<?>> propertyList;
	
	// The group of statistics for this strategy.
	private StatGroup statistics;
	
	// The parent statistic group
	private StatGroup parent;
	
	// The notifier which contains the listeners and proxy listener.
	protected final Notifier<StrategyListener<S>> notifier;
	
	
	/**
	 * Instantiates a new AbstractStrategy.
	 * 
	 * @param name
	 * 		The name of the strategy.
	 */
	public AbstractStrategy(String name) 
	{
		this.name = name;
		this.ref = new LockRef<S>();
		this.propertyList = new ArrayList<Property<?>>();
		this.propertyMap = new HashMap<String, Property<?>>();
		this.notifier = Notifier.create(StrategyListener.class);
	}

	/**
	 * Requires the strategy to force an update of the changed properties to
	 * the implementation. This will only be invoked if changes to properties
	 * have been made which were not visible immediately to the implementation.
	 * The current implementation is given and the updated implementation must
	 * be returned.
	 * 
	 * @param current
	 * 		The current strategy implementation.
	 * @return
	 * 		The reference to set as the implementation, or null if a problem
	 * 		occurred during update. If an error occurred during update the
	 * 		strategy must account for that itself.
	 */
	protected abstract S onUpdate(S current);
	
	/**
	 * Loads the properties for the strategy. This is only invoked once and
	 * must be invoked as the last line of the constructor of the strategy
	 * implementation by calling the load method.
	 * 
	 * @param props
	 * 		The list to add the properties.
	 */
	protected abstract void onPropertyLoad(List<Property<?>> props);
	
	/**
	 * Loads the properties for the strategy.
	 */
	protected void load()
	{
		if (propertyList.size() == 0) 
		{
			onPropertyLoad(propertyList);
			for (Property<?> prop : propertyList) {
				propertyMap.put(prop.getName(), prop);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public S get() 
	{
		return ref.get();
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
	public LockRef<S> ref() 
	{
		return ref;
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
	public List<Property<?>> getProperties() 
	{
		return propertyList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Property<?>> T getProperty(String name) 
	{
		return (T)propertyMap.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> void addProperty(Property<T> property) 
	{
		propertyMap.put(property.getName(), property);
		propertyList.add(property);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPropertyCount() 
	{
		return propertyList.size();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChanged() 
	{
		// If atleast one of the properties has changed...
		for (Property<?> p : propertyList) {
			if (p.hasChanged()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update() 
	{
		// If the strategy has not been changed, update succeeded.
		if (!hasChanged()) {
			return true;
		}
		
		// Lock the current implementation...
		S current = ref.lock();
		
		try {
			// Update the implementation, getting the next.
			S next = onUpdate(current);

			// If the next was given, set it.
			if (next != null) {
				ref.set(next);
			}
			
			// Update success, reset each property marking as unchanged.
			for (Property<?> p : propertyList) {
				p.reset();
			}
			
			// Notify listeners of success
			notifier.proxy().onStrategyUpdate(this);
			
			// Success
			return true;
		}
		catch (Exception e) {
			// Notify listeners of exception thrown.
			notifier.proxy().onStrategyError(this, e);
			
			// Failure
			return false;
		}
		finally {
			// Release lock on implementation no matter what
			ref.unlock();	
		}
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
	public Notifier<StrategyListener<S>> getListeners()
	{
		return notifier;
	}

}
