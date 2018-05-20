package com.wmd.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wmd.client.entity.Decimal;
import com.wmd.util.XMLUtil;
/**
 * Test class for the class Decimal
 * Chris Eby & Steve Unger
 */
public class TestDecimal 
{

		private final DecimalParser parser = new DecimalParser();
		
		/**
		 * Test that a Decimal can be parsed from an XML string.
		 */
		@Test
		public void testDecimalFromXML()
		{
			String xml = "<decimal whole=\"5\" dec=\".5\" />";
			Document doc = XMLUtil.parse(xml);
			Element element = doc.getDocumentElement();
			Decimal decimal = this.parser.fromXML(element);
			
			assertEquals(".5",decimal.getDecimal());
			assertEquals("5",decimal.getWhole());
		}
		
		/**
		 * Test that a Decimal can be translated to XML, then from XML back
		 * to a Decimal.
		 */
		@Test
		public void testDecimalToXML()
		{
			Document doc = XMLUtil.createEmpty();
			Decimal decimal = new Decimal("5",".5");
			Element element = this.parser.toXML(doc, decimal);
			Decimal decimal2 = this.parser.fromXML(element);

			assertEquals(".5", decimal2.getDecimal());		
			assertEquals("5",decimal.getWhole());
		}
	}
