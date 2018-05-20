package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with an integer value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyInt extends AbstractProperty<Integer> 
{

	// The current value of the property.
	private int value;
	
	
	/**
	 * Instantiates a new PropertyInt.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyInt(String name, boolean immediate) 
	{
		super(name, immediate);
	}

	/**
	 * Instantiates a new PropertyInt.
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
	public PropertyInt(String name, boolean immediate, PropertyValidator<Integer> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(Integer newValue) 
	{
		if (newValue != null) {
			value = newValue.intValue();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getValue() 
	{
		return value;
	}
	
	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public int get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(int newValue) 
	{
		setValue(newValue);
	}

}
