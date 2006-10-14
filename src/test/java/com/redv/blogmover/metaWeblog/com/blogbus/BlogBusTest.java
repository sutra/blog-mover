/**
 * Created on 2006-10-14 上午02:32:36
 */
package com.redv.blogmover.metaWeblog.com.blogbus;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.junit.Test;

import com.redv.bloggerapi.client.Fault;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.metaWeblog.cn.xpert.XpertTest;

/**
 * @author Shutra
 * 
 */
public class BlogBusTest {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(XpertTest.class);

	@Test
	public void testGetUsersBlogs() throws MalformedURLException, Fault {
		/*
		 * Object[] params = new Object[] { "dummy33", "blogmover", "xpert.cn" };
		 * Object o = this.execute("blogger.getUsersBlogs", params); Object[]
		 * result = (Object[]) o; int len = result.length; Blog[] blogs = new
		 * Blog[len]; for (int i = 0; i < len; i++) { Map<String, String> m =
		 * (Map<String, String>) result[i]; blogs[i] = new Blog();
		 * blogs[i].setBlogid(m.get("blogid"));
		 * blogs[i].setBlogName(m.get("blogName"));
		 * blogs[i].setUrl(m.get("url")); }
		 */
	}

	@SuppressWarnings("unused")
	private Object execute(String methodName, Object[] params) throws Fault,
			MalformedURLException {
		try {
			XmlRpcClient client = new XmlRpcClient();
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://rpc.blogbus.com/blogger"));
			config.setEnabledForExtensions(false);
			client.setConfig(config);
			client
					.setTransportFactory(new XmlRpcCommonsTransportFactory(
							client));

			return client.execute(methodName, params);
		} catch (XmlRpcException e) {
			Fault fault = new Fault(e.getMessage(), e.getCause());
			fault.setCode(e.code);
			throw fault;
		}

	}

	@Test
	public void testWriteRead() throws BlogMoverException,
			MalformedURLException {
		// XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		// config.setServerURL(new URL("http://rpc.blogbus.com/blogger"));
		// config.setEnabledForExtensions(Boolean.TRUE);
		//
		// XmlRpcClient client = new XmlRpcClient();
		// client.setConfig(config);
		// Object[] params = new Object[4];
		// params[0] = "blogmover";
		// params[1] = username;
		// params[2] = password;
		// params[3] = Integer.MAX_VALUE;
		// Object o;
		// try {
		// o = client.execute("metaWeblog.getRecentPosts", params);
		// } catch (XmlRpcException e) {
		// throw new BlogMoverRuntimeException(e);
		// }
		// Object[] objects = (Object[]) o;
		// List<WebLog> webLogs = new ArrayList<WebLog>(objects.length);
		// for (Object obj : objects) {
		// Map m = (Map) obj;
		// WebLog webLog = new WebLogImpl();
		// webLog.setUrl(ObjectUtils.toString(m.get("permaLink"), null));
		// // userid
		// webLog.setTitle(m.get("title").toString());
		// webLog.setBody(m.get("description").toString());
		// log.debug(webLog.getBody());
		// webLog.setPublishedDate((Date) m.get("pubDate"));
		// webLogs.add(webLog);
		// }
	}
}
