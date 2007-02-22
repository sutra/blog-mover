/**
 * Created on 2007-2-22 下午11:47:51
 */
package com.redv.blogmover.bsps.com.sohu.blog;

import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

/**
 * @author shutra
 * 
 */
public class EditPageParserTest extends TestCase {
	private EditPageParser parser;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.parser = new EditPageParser();
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
	 * {@link com.redv.blogmover.bsps.com.sohu.blog.EditPageParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	private void testParse(String file, String title, String body,
			String[] tags, String excerpt) throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(file), "GBK");
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		assertEquals(title, webLog.getTitle());
		assertEquals(body, webLog.getBody());
		ArrayAssert.assertEquals(tags, webLog.getTags());
		assertEquals(excerpt, webLog.getExcerpt());
	}

	public void testParse28591754() throws IOException, SAXException {
		testParse("entry.do_m_edit_id_28591754.html", "测试：标题。", "测试：内容。",
				new String[] {}, "测试：摘要。");
	}

	public void testParse31430905() throws IOException, SAXException {
		testParse("entry.do_m_edit_id_31430905.html", "测试：标题。", "测试：内容。",
				new String[] {}, "测试：摘要。");
	}

	public void testParse33925019() throws IOException, SAXException {
		testParse(
				"entry.do_m_edit_id_33925019.html",
				"一个新的日志用来测试",
				org.apache.commons.lang.StringEscapeUtils
						.unescapeXml("这里填写日志内容，这是一个为了 Blog Mover 测试而写的日志。&lt;br /&gt;再搞第二行。&lt;br /&gt;第三行。&lt;br /&gt;&lt;br /&gt;"),
				new String[] { "标签1", "标签2", "再来一个标签" }, "这里填写摘要信息，就这个是摘要了。");
	}
}
