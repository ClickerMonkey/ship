package com.wmd.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Symbol;
import com.wmd.util.XMLUtil;
/**
 * 
 * @author Steve Unger, Chris Eby
 * Test functionality of Symbol and Symbol Parser
 */
public class TestSymbol
{
	//Symbol parser
	private final SymbolParser parser = new SymbolParser();
	
	/**
	 * Test that a Symbol can be parsed from an XML string.
	 */
	@Test
	public void testTextFromXml()
	{
		String xml = "<symbol sym=\"\u00F7\" />";		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Symbol symbol = this.parser.fromXML(element);
		
		assertEquals("\u00F7",symbol.getSymbol());
	}
	
	/**
	 * Test that a Symbol can be translated to XML, then from XML back to a Text.
	 */
	@Test
	public void testTextToXml()
	{
		Document doc = XMLUtil.createEmpty();
		Symbol sym = new Symbol("\u00F7");
		Element element = this.parser.toXML(doc, sym);
		Symbol sym2 = this.parser.fromXML(element);
		
		assertEquals("\u00F7", sym2.getSymbol());		
	}
}
