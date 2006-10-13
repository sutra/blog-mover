/**
 * Created on 2006-10-14 上午12:27:42
 */
package com.redv.blogmover.metaWeblog.net.csdn.blog;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.redv.bloggerapi.client.Blog;
import com.redv.bloggerapi.client.Blogger;
import com.redv.bloggerapi.client.BloggerImpl;
import com.redv.bloggerapi.client.Fault;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.metaWeblog.MetaWeblogReader;

/**
 * @author Shutra
 * 
 */
public class CSDNBlogTest {
	private static final Log log = LogFactory.getLog(CSDNBlogTest.class);

	@Test
	public void testGetUsersBlogs() throws MalformedURLException, Fault {
		Blogger blogger = new BloggerImpl(
				"http://blog.csdn.net/blogremover/services/MetaBlogApi.aspx");
		Blog[] blogs = blogger.getUsersBlogs("dummy", "blogremover",
				"blogremover");
		assertEquals(blogs.length, 1);
		assertEquals(blogs[0].getBlogid(), "142658");
		assertEquals(blogs[0].getBlogName(), "blogremover的专栏");
	}

	@Test
	public void testRead() throws MalformedURLException, BlogMoverException {
		MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		metaWeblogReader
				.setServerURL("http://blog.csdn.net/blogremover/services/MetaBlogApi.aspx");
		metaWeblogReader.setUsername("blogremover");
		metaWeblogReader.setPassword("blogremover");
		metaWeblogReader.setBlogid("142658");
		List<WebLog> webLogs = metaWeblogReader.read();
		assertEquals(webLogs.size(), 1148);

		for (WebLog webLog : webLogs) {
			log.debug(webLog.toString());
		}
	}
}
