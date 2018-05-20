package net.philsprojects.data.var;

import net.philsprojects.data.Bits;
import net.philsprojects.data.Data;
import net.philsprojects.data.Store;

/**
 * A Var with a boolean value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class BooleanVar extends AbstractVar<Boolean> 
{
	
	// The size of the var in bytes.
	public static final int SIZE = 1;

	// The value of the var.
	private boolean value;

	/**
	 * Instantiates a new BooleanVar. 
	 */
	public BooleanVar() 
	{
		this(null, 0, false);
	}

	/**
	 * Instantiates a new BooleanVar.
	 *  
	 * @param value
	 * 		The initial value.
	 */
	public BooleanVar(boolean value) 
	{
		this(null, 0, value);
	}
	
	/**
	 * Instantiates a new BooleanVar.
	 * 
	 * @param store
	 * 		The intial store.
	 * @param location
	 * 		The intial location.
	 */
	public BooleanVar(Store store, int location) 
	{
		this(store, location, false);
	}
	
	/**
	 * Instantiates a new BooleanVar.
	 * 
	 * @param store
	 * 		The initial store.
	 * @param location
	 * 		The initial location.
	 * @param value
	 * 		The initial value.
	 */
	public BooleanVar(Store store, int location, boolean value) 
	{
		super(SIZE);
		this.setStore(store);
		this.setLocation(location);
		this.set(value);
	}
	
	/**
	 * Returns the current value of the var.
	 * 
	 * @return
	 * 		The current value.
	 */
	public boolean get() 
	{
		return value;
	}
	
	/**
	 * Sets the value of this var, but does not write it to the store.
	 * 
	 * @param value
	 * 		The new value.
	 */
	public void set(boolean value) 
	{
		this.value = value;
	}
	
	/**
	 * Sets the value of this var and writes it to the store.
	 * 
	 * @param value
	 * 		The new value.
	 */
	public void put(boolean value) 
	{
		this.value = value;
		this.write();
	}
	
	/**
	 * Returns the value of this var by first reading it from the store.
	 * 
	 * @return
	 * 		The value of this var.
	 */
	public boolean take() 
	{
		this.read();
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getValue() 
	{
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Boolean value) 
	{
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onRead(int location, Store store) 
	{
		value = Bits.getBoolean(store.get(location, SIZE)[0]);
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void onWrite(int location, Store store) 
	{
		store.put(location, new byte[] {Bits.getBooleanBytes(value)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data copy() 
	{
		return new BooleanVar(getStore(), getLocation(), value);
	}

}
