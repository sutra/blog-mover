/**
 * Created on 2006-9-26 下午09:18:51
 */
package com.redv.blogmover.metaWeblog;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	/**
	 * Test method for
	 * {@link com.redv.blogmover.impl.AbstractBlogWriter#write(java.util.List)}.
	 * 
	 * @throws BlogMoverException
	 * @throws MalformedURLException
	 */
	//@Test
	public void testWrite() throws BlogMoverException, MalformedURLException {
		assertEquals("", "");
		MetaWeblogWriter w = new MetaWeblogWriter();
		w.setServerURL("http://www.bokeland.com/xmlrpc.php");
		w.setBlogid("722");
		w.setUsername("blogmover");
		w.setPassword("xpert.cn");
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("test");
		webLog.setBody("test");
		webLog.setPublishedDate(new Date());
		webLogs.add(webLog);
		w.write(webLogs);
	}
	
	@Test
	public void testWriteToXpert() throws BlogMoverException, MalformedURLException {
		MetaWeblogWriter w = new MetaWeblogWriter();
		w.setServerURL("http://xpert.cn/xmlrpc");
		w.setBlogid("shutra");
		w.setUsername("shutra");
		w.setPassword("wangjing");
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("test");
		webLog.setBody("test");
		webLog.setPublishedDate(new Date());
		webLogs.add(webLog);
		w.write(webLogs);
	}
	
	/**
	 * Get all blogs of a user.
	 * @throws MalformedURLException 
	 * @throws XmlRpcException 
	 *
	 */
	//@Test
	public void getAllBlogs() throws MalformedURLException, XmlRpcException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://blog.csdn.net/blogremover/services/MetaBlogApi.aspx"));
		config.setEnabledForExtensions(Boolean.TRUE);
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
		Object[] params = new Object[] {"dummy", "blogremover", "blogremover"};
		Object o = client.execute("blogger.getUsersBlogs", params);
		System.out.println(o);
	}
	
	//@Test
	public void testIBlog() throws BlogMoverException, MalformedURLException {
		MetaWeblogWriter w = new MetaWeblogWriter();
		w.setServerURL("http://cn.iblog.com/xmlrpc.php");
		w.setBlogid("");
	}

}
