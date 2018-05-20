package net.philsprojects.live;

import net.philsprojects.util.Notifier;

/**
 * An abstract implementation of a Property.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 * 		The type of value stored in the property.
 */
public abstract class AbstractProperty<T> implements Property<T> 
{

	// Whether this property has changed and requires an update.
	private boolean changed;

	// The validator the validates the value of the property.
	private PropertyValidator<T> validator;
	
	// The unique name of this property in its strategy.
	private final String name;
	
	// The notifier which contains the listeners and proxy listener.
	private final Notifier<PropertyListener<T>> notifier;
	
	// Whether changes to this property are immediately visible to the strategy
	// implementation (true) or the strategy must be updated to apply the 
	// changes made to properties (false).
	private final boolean immediate;

	
	/**
	 * Instantiates a new AbstractProperty.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public AbstractProperty(String name, boolean immediate)
	{
		this(name, immediate, null);
	}

	
	/**
	 * Instantiates a new AbstractProperty.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 * @param validator
	 * 		The validator for this property.
	 */
	public AbstractProperty(String name, boolean immediate, PropertyValidator<T> validator) 
	{
		this.name = name;
		this.immediate = immediate;
		this.validator = validator;
		this.notifier = Notifier.create(PropertyListener.class);
	}
	
	/**
	 * Sets the value of the property.
	 * 
	 * @param newValue
	 * 		The new value of the property. This will not equal the current value
	 * 		and will not be null.
	 */
	protected abstract void setInternalValue(T newValue);
	
	
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
	public boolean isImmediate() 
	{
		return immediate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChanged() 
	{
		return changed;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void reset()
	{
		changed = false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T setValue(T newValue) 
	{
		T oldValue = getValue();
		// Values must not be equal!
		if (oldValue != newValue) 
		{
			// If a validator was not given or the validator deems the new value
			// acceptable, set the internal value, mark as changed, and notify.
			if (validator == null || validator.isValidValue(this, oldValue, newValue)) 
			{
				// Set value internally...
				setInternalValue(newValue);
				
				// Changed is true only if immediate is false.
				changed = !immediate;
				
				// Notify listeners
				notifier.proxy().onPropertyChange(this, oldValue, newValue);
			}
		}
		// The previous value (or current if unchanged).
		return oldValue;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public final void setValidator(PropertyValidator<T> validator)
	{
		this.validator = validator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final PropertyValidator<T> getValidator()
	{
		return validator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Notifier<PropertyListener<T>> getListeners()
	{
		return notifier;
	}
	
}
