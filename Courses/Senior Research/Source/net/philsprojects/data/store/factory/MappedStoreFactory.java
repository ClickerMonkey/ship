package net.philsprojects.data.store.factory;

import java.io.File;

import net.philsprojects.data.Store;
import net.philsprojects.data.StoreAccess;
import net.philsprojects.data.StoreFactory;
import net.philsprojects.data.store.MappedStore;

/**
 * A factory for creating mapped file stores.
 * 
 * @author Philip Diffenderfer
 *
 */
public class MappedStoreFactory implements StoreFactory 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Store create(String name, int capacity) 
	{
		return new MappedStore(new File(name), StoreAccess.ReadWrite, capacity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Store create(String name) 
	{
		return new MappedStore(new File(name));
	}

}
