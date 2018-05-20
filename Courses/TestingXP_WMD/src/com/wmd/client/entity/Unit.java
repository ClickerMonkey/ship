package com.wmd.client.entity;

import java.util.ArrayList;

/**
 * This class can instantiate a Unit entity and parse one to and from XML
 * elements.
 */
public class Unit implements Entity
{

	private ArrayList<String> options;
	private String correct;

	/**
	 * Empty constructor needed for serializability.
	 */
	public Unit()
	{
		// Empty
	}

	/**
	 * Constructor that creates a Unit entity
	 * 
	 * @param correct
	 *            String of correct unit
	 * @param options
	 *            ArrayList of Entities containing optional unit choices
	 */
	public Unit(String correct, ArrayList<String> options)
	{
		this.options = options;
		this.correct = correct;
	}

	/**
	 * Sets the correct answer.
	 * 
	 * @param answer
	 *            Correct answer as a String
	 */
	public void setCorrect(String answer)
	{
		this.correct = answer;
	}

	/**
	 * Returns the correct unit.
	 * 
	 * @return Correct answer as a String
	 */
	public String getCorrect()
	{
		return this.correct;
	}

	/**
	 * Returns the number of options.
	 * 
	 * @return Number of options
	 */
	public int getOptionCount()
	{
		return this.options.size();
	}

	/**
	 * Method that allows for an option to be added to the ArrayList of options.
	 * 
	 * @param option
	 *            to be added as a String
	 */
	public void addOption(String option)
	{
		this.options.add(option);
	}

	/**
	 * Method that allows for an unit to removed
	 * 
	 * @param option
	 *            - Option to be removed as a String
	 */
	public void removeOption(Entity option)
	{
		this.options.remove(option);
	}

	/**
	 * Returns the unit option at the given index.
	 * 
	 * @param index
	 *            - where it is held in the array list
	 * @return - Requested unit as a string
	 */
	public String getOption(int index)
	{
		return this.options.get(index);
	}

	/**
	 * Sets the options ArrayList. Must be an ArrayList of stings.
	 * 
	 * @param options
	 *            - Options as an ArrayList<String>
	 */
	public void setOptions(ArrayList<String> options)
	{
		this.options = options;
	}

	/**
	 * Returns the options ArrayList.
	 * 
	 * @return The reference to the options array.
	 */
	public ArrayList<String> getOptions()
	{
		return this.options;
	}

}
