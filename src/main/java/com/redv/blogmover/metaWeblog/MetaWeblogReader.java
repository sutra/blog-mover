/**
 * Created on 2006-9-25 下午09:49:45
 */
package com.redv.blogmover.metaWeblog;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
//import org.apache.xmlrpc.XmlRpcClient;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;

/**
 * @author Shutra
 * 
 */
public class MetaWeblogReader extends AbstractBlogReader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws MalformedURLException,
			XmlRpcException {
//		URL serverURL = new URL("http://blog.csdn.net/blogremover/services/MetaBlogApi.aspx");
//		String blogid = "blogremover";
//		String username = "blogremover";
//		String password = "wangjing";
//		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//		config.setServerURL(serverURL);
//		// config.setEnabledForExtensions(Boolean.TRUE);
//		// config.setBasicEncoding("UTF-8");
//
//		XmlRpcClient client = new XmlRpcClient();
//		client.setConfig(config);
//		Object[] params = new Object[4];
//		params[0] = blogid;
//		params[1] = username;
//		params[2] = password;
//		params[3] = 10;
//		Object o = client.execute("metaWeblog.getRecentPosts", params);
//		Object[] a = (Object[]) o;
//		System.out.println(a.length);
	}

}
