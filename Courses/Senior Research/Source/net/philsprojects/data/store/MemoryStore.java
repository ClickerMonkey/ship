package net.philsprojects.data.store;

import java.io.IOException;
import java.nio.ByteBuffer;

import net.philsprojects.data.Bits;
import net.philsprojects.data.StoreAccess;

/**
 * A store which is kept entirely in memory and is not persisted to any medium.
 * 
 * @author Philip Diffenderfer
 *
 */
public class MemoryStore extends AbstractStore
{
	
	// The initial capacity of the store. Also updated when the store is resized.
	private int capacity;
	
	// The buffer which holds all of the stores data.
	private ByteBuffer buffer;

	
	/**
	 * Instantiates a MemoryStore.
	 * 
	 * @param name
	 * 		The unique name of the Store.
	 */
	public MemoryStore(String name) 
	{
		this(name, 0);
	}
	
	/**
	 * Instantiates a MemoryStore.
	 *  
	 * @param name
	 * 		The unique name of the Store.
	 * @param capacity
	 * 		The requested capacity of the store.
	 */
	public MemoryStore(String name, int capacity) 
	{
		super(name);
		this.capacity = capacity;
	}
	
	/**
	 * Instantiates a MemoryStore.
	 *  
	 * @param name
	 * 		The unique name of the Store.
	 * @param access
	 * 		The requested access to the store.
	 * @param capacity
	 * 		The requested capacity of the store.
	 */
	public MemoryStore(String name, StoreAccess access, int capacity) 
	{
		super(name);
		this.capacity = capacity;
		this.create(access, capacity);
	}

	/**
	 * Returns the buffer which contains all this stores data.
	 * 
	 * @return
	 * 		The reference to the underlying buffer.
	 */
	public ByteBuffer getBuffer() 
	{
		return buffer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int storeOpen(StoreAccess access) throws IOException 
	{
		return storeResize(capacity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storeLoad() throws IOException 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storeFlush() throws IOException 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storeClose() throws IOException 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storeDelete() 
	{
		Bits.free(buffer);
		buffer = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean storeExists() 
	{
		return (buffer != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int storeResize(int bytes) 
	{
		if (buffer == null) {
			buffer = ByteBuffer.allocateDirect(bytes);
		}
		else {
			buffer.position(0);
			buffer.limit(Math.min(buffer.capacity(), bytes));
			
			ByteBuffer newBuffer = ByteBuffer.allocateDirect(bytes);
			newBuffer.put(buffer);
			newBuffer.clear();
			
			Bits.free(buffer);
			
			buffer = newBuffer;
		}
		capacity = buffer.capacity();
		return capacity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storeGet(int location, byte[] bytes, int offset, int length) throws IOException 
	{
		buffer.position(location);
		buffer.get(bytes, offset, length);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storePut(int location, byte[] bytes, int offset, int length) throws IOException 
	{
		buffer.position(location);
		buffer.put(bytes, offset, length);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storeGet(int location, ByteBuffer b) throws IOException 
	{
		buffer.position(location);
		buffer.limit(location + b.remaining());
		b.put(buffer);
		buffer.limit(buffer.capacity());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storePut(int location, ByteBuffer b) throws IOException 
	{
		buffer.position(location);
		buffer.put(b);
	}

}
