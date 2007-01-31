/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junitx.framework.ListAssert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class ListWebLogHtmlParserTest {
	private ListWebLogHtmlParser parser;

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
		parser = new ListWebLogHtmlParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcup.ListWebLogHtmlParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testParse() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("user_blogmanage.asp_1_entries.html"),
				"UTF-8");
		parser.setDocument(document);
		parser.parse();

		assertEquals(1, parser.getTotalCount());
		assertEquals(1, parser.getCurrentPage());
		assertEquals(1, parser.getTotalPage());

		List<String> expectedIds = new ArrayList<String>();
		expectedIds.add("501609");
		List<String> expectedLinks = new ArrayList<String>();
		expectedLinks.add("b4/blogmover/archives/2007/501609.shtml");

		assertEquals(expectedIds.size(), parser.getIds().size());
		ListAssert.assertEquals(expectedIds, parser.getIds());

		assertEquals(expectedLinks.size(), parser.getLinks().size());
		ListAssert.assertEquals(expectedLinks, parser.getLinks());
	}
}
