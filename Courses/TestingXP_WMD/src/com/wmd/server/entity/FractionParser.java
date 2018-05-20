package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Fraction;

/**
 * A parser for the Fraction entity type.
 * 
 * @author Philip Diffenderfer, Steve Unger, Chris Eby
 * 
 */
public class FractionParser implements Parser<Fraction>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "fraction";

	/**
	 * The child node name for the numerator in the Xml element.
	 */
	public static final String NUM = "num";

	/**
	 * The child node name for the denominator in the Xml element.
	 */
	public static final String DEN = "den";

	/**
	 * {@inheritDoc}
	 */
	public Fraction fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		Fraction fraction = new Fraction();

		Element numTag = (Element) e.getChildNodes().item(0);
		fraction.setNumerator(EntityContainerParser.fromXML(numTag));

		Element denTag = (Element) e.getChildNodes().item(1);
		fraction.setDenominator(EntityContainerParser.fromXML(denTag));

		return fraction;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Fraction);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Fraction entity)
	{
		Element element = owner.createElement(TAG);
		element.appendChild(EntityContainerParser.toXML(owner, entity
				.getNumerator(), NUM));
		element.appendChild(EntityContainerParser.toXML(owner, entity
				.getDenominator(), DEN));
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		Element num = (Element) e.getChildNodes().item(0);
		Element den = (Element) e.getChildNodes().item(1);
		return (e.getTagName().equals(TAG) && num.getTagName().equals(NUM) && den
				.getTagName().equals(DEN));
	}

}
