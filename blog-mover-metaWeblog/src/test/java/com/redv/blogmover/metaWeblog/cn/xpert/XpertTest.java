/**
 * Created on 2006-10-12 下午09:45:20
 */
package com.redv.blogmover.metaWeblog.cn.xpert;

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

/**
 * @author Shutra
 * 
 */
public class XpertTest extends TestCase {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(XpertTest.class);
	
	public void test() {
		assertTrue(true);
	}

	public void _testGetUsersBlogs() throws MalformedURLException, Fault {
		// Version 2.3 is http://xpert.cn/xmlrpc.
		Blogger blogger = new BloggerImpl(
				"http://xpert.cn/roller-services/xmlrpc");
		Blog[] blogs = blogger.getUsersBlogs("dummy", "joe", "xpert.cn");
		// for (Blog blog : blogs) {
		// log.debug(blog);
		// }
		assertEquals(blogs.length, 1);
		assertEquals(blogs[0].getBlogid(), "joe");
	}

	public void _testWriteRead() throws BlogMoverException,
			MalformedURLException {
		MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		metaWeblogReader.setServerURL("http://xpert.cn/roller-services/xmlrpc");
		metaWeblogReader.setUsername("joe");
		metaWeblogReader.setPassword("xpert.cn");
		metaWeblogReader.setBlogid("joe");
		List<WebLog> webLogs = metaWeblogReader.read();
		assertEquals(webLogs.size(), 0);
	}
}
