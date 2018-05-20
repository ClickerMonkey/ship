package net.philsprojects.data.store.factory;


import net.philsprojects.data.Store;
import net.philsprojects.data.StoreFactory;
import net.philsprojects.data.store.MemoryStore;

/**
 * A factory for creating memory stores.
 * 
 * @author Philip Diffenderfer
 *
 */
public class MemoryStoreFactory implements StoreFactory 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Store create(String name, int capacity) 
	{
		return new MemoryStore(name, capacity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Store create(String name) 
	{
		return new MemoryStore(name);
	}

}
