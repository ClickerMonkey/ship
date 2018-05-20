package net.philsprojects.data.var;

import net.philsprojects.data.AbstractData;
import net.philsprojects.data.Var;

/**
 * An abstract implementation of Var.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 */
public abstract class AbstractVar<E> extends AbstractData implements Var<E>
{

	/**
	 * Instantiates a new AbstractVar.
	 * 
	 * @param size
	 * 		The fixed size of the Var in bytes.
	 */
	public AbstractVar(int size) 
	{
		super(size);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public E takeValue() 
	{
		read();
		return getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean putValue(E newValue) 
	{
		setValue(newValue);
		write();
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) 
	{
		if (o instanceof Var<?>) {
			return getValue().equals(((Var<?>)o).getValue());
		}
		return getValue().equals(o);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() 
	{
		E value = getValue();
		if (value != null) {
			return value.hashCode();
		}
		return super.hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() 
	{
		E value = getValue();
		if (value != null) {
			return value.toString();
		}
		return super.toString();
	}

}
