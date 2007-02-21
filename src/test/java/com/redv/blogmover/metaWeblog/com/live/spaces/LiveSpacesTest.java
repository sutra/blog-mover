/**
 * Created on 2006-10-14 上午02:48:21
 */
package com.redv.blogmover.metaWeblog.com.live.spaces;

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
 * @see <a
 *      href="http://wangxiaohu.com/index.php/2006/08/06/windows-live-spaces%E7%9A%84metaweblog-api%E7%9A%84%E5%BC%80%E5%90%AF/">Windows
 *      Live Spaces的MetaWeblog API的开启</a>
 */
public class LiveSpacesTest {
	// @Test
	public void testGetUsersBlogs() throws MalformedURLException, Fault {
		Blogger blogger = new BloggerImpl(
				"https://storage.msn.com/storageservice/MetaWeblog.rpc");
		Blog[] blogs = blogger
				.getUsersBlogs("dummy", "blogmover", "dd393abcc3");
		assertEquals(blogs.length, 1);
		assertEquals(blogs[0].getBlogid(), "MyBlog");
		assertEquals(blogs[0].getBlogName(), "Blog Mover的共享空间");
		assertEquals(blogs[0].getUrl(),
				"http://spaces.msn.com/members/blogmover");
	}

	// @Test
	public void testRead() throws MalformedURLException, BlogMoverException {
		MetaWeblogReader metaWeblogReader = new MetaWeblogReader();
		metaWeblogReader
				.setServerURL("https://storage.msn.com/storageservice/MetaWeblog.rpc");
		metaWeblogReader.setUsername("blogmover");
		metaWeblogReader.setPassword("dd393abcc3");
		metaWeblogReader.setBlogid("MyBlog");
		metaWeblogReader.setNumberOfPosts("20");
		List<WebLog> webLogs = metaWeblogReader.read();
		assertEquals(webLogs.size(), 0);
	}
}
