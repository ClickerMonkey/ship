package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Decimal;
import com.wmd.client.entity.Entity;

/**
 * A parser for the Decimal entity type.
 * 
 * @author Steve Unger, Chris Eby
 * 
 */
public class DecimalParser implements Parser<Decimal>
{
	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "decimal";
	/**
	 * The attribute name for the value in the Xml element.
	 */
	public static final String ATTR_WHOLE = "whole";
	/**
	 * The attribute name for the value in the Xml element.
	 */
	public static final String ATTR_DEC = "dec";

	/**
	 * {@inheritDoc}
	 */
	public Decimal fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		Decimal decimal = new Decimal();
		decimal.setDecimal(e.getAttribute(ATTR_DEC));
		decimal.setWhole(e.getAttribute(ATTR_WHOLE));
		return decimal;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Decimal);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Decimal entity)
	{
		Element element = owner.createElement(TAG);
		element.setAttribute(ATTR_WHOLE, entity.getDecimal());
		element.setAttribute(ATTR_DEC, entity.getDecimal());
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		return (e.getTagName().equals(TAG) && e.hasAttribute(ATTR_WHOLE) && e
				.hasAttribute(ATTR_DEC));
	}
}
