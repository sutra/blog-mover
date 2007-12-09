/**
 * Created on 2007-12-9 下午02:18:53
 */
package com.redv.blogmover.bsps.hexun.htmlparser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.bsps.hexun.HexunWebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author sutra
 * 
 */
public class ListParserTest {

	/**
	 * {@link com.redv.blogmover.bsps.hexun.htmlparser.htmlparser.ListParser#ListParser(org.w3c.dom.Document)}
	 * 的测试方法。
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testListParser() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("adminarticle.aspx"), "UTF-8");
		ListParser lp = new ListParser(document);
		List<HexunWebLog> entries = lp.getEntries();
		int i = 21;
		for (HexunWebLog entry : entries) {
			assertEquals("测试" + (i--), entry.getTitle());
		}
		assertEquals("4574698", entries.get(0).getArticleId());
		assertEquals(
				"http://post.blog.hexun.com/blogremover/postarticle.aspx?articleid=4574698",
				entries.get(0).getEditUrl());
	}

}
