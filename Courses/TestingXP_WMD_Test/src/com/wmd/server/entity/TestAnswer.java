package com.wmd.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Answer;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Text;
import com.wmd.util.XMLUtil;
/**
 * Test Class for the class Answer. Tests all methods and functionality
 */
public class TestAnswer
{
	private final AnswerParser parser = new AnswerParser();
	
	/**
	 * Test method for testing that an Answer entity can be parsed from
	 * XML
	 */
	@Test
	public void testAnswerFromXml()
	{
		String xml = "<answer><integer int =\"8\"  /></answer>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		Answer answer = this.parser.fromXML(element);
		Integer integer = (Integer) answer.getEntities().get(0);
		assertEquals("8",integer.getInteger());
	}
	
	/**
	 * Test more complex fromXML parsing.
	 */
	@Test
	public void  testComplexXML()
	{
		String xml = "<answer><text val=\"What is \" /><integer int=\"4\" /><fraction><num><integer int=\"2\" /></num><den><integer int=\"3\" /></den></fraction><text val=\"?\" /></answer>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		Answer answer = this.parser.fromXML(element);
		Text text = (Text) answer.getEntities().get(0);
		Integer inte = (Integer) answer.getEntities().get(1);
		Fraction frac = (Fraction) answer.getEntities().get(2);
		Text text2 = (Text) answer.getEntities().get(3);
		Integer intNum= (Integer) frac.getNumerator().getEntity(0);
		Integer intDen = (Integer) frac.getDenominator().getEntity(0);
		assertEquals("What is ",text.getText());
		assertEquals("4",inte.getInteger());
		assertEquals("2",intNum.getInteger());
		assertEquals("3",intDen.getInteger());
		assertEquals("?",text2.getText());
		
	}
	/**
	 * Tests a complex toXML call.
	 */
	@Test
	public void testComplexToXML()
	{

		String xml ="<answer><text val=\"What is \" /><integer int=\"4\" /><fraction><num><integer int=\"2\" /></num><den><integer int=\"3\" /></den></fraction><text val=\"?\" /></answer>";
		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Answer answer = this.parser.fromXML(element);
		
		Element element2 =  this.parser.toXML(XMLUtil.createEmpty(), answer);
		NodeList nodelist = element2.getElementsByTagName("text");
		Element element3 = (Element) nodelist.item(0);
		
		assertEquals("What is ",element3.getAttribute("val"));
	}
}
