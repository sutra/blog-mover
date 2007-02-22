/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import java.io.IOException;
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
public class ModifyWebLogHtmlParserTest extends TestCase {
	private ModifyWebLogHtmlParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		parser = new ModifyWebLogHtmlParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcup.ModifyWebLogHtmlParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("user_post.asp_logid_501609.html"),
				"UTF-8");
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		assertEquals("昨天用了一下Commons-launcher，感觉不错", webLog.getTitle());
		assertEquals("昨天用了一下Commons-launcher，感觉不错的内容~~<br>", webLog.getBody());
		assertEquals(new GregorianCalendar(2007, 1 - 1, 31, 10, 42, 0)
				.getTime(), webLog.getPublishedDate());
		ArrayAssert.assertEquals(new String[] { "commons-launcher", "感觉" }, webLog
				.getTags());
	}
}
