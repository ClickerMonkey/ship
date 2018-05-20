package net.philsprojects.live;

/**
 * A listener invoked when a property change has occurred.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 * 		The type of value stored in the property.
 */
public interface PropertyListener<T> 
{

	/**
	 * Invoked when a property has been changed.
	 *  
	 * @param property
	 * 		The property changed.
	 * @param oldValue
	 * 		The previous value for the property.
	 * @param newValue
	 * 		The next value for the property.
	 */
	public void onPropertyChange(Property<T> property, T oldValue, T newValue);
	
}
