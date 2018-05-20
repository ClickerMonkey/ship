package com.wmd.server.entity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Question;
import com.wmd.client.entity.Text;
import com.wmd.util.XMLUtil;
/**
 * Test class for the Question class
 * @author Chris Eby and Steve Unger
 */
public class TestQuestion 
{
	private final QuestionParser parser = new QuestionParser();
	
	/**
	 * Tests getting a question from an XML element.
	 */
	@Test
	public void testQuestionFromXML()
	{
		String xml = "<question><text val=\"What is 3+5?\" /></question>";
		Document doc = XMLUtil.parse(xml);
		Element e = doc.getDocumentElement();
		
		Question q = this.parser.fromXML(e);
		Text text = (Text) q.getEntities().get(0);
		assertEquals("What is 3+5?",text.getText());
	}
	
	/**
	 * Test getting a more complex question from an xml element.
	 */
	@Test
	public void  testComplexQuestionFromXML()
	{
		String xml = "<question><text val=\"What is \" /><integer int=\"4\" /><fraction><num><integer int=\"2\" /></num><den><integer int=\"3\" /></den></fraction><text val=\"?\" /></question>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		Question question = this.parser.fromXML(element);
		Text text = (Text) question.getEntities().get(0);
		Integer inte = (Integer) question.getEntities().get(1);
		Fraction frac = (Fraction) question.getEntities().get(2);
		Text text2 = (Text) question.getEntities().get(3);
		Integer intNum= (Integer) frac.getNumerator().getEntity(0);
		Integer intDen = (Integer) frac.getDenominator().getEntity(0);
		assertEquals("What is ",text.getText());
		assertEquals("4",inte.getInteger());
		assertEquals("2",intNum.getInteger());
		assertEquals("3",intDen.getInteger());
		assertEquals("?",text2.getText());

	}
	/**
	 * Tests sending a complex question to an XML element.
	 */
	@Test
	public void testQuestionComplexToXML()
	{
		String xml ="<question><text val=\"What is \" /><integer int=\"4\" /><fraction><num><integer int=\"2\" /></num><den><integer int=\"3\" /></den></fraction><text val=\"?\" /></question>";
		
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Question question = this.parser.fromXML(element);
		
		Element element2 =  this.parser.toXML(XMLUtil.createEmpty(), question);
		NodeList nodelist = element2.getElementsByTagName("text");
		Element element3 = (Element) nodelist.item(0);
		
		assertEquals("What is ",element3.getAttribute("val"));
	}
}
