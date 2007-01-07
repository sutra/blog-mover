/**
 * Created on 2006-9-25 下午09:49:45
 */
package com.redv.blogmover.metaWeblog;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author Shutra
 * 
 */
public class MetaWeblogReader extends AbstractBlogReader {
	private URL serverURL;

	private String blogid;

	private String username;

	private String password;

	private int numberOfPosts = Integer.MAX_VALUE;

	/**
	 * @param blogid
	 *            the blogid to set
	 */
	public void setBlogid(String blogid) {
		this.blogid = blogid;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param serverURL
	 *            the serverURL to set
	 * @throws MalformedURLException
	 */
	public void setServerURL(String serverURL) throws MalformedURLException {
		this.serverURL = new URL(serverURL);

		this.bsp.setId(serverURL);
		this.bsp.setName(serverURL);
		this.bsp.setServerURL(serverURL);
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param numberOfPosts
	 *            the numberOfPosts to set
	 */
	public void setNumberOfPosts(String numberOfPosts) {
		this.numberOfPosts = NumberUtils
				.toInt(numberOfPosts, Integer.MAX_VALUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(serverURL);
		config.setEnabledForExtensions(Boolean.TRUE);

		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		Object[] params = new Object[4];
		params[0] = this.blogid;
		params[1] = this.username;
		params[2] = this.password;
		params[3] = this.numberOfPosts;
		Object o;
		try {
			o = client.execute("metaWeblog.getRecentPosts", params);
		} catch (XmlRpcException e) {
			throw new BlogMoverRuntimeException(e);
		}
		List<WebLog> webLogs = null;
		log.debug(o);
		if (o instanceof Object[]) {
			Object[] objects = (Object[]) o;
			webLogs = new ArrayList<WebLog>(objects.length);
			for (Object obj : objects) {
				Map m = (Map) obj;
				WebLog webLog = new WebLogImpl();
				webLog.setUrl(ObjectUtils.toString(m.get("permaLink"), null));
				// userid
				webLog.setTitle(m.get("title").toString());
				webLog.setBody(m.get("description").toString());
				log.debug(webLog.getBody());
				webLog.setPublishedDate((Date) m.get("pubDate"));
				webLogs.add(webLog);
			}
		}
		return webLogs;
	}

}
