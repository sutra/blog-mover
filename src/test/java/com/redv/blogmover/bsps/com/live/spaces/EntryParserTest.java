/**
 * Created on 2007-4-22 上午11:51:31
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutra
 * 
 */
public class EntryParserTest extends TestCase {
	private final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/M/dd h:mm:ss");

	private EntryParser ep;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		ep = new EntryParser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testParseId() {
		String actualId = EntryParser
				.parseId("http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!975.entry");
		assertEquals("Id is not equals.", "cns!2B070A76FD6627CE!975", actualId);
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.live.spaces.EntryParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testParse() throws IOException, SAXException, ParseException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass()
				.getResource("entry_zhoushuqun.spaces.live.com.html"), "UTF-8");
		ep.setDocument(document);
		ep
				.setId(EntryParser
						.parseId("http://zhoushuqun.spaces.live.com/blog/cns!2B070A76FD6627CE!975.entry"));
		ep.parse();
		WebLog webLog = ep.getWebLog();
		assertEquals("Title is not equals.", "今天去整了一个主板、CPU、电源和风扇", webLog
				.getTitle());
		String expectedBody = "今天去整了一个主板、CPU、电源和风扇，然后把原有的2个256的DDR内存给插上了，硬盘那是以前在学校买的一个希捷120G的，显卡、网卡都是集成的。<BR/>由于这里速度太慢，上传图片太久，看<A href=\"http://blog.redv.com/shutra/entry/4\">这里</A>吧。<BR/>下面给个图，哈哈：";
		assertEquals("Body is not equals.", expectedBody, webLog.getBody());

		assertEquals("Publish date is not equals.", sdf
				.parse("2007/4/20 1:29:58"), webLog.getPublishedDate());
	}

}
