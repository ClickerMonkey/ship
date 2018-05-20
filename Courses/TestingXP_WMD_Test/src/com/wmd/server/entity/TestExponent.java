package com.wmd.server.entity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Exponent;
import com.wmd.client.entity.Integer;
import com.wmd.util.XMLUtil;
/**
 * Tests the exponent class.
 * @author Christopher Eby and Steve Unger
 */
public class TestExponent
{
	private final ExponentParser eparser = new ExponentParser();
	/**
	 * Tests receiving from XML
	 */
	@Test
	public void testExponentFromXML()
	{
		String xml = "<exponent><base><integer int=\"5\" /></base><pow><integer int=\"2\" /></pow></exponent>";
		Document doc = XMLUtil.parse(xml);
		Element element = doc.getDocumentElement();
		Exponent exponent = this.eparser.fromXML(element);
		Integer base = (Integer) exponent.getBase().getEntity(0);
		Integer pow = (Integer) exponent.getExponent().getEntity(0);
		
		assertEquals("5", base.getInteger());
		assertEquals("2", pow.getInteger());
	}
	/**
	 * Tests sending to XML and then to check recieves it back
	 * from XML
	 */
	@Test
	public void testExponentToXML()
	{
		Document doc = XMLUtil.createEmpty();
		Integer integer = new Integer("5");
		Integer integerEC = new Integer("2");
		EntityContainer ecBase = new EntityContainer(integer);
		EntityContainer ecPow = new EntityContainer(integerEC);
		Exponent exponent1= new Exponent(ecBase,ecPow);
		
		Element element = this.eparser.toXML(doc, exponent1);
		Exponent exponent2 = this.eparser.fromXML(element);
		
		Integer pow = (Integer) exponent2.getExponent().getEntity(0);
		Integer base = (Integer)exponent2.getBase().getEntity(0);
		
		assertEquals("2", pow.getInteger());		
		assertEquals("5", base.getInteger());
	}
}
