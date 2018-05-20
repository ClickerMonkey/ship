package com.wmd.client.entity;

/**
 * Stores an exponent as an Entity base and an exponent EntityContainer
 * 
 * @author Christopher Eby and Steve Unger
 */
public class Exponent implements Entity
{
	// base of the exponent
	private EntityContainer base;
	// exponent of the exponent
	private EntityContainer exponent;

	/**
	 * Empty constructor for serializability.
	 */
	public Exponent()
	{
		// Empty
	}

	/**
	 * Constructs a new exponent object.
	 * 
	 * @param base
	 *            base of the exponent as an entity
	 * @param exponent
	 *            exponent of the exponent as an entity
	 */
	public Exponent(EntityContainer base, EntityContainer exponent)
	{
		this.setBase(base);
		this.exponent = exponent;
	}

	/**
	 * Sets the exponent entity container.
	 * 
	 * @param exponent
	 *            EntityContainer to set as exponent
	 */
	public void setExponent(EntityContainer exponent)
	{
		this.exponent = exponent;
	}

	/**
	 * Gets the exponent entity container.
	 * 
	 * @return exponent entity container
	 */
	public EntityContainer getExponent()
	{
		return this.exponent;
	}

	/**
	 * Sets the base entity.
	 * 
	 * @param base
	 *            Entity to be the base.
	 */
	public void setBase(EntityContainer base)
	{
		this.base = base;
	}

	/**
	 * Gets the base entity.
	 * 
	 * @return base Entity
	 */
	public EntityContainer getBase()
	{
		return this.base;
	}
}
