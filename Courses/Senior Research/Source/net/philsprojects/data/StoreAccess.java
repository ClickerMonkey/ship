package net.philsprojects.data;

import net.philsprojects.data.error.StoreAccessException;

/**
 * A stores access imposes restrictions on the store and its permanent medium if
 * one exists.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum StoreAccess 
{
	
	/**
	 * A store with only read access. Any attempt to write to the store will
	 * cause a StoreIOException to be thrown.
	 */
	ReadOnly(true, false, false, null),
	
	/**
	 * A store with read and write access. The implementation will not use
	 * any locking mechanism to ensure its the only process using the medium to
	 * which the store may be persisted to.  
	 */
	ReadWrite(true, true, false, ReadOnly),
	
	/**
	 * A store with read and write access. The implementation may also choose to
	 * acquire an exclusive lock on the data if the data is persisted to some 
	 * medium. This will avoid concurrent access to the data by this process and
	 * any other process.
	 */
	Exclusive(true, true, true, ReadWrite);
	
	
	/**
	 * Whether the store can have data read from it.
	 */
	public final boolean canRead;
	
	/**
	 * Whether the store can have data written to it.
	 */
	public final boolean canWrite;
	
	/**
	 * Whether the store should acquire exclisive access to any persisted 
	 * medium.
	 */
	public final boolean canLock;
	
	/**
	 * The next access if this state could not be applied.
	 */
	public final StoreAccess next;
	
	
	/**
	 * Instantiates a new StoreAccess.
	 * 
	 * @param canRead
	 * 		Whether the store can have data read from it.
	 * @param canWrite
	 * 		Whether the store can have data written to it.
	 * @param canLock
	 * 		Whether the store should acquire exclisive access to any persisted 
	 * 		medium.
	 */
	private StoreAccess(boolean canRead, boolean canWrite, boolean canLock, StoreAccess next) 
	{
		this.canRead = canRead;
		this.canWrite = canWrite;
		this.canLock = canLock;
		this.next = next;
	}
	
	/**
	 * Checks if this access can read on the given store, if not an access
	 * exception is thrown.
	 * 
	 * @param store
	 * 		The store to try to read on.
	 */
	public void tryRead(Store store) 
	{
		if (!canRead) {
			throw new StoreAccessException(store, this);
		}
	}
	
	/**
	 * Checks if this access can writen on the given store, if not an access
	 * exception is thrown.
	 * 
	 * @param store
	 * 		The store to try to write on.
	 */
	public void tryWrite(Store store) 
	{
		if (!canWrite) {
			throw new StoreAccessException(store, this);
		}
	}
	
}
