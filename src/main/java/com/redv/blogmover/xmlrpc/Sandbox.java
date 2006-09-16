/**
 * Created on 2006-9-16 上午11:30:53
 */
package com.redv.blogmover.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * @author Shutra
 * 
 */
public class Sandbox {
	public static void main(String[] args) throws MalformedURLException, XmlRpcException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://xpert.cn/xmlrpc"));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		Object[] params = new Object[] {"blogmover", "shutra", "********"};
		Object ret = client.execute("metaWeblog.getCategories", params);
		System.out.println(ret);
		
		
	}
}
