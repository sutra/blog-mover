package com.redv.blogremover.bsps.donews;

import org.apache.commons.httpclient.HttpClient;

import com.redv.blogremover.BlogRemoverRuntimeException;
import com.redv.blogremover.util.HttpDocument;

import junit.framework.TestCase;

public class DonewsLoginTest extends TestCase {
	private DoNewsLogin donewsLogin;

	protected void setUp() throws Exception {
		super.setUp();
		HttpClient httpClient = new HttpClient();
		HttpDocument httpDocument = new HttpDocument(httpClient);
		donewsLogin = new DoNewsLogin(httpClient, httpDocument);
		try {
			donewsLogin.login("blogremover", "wangjing", "");
		} catch (BlogRemoverRuntimeException ex) {
			assertEquals(ex.getMessage(), "请输入验证码。");
		}
	}

	/*
	 * Test method for
	 * 'com.redv.blogremover.bsps.donews.DonewsLogin.login(String, String,
	 * String)'
	 */
	public void testLogin() {

	}

}
