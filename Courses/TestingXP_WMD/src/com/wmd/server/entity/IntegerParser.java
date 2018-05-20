package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Integer;

/**
 * A parser for the Integer entity type.
 * 
 * @author Philip Diffenderfer, Steve Unger
 * 
 */
public class IntegerParser implements Parser<Integer>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "integer";

	/**
	 * The attribute name for the value in the Xml element.
	 */
	public static final String ATTR_INT = "int";

	/**
	 * {@inheritDoc}
	 */
	public Integer fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		Integer integer = new Integer();
		integer.setInteger(e.getAttribute(ATTR_INT));
		return integer;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Integer);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Integer entity)
	{
		Element element = owner.createElement(TAG);
		element.setAttribute(ATTR_INT, entity.getInteger());
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		return (e.getTagName().equals(TAG) && e.hasAttribute(ATTR_INT));
	}

}
