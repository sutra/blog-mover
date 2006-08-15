package com.redv.blogremover.bsps.baidu;

import org.apache.commons.httpclient.HttpClient;

import com.redv.blogremover.BlogRemoverException;
import com.redv.blogremover.util.HttpDocument;

import junit.framework.TestCase;

public class BaiduLoginTest extends TestCase {
	private BaiduLogin baiduLogin;

	protected void setUp() throws Exception {
		super.setUp();
		HttpClient httpClient = new HttpClient();
		HttpDocument httpDocument = new HttpDocument(httpClient, "GB2312");
		baiduLogin = new BaiduLogin(httpDocument);
	}

	/*
	 * Test method for 'com.redv.blogremover.bsps.baidu.BaiduLogin.login(String,
	 * String)'
	 */
	public void testLogin() throws BlogRemoverException {
		baiduLogin.login("blogremover", "wangjing");
	}

}
