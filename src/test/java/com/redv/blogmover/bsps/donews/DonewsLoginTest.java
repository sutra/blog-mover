package com.redv.blogmover.bsps.donews;

import org.apache.commons.httpclient.HttpClient;
import org.w3c.dom.DOMException;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.bsps.donews.DoNewsLogin;
import com.redv.blogmover.util.HttpDocument;

import junit.framework.TestCase;

public class DonewsLoginTest extends TestCase {
	private DoNewsLogin donewsLogin;

	protected void setUp() throws Exception {
		super.setUp();
		HttpClient httpClient = new HttpClient();
		HttpDocument httpDocument = new HttpDocument(httpClient);
		donewsLogin = new DoNewsLogin(httpClient, httpDocument);
	}

	/*
	 * Test method for
	 * 'com.redv.blogremover.bsps.donews.DonewsLogin.login(String, String,
	 * String)'
	 */
	public void _testLogin() throws DOMException, BlogMoverException {
		try {
			donewsLogin.login("blogremover", "wangjing", "");
		} catch (BlogMoverRuntimeException ex) {
			assertEquals(ex.getMessage(), "请输入验证码。");
		}
	}

	/**
	 * Test login by another user.
	 * 
	 * @throws DOMException
	 * @throws BlogMoverExcetption
	 */
	public void _testLoginByAnotherUsernamePasswordPair() throws DOMException,
			BlogMoverException {
		try {
			donewsLogin.login("blogremover", "wangjing", "");
		} catch (BlogMoverRuntimeException ex) {
			assertEquals(ex.getMessage(), "请输入验证码。");
		}
	}

}
