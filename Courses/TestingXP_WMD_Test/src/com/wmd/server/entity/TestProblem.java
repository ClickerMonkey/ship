package com.wmd.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Answer;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.entity.Question;
import com.wmd.client.entity.Text;
import com.wmd.util.XMLUtil;
/**
 * Tests the problem class.
 */
public class TestProblem 
{
	private final StatementParser parser = new StatementParser();
	
	/**
	 * Test that a problem can be parsed from an XML element into
	 * a question and answer entities.
	 */
	@Test
	public void testProblemFromXML()
	{
		String xml = "<problem><question><text val=\"What is 3+5?\" /></question><answer><integer int=\"8\"  /></answer></problem>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		
		ProblemStatement problem = this.parser.fromXML(element);
		Question question = problem.getQuestion();
		Answer answer = problem.getAnswer();
		Text text = (Text) question.getEntities().get(0);
		Integer integer = (Integer) answer.getEntities().get(0);
		assertEquals("8",integer.getInteger());
		assertEquals("What is 3+5?",text.getText());
	}
	/**
	 * Tests that a problem can be created into an XML element.
	 */
	@Test
	public void testProblemToXML()
	{
		String xml = "<problem><question><text val=\"What is 3+5?\" /></question><answer><integer int=\"8\"  /></answer></problem>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		ProblemStatement problem = this.parser.fromXML(element);
		
		Element problemElement = this.parser.toXML(doc, problem);
		NodeList nl = problemElement.getElementsByTagName("question");
		Element childElement = (Element) nl.item(0).getFirstChild();
		Text text = new TextParser().fromXML(childElement);
		assertEquals("What is 3+5?",text.getText());
		
		nl = problemElement.getElementsByTagName("answer");
		childElement = (Element) nl.item(0).getFirstChild();
		Integer integer = new IntegerParser().fromXML(childElement);
		assertEquals("8",integer.getInteger());
	}
}
