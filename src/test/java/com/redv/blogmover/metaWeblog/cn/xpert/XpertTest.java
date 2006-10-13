/**
 * Created on 2006-10-12 下午09:45:20
 */
package com.redv.blogmover.metaWeblog.cn.xpert;

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
public class XpertTest {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(XpertTest.class);

	@Test
	public void testGetUsersBlogs() throws MalformedURLException, Fault {
		Blogger blogger = new BloggerImpl("http://xpert.cn/xmlrpc");
		Blog[] blogs = blogger.getUsersBlogs("dummy", "joe", "xpert.cn");
		// for (Blog blog : blogs) {
		// log.debug(blog);
		// }
		assertEquals(blogs.length, 1);
		assertEquals(blogs[0].getBlogid(), "joe");
	}

	@Test
	public void testWriteRead() throws BlogMoverException,
			MalformedURLException {
		MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		metaWeblogReader.setServerURL("http://xpert.cn/xmlrpc");
		metaWeblogReader.setUsername("joe");
		metaWeblogReader.setPassword("xpert.cn");
		metaWeblogReader.setBlogid("joe");
		List<WebLog> webLogs = metaWeblogReader.read();
		assertEquals(webLogs.size(), 0);
	}
}
