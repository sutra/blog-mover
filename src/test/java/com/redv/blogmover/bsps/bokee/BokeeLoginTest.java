/**
 * Created on 2006-8-22 下午09:12:41
 */
package com.redv.blogmover.bsps.bokee;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;

import com.redv.blogmover.util.HttpDocument;

import junit.framework.TestCase;

/**
 * @author Shutra
 * 
 */
public class BokeeLoginTest extends TestCase {
	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private BokeeLogin bokeeLogin;

	/**
	 * @param name
	 */
	public BokeeLoginTest(String name) {
		super(name);
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, "GBK");
		bokeeLogin = new BokeeLogin(httpClient, httpDocument);
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.bokee.BokeeLogin#login(java.lang.String, java.lang.String)}.
	 */
	public void testLoginStringString() {
		bokeeLogin.login("blogremover", "wangjing");
		httpDocument
				.get("http://blogremover.bokee.com/control/diary/postDiary.b");
	}

}
