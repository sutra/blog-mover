/**
 * Created on 2006-10-14 下午05:25:32
 */
package com.redv.blogmover.metaWeblog.cn.blogme;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

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
public class BlogMeTest {
	// @Test
	public void testGetUsersBlogs() throws MalformedURLException, Fault {
		Blogger blogger = new BloggerImpl("http://www.blogme.cn/xmlrpc.php");
		Blog[] blogs = blogger
				.getUsersBlogs("dummy", "blogmover", "8474639898");
		assertEquals(blogs.length, 1);
		assertEquals(blogs[0].getBlogid(), "blog");
		assertEquals(blogs[0].getBlogName(), "blogmover: blog");
	}

	// @Test
	public void testRead() throws MalformedURLException, BlogMoverException {
		MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		metaWeblogReader.setServerURL("http://www.blogme.cn/xmlrpc.php");
		metaWeblogReader.setUsername("blogmover");
		metaWeblogReader.setPassword("8474639898");
		metaWeblogReader.setBlogid("722");
		List<WebLog> webLogs = metaWeblogReader.read();
		assertEquals(webLogs.size(), 1);
	}
}
