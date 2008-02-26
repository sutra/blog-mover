/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class ListEntryHtmlParserTest extends TestCase {
	private static final Log log = LogFactory
			.getLog(ListEntryHtmlParserTest.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
	 * Test for 还未发表过日志。
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * 
	 */
	public void testParseEmptyBlog() throws IOException, SAXException {
		testParse(this.getClass().getResource("nodiaryNew.html"), "GB2312", 0,
				0, 0, new ArrayList<String>(), new ArrayList<Date>(),
				new ArrayList<String>(), new ArrayList<String>());
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcn.ListEntryHtmlParser#parse()}.
	 * 您共发表了1篇日志，公开日志（1）隐藏日志（0）。
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
		String editUrlPart = "/blue_log_ct.asp?action=edit&id=52108763&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D2%2D1+10%3A03%3A42";
		modifyLinks.add(editUrlPart);

		String filename = "blue_modfile_1.html"; // blue_modfile.asp_1_entry.html
		URL url = this.getClass().getResource(filename);
		assertNotNull(url);
		testParse(url, "UTF-8", 1, 1, 1, titles, publishedDates, permalinks,
				modifyLinks);
	}

	/**
	 * Test for 2页的日志列表的第1页。
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	public void testParse2PagesEntriesPage1() throws IOException, SAXException,
			ParseException {
		log.debug("testParse2PagesEntriesPage1");
		URL url = this.getClass()
				.getResource("list-1.txt");
		String encoding = "UTF-8"; // list-page-2pages-page1.html.txt
		int expectedCurrentPage = 1;
		int expectedTotalEntries = 12;
		int expectedTotalPages = 2;
		List<String> expectedTitles = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			expectedTitles.add("测试");
		}

		List<Date> expectedPublishedDates = new ArrayList<Date>();
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:04:39"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:04:01"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:03:21"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:02:58"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:01:47"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:01:29"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:01:13"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 12:00:31"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 11:59:16"));
		expectedPublishedDates.add(sdf.parse("2007-03-11 11:58:58"));

		List<String> expectedPermalinks = new ArrayList<String>();
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544359.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544330.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544313.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544301.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544260.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544250.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544222.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544201.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544158.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544149.html");

		List<String> expectedModifyLinks = new ArrayList<String>();
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544359&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544330&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544313&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544301&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544260&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544250&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544222&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544201&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544158&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544149&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A27%3A15");

		this
				.testParse(url, encoding, expectedCurrentPage,
						expectedTotalEntries, expectedTotalPages,
						expectedTitles, expectedPublishedDates,
						expectedPermalinks, expectedModifyLinks);
	}

	/**
	 * Test for 2页的日志列表的第2页。
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	public void testParse2PagesEntriesPage2() throws IOException, SAXException,
			ParseException {
		log.debug("testParse2PagesEntriesPage2");
		URL url = this.getClass()
				.getResource("list-2.txt"); // list-page-2pages-page2.html.txt
		String encoding = "UTF-8";
		int expectedCurrentPage = 2;
		int expectedTotalEntries = 12;
		int expectedTotalPages = 2;
		List<String> expectedTitles = new ArrayList<String>();
		expectedTitles.add("测试");
		expectedTitles.add("测试");

		List<Date> expectedPublishedDates = new ArrayList<Date>();
		expectedPublishedDates.add(sdf.parse("2007-03-11 11:58:24"));
		expectedPublishedDates.add(sdf.parse("2007-02-01 10:03:31"));

		List<String> expectedPermalinks = new ArrayList<String>();
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/54544120.html");
		expectedPermalinks
				.add("http://www.blogcn.com//u3/95/81/blogmoverdev/blog/52108763.html");

		List<String> expectedModifyLinks = new ArrayList<String>();
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=54544120&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A28%3A53");
		expectedModifyLinks
				.add("/blue_log_ct.asp?action=edit&id=52108763&UserID=32361141&uid=blogmoverdev&dbID=3&t=2007%2D3%2D11+12%3A28%3A53");

		this
				.testParse(url, encoding, expectedCurrentPage,
						expectedTotalEntries, expectedTotalPages,
						expectedTitles, expectedPublishedDates,
						expectedPermalinks, expectedModifyLinks);
	}

	private void testParse(URL url, String encoding, int expectedCurrentPage,
			int expectedTotalEntries, int expectedTotalPages,
			List<String> expectedTitles, List<Date> expectedPublishedDates,
			List<String> expectedPermalinks, List<String> expectedModifyLinks)
			throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(url, encoding);
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
