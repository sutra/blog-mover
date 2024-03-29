/**
 * Created on 2006-10-12 下午11:28:01
 */
package com.redv.blogmover.metaWeblog.com.bokeland;

import java.net.MalformedURLException;
import java.util.List;

import junit.framework.TestCase;

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
public class BokelandTest extends TestCase {
	public void test() {
		assertTrue(true);
	}

	public void _testGetUsersBlogs() throws MalformedURLException, Fault {
		Blogger blogger = new BloggerImpl("http://www.bokeland.com/xmlrpc.php");
		Blog[] blogs = blogger.getUsersBlogs("dummy", "blogmover", "xpert.cn");
		assertEquals(blogs.length, 1);
		assertEquals(blogs[0].getBlogid(), "722");
		assertEquals(blogs[0].getBlogName(), "Blog Mover");
	}

	public void _testRead() throws MalformedURLException, BlogMoverException {
		MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		metaWeblogReader.setServerURL("http://www.bokeland.com/xmlrpc.php");
		metaWeblogReader.setUsername("blogmover");
		metaWeblogReader.setPassword("xpert.cn");
		metaWeblogReader.setBlogid("722");
		List<WebLog> webLogs = metaWeblogReader.read();
		assertTrue(webLogs.size() > 0);
	}
}
