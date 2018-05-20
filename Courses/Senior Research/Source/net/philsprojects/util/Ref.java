package net.philsprojects.util;

/**
 * A reference to some element.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The element type.
 */
public interface Ref<E> 
{
	
	/**
	 * Returns the reference to the element. Depending on the implementation
	 * this method may block or cause additional computation, thus is not
	 * gauranteed to return immediately after being invoked.
	 * 
	 * @return
	 * 		The reference to the element.
	 */
	public E get();
	
	/**
	 * 
	 * @param value
	 */
	public void set(E value);
	
}
