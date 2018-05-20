package net.philsprojects.data;

/**
 * A factory for creating stores given their name and their desired capacity.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface StoreFactory 
{
	
	/**
	 * Creates a store given its name and desired capacity.
	 * 
	 * @param name
	 * 		The name of the store. If the store is persistable this may be the
	 * 		file name to the file to which the store is persisted. If the store
	 * 		is memory based then it is merely a reference.
	 * @param capacity
	 * 		The desired capacity of the store in bytes.
	 * @return
	 * 		The reference to a newly instantiated store with the given name.
	 */
	public Store create(String name, int capacity);
	
	/**
	 * Creates a store given its name.
	 * 
	 * @param name
	 * 		The name of the store. If the store is persistable this may be the
	 * 		file name to the file to which the store is persisted. If the store
	 * 		is memory based then it is merely a reference.
	 * @return
	 * 		The reference to a newly instantiated store with the given name.
	 */
	public Store create(String name);
	
}
