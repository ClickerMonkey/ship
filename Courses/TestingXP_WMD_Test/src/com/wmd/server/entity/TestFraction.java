package com.wmd.server.entity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Fraction;
import com.wmd.util.XMLUtil;
import com.wmd.client.entity.Integer;
/**
 * Tests the fraction class.
 */
public class TestFraction 
{
	private final FractionParser parser = new FractionParser();
	
	/**
	 * Test that a Fraction can be parsed from an XML string.
	 */
	@Test
	public void testFractionFromXML()
	{
		String xml = "<fraction><num><integer int=\"2\" /></num><den><integer int=\"5\" /></den></fraction>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Fraction fraction = this.parser.fromXML(element);
		
		Integer num2 = (Integer)fraction.getNumerator().getEntity(0);
		Integer den2 = (Integer)fraction.getDenominator().getEntity(0);
			
		assertEquals("2", num2.getInteger());		
		assertEquals("5", den2.getInteger());
	}
	
	/**
	 * Test that a Fraction can be translated to XML, then from XML back
	 * to a Fraction.
	 */
	@Test
	public void testFractionToXML()
	{
		Document doc = XMLUtil.createEmpty();
		EntityContainer num1 = new EntityContainer(new Integer("2"));
		EntityContainer den1 = new EntityContainer(new Integer("5"));
		Fraction fraction1 = new Fraction(num1, den1);
		Element element = this.parser.toXML(doc, fraction1);
		Fraction f2 = this.parser.fromXML(element);
		Integer num2 = (Integer)f2.getNumerator().getEntity(0);
		Integer den2 = (Integer)f2.getDenominator().getEntity(0);
			
		assertEquals("2", num2.getInteger());		
		assertEquals("5", den2.getInteger());
	}
	
}
