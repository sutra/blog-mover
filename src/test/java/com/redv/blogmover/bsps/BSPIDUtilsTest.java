/**
 * 
 */
package com.redv.blogmover.bsps;

import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import com.redv.blogmover.bsps.baidu.BaiduReader;
import com.redv.blogmover.bsps.baidu.BaiduWriter;
import com.redv.blogmover.bsps.blogger.GDataWriter;
import com.redv.blogmover.bsps.bokee.BokeeReader;
import com.redv.blogmover.bsps.bokee.BokeeWriter;
import com.redv.blogmover.bsps.cn.com.blog.BlogWriter;
import com.redv.blogmover.bsps.com.sohu.blog.SohuBlogReader;
import com.redv.blogmover.bsps.com.sohu.blog.SohuBlogWriter;
import com.redv.blogmover.bsps.csdn.CSDNBlogReader;
import com.redv.blogmover.bsps.csdn.CSDNBlogWriter;
import com.redv.blogmover.bsps.donews.DoNewsBlogReader;
import com.redv.blogmover.bsps.donews.DoNewsBlogWriter;
import com.redv.blogmover.bsps.hexun.HexunReader;
import com.redv.blogmover.bsps.sina.SinaReader;
import com.redv.blogmover.bsps.sina.SinaWriter;
import com.redv.blogmover.feed.RomeWriter;
import com.redv.blogmover.metaWeblog.MetaWeblogReader;
import com.redv.blogmover.metaWeblog.MetaWeblogWriter;

/**
 * @author shutrazh
 * 
 */
public class BSPIDUtilsTest extends TestCase {

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.BSPIDUtils#getId(java.lang.Class)}.
	 */
	public void testGetId() {
		String id = BSPIDUtils
				.getId(com.redv.blogmover.bsps.baidu.BaiduReader.class);
		assertEquals("baidu", id);
	}

	public void testBaidu() {
		assertEquals("baidu", new BaiduReader().getBsp().getId());
		assertEquals("baidu", new BaiduWriter().getBsp().getId());
	}

	public void testBlogcn() {
		assertEquals("blog.com.cn", new BlogWriter().getBsp().getId());
	}

	public void testBlogger() {
		assertEquals("blogger", new GDataWriter().getBsp().getId());
	}

	public void testBokee() {
		assertEquals("bokee", new BokeeReader().getBsp().getId());
		assertEquals("bokee", new BokeeWriter().getBsp().getId());
	}

	public void testCsdn() throws HttpException, IOException, SAXException {
		assertEquals("csdn", new CSDNBlogReader().getBsp().getId());
		assertEquals("csdn", new CSDNBlogWriter().getBsp().getId());
	}

	public void testDonews() {
		assertEquals("donews", new DoNewsBlogReader().getBsp().getId());
		assertEquals("donews", new DoNewsBlogWriter().getBsp().getId());
	}

	public void testHexun() {
		assertEquals("hexun", new HexunReader().getBsp().getId());
	}

	public void testSina() {
		assertEquals("sina", new SinaReader().getBsp().getId());
		assertEquals("sina", new SinaWriter().getBsp().getId());
	}

	public void testSohu() {
		assertEquals("sohu", new SohuBlogReader().getBsp().getId());
		assertEquals("sohu", new SohuBlogWriter().getBsp().getId());
	}

	public void testRome() {
		assertEquals("feed.rss-atom", new RomeWriter().getBsp().getId());
	}

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
