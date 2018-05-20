package com.wmd.server.entity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Text;
import com.wmd.util.XMLUtil;

/**
 * Test class for the class Text.
 * @author Chris Eby and Steve Unger
 */
public class TestText
{
	//Text parser
	private final TextParser parser = new TextParser();
	
	/**
	 * Test that a Text can be parsed from an XML string.
	 */
	@Test
	public void testTextFromXml()
	{
		String xml = "<text val=\"Steve Unger Smells\" />";		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Text text = this.parser.fromXML(element);
		
		assertEquals("Steve Unger Smells",text.getText());
	}
	
	/**
	 * Test that a Text can be translated to XML, then from XML back to a Text.
	 */
	@Test
	public void testTextToXml()
	{
		Document doc = XMLUtil.createEmpty();
		Text text = new Text("Steve Unger Smells");
		Element element = this.parser.toXML(doc, text);
		Text text2 = this.parser.fromXML(element);
		
		assertEquals("Steve Unger Smells", text2.getText());		
	}
}

