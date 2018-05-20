package com.wmd.server.entity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.EntityContainer;
import com.wmd.util.XMLUtil;

/**
 * Parses an entity container object.
 * 
 * @author Christopher Eby and Steve Unger
 */
public class EntityContainerParser
{
	/**
	 * {@inheritDoc}
	 * 
	 * @return Returns an EntityContainer
	 */
	public static EntityContainer fromXML(Element e)
	{
		// does not validate because this is not called from Entity parser

		NodeList nodes = e.getChildNodes();
		ArrayList<Entity> entities = new ArrayList<Entity>();

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element node = (Element) nodes.item(i);
			Entity entity = EntityParser.fromXML(node);
			if (entity != null)
			{
				entities.add(entity);
			} else
			{
				GWT.log("The element " + node.getTagName()
						+ " could not be parsed", null);
			}
		}

		return new EntityContainer(entities);
	}

	/**
	 * {@inheritDoc}
	 */
	public static boolean handlesEntity(Entity e)
	{
		return (e instanceof EntityContainer);
	}

	/**
	 * {@inheritDoc}
	 */
	public static Element toXML(Document owner,
			EntityContainer entityContainer, String TAG)
	{
		Element element = owner.createElement(TAG);
		ArrayList<Entity> entities = entityContainer.getEntities();

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
	 * Converts the given entity container to a string. If an entity isn't
	 * supported or there is an error in the translation process then null is
	 * returned.
	 * 
	 * @param entityContainer
	 *            The entity container to convert.
	 * @return The generated string, or null if one could not be generated.
	 */
	public static String toString(EntityContainer entityContainer, String tag)
	{
		Document doc = XMLUtil.createEmpty();
		if (doc == null)
		{
			return null;
		}

		Element element = toXML(doc, entityContainer, tag);
		if (element == null)
		{
			return null;
		}

		doc.appendChild(element);

		return XMLUtil.toString(doc);
	}
}
