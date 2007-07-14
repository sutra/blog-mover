/**
 * Created on 2007-2-3 上午12:06:19
 */
package com.redv.blogmover.blogengines.cn.oblog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.w3c.dom.Document;

import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public abstract class AbstractOBlogLogin {

	protected HttpDocument httpDocument;

	public AbstractOBlogLogin(HttpDocument httpDocument) {
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
		Document document = httpDocument
				.post(buildLoginActionUrl(), parameters);
		boolean loggedIn = checkLoginSuccess(document);
		if (!loggedIn) {
			throw new com.redv.blogmover.LoginFailedException("用户名或者密码不正确。");
		}
	}

	protected abstract String buildLoginActionUrl();

	protected abstract boolean checkLoginSuccess(Document document);

}
