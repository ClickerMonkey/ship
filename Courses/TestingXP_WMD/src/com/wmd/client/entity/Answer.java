package com.wmd.client.entity;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Answer object that contains an ArrayList of entities in the answer.
 */
public class Answer extends EntityContainer implements IsSerializable
{
	/**
	 * Empty constructor for serializable compatibility.
	 */
	public Answer()
	{
		// Empty
	}

	/**
	 * Constructs Answer object.
	 * 
	 * @param entities
	 *            entities to place in this object
	 */
	public Answer(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}
}
