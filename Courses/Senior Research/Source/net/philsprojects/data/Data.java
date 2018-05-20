package net.philsprojects.data;

/**
 * A fixed size chunk of data which can be read and written to and from a store.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Data 
{

	/**
	 * Reads the value from the store at the location.
	 */
	public void read();
	
	/**
	 * Reads the value from the store at the location with the given offset.
	 * 
	 * @param offset
	 * 		The offset from the location to read the value.
	 */
	public void read(int offset);
	
	/**
	 * Reads the value from the given store at the location.
	 * 
	 * @param store
	 * 		The store to read from.
	 */
	public void read(Store store);
	
	/**
	 * Reads the value from the given store at the location with the given offset.
	 * 
	 * @param offset
	 * 		The offset from the location to read the value.
	 * @param store
	 * 		The store to read from.
	 */
	public void read(int offset, Store store);
	

	/**
	 * Writes the value to the store at the location.
	 */
	public void write();

	/**
	 * Writes the value to the store at the location with the given offset.
	 * 
	 * @param offset
	 * 		The offset from the location to write the value.
	 */
	public void write(int offset);

	/**
	 * Writes the value to the given store at the location.
	 * 
	 * @param store
	 * 		The store to write to.
	 */
	public void write(Store store);
	
	/**
	 * Writes the value to the given store at the location with the given offset.
	 * 
	 * @param offset
	 * 		The offset from the location to write the value.
	 * @param store
	 * 		The store to write to.
	 */
	public void write(int offset, Store store);
	

	/**
	 * Returns the size of this variable in a store.
	 * 
	 * @return
	 * 		The number of bytes this variable takes up in a store.
	 */
	public int getSize();
	
	/**
	 * Returns the location in bytes of this variable in the store.
	 * 
	 * @return
	 * 		The offset in bytes from the beginning of the store.
	 */
	public int getLocation();
	
	/**
	 * Sets the location in bytes of this variable in the store.
	 * 
	 * @param newLocation
	 * 		The offset in bytes from beginning of the store.
	 */
	public void setLocation(int newLocation);
	
	/**
	 * Returns the store this variable is read and written to and from.
	 * 
	 * @return
	 * 		The reference to the store.
	 */
	public Store getStore();
	
	/**
	 * Sets the store this variable is read and written to and from.
	 * 
	 * @param newStore
	 * 		The new store of this variable.
	 */
	public void setStore(Store newStore);
	
	/**
	 * Creates a clone of this data. The resulting data will be of the same 
	 * type, have the same location, the same store, and have other similar
	 * attributes to this type. 
	 * 
	 * @param <T>
	 * 		The data type.
	 * @return
	 * 		A clone of this data.
	 */
	public Data copy();
	
	/**
	 * Returns the parent Data object if one exists.
	 * 
	 * @return
	 * 		The reference to the parent Data object.
	 */
	public Data getParent();
	
	/**
	 * Sets the parent Data object.
	 * 
	 * @param parent
	 * 		The parent Data object.
	 */
	public void setParent(Data parent);
	
	/**
	 * Returns the actual location of this Data in its store with respect
	 * to the parent if one exists.
	 * 
	 * @return
	 * 		The actual location of the data in bytes.
	 */
	public int getActualLocation();
	
}
