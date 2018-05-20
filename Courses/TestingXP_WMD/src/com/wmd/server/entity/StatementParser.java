package com.wmd.server.entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wmd.client.entity.Answer;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.entity.Question;

/**
 * A parser for the ProblemStatement entity type.
 * 
 * @author Philip Diffenderfer, Steve Unger
 * 
 */
public class StatementParser implements Parser<ProblemStatement>
{

	/**
	 * The tag of the Xml element.
	 */
	public static final String TAG = "problem";

	/**
	 * {@inheritDoc}
	 */
	public ProblemStatement fromXML(Element e)
	{
		if (!handlesElement(e))
		{
			return null;
		}

		NodeList nodes = e.getChildNodes();

		// It must have exactly 2 children (question+answer)
		Element first = (Element) nodes.item(0);
		Element second = (Element) nodes.item(1);

		Entity question = EntityParser.fromXML(first);
		Entity answer = EntityParser.fromXML(second);

		// They must return non-null and a specific entity
		if (question == null || !(question instanceof Question))
		{
			return null;
		}
		if (answer == null || !(answer instanceof Answer))
		{
			return null;
		}

		return new ProblemStatement((Question) question, (Answer) answer);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesEntity(Entity e)
	{
		return (e instanceof ProblemStatement);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element toXML(Document owner, ProblemStatement entity)
	{
		Element question = EntityParser.toXML(owner, entity.getQuestion());
		Element answer = EntityParser.toXML(owner, entity.getAnswer());

		if (question == null || answer == null)
		{
			return null;
		}

		Element element = owner.createElement(TAG);
		element.appendChild(question);
		element.appendChild(answer);
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean handlesElement(Element e)
	{
		return (e.getTagName().equals(TAG));
	}

}
