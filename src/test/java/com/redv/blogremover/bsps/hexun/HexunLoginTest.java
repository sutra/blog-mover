package com.redv.blogremover.bsps.hexun;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;

import com.redv.blogremover.BlogRemoverException;
import com.redv.blogremover.util.HttpDocument;

public class HexunLoginTest extends TestCase {
	HexunLogin hexunLogin;

	public HexunLoginTest(String name) {
		super(name);
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpDocument httpDocument = new HttpDocument(httpClient, null, true,
				"GBK");
		hexunLogin = new HexunLogin(httpDocument);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for 'com.redv.blogremover.bsps.hexun.HexunLogin.login(String,
	 * String, String)'
	 */
	public void testLogin() throws BlogRemoverException {
		hexunLogin.login("blogremover", "wangjing",
				"http://blog.hexun.com/group/inc/login.aspx");
	}

}
