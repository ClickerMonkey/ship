package com.wmd.server.entity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Unit;
import com.wmd.util.XMLUtil;
/**
 * Tests the unit class.
 * @author Chris Eby & Steve Unger
 */
public class TestUnit
{
	//Instantiates the unit parser
	private final UnitParser parser = new UnitParser();
	
	/**
	 * Tests getting a unit from an XML element.
	 */
	@Test
	public void testUnitInst()
	{
		String xml ="<unit correct=\"miles\"> <option>yards</option></unit>";
		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Unit unit = this.parser.fromXML(element);
		assertEquals("miles",unit.getCorrect());
		assertEquals("yards",unit.getOption(0));
	}
	/**
	 * Tests sending a unit to an XML element.
	 */
	@Test
	public void testToXML()
	{
		String xml ="<unit correct=\"miles\"><option>yards</option></unit>";
		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Unit unit = this.parser.fromXML(element);
		
		Element element2 =  this.parser.toXML(XMLUtil.createEmpty(), unit);
		assertEquals("miles", element2.getAttribute("correct"));
		
		NodeList nodelist2 = element2.getElementsByTagName("option");
		Element element3 = (Element) nodelist2.item(0);
		assertEquals("yards",element3.getFirstChild().getNodeValue());
		}
}
