package com.wmd.server.entity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;
import com.wmd.util.XMLUtil;

/**
 * Holds a list of all supported parsers. Given any supported entity this can
 * convert between xml and an entity object.
 * 
 * @author Philip Diffenderfer , Chris Eby, and Steven Unger
 * 
 */
public class EntityParser
{

	// The list of supported parsers.
	private static final ArrayList<Parser<?>> parsers;

	/**
	 * Initializes the single instance of this class. This loads all supported
	 * entity parsers.
	 */
	static
	{
		parsers = new ArrayList<Parser<?>>();

		parsers.add(new StatementParser());
		parsers.add(new QuestionParser());
		parsers.add(new AnswerParser());
		parsers.add(new DecimalParser());
		parsers.add(new FractionParser());
		parsers.add(new IntegerParser());
		parsers.add(new NewlineParser());
		parsers.add(new TextParser());
		parsers.add(new UnitParser());
		parsers.add(new ExponentParser());
	}

	/**
	 * Given the Xml document and the entity to convert this will return the
	 * entity converted to an xml element.
	 * 
	 * @param owner
	 *            The associated Xml document.
	 * @param e
	 *            The entity to convert.
	 * @return The element generated, or null if entity is unsupported.
	 */
	@SuppressWarnings("unchecked")
	public static Element toXML(Document owner, Entity e)
	{
		if (owner == null || e == null)
		{
			return null;
		}
		for (Parser p : parsers)
		{
			if (p.handlesEntity(e))
			{
				return p.toXML(owner, e);
			}
		}
		return null;
	}

	/**
	 * Given an Xml element this will use the supported parsers and generate an
	 * entity. If the xml element is not supported then null will be returned.
	 * 
	 * @param e
	 *            The element to convert from.
	 * @return The entity generated, or null if the element was unsupported.
	 */
	public static Entity fromXML(Element e)
	{
		if (e == null)
		{
			return null;
		}
		for (Parser<?> p : parsers)
		{
			if (p.handlesElement(e))
			{
				return p.fromXML(e);
			}
		}
		return null;
	}

	/**
	 * Converts the given string to an entity. If the xml could not be parsed
	 * into an entity then null will be returned.
	 * 
	 * @param xml
	 *            The xml string to convert.
	 * @return The generated entity, or null if one could not be generated.
	 */
	public static Entity toEntity(String xml)
	{
		Document doc = XMLUtil.parse(xml);

		if (doc == null)
		{
			return null;
		}

		return fromXML(doc.getDocumentElement());
	}

	/**
	 * Converts the given entity to a string. If the entity isn't supported or
	 * there is an error in the translation process then null is returned.
	 * 
	 * @param e
	 *            The entity to convert.
	 * @return The generated string, or null if one could not be generated.
	 */
	public static String toString(Entity e)
	{
		Document doc = XMLUtil.createEmpty();
		if (doc == null)
		{
			return null;
		}

		Element element = toXML(doc, e);
		if (element == null)
		{
			return null;
		}

		doc.appendChild(element);

		return XMLUtil.toString(doc);
	}

}
