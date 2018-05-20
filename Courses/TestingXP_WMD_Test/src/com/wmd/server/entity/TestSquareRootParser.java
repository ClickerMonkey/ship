package com.wmd.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.SquareRoot;
import com.wmd.util.XMLUtil;
/**
 * Tests the SquareRoot class and the square root parser.
 * @author Steve Unger, Chris Eby
 *
 */
public class TestSquareRootParser
{
	private final SquareRootParser parser = new SquareRootParser();

	/**
	 * Test method for testing that a square root entity can be parsed from XML
	 */
	@Test
	public void testAnswerFromXml()
	{
		String xml = "<sqrt><integer int =\"8\"  /></sqrt>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();

		SquareRoot sqrt = this.parser.fromXML(element);
		Integer integer = (Integer) sqrt.getRoot().getEntities().get(0);
		assertEquals("8", integer.getInteger());
	}

	/**
	 * Test more complex fromXML parsing.
	 */
	@Test
	public void testComplexXML()
	{
		String xml = "<sqrt><integer int=\"4\" /><fraction><num><integer int=\"2\" /></num><den><integer int=\"3\" /></den></fraction></sqrt>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();

		SquareRoot sqrt = this.parser.fromXML(element);
		Integer inte = (Integer) sqrt.getRoot().getEntities().get(0);
		Fraction frac = (Fraction) sqrt.getRoot().getEntities().get(1);
		Integer intNum = (Integer) frac.getNumerator().getEntity(0);
		Integer intDen = (Integer) frac.getDenominator().getEntity(0);
		assertEquals("4", inte.getInteger());
		assertEquals("2", intNum.getInteger());
		assertEquals("3", intDen.getInteger());

	}

	/**
	 * Tests a complex toXML call.
	 */
	@Test
	public void testComplexToXML()
	{

		String xml = "<sqrt><integer int=\"4\" /><fraction><num><integer int=\"2\" /></num><den><integer int=\"3\" /></den></fraction></sqrt>";

		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		SquareRoot sqrt = this.parser.fromXML(element);

		Element element2 = this.parser.toXML(XMLUtil.createEmpty(), sqrt);
		NodeList nodelist = element2.getElementsByTagName("integer");
		Element element3 = (Element) nodelist.item(0);

		assertEquals("4", element3.getAttribute("int"));
	}
}
