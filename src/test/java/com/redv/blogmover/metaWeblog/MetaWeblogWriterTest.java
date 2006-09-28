/**
 * Created on 2006-9-26 下午09:18:51
 */
package com.redv.blogmover.metaWeblog;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.junit.Test;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author Shutra
 * 
 */
public class MetaWeblogWriterTest {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(MetaWeblogWriterTest.class);

	/**
	 * Test method for
	 * {@link com.redv.blogmover.impl.AbstractBlogWriter#write(java.util.List)}.
	 * 
	 * @throws BlogMoverException
	 * @throws MalformedURLException
	 */
	// @Test
	public void testWriteBokeland() throws BlogMoverException,
			MalformedURLException {
		MetaWeblogWriter w = new MetaWeblogWriter();
		w.setServerURL("http://www.bokeland.com/xmlrpc.php");
		w.setBlogid("722");
		w.setUsername("blogmover");
		w.setPassword("xpert.cn");
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("test");
		webLog.setBody(org.apache.commons.lang.StringEscapeUtils
				.escapeHtml("测试"));
		webLog.setPublishedDate(new Date());
		webLogs.add(webLog);
		w.write(webLogs);
	}

	/**
	 * Get all blogs of a user.
	 * 
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 * 
	 */
	@SuppressWarnings("unchecked")
	// @Test
	public void getAllBlogsFromBokeland() throws MalformedURLException,
			XmlRpcException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://www.bokeland.com/xmlrpc.php"));
		config.setEnabledForExtensions(false);
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
		Object[] params = new Object[] { "dummy", "blogmover", "xpert.cn" };
		Object o = client.execute(config, "blogger.getUsersBlogs", params);
		Object[] result = (Object[]) o;
		assertEquals(result.length, 1);
		Map<String, String> m = (Map<String, String>) result[0];
		assertEquals(m.get("blogid"), "722");
		assertEquals(m.get("blogName"), "Blog Mover");

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAllBlogsFromIBlog() throws MalformedURLException,
			XmlRpcException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://cn.iblog.com/xmlrpc.php"));
		config.setEnabledForExtensions(false);
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
		Object[] params = new Object[] { "dummy", "blogmover", "xpert.cn" };
		Object o = client.execute(config, "blogger.getUsersBlogs", params);
		Object[] result = (Object[]) o;
		assertEquals(result.length, 1);
		log.debug(result[0]);
		Map<String, String> m = (Map<String, String>) result[0];
		assertEquals(m.get("blogid"), "17392");
		assertEquals(m.get("blogName"), "Blog Mover");
	}

	@Test
	public void testWriteIBlog() throws BlogMoverException,
			MalformedURLException {
		MetaWeblogWriter w = new MetaWeblogWriter();
		w.setServerURL("http://cn.iblog.com/xmlrpc.php");
		w.setBlogid("17392");
		w.setUsername("blogmover");
		w.setPassword("xpert.cn");
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("test");
		webLog.setBody("测试");
		webLog.setPublishedDate(new Date());
		webLogs.add(webLog);
		w.write(webLogs);
	}

}
