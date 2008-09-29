/**
 * Created on 2008-9-27 下午10:23:38
 */
package com.redv.blogmover.util;

import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Transform a string to a document.
 * 
 * @author Sutra Zhou
 * 
 */
public class StringToDocument {
	/**
	 * Transform the string to a document.
	 * 
	 * @param string
	 *            the string to transform
	 * @return the document
	 * @throws SAXException
	 */
	public static Document toDocument(String string) throws SAXException {
		StringReader characterStream = new StringReader(string);
		try {
			return new HtmlFileToDocument().getDocument(characterStream);
		} catch (IOException e) {
			// this exception will not happen.
			throw new RuntimeException(e);
		} finally {
			characterStream.close();
		}
	}
}
