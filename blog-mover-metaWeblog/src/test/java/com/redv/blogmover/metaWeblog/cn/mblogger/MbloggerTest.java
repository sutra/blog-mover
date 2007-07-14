/**
 * Created on 2006-10-14 下午05:52:09
 */
package com.redv.blogmover.metaWeblog.cn.mblogger;

import java.net.MalformedURLException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.bloggerapi.client.Blog;
import com.redv.bloggerapi.client.Blogger;
import com.redv.bloggerapi.client.BloggerImpl;
import com.redv.bloggerapi.client.Fault;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.metaWeblog.MetaWeblogReader;
import com.redv.blogmover.metaWeblog.net.csdn.blog.CSDNBlogTest;

/**
 * @author Shutra
 * 
 */
public class MbloggerTest extends TestCase {
	private static final Log log = LogFactory.getLog(CSDNBlogTest.class);

	public void test() {
		assertTrue(true);
	}

	public void _testGetUsersBlogs() throws MalformedURLException, Fault {
		Blogger blogger = new BloggerImpl(
				"http://java.mblogger.cn/blogmover/services/metablogapi.aspx");
		Blog[] blogs = blogger.getUsersBlogs("dummy", "blogmover", "xpert.cn");
		assertEquals(blogs.length, 1);
		assertEquals(blogs[0].getBlogid(), "1664");
		assertEquals(blogs[0].getBlogName(), "Blog Mover");
	}

	public void _testRead() throws MalformedURLException, BlogMoverException {
		MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		metaWeblogReader
				.setServerURL("http://java.mblogger.cn/blogmover/services/metablogapi.aspx");
		metaWeblogReader.setUsername("blogmover");
		metaWeblogReader.setPassword("xpert.cn");
		metaWeblogReader.setBlogid("1664");
		List<WebLog> webLogs = metaWeblogReader.read();
		assertEquals(webLogs.size(), 0);

		for (WebLog webLog : webLogs) {
			log.debug(webLog.toString());
		}
	}
}
