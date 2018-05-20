package com.wmd.client.entity;
/**
 * 
 * @author Steve Unger, Chris Eby
 * Class that instantiates the square root entity and all
 * its functionality
 */
public class SquareRoot implements Entity
{
	/**
	 * Variable to hold to the enitities for the root
	 */
	private EntityContainer root;
	/**
	 * Empty Constructor for serializability
	 */
	public SquareRoot()
	{
		//empty constructor
	}
	/**
	 * Constructor for SqaureRoot class and passes an entity container
	 * which holds the enitities in the root
	 * @param root - entity container
	 */
	public SquareRoot(EntityContainer root)
	{
		this.setRoot(root);
	}
	/**
	 * Method that sets the root
	 * @param root -  entity container
	 */
	public void setRoot(EntityContainer root) 
	{
		this.root = root;
	}
	/**
	 * Method that gets the entity container for the root
	 * @return - entity container
	 */
	public EntityContainer getRoot() 
	{
		return root;
	}
}
