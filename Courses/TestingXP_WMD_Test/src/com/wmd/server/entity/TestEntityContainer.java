package com.wmd.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Answer;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Exponent;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Text;
import com.wmd.util.XMLUtil;
/**
 * Tests the entity container.
 * @author Christopher Eby & Steve Unger
 */
public class TestEntityContainer
{
	//Answer parser extends EntityConatiner
	private final AnswerParser answerParser = new AnswerParser();
	//Fraction parser extends EntityConatiner
	private final FractionParser fractionParser = new FractionParser();
	/**
	 * Test method for testing that an Answer entity can be parsed from
	 * XML
	 */
	@Test
	public void testContainerFromXml()
	{
		String xml = "<answer><integer int =\"8\"  /></answer>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Answer answer = this.answerParser.fromXML(element);
		Integer integer = (Integer) answer.getEntity(0);
		assertEquals("8",integer.getInteger());
	}
	
	/**
	 * Test more complex fromXML parsing.
	 */
	@Test
	public void  testComplexXML()
	{
		String xml = "<answer><text val=\"What is \" /><integer int=\"4\" /><fraction><num>" +
				"<integer int=\"2\" /></num><den><integer int=\"5\" /></den></fraction>" +
				"<text val=\"?\" /></answer>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		Answer answer = this.answerParser.fromXML(element);
		Text text = (Text) answer.getEntity(0);
		Integer inte = (Integer) answer.getEntity(1);
		Fraction frac = (Fraction) answer.getEntity(2);
		Text text2 = (Text) answer.getEntity(3);
		Integer num2 = (Integer)frac.getNumerator().getEntity(0);
		Integer den2 = (Integer)frac.getDenominator().getEntity(0);
			
		assertEquals("2", num2.getInteger());		
		assertEquals("5", den2.getInteger());
		assertEquals("What is ",text.getText());
		assertEquals("4",inte.getInteger());
		assertEquals("?",text2.getText());
		
	}
	/**
	 * Tests a complex toXML call.
	 */
	@Test
	public void testComplexToXML()
	{

		String xml ="<answer><text val=\"What is \" /><integer int=\"4\" />" +
				"<fraction><num><integer int=\"2\" /></num><den>" +
				"<integer int=\"5\" /></den></fraction><text val=\"?\" /></answer>";
		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Answer answer = this.answerParser.fromXML(element);
		
		Element element2 =  this.answerParser.toXML(XMLUtil.createEmpty(), answer);
		NodeList nl = element2.getElementsByTagName("text");
		Element element3 = (Element) nl.item(0);
		
		assertEquals("What is ",element3.getAttribute("val"));
	}
	/**
	 * An epicly long xml string to parse.
	 */
	@Test
	public void testUltimateFractionKiller()
	{
		String xml = "<fraction><num><integer int=\"1\" /></num><den><integer int=\"5\" />" +
				"<exponent><base><text val=\"x\" /></base><pow><fraction><num>" +
				"<integer int=\"1\" /></num><den><integer int=\"2\" /></den></fraction>" +
				"</pow></exponent></den></fraction>";
		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		//Get fraction
		Fraction fraction = this.fractionParser.fromXML(element);
		//Get exponent from den
		Exponent exponent = (Exponent) fraction.getDenominator().getEntity(1);
		//Get fraction in the exponent
		Fraction fraction2 = (Fraction) exponent.getExponent().getEntity(0);
		//Get int from num
		Integer num = (Integer) fraction2.getNumerator().getEntity(0);
		//Get int from den
		Integer den = (Integer) fraction2.getDenominator().getEntity(0);
		
		assertEquals("1",num.getInteger());
		assertEquals("2",den.getInteger());
	}
	
	/**
	 * Tests the toString method.
	 */
	@Test
	public void testToString()
	{
		String xml = "<answer><integer int=\"8\"/></answer>";
		Document doc = XMLUtil.parse(xml);
		
		Element element = doc.getDocumentElement();
		EntityContainer entityContainer = EntityContainerParser.fromXML(element);
		
		String correctString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><answer><integer int=\"8\"/></answer>";
		assertEquals(correctString,EntityContainerParser.toString(entityContainer, "answer"));
	}
}


