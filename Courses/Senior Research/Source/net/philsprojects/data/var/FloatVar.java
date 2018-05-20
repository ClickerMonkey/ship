package net.philsprojects.data.var;

import net.philsprojects.data.Bits;
import net.philsprojects.data.Data;
import net.philsprojects.data.Store;

/**
 * A Var with a float value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class FloatVar extends AbstractVar<Float> 
{
	
	// The size of the var in bytes.
	public static final int SIZE = 4;

	// The value of the var.
	private float value;

	/**
	 * Instantiates a new FloatVar. 
	 */
	public FloatVar() 
	{
		this(null, 0, 0f);
	}

	/**
	 * Instantiates a new FloatVar.
	 *  
	 * @param value
	 * 		The initial value.
	 */
	public FloatVar(float value) 
	{
		this(null, 0, value);
	}
	
	/**
	 * Instantiates a new FloatVar.
	 * 
	 * @param store
	 * 		The intial store.
	 * @param location
	 * 		The intial location.
	 */
	public FloatVar(Store store, int location) 
	{
		this(store, location, 0f);
	}
	
	/**
	 * Instantiates a new FloatVar.
	 * 
	 * @param store
	 * 		The initial store.
	 * @param location
	 * 		The initial location.
	 * @param value
	 * 		The initial value.
	 */
	public FloatVar(Store store, int location, float value) 
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
	public float get() 
	{
		return value;
	}
	
	/**
	 * Adds the given value to this var and returns the result.
	 * 
	 * @param x
	 * 		The value to add to this var.
	 * @return
	 * 		The result of the operation.
	 */
	public float add(float x) 
	{
		return (value += x);
	}
	
	/**
	 * Sets the var to the max between this var and the value.
	 * 
	 * @param x
	 * 		The value to take the max of with this var.
	 * @return
	 * 		The result of the operation.
	 */
	public float max(float x) 
	{
		return (value = Math.max(value, x));
	}

	/**
	 * Sets the var to the min between this var and the value.
	 * 
	 * @param x
	 * 		The value to take the min of with this var.
	 * @return
	 * 		The result of the operation.
	 */
	public float min(float x) 
	{
		return (value = Math.min(value, x));
	}

	/**
	 * Multiplies the given value to this var and returns the result.
	 * 
	 * @param x
	 * 		The value to multiply by this var.
	 * @return
	 * 		The result of the operation.
	 */
	public float mul(float x) 
	{
		return (value *= x);
	}
	
	/**
	 * Sets the value of this var, but does not write it to the store.
	 * 
	 * @param value
	 * 		The new value.
	 */
	public void set(float value) 
	{
		this.value = value;
	}
	
	/**
	 * Sets the value of this var and writes it to the store.
	 * 
	 * @param value
	 * 		The new value.
	 */
	public void put(float value) 
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
	public float take() 
	{
		this.read();
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Float getValue() 
	{
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Float value) 
	{
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onRead(int location, Store store) 
	{
		value = Bits.getFloat(store.get(location, SIZE));
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void onWrite(int location, Store store) 
	{
		store.put(location, Bits.getFloatBytes(value));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data copy() 
	{
		return new FloatVar(getStore(), getLocation(), value);
	}

}
