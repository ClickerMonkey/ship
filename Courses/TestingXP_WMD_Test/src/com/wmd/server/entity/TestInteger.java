package com.wmd.server.entity;

import static org.junit.Assert.*;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Integer;
import com.wmd.util.XMLUtil;

/**
 * Test class for the class Integer
 */
public class TestInteger
{
	private final IntegerParser parser = new IntegerParser();
	
	/**
	 * Test that a Integer can be parsed from an XML string.
	 */
	@Test
	public void testIntegerFromXML()
	{
		String xml = "<integer int=\"5\" />";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Integer integer = this.parser.fromXML(element);
		
		assertEquals("5",integer.getInteger());
	}
	
	/**
	 * Test that a Integer can be translated to XML, then from XML back
	 * to a Integer.
	 */
	@Test
	public void testIntegerToXML()
	{
		Document doc = XMLUtil.createEmpty();
		Integer integer = new Integer("5");
		Element e = this.parser.toXML(doc, integer);
		Integer integer2 = this.parser.fromXML(e);

		assertEquals("5", integer2.getInteger());		
	}
}
