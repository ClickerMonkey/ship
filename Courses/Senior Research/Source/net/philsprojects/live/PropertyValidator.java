package net.philsprojects.live;

/**
 * Each property has a single validator to determine whether a change in value
 * can occur.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 * 		The type of value stored in the property.
 */
public interface PropertyValidator<T> 
{

	/**
	 * Validates whether the new value is an acceptable change from the current
	 * value. 
	 * 
	 * @param property
	 * 		The property being changed.
	 * @param oldValue
	 * 		The current value of the property.
	 * @param newValue
	 * 		The new value for the property.
	 * @return
	 * 		True if the newValue is valid, otherwise false.
	 */
	public boolean isValidValue(Property<T> property, T oldValue, T newValue);
	
}
