package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with a float value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyFloat extends AbstractProperty<Float> 
{

	// The current value of the property.
	private float value;
	
	
	/**
	 * Instantiates a new PropertyFloat.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyFloat(String name, boolean immediate) 
	{
		super(name, immediate);
	}

	/**
	 * Instantiates a new PropertyFloat.
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
	public PropertyFloat(String name, boolean immediate, PropertyValidator<Float> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(Float newValue) 
	{
		if (newValue != null) {
			value = newValue.floatValue();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Float getValue() 
	{
		return value;
	}
	
	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public float get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(float newValue) 
	{
		setValue(newValue);
	}

}
