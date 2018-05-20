
package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with a short value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyShort extends AbstractProperty<Short> 
{

	// The current value of the property.
	private short value;
	
	/**
	 * Instantiates a new PropertyShort.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyShort(String name, boolean immediate) 
	{
		super(name, immediate);
	}

	/**
	 * Instantiates a new PropertyShort.
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
	public PropertyShort(String name, boolean immediate, PropertyValidator<Short> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(Short newValue) 
	{
		if (newValue != null) {
			value = newValue.shortValue();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Short getValue() 
	{
		return value;
	}

	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public short get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(short newValue) 
	{
		setValue(newValue);
	}

}
