package com.wmd.client.entity;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Question class stores an returns an ArrayList of question entities.
 * 
 * @author Chris Eby & Steve Unger
 */
public class Question extends EntityContainer implements IsSerializable
{
	/**
	 * Empty constructor for Serializable compatibility.
	 */
	public Question()
	{
		this.entities = new ArrayList<Entity>();
	}

	/**
	 * Constructs Question object.
	 * 
	 * @param entities
	 *            - entities to store in question.
	 */
	public Question(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}
}
