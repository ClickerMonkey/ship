package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Symbol;

/**
 * 
 * @author Steve Unger, Chris Eby
 * Class that parses the Symbol class for unicode math
 * symbols
 *
 */
public class SymbolParser
{
	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "symbol";

	/**
	 * The attribute name for the value in the Xml element.
	 */
	public static final String ATTR_SYM = "sym";

	/**
	 * {@inheritDoc}
	 */
	public Symbol fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		Symbol symbol = new Symbol();
		symbol.setSymbol(e.getAttribute(ATTR_SYM));
		return symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof Symbol);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, Symbol sym)
	{
		Element element = owner.createElement(TAG);
		element.setAttribute(ATTR_SYM, sym.getSymbol());
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		return (e.getTagName().equals(TAG) && e.hasAttribute(ATTR_SYM));
	}
}
