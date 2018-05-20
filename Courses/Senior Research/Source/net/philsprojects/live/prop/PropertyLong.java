package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with a long value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyLong extends AbstractProperty<Long> 
{

	// The current value of the property.
	private long value;
	
	
	/**
	 * Instantiates a new PropertyLong.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyLong(String name, boolean immediate) 
	{
		super(name, immediate);
	}
	
	/**
	 * Instantiates a new PropertyLong.
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
	public PropertyLong(String name, boolean immediate, PropertyValidator<Long> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(Long newValue) 
	{
		if (newValue != null) {
			value = newValue.longValue();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getValue() 
	{
		return value;
	}
	
	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public long get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(long newValue) 
	{
		setValue(newValue);
	}

}
