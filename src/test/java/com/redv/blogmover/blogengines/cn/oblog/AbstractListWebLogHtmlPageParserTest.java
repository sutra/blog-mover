/**
 * Created on 2007-1-28 上午12:01:13
 */
package com.redv.blogmover.blogengines.cn.oblog;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import junitx.framework.ArrayAssert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author <a href="mail:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class AbstractListWebLogHtmlPageParserTest {
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

	protected void testParse(String htmlFile, String encoding,
			int currentPageNumber, int expectedLastPageNumber,
			int expectedTotalCount, String[] expectedIds, String[] expectedUrls)
			throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(htmlFile), encoding);

		listParser.setCurrentPageNumber(currentPageNumber);
		listParser.setDocument(document);
		listParser.parse();
		assertEquals(expectedLastPageNumber, listParser.getLastPageNumber());
		assertEquals(expectedTotalCount, listParser.getTotalCount());
		List<String> webLogIds = listParser.getWebLogIds();
		String[] actualIds = new String[webLogIds.size()];
		webLogIds.toArray(actualIds);
		junitx.framework.ArrayAssert.assertEquals(expectedIds, actualIds);

		List<String> urls = listParser.getUrls();
		String[] actualUrls = new String[urls.size()];
		urls.toArray(actualUrls);
		ArrayAssert.assertEquals(expectedUrls, actualUrls);
	}

}
