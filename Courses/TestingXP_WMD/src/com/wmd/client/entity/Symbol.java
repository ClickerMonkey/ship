package com.wmd.client.entity;
/**
 * 
 * @author Steve Unger and Chris Eby and Philip Diffenderfer
 * Class for handeling unicode math characters and the
 * functionality
 */
public class Symbol implements Entity
{
	
	/**
	 * The multiplication symbol.
	 */
	public static String MULTIPLICATION = "&times;";
	
	/**
	 * The inline division symbol.
	 */
	public static String DIVISION = "&divide;";
	
	/**
	 * The addition symbol.
	 */
	public static String ADDITION = "+";
	
	/**
	 * The subtraction symbol.
	 */
	public static String SUBTRACTION = "-";
	
	/**
	 * Unicode symbol for math characters
	 */
	private String symbol;
	/**
	 * Empty constructor for the class
	 */
	public Symbol()
	{
		//Empty 
	}
	/**
	 * Constructor for Symbol Class and has a parameter
	 * for String
	 * @param symbol
	 */
	public Symbol(String symbol)
	{
		this.setSymbol(symbol);
	}
	/**
	 * Method that sets the unicode symbol
	 * @param symbol - unicode
	 */
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	/**
	 * Method that gets the math unicode string
	 * @return - unicode string
	 */
	public String getSymbol()
	{
		return symbol;
	}
}
