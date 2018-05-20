package com.wmd.server.entity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Question;

/**
 * A parser for the Question entity type.
 * 
 * @author Philip Diffenderfer, Steve Unger
 * 
 */
public class QuestionParser implements Parser<Question>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "question";

	/**
	 * {@inheritDoc}
	 */
	public Question fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

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

		return new Question(entities);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Question);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Question question)
	{
		Element element = owner.createElement(TAG);
		ArrayList<Entity> entities = question.getEntities();

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
