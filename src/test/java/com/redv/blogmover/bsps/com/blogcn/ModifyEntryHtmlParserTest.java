/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class ModifyEntryHtmlParserTest extends TestCase {
	private ModifyEntryHtmlParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		parser = new ModifyEntryHtmlParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcn.ModifyEntryHtmlParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse() throws IOException, SAXException {
		Date date = new GregorianCalendar(2007, 2 - 1, 1, 10, 3, 31).getTime();
		String[] tags = new String[] { "测试tag", "第二个tag", "第三个tag" };
		testParse("blue_log_ct.asp_action_edit_id_52108763.html", "UTF-8",
				"测试", "测试日志内容。", date, tags);
	}

	private void testParse(String htmlFile, String encoding,
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
