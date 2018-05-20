package net.philsprojects.live;

/**
 * TODO
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 * 		The type of value stored in the property.
 */
public interface PropertySync<T> 
{
	
	/**
	 * TODO
	 * 
	 * @return
	 * 		TODO
	 */
	public T getPropertyValue();
	
	/**
	 * 
	 * 
	 * @param value
	 * 		The value to set to the implementation.
	 */
	public void setPropertyValue(T value);
	
}
