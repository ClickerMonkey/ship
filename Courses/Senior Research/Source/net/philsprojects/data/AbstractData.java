package net.philsprojects.data;

import java.io.IOException;

import net.philsprojects.data.error.StoreClosedException;
import net.philsprojects.data.error.StoreIOException;
import net.philsprojects.data.error.StoreNullException;

/**
 * An abstract implementation of Data.
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class AbstractData implements Data 
{

	// The location of this data in the store, in bytes.
	private int location;
	
	// The store this data can be read and written to and from.
	private Store store;
	
	// The size of this data in bytes.
	private final int size;
	
	// The parent Data object.
	private Data parent;
	
	
	/**
	 * Instantiates a new AbstractData.
	 * 
	 * @param size
	 * 		The size of the data in bytes.
	 */
	public AbstractData(int size) 
	{
		this.size = size;
	}
	
	
	/**
	 * Reads this data from the given store at the given location.
	 * 
	 * @param location
	 * 		The offset in bytes from the beginning of the store to read from.
	 * @param store
	 * 		The store to read from.
	 */
	protected abstract void onRead(int location, Store store);
	
	/**
	 * Writes this data to the given store at the given location.
	 * 
	 * @param location
	 * 		The offset in bytes from the beginning of the store to write to.
	 * @param store
	 * 		The store to write to.
	 */
	protected abstract void onWrite(int location, Store store);

	
	/**
	 * Validates the location and store to write and read from.
	 * 
	 * @param location
	 * 		The offset in the store to check for validity.
	 * @param store
	 * 		The store to check for validity.
	 */
	private void doCheck(int location, Store store) 
	{
		if (store == null) {
			throw new StoreNullException();
		}
		if (store.isClosed()) {
			throw new StoreClosedException();
		}
		if (location < 0 || location + size > store.capacity()) {
			throw new StoreIOException(new IOException("Invalid location: " + location + " and size: " + size));
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setLocation(int newLocation) 
	{
		location = newLocation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getLocation() 
	{
		return location;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getSize() 
	{
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setStore(Store newStore) 
	{
		store = newStore;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Store getStore() 
	{
		return store;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Data getParent()
	{
		return parent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setParent(Data parent)
	{
		this.parent = parent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getActualLocation()
	{
		return getLocation() + (parent == null ? 0 : parent.getLocation());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void read() 
	{
		doCheck(location, store);
		onRead(location, store);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void read(int offset) 
	{
		int absolute = location + offset;
		doCheck(absolute, store);
		onRead(absolute, store);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void read(Store store) 
	{
		doCheck(location, store);
		onRead(location, store);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void read(int offset, Store store) 
	{
		int absolute = location + offset;
		doCheck(absolute, store);
		onRead(absolute, store);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void write() 
	{
		doCheck(location, store);
		onWrite(location, store);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void write(int offset) 
	{
		int absolute = location + offset;
		doCheck(absolute, store);
		onWrite(absolute, store);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void write(Store store) 
	{
		doCheck(location, store);
		onWrite(location, store);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void write(int offset, Store store) 
	{
		int absolute = location + offset;
		doCheck(absolute, store);
		onWrite(absolute, store);
	}

}
