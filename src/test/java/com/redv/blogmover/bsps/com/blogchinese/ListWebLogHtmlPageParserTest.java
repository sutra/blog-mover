/**
 * Created on 2007-1-28 上午12:01:13
 */
package com.redv.blogmover.bsps.com.blogchinese;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import junitx.framework.ArrayAssert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author shutra
 * 
 */
public class ListWebLogHtmlPageParserTest {
	private ListWebLogHtmlPageParser listParser;

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
		listParser = new ListWebLogHtmlPageParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogchinese.ListWebLogHtmlPageParser#parse(org.w3c.dom.Document)}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testParse() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("user_blogmanage.html"), "gb2312");

		listParser.setDocument(document);
		listParser.parse();
		assertEquals(2, listParser.getLastPageNumber());
		assertEquals(21, listParser.getTotalCount());
		String[] expectIds = new String[] { "939711", "939712", "939713",
				"939714", "939718", "939722", "939724", "939727", "939730",
				"939736", "939739", "939741", "939742", "939743", "939745",
				"939751", "939753", "939755", "939756", "939703" };
		List<String> webLogIds = listParser.getWebLogIds();
		String[] actualIds = new String[webLogIds.size()];
		webLogIds.toArray(actualIds);
		junitx.framework.ArrayAssert.assertEquals(expectIds, actualIds);

		String[] expectUrls = new String[] {
				"06091/260430/archives/2007/2007127212020.shtml",
				"06091/260430/archives/2007/2007127212644.shtml",
				"06091/260430/archives/2007/2007127212710.shtml",
				"06091/260430/archives/2007/2007127212720.shtml",
				"06091/260430/archives/2007/200712721289.shtml",
				"06091/260430/archives/2007/2007127212830.shtml",
				"06091/260430/archives/2007/2007127212839.shtml",
				"06091/260430/archives/2007/200712721299.shtml",
				"06091/260430/archives/2007/2007127212934.shtml",
				"06091/260430/archives/2007/200712721303.shtml",
				"06091/260430/archives/2007/2007127213010.shtml",
				"06091/260430/archives/2007/2007127213028.shtml",
				"06091/260430/archives/2007/2007127213056.shtml",
				"06091/260430/archives/2007/200712721312.shtml",
				"06091/260430/archives/2007/2007127213123.shtml",
				"06091/260430/archives/2007/2007127213137.shtml",
				"06091/260430/archives/2007/2007127213234.shtml",
				"06091/260430/archives/2007/2007127213255.shtml",
				"06091/260430/archives/2007/2007127213320.shtml",
				"06091/260430/archives/2007/2007127211931.shtml" };
		List<String> urls = listParser.getUrls();
		String[] actualUrls = new String[urls.size()];
		urls.toArray(actualUrls);
		ArrayAssert.assertEquals(expectUrls, actualUrls);
	}
}
