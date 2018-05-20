package net.philsprojects.data.error;

import net.philsprojects.data.Store;
import net.philsprojects.data.StoreAccess;

/**
 * A store does not have sufficient access to perform the requested operaiton.
 * 
 * @author Philip Diffenderfer
 *
 */
public class StoreAccessException extends RuntimeException 
{
	
	/**
	 * The generic message for the exception.
	 */
	private final static String MESSAGE = "%s is not sufficient access to perform the requested operation on %s";

	// The store which did not have proper access.
	private final Store store;
	
	// The access which was not sufficient.
	private final StoreAccess access;
	
	
	/**
	 * Instantiates a new StoreAccessException.
	 * 
	 * @param store
	 * 		The store which did not have proper access.
	 * @param access
	 * 		The access which was not sufficient.
	 */
	public StoreAccessException(Store store, StoreAccess access) 
	{
		super(String.format(MESSAGE, access, store));
		this.store = store;
		this.access = access;
	}
	
	/**
	 * The store which did not have proper access.
	 * 
	 * @return
	 * 		The reference to the store.
	 */
	public Store getStore() 
	{
		return store;
	}
	
	/**
	 * The access which was not sufficient.
	 * 
	 * @return
	 * 		The reference to the access.
	 */
	public StoreAccess getAccess() 
	{
		return access;  
	}
	
}
