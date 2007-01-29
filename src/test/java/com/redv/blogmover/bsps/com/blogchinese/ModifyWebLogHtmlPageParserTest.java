/**
 * 
 */
package com.redv.blogmover.bsps.com.blogchinese;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;

/**
 * @author shutrazh
 * 
 */
public class ModifyWebLogHtmlPageParserTest {

	private ModifyWebLogHtmlPageParser parser;

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
		this.parser = new ModifyWebLogHtmlPageParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogchinese.ModifyWebLogHtmlPageParser#parse(org.w3c.dom.Document)}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testParse() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("user_post.asp_logid_939693.html"),
				"gb2312");
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		assertEquals("测试", webLog.getTitle());
		assertEquals("我测试测试blahblahblah", webLog.getBody());
		Calendar cal = new GregorianCalendar(2007, 1 - 1, 27, 20, 18, 0);
		assertEquals(cal.getTime(), webLog.getPublishedDate());
		String[] tags = new String[] { "测试" };
		assertEquals(tags, webLog.getTags());
	}

}
