package com.redv.blogmover.bsps.hexun;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.bsps.hexun.HexunReader;

import junit.framework.TestCase;

public class HexunReaderTest extends TestCase {
	private static final Log log = LogFactory.getLog(HexunReaderTest.class);

	private HexunReader hexunReader;

	public HexunReaderTest(String name) {
		super(name);
		hexunReader = new HexunReader();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for 'com.redv.blogremover.bsps.hexun.HexunReader.read()'
	 */
	public void testRead() throws BlogMoverException {
		hexunReader.setUsername("blogremover");
		hexunReader.setPassword("wangjing");
		List<WebLog> webLogs = hexunReader.read();
		log.debug("webLogs.size(): " + webLogs.size());
		for (WebLog webLog : webLogs) {
			log.debug("url: " + webLog.getUrl());
			log.debug("publishedDate: " + webLog.getPublishedDate());
			log.debug("title: " + webLog.getTitle());
			log.debug("body: " + webLog.getBody());
		}

	}

	/*
	 * Test method for
	 * 'com.redv.blogremover.impl.AbstractBlogReader.getStatus()'
	 */
	public void testGetStatus() {

	}

	public void testParseTags() {
		String t = "a, b, c";
		String[] tags = this.hexunReader.parseTags(t);
		assertEquals(tags[0], "a");
		assertEquals(tags[1], "b");
		assertEquals(tags[2], "c");

		t = "a, b,c,";
		tags = this.hexunReader.parseTags(t);
		assertEquals(tags[0], "a");
		assertEquals(tags[1], "b");
		assertEquals(tags[2], "c");

		t = "a b c";
		tags = this.hexunReader.parseTags(t);
		assertEquals(tags[0], "a");
		assertEquals(tags[1], "b");
		assertEquals(tags[2], "c");

		t = "a b \"c d\"";
		tags = this.hexunReader.parseTags(t);
		assertEquals(tags[0], "a");
		assertEquals(tags[1], "b");
		assertEquals(tags[2], "c d");
	}

}
