/**
 * Created on 2007-1-27 下午12:26:56
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.w3c.dom.Document;

import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.HttpDocument;

/**
 * To login to http://www.blogchinse.com with http client.
 * <p>
 * Test account: <br />
 * username: blogmover, <br />
 * password: blogmover
 * </p>
 * 
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class BlogChineseLogin {
	private static final String loginActionUrl = "http://www.blogchinese.com/login.asp";

	private HttpDocument httpDocument;

	public BlogChineseLogin(HttpDocument httpDocument) {
		this.httpDocument = httpDocument;
	}

	/**
	 * Try to login to the http://www.blogchinese.com/.
	 * 
	 * @param username
	 * @param password
	 * @throws LoginFailedException
	 *             if login failed as username or password is not correct.
	 */
	public void login(String username, String password)
			throws LoginFailedException {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new NameValuePair("username", username));
		parameters.add(new NameValuePair("password", password));
		parameters.add(new NameValuePair("login", "登录"));
		Document document = httpDocument.post(loginActionUrl, parameters);
		boolean loggedIn = new LoginResponseParser()
				.checkLoginSuccess(document);
		if (!loggedIn) {
			throw new com.redv.blogmover.LoginFailedException("用户名或者密码不正确。");
		}
	}
}
