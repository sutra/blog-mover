/**
 * 
 */
package com.redv.blogmover.bsps;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.redv.blogmover.bsps.baidu.BaiduReader;
import com.redv.blogmover.bsps.baidu.BaiduWriter;
import com.redv.blogmover.bsps.blogger.GDataWriter;
import com.redv.blogmover.bsps.bokee.BokeeReader;
import com.redv.blogmover.bsps.bokee.BokeeWriter;
import com.redv.blogmover.bsps.cn.com.blog.BlogWriter;
import com.redv.blogmover.bsps.csdn.CSDNBlogReader;
import com.redv.blogmover.bsps.csdn.CSDNBlogWriter;
import com.redv.blogmover.bsps.donews.DoNewsBlogReader;
import com.redv.blogmover.bsps.donews.DoNewsBlogWriter;
import com.redv.blogmover.bsps.hexun.HexunReader;
import com.redv.blogmover.bsps.sina.SinaReader;
import com.redv.blogmover.bsps.sina.SinaWriter;
import com.redv.blogmover.bsps.sohu.SohuBlogReader;
import com.redv.blogmover.bsps.sohu.SohuBlogWriter;
import com.redv.blogmover.feed.RomeWriter;
import com.redv.blogmover.metaWeblog.MetaWeblogReader;
import com.redv.blogmover.metaWeblog.MetaWeblogWriter;

/**
 * @author shutrazh
 * 
 */
public class BSPIDUtilsTest {

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
	 * Test method for
	 * {@link com.redv.blogmover.bsps.BSPIDUtils#getId(java.lang.Class)}.
	 */
	@Test
	public void testGetId() {
		String id = BSPIDUtils
				.getId(com.redv.blogmover.bsps.baidu.BaiduReader.class);
		assertEquals("baidu", id);
	}

	@Test
	public void testBaidu() {
		assertEquals("baidu", new BaiduReader().getBsp().getId());
		assertEquals("baidu", new BaiduWriter().getBsp().getId());
	}

	@Test
	public void testBlogcn() {
		assertEquals("blogcn", new BlogWriter().getBsp().getId());
	}

	@Test
	public void testBlogger() {
		assertEquals("blogger", new GDataWriter().getBsp().getId());
	}

	@Test
	public void testBokee() {
		assertEquals("bokee", new BokeeReader().getBsp().getId());
		assertEquals("bokee", new BokeeWriter().getBsp().getId());
	}

	@Test
	public void testCsdn() throws HttpException, IOException, SAXException {
		assertEquals("csdn", new CSDNBlogReader().getBsp().getId());
		assertEquals("csdn", new CSDNBlogWriter().getBsp().getId());
	}

	@Test
	public void testDonews() {
		assertEquals("donews", new DoNewsBlogReader().getBsp().getId());
		assertEquals("donews", new DoNewsBlogWriter().getBsp().getId());
	}

	@Test
	public void testHexun() {
		assertEquals("hexun", new HexunReader().getBsp().getId());
	}

	@Test
	public void testSina() {
		assertEquals("sina", new SinaReader().getBsp().getId());
		assertEquals("sina", new SinaWriter().getBsp().getId());
	}

	@Test
	public void testSohu() {
		assertEquals("sohu", new SohuBlogReader().getBsp().getId());
		assertEquals("sohu", new SohuBlogWriter().getBsp().getId());
	}

	@Test
	public void testRome() {
		assertEquals("feed.rss-atom", new RomeWriter().getBsp().getId());
	}

	@Test
	public void testMetaWebLog() throws MalformedURLException {
		MetaWeblogReader mwr = new MetaWeblogReader();
		MetaWeblogWriter mww = new MetaWeblogWriter();
		assertEquals("metaWeblog", mwr.getBsp().getId());
		assertEquals("metaWeblog", mww.getBsp().getId());

		mwr.setServerURL("http://example.com/test");
		mww.setServerURL("http://example.com/test");

		assertEquals("http://example.com/test", mwr.getBsp().getId());
		assertEquals("http://example.com/test", mww.getBsp().getId());

	}
}
