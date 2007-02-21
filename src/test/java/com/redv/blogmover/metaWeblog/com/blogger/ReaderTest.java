/**
 * Created on 2006-11-10 下午09:33:05
 */
package com.redv.blogmover.metaWeblog.com.blogger;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.bloggerapi.client.Blog;
import com.redv.bloggerapi.client.Blogger;
import com.redv.bloggerapi.client.BloggerImpl;
import com.redv.bloggerapi.client.Fault;
import com.redv.bloggerapi.client.User;

/**
 * @author Shutra
 * 
 */
public class ReaderTest {
	private static final Log log = LogFactory.getLog(ReaderTest.class);

	// @Test
	public void test() throws MalformedURLException, Fault {
		Blogger blogger = new BloggerImpl("http://www.blogger.com/api");
		User user = blogger.getUserInfo("", "shutra", "wangjing");
		assertEquals("Shutra's", user.getNickname());
		assertEquals("", user.getFirstname());
		assertEquals("", user.getLastname());
		assertEquals("34207197", user.getUserid());
		assertEquals("", user.getUrl());
		Blog[] blogs = blogger.getUsersBlogs("", "shutra", "wangjing");
		assertEquals(1, blogs.length);
		assertEquals("37213153", blogs[0].getBlogid());
		assertEquals("Shutra's", blogs[0].getBlogName());
		assertEquals("http://shutra.blogspot.com", blogs[0].getUrl());
		String id = blogger.newPost("", "37213153", "shutra", "wangjing",
				"test", true);
		log.debug(id);
	}
}
