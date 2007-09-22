/**
 * 
 */
package com.redv.blogmover.bsps.sina;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author sutra
 * 
 */
public class ViewBlogPageParserTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * {@link com.redv.blogmover.bsps.sina.ViewBlogPageParser#getCommentsFirstPageUrl()}
	 * 的测试方法。
	 * 
	 * @throws SAXException
	 *             If xml parse error
	 * @throws IOException
	 *             If IOException while reading the html for parsing
	 */
	@Test
	public void testGetCommentsFirstPageUrl() throws IOException, SAXException {
		HtmlFileToDocument htmlFileToDocument = new HtmlFileToDocument();
		Document document = htmlFileToDocument.getDocument(this.getClass()
				.getResource("view-blog.html"), "UTF-8");
		ViewBlogPageParser viewBlogPageParser = new ViewBlogPageParser(document);
		assertEquals(
				"http://blog.sina.com.cn/s/comment_46f37fb501000b53_1.html",
				viewBlogPageParser.getCommentsFirstPageUrl());
	}
}
