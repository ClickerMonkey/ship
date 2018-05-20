package com.wmd.client.entity;


/**
 * This class can instantiate an Decimal and parse it to and from XML elements.
 * 
 * @author Steven Unger and Chris Eby
 */
public class Decimal implements Entity
{
	// Holds the value for the whole number part of the decimal
	private String whole;
	// Holds the value for the decimal
	private String decimal;

	/**
	 * Empty constructor needed for serializability.
	 */
	public Decimal()
	{
		// Empty
	}

	/**
	 * Constructor method to create a decimal entity
	 * 
	 * @param whole
	 *            - whole number of the decimal
	 * @param decimal
	 *            - decimal
	 */
	public Decimal(String whole, String decimal)
	{
		this.whole = whole;
		this.decimal = decimal;
	}

	/**
	 * Gets the decimal.
	 * 
	 * @return - String decimal
	 */
	public String getDecimal()
	{
		return this.decimal;
	}

	/**
	 * Sets the decimal.
	 * 
	 * @param decimal
	 *            - set the decimal
	 */
	public void setDecimal(String decimal)
	{
		this.decimal = decimal;
	}

	/**
	 * Method that gets the whole decimal part of the decimal
	 * 
	 * @return - whole number value
	 */
	public String getWhole()
	{
		return this.whole;
	}

	/**
	 * Method that sets the whole number part of the decimal
	 * 
	 * @param whole
	 *            - number pass in to be set as the whole number
	 */
	public void setWhole(String whole)
	{
		this.whole = whole;
	}

}
