package com.wmd.server.entity;

import static org.junit.Assert.*;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Newline;
import com.wmd.util.XMLUtil;
/**
 * Tests the newline class.
 */
public class TestNewline
{
	private final NewlineParser parser = new NewlineParser(); 
	
	/**
	 * Test method for testing that a Newline entity can be parsed from
	 * XML
	 */
	@Test
	public void testNewlineFromXml()
	{
		String xml = "<newline />";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		Newline newline = this.parser.fromXML(element);
		assertNotNull(newline);
	}
	/**
	 * Test method for testing that a Newline entity can be parsed from
	 * XML
	 */
	@Test
	public void testNewlineToXml()
	{
		Newline newline = new Newline();
		Document doc = XMLUtil.createEmpty();
		Element element = this.parser.toXML(doc, newline);
		assertEquals(NewlineParser.TAG, element.getTagName());
	}
}
