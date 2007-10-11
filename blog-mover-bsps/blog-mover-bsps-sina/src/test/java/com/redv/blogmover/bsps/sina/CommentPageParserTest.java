/**
 * 
 */
package com.redv.blogmover.bsps.sina;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.Comment;
import com.redv.blogmover.impl.CommentImpl;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author sutra
 * 
 */
public class CommentPageParserTest {

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
	 * {@link com.redv.blogmover.bsps.sina.CommentPageParser#CommentPageParser(org.w3c.dom.Document)}
	 * 的测试方法。
	 * 
	 * @throws SAXException
	 *             if xml parse error
	 * @throws IOException
	 *             if io error
	 * @throws ParseException
	 */
	@Test
	public void testCommentPageParser() throws IOException, SAXException,
			ParseException {
		HtmlFileToDocument htmlFileToDocument = new HtmlFileToDocument();
		Document document = htmlFileToDocument.getDocument(this.getClass()
				.getResource("comment_46f37fb501000b53_1.html"), "UTF-8");
		CommentPageParser commentPageParser = new CommentPageParser(document);
		List<Comment> comments = commentPageParser.getComments();
		assertEquals(50, comments.size());
		Comment comment = new CommentImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		comment.setName("[匿名] 新浪网友");
		comment.setPublishedDate(sdf.parse("2007-09-22 11:33:04"));
		comment.setContent("sf哈哈<BR/>" + SystemUtils.LINE_SEPARATOR
				+ "ENJOY SUNSHINE");
		assertEquals(comment.getContent(), comments.get(0).getContent());
		assertEquals(comment, comments.get(0));
	}

	@Test
	public void testNoComment() throws IOException, SAXException {
		HtmlFileToDocument htmlFileToDocument = new HtmlFileToDocument();
		Document document = htmlFileToDocument.getDocument(this.getClass()
				.getResource("comment_46f37fb501000b53_11.html"), "UTF-8");
		CommentPageParser commentPageParser = new CommentPageParser(document);
		List<Comment> comments = commentPageParser.getComments();
		assertEquals(0, comments.size());
	}

	@Test
	public void testUrlBuild() {
		String firstPageUrl = "http://blog.sina.com.cn/s/comment_46f37fb501000b53_1.html";
		String pattern = firstPageUrl.substring(0, firstPageUrl.length() - 6)
				+ "%1$s.html";
		assertEquals(
				"http://blog.sina.com.cn/s/comment_46f37fb501000b53_%1$s.html",
				pattern);
	}
}
