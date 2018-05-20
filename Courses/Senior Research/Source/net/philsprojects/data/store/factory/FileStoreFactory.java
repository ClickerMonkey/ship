package net.philsprojects.data.store.factory;

import java.io.File;

import net.philsprojects.data.Store;
import net.philsprojects.data.StoreAccess;
import net.philsprojects.data.StoreFactory;
import net.philsprojects.data.store.FileStore;

/**
 * A factory for creating file stores.
 * 
 * @author Philip Diffenderfer
 *
 */
public class FileStoreFactory implements StoreFactory 
{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Store create(String name, int capacity) 
	{
		return new FileStore(new File(name), StoreAccess.ReadWrite, capacity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Store create(String name) 
	{
		return new FileStore(new File(name));
	}

}
