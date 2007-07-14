/**
 * Created on 2007-4-21 下午10:30:47
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.io.IOException;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutra
 * 
 */
public class ListParserTest extends TestCase {
	private ListParser lp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		lp = new ListParser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.live.spaces.ListParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(
						"listpage_zhoushuqun.spaces.live.com.html"), "UTF-8");
		lp.setDocument(document);
		lp.parse();
		String expectedPreviousPage = "http://zhoushuqun.spaces.live.com/?_c11_BlogPart_p=1&_c11_BlogPart_handle=cns!2B070A76FD6627CE!923&_c11_BlogPart_FullView=1&_c11_BlogPart_BlogPart=blogview&_c=BlogPart";
		String expectedNextPage = "http://zhoushuqun.spaces.live.com/?_c11_BlogPart_n=1&_c11_BlogPart_handle=cns!2B070A76FD6627CE!786&_c11_BlogPart_FullView=1&_c11_BlogPart_BlogPart=blogview&_c=BlogPart";
		assertEquals("Previous page link string not equals.",
				expectedPreviousPage, lp.getPreviousPageUrl());
		assertEquals("Next page link string not equals.", expectedNextPage, lp
				.getNextPageUrl());
		String[] urls = new String[] {
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!923.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!922.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!921.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!920.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!919.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!915.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!913.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!884.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!881.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!877.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!874.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!870.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!869.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!868.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!866.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!858.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!840.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!793.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!791.entry",
				"http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!786.entry" };
		String[] actualPermalinks = new String[lp.getPermalinks().size()];
		lp.getPermalinks().toArray(actualPermalinks);
		ArrayAssert.assertEquals("Permalinks not equals.", urls,
				actualPermalinks);
	}

	public void testNoPreviousPage() throws IOException, SAXException {
		String pageName = "listpage-no-previous-page_zhoushuqun.spaces.live.com.html";
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(pageName), "UTF-8");
		lp.setDocument(document);
		lp.parse();
		System.out.println(lp.getPreviousPageUrl());
		assertNull(lp.getPreviousPageUrl());
		assertNotNull(lp.getNextPageUrl());
	}

	public void testNoNextPage() throws IOException, SAXException {
		String pageName = "listpage-no-next-page_zhoushuqun.spaces.live.com.html";
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(pageName), "UTF-8");
		lp.setDocument(document);
		lp.parse();
		System.out.println(lp.getPreviousPageUrl());
		assertNotNull(lp.getPreviousPageUrl());
		assertNull(lp.getNextPageUrl());
	}
}
