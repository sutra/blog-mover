/**
 * 
 */
package com.redv.blogmover.blogengines.cn.oblog;

import java.io.IOException;
import java.util.Date;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author <a href="mail:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public abstract class AbstractModifyWebLogHtmlPageParserTest extends TestCase {

	private ModifyWebLogHtmlPageParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		this.parser = new ModifyWebLogHtmlPageParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	protected void testParse(String htmlFile, String encoding,
			String expectedTitle, String expectedBody,
			Date expectedPublishedDate, String[] expectedTags)
			throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(htmlFile), encoding);
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		assertEquals(expectedTitle, webLog.getTitle());
		assertEquals(expectedBody, webLog.getBody());
		assertEquals(expectedPublishedDate, webLog.getPublishedDate());
		ArrayAssert.assertEquals(expectedTags, webLog.getTags());
	}

}
