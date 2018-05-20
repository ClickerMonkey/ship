package com.wmd.server.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.w3c.dom.Document;

import com.wmd.client.entity.Answer;
import com.wmd.client.entity.Decimal;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Exponent;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Newline;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.entity.Question;
import com.wmd.client.entity.Text;
import com.wmd.client.entity.Unit;
import com.wmd.util.XMLUtil;

/**
 * Tests the basic methods in the entity parser class.
 * 
 * @author Philip Diffenderfer, Steve Unger
 */
public class TestEntityParser 
{
	
	/**
	 * Tests the toXML method against all supported entities.
	 */
	@Test
	public void testToXML()
	{
		Document doc = XMLUtil.createEmpty();

		assertNotNull( EntityParser.toXML(doc, new Text("val")));
		assertNotNull( EntityParser.toXML(doc, new Answer()));
		Integer int1 = new Integer("1");
		Integer int2 = new Integer("2");
		assertNotNull( EntityParser.toXML(doc, new Fraction(new EntityContainer(int1), new EntityContainer(int2))));
		assertNotNull( EntityParser.toXML(doc, new Integer("int")));
		assertNotNull( EntityParser.toXML(doc, new Newline()));
		assertNotNull( EntityParser.toXML(doc, new ProblemStatement(new Question(), new Answer())));
		assertNotNull( EntityParser.toXML(doc, new Question()));
		assertNotNull( EntityParser.toXML(doc, new Unit("correct", new ArrayList<String>())));
		assertNotNull( EntityParser.toXML(doc, new Exponent(new EntityContainer(int1),new EntityContainer(int2))));
		assertNotNull( EntityParser.toXML(doc, new Decimal("1",".5")));
	}

	/**
	 * Tests the fromXML and toXML methods hand in hand.
	 */
	@Test
	public void testFromXML()
	{
		Document doc = XMLUtil.createEmpty();
		Integer int1 = new Integer("1");
		Integer int2 = new Integer("2");
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Text("val"))));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Answer())));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Fraction(new EntityContainer(int1)
				, new EntityContainer(int1)))));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Integer("int"))));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Newline())));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new ProblemStatement(new Question(), new Answer()))));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Question())));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Unit("correct", new ArrayList<String>()))));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Exponent(new EntityContainer(int1),new EntityContainer(int2)))));
		assertNotNull( EntityParser.fromXML(
				EntityParser.toXML(doc, new Decimal("1",".5"))));
	}
	
	/**
	 * Tests the to string method for each supported entity.
	 */
	@Test
	public void testToString()
	{
		Integer int1 = new Integer("1");
		Integer int2 = new Integer("2");
		assertNotNull( EntityParser.toString(new Text("1")) );
		assertNotNull( EntityParser.toString(new Answer()) );
		assertNotNull( EntityParser.toString(new Fraction(new EntityContainer(int1), new EntityContainer(int2))));
		assertNotNull( EntityParser.toString(new Integer("4")) );
		assertNotNull( EntityParser.toString(new Newline()) );
		assertNotNull( EntityParser.toString(new ProblemStatement(new Question(), new Answer())) );
		assertNotNull( EntityParser.toString(new Unit("5", new ArrayList<String>())) );
		assertNotNull( EntityParser.toString(new Exponent(new EntityContainer(int1),new EntityContainer(int2))));
		assertNotNull( EntityParser.toString(new Decimal("1",".5")));
	}
	
	/**
	 * Tests the to entity method for each supported entity.
	 */
	@Test
	public void testToEntity()
	{
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		assertNotNull( EntityParser.toEntity(header+"<text val=\"1\"/>") );
		assertNotNull( EntityParser.toEntity(header+"<answer/>") );
		assertNotNull( EntityParser.toEntity(header+"<fraction><num><integer int=\"2\" /></num><den><integer int=\"5\" /></den></fraction>"));
		assertNotNull( EntityParser.toEntity(header+"<integer int=\"4\"/>") );
		assertNotNull( EntityParser.toEntity(header+"<newline/>") );
		assertNotNull( EntityParser.toEntity(header+"<problem><question/><answer/></problem>") );
		assertNotNull( EntityParser.toEntity(header+"<unit correct=\"5\"/>") );
		assertNotNull( EntityParser.toEntity(header+"<decimal whole=\"5\" dec=\".5\" />"));
		assertNotNull( EntityParser.toEntity(header+"<exponent><base><integer int=\"5\" /></base><pow><integer int=\"2\" /></pow></exponent>"));
	}
	
	
	
}
