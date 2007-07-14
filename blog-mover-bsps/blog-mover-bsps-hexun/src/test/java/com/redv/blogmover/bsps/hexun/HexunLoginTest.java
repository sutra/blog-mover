package com.redv.blogmover.bsps.hexun;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.bsps.hexun.HexunLogin;
import com.redv.blogmover.util.HttpDocument;

public class HexunLoginTest extends TestCase {
	HexunLogin hexunLogin;

	protected void setUp() throws Exception {
		super.setUp();
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpDocument httpDocument = new HttpDocument(httpClient, null, true,
				"GBK");
		hexunLogin = new HexunLogin(httpDocument);
	}
	
	public void test() {
		assertTrue(true);
	}

	/*
	 * Test method for 'com.redv.blogremover.bsps.hexun.HexunLogin.login(String,
	 * String, String)'
	 */
	public void _testLogin() throws BlogMoverException {
		hexunLogin.login("blogremover", "wangjing",
				"http://blog.hexun.com/group/inc/login.aspx");
	}

}
