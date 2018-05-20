package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with a double value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyDouble extends AbstractProperty<Double> 
{

	// The current value of the property.
	private double value;
	
	
	/**
	 * Instantiates a new PropertyDouble.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyDouble(String name, boolean immediate) 
	{
		super(name, immediate);
	}
	
	/**
	 * Instantiates a new PropertyDouble.
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
	public PropertyDouble(String name, boolean immediate, PropertyValidator<Double> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(Double newValue) 
	{
		if (newValue != null) {
			value = newValue.doubleValue();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getValue() 
	{
		return value;
	}

	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public double get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(double newValue) 
	{
		setValue(newValue);
	}

}
