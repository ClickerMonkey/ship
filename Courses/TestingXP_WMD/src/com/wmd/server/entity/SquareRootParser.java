package com.wmd.server.entity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.SquareRoot;

/**
 * A parser for the SquareRoot entity type.
 * 
 * @author Chris Eby, Steve Unger
 * 
 */
public class SquareRootParser implements Parser<SquareRoot>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "sqrt";

	/**
	 * {@inheritDoc}
	 */
	public SquareRoot fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		NodeList nodes = e.getChildNodes();
		EntityContainer entities = new EntityContainer();

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element node = (Element) nodes.item(i);
			Entity entity = EntityParser.fromXML(node);
			if (entity != null)
			{
				entities.addEntity(entity);
			} else
			{
				GWT.log("The element " + node.getTagName()
						+ " could not be parsed", null);
			}
		}

		return new SquareRoot(entities);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof SquareRoot);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, SquareRoot sqrt)
	{
		Element element = owner.createElement(TAG);
		ArrayList<Entity> entities = sqrt.getRoot().getEntities();

		for (int loop = 0; loop < entities.size(); loop++)
		{
			Entity entity = entities.get(loop);
			if (entity == null)
			{
				continue;
			}
			Element node = EntityParser.toXML(owner, entity);
			if (node != null)
			{
				element.appendChild(node);
			} else
			{
				GWT.log("Error parsing entity " + entity.getClass().getName()
						+ "to Xml node", null);
			}
		}
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
