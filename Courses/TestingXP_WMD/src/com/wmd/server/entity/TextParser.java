package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Text;

/**
 * A parser for the Text entity type.
 * 
 * @author Philip Diffenderfer, Steve Unger
 * 
 */
public class TextParser implements Parser<Text>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "text";

	/**
	 * The attribute name for the value in the Xml element.
	 */
	public static final String ATTR_VAL = "val";

	/**
	 * {@inheritDoc}
	 */
	public Text fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		Text text = new Text();
		text.setText(e.getAttribute(ATTR_VAL));
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Text);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Text entity)
	{
		Element element = owner.createElement(TAG);
		element.setAttribute(ATTR_VAL, entity.getText());
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		return (e.getTagName().equals(TAG) && e.hasAttribute(ATTR_VAL));
	}

}
