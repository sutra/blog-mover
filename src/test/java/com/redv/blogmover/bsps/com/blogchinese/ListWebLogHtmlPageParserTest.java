/**
 * Created on 2007-1-28 上午12:01:13
 */
package com.redv.blogmover.bsps.com.blogchinese;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * @author shutra
 * 
 */
public class ListWebLogHtmlPageParserTest {
	private ListWebLogHtmlPageParser listParser;

	private Document document;

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
		InputStream inputStream = this.getClass().getResourceAsStream(
				"user_blogmanage.asp");
		try {
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			document = new HtmlFileToDocument().getDocument(reader);
		} finally {
			inputStream.close();
		}
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
	 */
	@Test
	public void testParse() {
		listParser.parse(document);
		assertEquals(2, listParser.getLastPageNumber());
		assertEquals(21, listParser.getTotalCount());
		List<String> webLogIds = listParser.getWebLogIds();
		assertEquals(20, webLogIds.size());
		String[] ids = new String[] { "939711", "939712", "939713", "939714",
				"939718", "939722", "939724", "939727", "939730", "939736",
				"939739", "939741", "939742", "939743", "939745", "939751",
				"939753", "939755", "939756", "939703" };
		for (int i = 0; i < ids.length; i++) {
			assertEquals(ids[i], webLogIds.get(i));
		}
	}

}
