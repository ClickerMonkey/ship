package com.wmd.client.entity;


/**
 * Stores a fraction with a numerator and a denominator.
 * 
 * @author Steve Unger and Chris Eby
 */
public class Fraction implements Entity
{
	// Entity container for numerator
	private EntityContainer numerator;
	// Entity container for denominator
	private EntityContainer denominator;

	/**
	 * Empty constructor needed for serializability.
	 */
	public Fraction()
	{
		// Empty
	}

	/**
	 * Creates a fraction with a numerator and denominator.
	 * 
	 * @param numerator
	 *            - Numerator of fraction
	 * @param denominator
	 *            - Denominator of fraction
	 */
	public Fraction(EntityContainer numerator, EntityContainer denominator)
	{
		setNumerator(numerator);
		setDenominator(denominator);
	}

	/**
	 * Gets the numerator.
	 * 
	 * @return EntityContainer of fraction numerator
	 */
	public EntityContainer getNumerator()
	{
		return this.numerator;
	}

	/**
	 * Sets the numerator.
	 * 
	 * @param numerator
	 *            fraction's numerator
	 */
	public void setNumerator(EntityContainer numerator)
	{
		this.numerator = numerator;
	}

	/**
	 * Gets the denominator.
	 * 
	 * @return EntityContainer of fraction denominator
	 */
	public EntityContainer getDenominator()
	{
		return this.denominator;
	}

	/**
	 * Sets the denominator.
	 * 
	 * @param denominator
	 *            - fraction denominator
	 */
	public void setDenominator(EntityContainer denominator)
	{
		this.denominator = denominator;
	}

}
