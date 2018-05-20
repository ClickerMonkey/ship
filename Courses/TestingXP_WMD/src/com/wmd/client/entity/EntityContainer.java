package com.wmd.client.entity;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class holds a group of entities
 * 
 * @author Christopher Eby & Steve Unger
 * 
 */
public class EntityContainer implements Entity, IsSerializable
{
	// List of entities in this container
	protected ArrayList<Entity> entities;

	/**
	 * Constructor for serializable compatibility.
	 */
	public EntityContainer()
	{
		this.entities = new ArrayList<Entity>();
	}

	/**
	 * Constructs EntityContainer object.
	 * 
	 * @param entity
	 *            entity to place in this object
	 */
	public EntityContainer(Entity entity)
	{
		this.entities = new ArrayList<Entity>();
		this.entities.add(entity);
	}

	/**
	 * Constructs EntityContainer object.
	 * 
	 * @param entities
	 *            entity to place in this object
	 */
	public EntityContainer(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}

	/**
	 * Gets the entities at the given index from an EntityContainer.
	 * 
	 * @param index
	 * 
	 * @return Entity entity requested
	 */
	public Entity getEntity(int index)
	{
		return this.entities.get(index);
	}

	/**
	 * Adds an entity in the question class.
	 * 
	 * @param entity
	 *            entity to add.
	 */
	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}

	/**
	 * Gets an array list of entities from the container
	 * 
	 * @return - array list of all entities
	 */
	public ArrayList<Entity> getEntities()
	{
		return this.entities;
	}

	/**
	 * Sets an array list of entities to an entity container
	 * 
	 * @param entities
	 *            - array list of entities
	 */
	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entities == null) ? 0 : entities.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityContainer other = (EntityContainer) obj;
		if (entities == null)
		{
			if (other.entities != null)
				return false;
		} else
		{
			for (Entity e : entities)
			{
				if (!other.entities.contains(e))
					return false;
			}
			for (Entity e : other.entities)
			{
				if (!entities.contains(e))
					return false;
			}
		}

		return true;
	}

}
