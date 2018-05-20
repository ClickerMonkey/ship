package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Exponent;

/**
 * This class parses an exponent entity
 * 
 * @author Christopher Eby, Steven Unger
 * 
 */
public class ExponentParser implements Parser<Exponent>
{
	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "exponent";
	/**
	 * The child node name name for the value in the Xml element.
	 */
	public static final String BASE = "base";
	/**
	 * The child node name name for the value in the Xml element.
	 */
	public static final String POW = "pow";

	/**
	 * {@inheritDoc}
	 */
	public Exponent fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		Exponent exponent = new Exponent();
		// Get base tag
		Element baseTag = (Element) e.getChildNodes().item(0);
		exponent.setBase(EntityContainerParser.fromXML(baseTag));
		// Get Exponent tag
		Element exponentTag = (Element) e.getChildNodes().item(1);
		exponent.setExponent(EntityContainerParser.fromXML(exponentTag));
		return exponent;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Exponent);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Exponent entity)
	{
		Element element = owner.createElement(TAG);
		Element base = owner.createElement(BASE);
		element.appendChild(base.appendChild(EntityContainerParser.toXML(owner,
				entity.getBase(), BASE)));
		element.appendChild(EntityContainerParser.toXML(owner, entity
				.getExponent(), POW));

		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		Element base = (Element) e.getChildNodes().item(0);
		Element pow = (Element) e.getChildNodes().item(1);
		return (e.getTagName().equals(TAG) && base.getTagName().equals(BASE) && pow
				.getTagName().equals(POW));
	}

}
