/**
 * Created on 2007-2-13 上午12:02:45
 */
package com.redv.blogmover.bsps.com.sohu.blog;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutra
 * 
 */
public class ListPageParserTest {
	private ListPageParser listPageParser;

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
		this.listPageParser = new ListPageParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.sohu.blog.ListPageParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void testParse(Document document, int expectedTotalPage,
			List<Date> expectedDates, List<String> expectedEditUrls,
			List<String> expectedPermalinks, List<String> expectedTitles)
			throws IOException, SAXException, ParseException {
		this.listPageParser.setDocument(document);
		this.listPageParser.parse();

		assertEquals(expectedTotalPage, this.listPageParser.getTotalPage());

		assertEquals(expectedDates, this.listPageParser.getDates());

		assertEquals(expectedEditUrls, this.listPageParser.getEditUrls());

		assertEquals(expectedPermalinks, this.listPageParser.getPermalinks());

		assertEquals(expectedTitles, this.listPageParser.getTitles());
	}

	@Test
	public void testPage1() throws IOException, SAXException, ParseException {
		Document document = new HtmlFileToDocument().getDocument(
				this.listPageParser.getClass().getResource(
						"entry.do_m_list.html"), "GBK");

		List<Date> expectedDates = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		expectedDates.add(sdf.parse("2007-01-27"));
		expectedDates.add(sdf.parse("2007-01-06"));
		expectedDates.add(sdf.parse("2007-01-06"));
		expectedDates.add(sdf.parse("2006-12-30"));
		expectedDates.add(sdf.parse("2006-12-08"));
		expectedDates.add(sdf.parse("2006-12-07"));
		expectedDates.add(sdf.parse("2006-12-02"));
		expectedDates.add(sdf.parse("2006-12-02"));
		expectedDates.add(sdf.parse("2006-12-02"));
		expectedDates.add(sdf.parse("2006-12-02"));

		List<String> expectedEditUrls = new ArrayList<String>();
		expectedEditUrls.add("/manage/entry.do?m=edit&id=31430905");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=28591754");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=28527473");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=27588721");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=24244068");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=24049359");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=23461576");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=23426126");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=23425357");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=23424105");

		List<String> expectedPermalinks = new ArrayList<String>();
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/31430905.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/28591754.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/28527473.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/27588721.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/24244068.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/24049359.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/23461576.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/23426126.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/23425357.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/23424105.html");

		List<String> expectedTitles = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			expectedTitles.add("测试：标题。");
		}

		this.testParse(document, 2, expectedDates, expectedEditUrls,
				expectedPermalinks, expectedTitles);
	}

	@Test
	public void testPage2() throws IOException, SAXException, ParseException {
		Document document = new HtmlFileToDocument().getDocument(
				this.listPageParser.getClass()
						.getResource("entry.do_pg_2.html"), "GBK");

		List<Date> expectedDates = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		expectedDates.add(sdf.parse("2006-12-02"));
		expectedDates.add(sdf.parse("2006-12-02"));

		List<String> expectedEditUrls = new ArrayList<String>();
		expectedEditUrls.add("/manage/entry.do?m=edit&id=23423146");
		expectedEditUrls.add("/manage/entry.do?m=edit&id=23335843");

		List<String> expectedPermalinks = new ArrayList<String>();
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/23423146.html");
		expectedPermalinks.add("http://blogmover1.blog.sohu.com/23335843.html");

		List<String> expectedTitles = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			expectedTitles.add("测试：标题。");
		}

		this.testParse(document, 2, expectedDates, expectedEditUrls,
				expectedPermalinks, expectedTitles);
	}

}
