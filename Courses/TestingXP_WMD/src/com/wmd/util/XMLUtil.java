package com.wmd.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.google.gwt.core.client.GWT;

/**
 * Does the work of XML parsing and document creating.
 * 
 * @author Philip Diffenderfer, Christopher Eby, Steve Unger
 */
public class XMLUtil
{
	private static DocumentBuilder builder;
	// Initialize document builder.
	static
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (Exception e)
		{
			GWT.log("Error initializing server-side XML", e);
		}
	}

	/**
	 * Parses a string into an XML Document.
	 * 
	 * @param xml
	 *            - String of XML to be parsed.
	 * @return parsed XML Document
	 */
	public static Document parse(String xml)
	{
		try
		{
			InputSource source = new InputSource(new StringReader(xml));
			Document doc = builder.parse(source);

			return doc;
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error parsing xml '" + xml + "'", e);
		}
		return null;
	}

	/**
	 * Creates an empty Document object.
	 * 
	 * @return empty document object
	 */
	public static Document createEmpty()
	{
		if (builder == null)
		{
			return null;
		}
		return builder.newDocument();
	}

	/**
	 * Converts the given document to a string.
	 * 
	 * @param doc
	 *            The document to convert.
	 * @return An Xml string representation of the document, or null if there
	 *         was an error transforming.
	 */
	public static String toString(Document doc)
	{
		try
		{
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			writer.flush();
			return writer.toString();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

}
