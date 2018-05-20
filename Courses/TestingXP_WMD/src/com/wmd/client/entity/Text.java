package com.wmd.client.entity;


/**
 * This class can instantiate a Text entity and parse it to and from XML
 * elements
 */
public class Text implements Entity
{

	private String text;

	/**
	 * Empty constructor needed for serializability.
	 */
	public Text()
	{
		// Empty
	}

	/**
	 * Constructor that creates a text object from a string.
	 * 
	 * @param text
	 *            - String to store in text object
	 */
	public Text(String text)
	{
		this.text = text;
	}

	/**
	 * Gets the text.
	 * 
	 * @return - String with text
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text
	 *            - String to set text value to
	 */
	public void setText(String text)
	{
		this.text = text;
	}

}
