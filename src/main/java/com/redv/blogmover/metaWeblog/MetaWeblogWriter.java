/**
 * Created on 2006-9-24 下午11:08:40
 */
package com.redv.blogmover.metaWeblog;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;

/**
 * @author Shutra
 * 
 */
public class MetaWeblogWriter extends AbstractBlogWriter {
	// ISO.8601
	// private static final SimpleDateFormat sdf = new SimpleDateFormat(
	// "yyyyMMdd'T'HH:mm:ss");

	private XmlRpcClient client;

	private URL serverURL;

	private String blogid;

	private String username;

	private String password;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#begin()
	 */
	@Override
	protected void begin() throws BlogMoverException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(serverURL);
		config.setEnabledForExtensions(Boolean.TRUE);
		config.setEncoding("GBK");
		client = new XmlRpcClient();
		client.setConfig(config);
		client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeAttachment(com.redv.blogmover.Attachment)
	 */
	@Override
	protected String writeAttachment(Attachment att) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeBlog(com.redv.blogmover.WebLog,
	 *      java.util.Map)
	 */
	@Override
	protected void writeBlog(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogMoverException {
		Map<String, Object> content = new Hashtable<String, Object>();
		content.put("title", webLog.getTitle());
		content.put("description", webLog.getBody());
		content.put("dateCreated", webLog.getPublishedDate());

		List<Object> params = new ArrayList<Object>(5);
		params.add(blogid);
		params.add(username);
		params.add(password);
		params.add(content);
		params.add(Boolean.TRUE);
		try {
			client.execute("metaWeblog.newPost", params);
		} catch (XmlRpcException e) {
			throw new BlogMoverRuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeComment(com.redv.blogmover.Comment)
	 */
	@Override
	protected void writeComment(Comment comment) throws BlogMoverException {

	}

}
