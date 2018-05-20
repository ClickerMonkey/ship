package net.philsprojects.live.prop;

import net.philsprojects.live.PropertyValidator;

/**
 * A property with a string value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class PropertyString extends PropertyGeneric<String>
{

	/**
	 * Instantiates a new PropertyString.
	 * 
	 * @param name
	 * 		The unique name of this property in its strategy.
	 * @param immediate
	 * 		Whether changes to this property are immediately visible to the 
	 * 		strategy implementation (true) or the strategy must be updated to 
	 * 		apply the changes made to properties (false).
	 * @param strategy
	 * 		The parent strategy to this property.
	 */
	public PropertyString(String name, boolean immediate) 
	{
		super(name, immediate);
	}
	
	/**
	 * Instantiates a new PropertyString.
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
	public PropertyString(String name, boolean immediate, PropertyValidator<String> validator) 
	{
		super(name, immediate, validator);
	}
	
}
