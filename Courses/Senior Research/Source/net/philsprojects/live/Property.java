package net.philsprojects.live;

import net.philsprojects.util.Notifier;

/**
 * A property holds a single value used by a strategy implementation. The 
 * strategy can either see property changes immediately or an update to the
 * strategy must be invoked for the implementation to see the changes.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 * 		The type of value stored in the property.
 */
public interface Property<T>
{
	
	/**
	 * The name of this property, unique in its scope. The typical scope of a
	 * property is a strategy, thus all properties in a strategy must be
	 * uniquely named. 
	 * 
	 * @return
	 * 		The reference to the name.
	 */
	public String getName();
	
	/**
	 * Returns the value of this property. If this property is not immediate 
	 * this value may not be in use currently until the strategy is updated.
	 * 
	 * @return
	 * 		The reference to the value.
	 */
	public T getValue();
	
	/**
	 * Sets the value of this property. If this property is immediate the
	 * strategy implementation will see it immediately. If this property is
	 * not immediate, the strategy will be notified of the change and an update 
	 * must be invoked for the value set to be seen by its implementation.
	 * 
	 * @param newValue
	 * 		The new value of this property.
	 * @return
	 * 		The previous value of this property, or null if none existed.
	 */
	public T setValue(T newValue);
	
	/**
	 * Returns true if this property has been changed it requires the strategy 
	 * to update for the change to take effect.   
	 * 
	 * @return
	 * 		True if this property has changed and an update is required to apply
	 * 		the changes, otherwise false.
	 */
	public boolean hasChanged();
	
	/**
	 * Resets this property as unchanged if it is currently not immediate and
	 * has been changed since the last strategy update.
	 */
	public void reset();
	
	/**
	 * Returns whether changes to this property are seen immediately by the
	 * strategy implementation or the strategy must be updated for changes to be
	 * applied.
	 * 
	 * @return
	 * 		True if the strategy implementation sees changes to this property
	 * 		immediately or false if the strategy must be updated for the 
	 * 		property changes to be applied to the implementation.
	 */
	public boolean isImmediate();
	
	/**
	 * Returns the validator of property changes.
	 * 
	 * @return
	 * 		The reference to the property change validator.
	 */
	public PropertyValidator<T> getValidator();

	/**
	 * Sets the property change validator.
	 * 
	 * @param validator
	 * 		The new property validator.
	 */
	public void setValidator(PropertyValidator<T> validator);
	
	/**
	 * Returns the notifier which manages the listeners to changes in the 
	 * property. PropertyListeners can be directly added and removed to the
	 * notifier. Avoid invoking the methods of the proxy object in the notifier
	 * since it may notify the listeners falsely when a change has not actually
	 * occurred with the property. 
	 * 
	 * @return
	 * 		The reference to the PropertyListener notifier.
	 */
	public Notifier<PropertyListener<T>> getListeners();
	
}
