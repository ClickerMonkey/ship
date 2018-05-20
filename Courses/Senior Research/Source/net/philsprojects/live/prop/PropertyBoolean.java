package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with a boolean value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyBoolean extends AbstractProperty<Boolean> 
{

	// The current value of the property.
	private boolean value;
	
	
	/**
	 * Instantiates a new PropertyBoolean.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyBoolean(String name, boolean immediate) 
	{
		super(name, immediate);
	}
	
	
	/**
	 * Instantiates a new PropertyBoolean.
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
	public PropertyBoolean(String name, boolean immediate, PropertyValidator<Boolean> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(Boolean newValue) 
	{
		if (newValue != null) {
			value = newValue.booleanValue();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getValue() 
	{
		return value;
	}
	
	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public boolean get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(boolean newValue) 
	{
		setValue(newValue);
	}

}
