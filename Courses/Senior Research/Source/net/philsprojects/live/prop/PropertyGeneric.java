package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with a generic value.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 * 		The generic type.
 */
public class PropertyGeneric<T> extends AbstractProperty<T> 
{

	// The current value of the property.
	private T value;
	
	
	/**
	 * Instantiates a new PropertyGeneric.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyGeneric(String name, boolean immediate)
	{
		super(name, immediate);
	}
	
	/**
	 * Instantiates a new PropertyGeneric.
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
	public PropertyGeneric(String name, boolean immediate, PropertyValidator<T> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(T newValue) 
	{
		value = newValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue() 
	{
		return value;
	}

	
	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public T get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(T newValue) 
	{
		setValue(newValue);
	}
	
}
