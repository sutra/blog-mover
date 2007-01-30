/**
 * 
 */
package com.redv.blogmover.bsps.com.blogchinese;

import static org.junit.Assert.assertEquals;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutrazh
 * 
 */
public class NekoHTMLTest {
	private static final Log log = LogFactory.getLog(NekoHTMLTest.class);

	@Test
	public void test() throws SAXException, IOException {
		DOMParser parser = new DOMParser();
		// parser.setFeature(
		// "http://cyberneko.org/html/features/override-doctype", true);
		// parser.setFeature("http://cyberneko.org/html/features/insert-doctype",
		// true);
		// parser.setProperty(
		// "http://cyberneko.org/html/properties/doctype/pubid",
		// "-//W3C//DTD XHTML 1.1//EN");
		// parser.setProperty(
		// "http://cyberneko.org/html/properties/doctype/sysid",
		// "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd");
		//
		// parser.setFeature(
		// "http://cyberneko.org/html/features/override-namespaces", true);
		// parser.setFeature(
		// "http://cyberneko.org/html/features/insert-namespaces", true);
		// parser.setProperty(
		// "http://cyberneko.org/html/properties/namespaces-uri",
		// "http://www.w3.org/1999/xhtml");

		// parser
		// .setFeature(
		// "http://cyberneko.org/html/features/scanner/notify-builtin-refs",
		// true);
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
}
