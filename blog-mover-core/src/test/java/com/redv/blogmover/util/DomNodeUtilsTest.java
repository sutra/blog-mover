/**
 * Created on 2008-9-27 下午10:11:53
 */
package com.redv.blogmover.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Sutra Zhou
 * 
 */
public class DomNodeUtilsTest {

	/**
	 * Test method for
	 * {@link com.redv.blogmover.util.DomNodeUtils#getTextContent(org.w3c.dom.Node)}
	 * .
	 * 
	 * @throws SAXException
	 */
	@Test
	public void testGetTextContent() throws SAXException {
		Document document = StringToDocument.toDocument("test");
		assertEquals("test", DomNodeUtils.getTextContent(document));

		document = StringToDocument.toDocument("<span>test</span>");
		assertEquals("test", DomNodeUtils.getTextContent(document));

		document = StringToDocument
				.toDocument("<span>a <a href='/'>link</a> b</span>");
		assertEquals("a link b", DomNodeUtils.getTextContent(document));

		document = StringToDocument
				.toDocument("<span>a <!-- comment --><a href='/'>link</a> b</span>");
		assertEquals("a link b", DomNodeUtils.getTextContent(document));
	}

}
