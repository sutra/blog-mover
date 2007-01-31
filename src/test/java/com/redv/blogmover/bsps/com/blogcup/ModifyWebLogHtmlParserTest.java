/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class ModifyWebLogHtmlParserTest {
	private ModifyWebLogHtmlParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new ModifyWebLogHtmlParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcup.ModifyWebLogHtmlParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testParse() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("user_post.asp_logid_501609.html"),
				"UTF-8");
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		assertEquals("昨天用了一下Commons-launcher，感觉不错", webLog.getTitle());
		assertEquals("昨天用了一下Commons-launcher，感觉不错的内容~~<br>", webLog.getBody());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2007);
		cal.set(Calendar.MONTH, 1 - 1);
		cal.set(Calendar.DATE, 31);
		cal.set(Calendar.HOUR, 10);
		cal.set(Calendar.MINUTE, 42);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		assertEquals(cal.getTime(), webLog.getPublishedDate());
		assertEquals(new String[] { "commons-launcher", "感觉" }, webLog
				.getTags());
	}
}
