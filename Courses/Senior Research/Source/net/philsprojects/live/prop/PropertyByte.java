
package net.philsprojects.live.prop;

import net.philsprojects.live.AbstractProperty;
import net.philsprojects.live.PropertyValidator;

/**
 * A property with a byte value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyByte extends AbstractProperty<Byte> 
{

	// The current value of the property.
	private byte value;
	
	/**
	 * Instantiates a new PropertyByte.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 */
	public PropertyByte(String name, boolean immediate) 
	{
		super(name, immediate);
	}

	/**
	 * Instantiates a new PropertyByte.
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
	public PropertyByte(String name, boolean immediate, PropertyValidator<Byte> validator) 
	{
		super(name, immediate, validator);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInternalValue(Byte newValue) 
	{
		if (newValue != null) {
			value = newValue.byteValue();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte getValue() 
	{
		return value;
	}

	/**
	 * Returns a non-object reference to this property's value.
	 * 
	 * @return
	 * 		This property's value.
	 */
	public byte get() 
	{
		return value;
	}
	
	/**
	 * Sets the non-object value of this property.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 */
	public void set(byte newValue) 
	{
		setValue(newValue);
	}

}
