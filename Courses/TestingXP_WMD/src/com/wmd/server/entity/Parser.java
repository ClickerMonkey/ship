package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;

/**
 * A class that converts to and from a specific entity from Xml.
 * 
 * @author Philip Diffenderfer, Steve Unger
 * 
 * @param <T>
 *            The entity type to parse to and from Xml.
 */
public interface Parser<T extends Entity>
{

	/**
	 * Tries to convert the given element into an Entity. If the element is not
	 * a valid entity type <T> then returns null.
	 * 
	 * @param e
	 *            The elemnt to convert.
	 * @return The entity parsed, or null if doesn't match entity type.
	 */
	public T fromXML(Element e);

	/**
	 * Converts the given entity (if this parser handles the entity) given an
	 * Xml document into an Xml element.
	 * 
	 * @param owner
	 *            The document.
	 * @param entity
	 *            The entity to put into
	 * @return The Xml element created, or null if this parser doesn't handle
	 *         the entity given.
	 */
	public Element toXML(Document owner, T entity);

	/**
	 * Returns true if the given entity is the entity supported by this parser.
	 * 
	 * @param e
	 *            The entity to compare.
	 * @return True if the entity passed matches <T>.
	 */
	public boolean handlesEntity(Entity e);

	/**
	 * Returns true if the given element is the element supported by this
	 * parser.
	 * 
	 * @param e
	 *            The element to check.
	 * @return True if the element passed is valid.
	 */
	public boolean handlesElement(Element e);

}
