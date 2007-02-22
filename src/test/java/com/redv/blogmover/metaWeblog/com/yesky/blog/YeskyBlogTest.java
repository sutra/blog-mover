/**
 * Created on 2006-10-14 下午04:52:30
 */
package com.redv.blogmover.metaWeblog.com.yesky.blog;

import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.bloggerapi.client.Fault;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.metaWeblog.net.csdn.blog.CSDNBlogTest;

/**
 * @author Shutra
 * 
 */
public class YeskyBlogTest extends TestCase {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(CSDNBlogTest.class);

	public void _testGetUsersBlogs() throws MalformedURLException, Fault {
		// Blogger blogger = new BloggerImpl(
		// "http://blog.yesky.com/Blog/blogmover/services/MetaBlogApi.aspx");
		// Blog[] blogs = blogger.getUsersBlogs("dummy", "blogmover",
		// "xpert.cn");
		// assertEquals(blogs.length, 1);
		// assertEquals(blogs[0].getBlogid(), "150895");
		// assertEquals(blogs[0].getBlogName(), "Blog Mover");
	}

	public void testRead() throws MalformedURLException, BlogMoverException {
		// MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		// metaWeblogReader
		// .setServerURL("http://blog.yesky.com/Blog/blogmover/services/MetaBlogApi.aspx");
		// metaWeblogReader.setUsername("blogmover");
		// metaWeblogReader.setPassword("xpert.cn");
		// metaWeblogReader.setBlogid("150895");
		// List<WebLog> webLogs = metaWeblogReader.read();
		// assertEquals(webLogs.size(), 0);
		//
		// for (WebLog webLog : webLogs) {
		// log.debug(webLog.toString());
		// }
	}
}
