package com.wmd.server.entity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Unit;

/**
 * A parser for the Unit entity type.
 * 
 * @author Philip Diffenderfer, Steve Unger
 * 
 */
public class UnitParser implements Parser<Unit>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "unit";

	/**
	 * The attribute name for the correct option in the Xml element.
	 */
	public static final String ATTR_ANS = "correct";

	/**
	 * {@inheritDoc}
	 */
	public Unit fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		NodeList nodes = e.getElementsByTagName("option");
		ArrayList<String> options = new ArrayList<String>();

		for (int loop = 0; loop < nodes.getLength(); loop++)
		{
			Element node = (Element) nodes.item(loop);
			options.add(node.getFirstChild().getNodeValue());
		}

		Unit unit = new Unit();
		unit.setCorrect(e.getAttribute(ATTR_ANS));
		unit.setOptions(options);
		return unit;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Unit);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Unit entity)
	{
		Element element = owner.createElement(TAG);
		element.setAttribute(ATTR_ANS, entity.getCorrect());

		for (int loop = 0; loop < entity.getOptionCount(); loop++)
		{
			Element option = owner.createElement("option");
			option.setTextContent(entity.getOption(loop));
			element.appendChild(option);
		}
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		return (e.getTagName().equals(TAG) && e.hasAttribute(ATTR_ANS));
	}

}
