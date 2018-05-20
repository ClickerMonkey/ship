package com.wmd.client.entity;


/**
 * This class can instantiate an Integer and parse it to and from XML elements.
 */
public class Integer implements Entity
{

	private String integer;

	/**
	 * Empty constructor needed for serializability.
	 */
	public Integer()
	{
		// Empty
	}

	/**
	 * Constructor that creates an integer
	 * 
	 * @param number
	 *            - String of number to be stored
	 */
	public Integer(String number)
	{
		this.integer = number;
	}

	/**
	 * Gets the integer.
	 * 
	 * @return - String integer
	 */
	public String getInteger()
	{
		return this.integer;
	}

	/**
	 * Sets the integer.
	 * 
	 * @param number
	 *            String integer
	 */
	public void setInteger(String number)
	{
		this.integer = number;
	}

}
