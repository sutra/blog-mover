/**
 * Created on 2007-12-9 下午03:25:22
 */
package com.redv.blogmover.bsps.hexun.htmlparser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.bsps.hexun.HexunWebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author sutra
 * 
 */
public class DetailParserTest {

	/**
	 * {@link com.redv.blogmover.bsps.hexun.htmlparser.DetailParser#DetailParser(com.redv.blogmover.bsps.hexun.HexunWebLog, org.w3c.dom.Document)}
	 * 的测试方法。
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testDetailParser() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("postarticle.aspx"), "UTF-8");
		HexunWebLog entry = new HexunWebLog();
		new DetailParser(entry, document);
		assertEquals("测试2", entry.getTitle());
		assertEquals("excerpt incorret.", "测试2", entry.getExcerpt());
		assertEquals("必须要标签？", entry.getTags()[0]);
		assertEquals("测试2", entry.getBody());
	}
}
