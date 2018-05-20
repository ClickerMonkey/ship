package net.philsprojects.data.var;

import net.philsprojects.data.Bits;
import net.philsprojects.data.Data;
import net.philsprojects.data.Store;

/**
 * A Var with a double value.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DoubleVar extends AbstractVar<Double> 
{
	
	// The size of the var in bytes.
	public static final int SIZE = 8;

	// The value of the var.
	private double value;

	/**
	 * Instantiates a new DoubleVar. 
	 */
	public DoubleVar() 
	{
		this(null, 0, 0.0);
	}

	/**
	 * Instantiates a new DoubleVar.
	 *  
	 * @param value
	 * 		The initial value.
	 */
	public DoubleVar(double value) 
	{
		this(null, 0, value);
	}
	
	/**
	 * Instantiates a new DoubleVar.
	 * 
	 * @param store
	 * 		The intial store.
	 * @param location
	 * 		The intial location.
	 */
	public DoubleVar(Store store, int location) 
	{
		this(store, location, 0.0);
	}
	
	/**
	 * Instantiates a new DoubleVar.
	 * 
	 * @param store
	 * 		The initial store.
	 * @param location
	 * 		The initial location.
	 * @param value
	 * 		The initial value.
	 */
	public DoubleVar(Store store, int location, double value) 
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
	public double get() 
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
	public double add(double x) 
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
	public double max(double x) 
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
	public double min(double x) 
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
	public double mul(double x) 
	{
		return (value *= x);
	}
	
	/**
	 * Sets the value of this var, but does not write it to the store.
	 * 
	 * @param value
	 * 		The new value.
	 */
	public void set(double value) 
	{
		this.value = value;
	}
	
	/**
	 * Sets the value of this var and writes it to the store.
	 * 
	 * @param value
	 * 		The new value.
	 */
	public void put(double value) 
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
	public double take() 
	{
		this.read();
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getValue() 
	{
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Double value) 
	{
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onRead(int location, Store store) 
	{
		value = Bits.getDouble(store.get(location, SIZE));
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void onWrite(int location, Store store) 
	{
		store.put(location, Bits.getDoubleBytes(value));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data copy() 
	{
		return new DoubleVar(getStore(), getLocation(), value);
	}

}
