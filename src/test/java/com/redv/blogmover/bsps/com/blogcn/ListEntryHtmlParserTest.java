/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class ListEntryHtmlParserTest extends TestCase {
	private ListEntryHtmlParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		parser = new ListEntryHtmlParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcn.ListEntryHtmlParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse1Entry() throws IOException, SAXException {
		List<String> titles = new ArrayList<String>();
		titles.add("测试");

		List<Date> publishedDates = new ArrayList<Date>();
		// 2007-02-01 10:03:31
		publishedDates.add(new GregorianCalendar(2007, 2 - 1, 1, 10, 3, 31)
				.getTime());

		List<String> permalinks = new ArrayList<String>();
		permalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/52108763.html");

		List<String> modifyLinks = new ArrayList<String>();
		modifyLinks
				.add("/blue_log_ct.asp?action=edit&id=52108763&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D2%2D1+10%3A03%3A42");

		testParse(1, 1, 1, titles, publishedDates, permalinks, modifyLinks);
	}

	private void testParse(int expectedCurrentPage, int expectedTotalEntries,
			int expectedTotalPages, List<String> expectedTitles,
			List<Date> expectedPublishedDates, List<String> expectedPermalinks,
			List<String> expectedModifyLinks) throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("blue_modfile.asp_1_entry.html"),
				"UTF-8");
		parser.setDocument(document);
		parser.parse();
		assertEquals(expectedCurrentPage, parser.getCurrentPage());
		assertEquals(expectedTotalEntries, parser.getTotalEntries());
		assertEquals(expectedTotalPages, parser.getTotalPages());

		assertEquals(expectedTitles, parser.getTitles());
		assertEquals(expectedPublishedDates, parser.getPublishedDates());
		assertEquals(expectedPermalinks, parser.getPermalinks());
		assertEquals(expectedModifyLinks, parser.getModifyLinks());
	}

}
