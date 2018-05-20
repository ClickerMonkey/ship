package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Newline;

/**
 * A parser for the Newline entity type.
 * 
 * @author Philip Diffenderfer, Steve Unger
 * 
 */
public class NewlineParser implements Parser<Newline>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "newline";

	/**
	 * {@inheritDoc}
	 */
	public Newline fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		return new Newline();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Newline);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Newline entity)
	{
		Element element = owner.createElement(TAG);
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		return (e.getTagName().equals(TAG));
	}

}
