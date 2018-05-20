package com.wmd.util;

import static org.junit.Assert.*;

import org.junit.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.util.XMLUtil;
/**
 * Tests the XMLUtil object.
 */
public class TestXMLUtil
{
	/**
	 * Tests creation of static parser.
	 */
		@Test
		public void test1()
		{
			Document d = XMLUtil.createEmpty();
			assertNotNull(d);
		}
		/**
		 * Tests parsing by tag.
		 */
		@Test
		public void testParse()
		{
			String xml ="<attribute>hello my name is fuzzy</attribute>";
			Document d = XMLUtil.parse(xml);
			NodeList nl = d.getElementsByTagName("attribute");
			Element f = (Element) nl.item(0);
			assertEquals("hello my name is fuzzy", f.getFirstChild().getNodeValue());
		}
		/**
		 * Tests parsing multiple tags.
		 */
		@Test
		public void testMultiParse()
		{
			String xml ="<problem><question>Whats is 1 + 1?</question><answer>2</answer></problem>";
			Document d = XMLUtil.parse(xml);
			NodeList nl = d.getElementsByTagName("question");
			Element f = (Element) nl.item(0);

			assertEquals("Whats is 1 + 1?", f.getFirstChild().getNodeValue());
			
			nl = d.getElementsByTagName("answer");
			f = (Element) nl.item(0);
			assertEquals("2", f.getFirstChild().getNodeValue());
		}
		
		
}
