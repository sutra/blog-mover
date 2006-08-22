/**
 * Created on 2006-7-6 14:41:58
 */
package com.redv.blogmover.bsps.bokee;

import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 */
public class BokeeReader extends AbstractBlogReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5088575804415839973L;

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private BokeeLogin bokeeLogin;

	private String username;

	private String password;

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public BokeeReader() {
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, "GBK");
		bokeeLogin = new BokeeLogin(httpClient, httpDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		bokeeLogin.login(username, password);
		return null;
	}

}
