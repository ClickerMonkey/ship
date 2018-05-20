package com.wmd.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Question;
import com.wmd.client.entity.Text;
import com.wmd.server.entity.*;
/**
 * Tests the EntityParser utility.
 */
public class TestEntityParser
{
	/**
	 * Tests parsing a simple element.
	 */
	@Test
	public void testElementParser()
	{
		String xml = "<text val=\"What is 3+5?\" />";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Text text = (Text)EntityParser.fromXML(element);
		assertEquals("What is 3+5?",text.getText());
	}
	/**
	 * Tests parsing a simple element.
	 */
	@Test
	public void testEnityParser()
	{
		Text text = new Text("What is 3+5?");
		Element element = EntityParser.toXML(XMLUtil.createEmpty(), text);
		assertEquals("What is 3+5?",element.getAttribute("val"));
	}
	/**
	 * Tests parsing a complex entity.
	 */
	@Test
	public void testEntityQuestionParsing()
	{
		String xml = "<question><text val=\"What is \" /><integer int=\"4\" /><fraction><num><integer int=\"2\"/></num><den><integer int=\"3\"/></den></fraction><text val=\"?\" /></question>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		Question question = (Question)EntityParser.fromXML(element);
		Text text = (Text) question.getEntities().get(0);
		Integer inte = (Integer) question.getEntities().get(1);
		Fraction frac = (Fraction) question.getEntities().get(2);
		Text text2 = (Text) question.getEntities().get(3);
		assertEquals("What is ",text.getText());
		assertEquals("4",inte.getInteger());
		
		EntityContainer num = frac.getNumerator();
		Integer numValue = (Integer)num.getEntity(0);
		assertEquals("2",numValue.getInteger());
		
		EntityContainer den = frac.getDenominator();
		Integer denValue = (Integer)den.getEntity(0);
		assertEquals("3",denValue.getInteger());
		assertEquals("?",text2.getText());
	}
	/**
	 * Tests parsing a complex element.
	 */
	@Test
	public void testElementQuestionParsing()
	{
		String xml = "<question><text val=\"What is \" /><integer int=\"4\" /><fraction><num><integer int=\"2\"/></num><den><integer int=\"3\"/></den></fraction><text val=\"?\" /></question>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		Question question = (Question)EntityParser.fromXML(element);
		Element questionElement = EntityParser.toXML(XMLUtil.createEmpty(), question);
		
		NodeList nl = questionElement.getElementsByTagName("integer");
		Element childElement = (Element) nl.item(0);
		
		assertEquals("4",childElement.getAttribute("int"));
	}
}
