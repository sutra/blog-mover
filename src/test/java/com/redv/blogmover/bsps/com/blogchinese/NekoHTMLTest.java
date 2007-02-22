/**
 * 
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutrazh
 * 
 */
public class NekoHTMLTest extends TestCase {
	private static final Log log = LogFactory.getLog(NekoHTMLTest.class);

	public void test1() throws SAXException, IOException {
		DOMParser parser = new DOMParser();

		InputSource inputSource = new InputSource();

		InputStream inputStream = this.getClass().getResourceAsStream(
				"redv.com.html");
		// Reader reader = new InputStreamReader(inputStream, "UTF-8");

		Reader reader = new CharArrayReader(
				"<html><head><title>test</title></head><body><div id='test'>test<a href='test'>test</a></div></body></html>"
						.toCharArray());
		try {
			inputSource.setCharacterStream(reader);
			parser.parse(inputSource);
			Document document = parser.getDocument();
			if (log.isDebugEnabled()) {
				try {
					log.debug(DomNodeUtils.getXmlAsString(document));
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Element div = document.getElementById("test");
			String s = div.getFirstChild().getNodeValue();
			assertEquals("test", s);

			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("title").item(0));
			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("TITLE").item(0));
			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("a").item(0));
			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("A").item(0));

			junitx.framework.Assert.assertNotNull(div.getElementsByTagName("a")
					.item(0));
			junitx.framework.Assert.assertNotNull(div.getElementsByTagName("A")
					.item(0));
		} finally {
			reader.close();
			inputStream.close();
		}
	}

	public void test2() throws SAXException, IOException {
		DOMParser parser = new DOMParser();

		InputSource inputSource = new InputSource();

		InputStream inputStream = this.getClass().getResourceAsStream(
				"redv.com.html");
		// Reader reader = new InputStreamReader(inputStream, "UTF-8");

		Reader reader = new CharArrayReader(
				"<html xmlns='http://www.w3.org/1999/xhtml'><head><title>test</title></head><body><div id='test'>test<a href='test'>test</a></div></body></html>"
						.toCharArray());
		try {
			inputSource.setCharacterStream(reader);
			parser.parse(inputSource);
			Document document = parser.getDocument();
			if (log.isDebugEnabled()) {
				try {
					log.debug(DomNodeUtils.getXmlAsString(document));
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Element div = document.getElementById("test");
			String s = div.getFirstChild().getNodeValue();
			assertEquals("test", s);

			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("title").item(0));
			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("TITLE").item(0));
			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("a").item(0));
			junitx.framework.Assert.assertNotNull(document
					.getElementsByTagName("A").item(0));

			junitx.framework.Assert.assertNull(div.getElementsByTagName("a")
					.item(0));
			junitx.framework.Assert.assertNotNull(div.getElementsByTagName("A")
					.item(0));
		} finally {
			reader.close();
			inputStream.close();
		}
	}
}
