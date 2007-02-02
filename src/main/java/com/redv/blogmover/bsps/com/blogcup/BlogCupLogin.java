/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.w3c.dom.Document;

import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.HttpDocument;

/**
 * Login from <a
 * href="http://blogcup.com/login.asp">http://blogcup.com/login.asp</a>.
 * <p>
 * Test account:<br />
 * username: blogmover<br />
 * password: blogmover
 * </p>
 * 
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
class BlogCupLogin {
	private static final String loginActionUrl = "http://blogcup.com/login.asp";

	private HttpDocument httpDocument;

	public BlogCupLogin(HttpDocument httpDocument) {
		this.httpDocument = httpDocument;
	}

	/**
	 * Try to login to the http://blogcup.com/.
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
		parameters.add(new NameValuePair("CookieDate", "0"));
		parameters.add(new NameValuePair("Submit.x", "35"));
		parameters.add(new NameValuePair("Submit.y", "21"));
		parameters.add(new NameValuePair("Submit", "提交"));
		Document document = httpDocument.post(loginActionUrl, parameters);
		LoginResponseParser parser = new LoginResponseParser();
		parser.setDocument(document);
		parser.parse();
		boolean loggedIn = parser.isLoggedIn();
		if (!loggedIn) {
			throw new com.redv.blogmover.LoginFailedException("用户名或者密码不正确。");
		}
	}

	public static void main(String[] args) throws LoginFailedException,
			UnsupportedEncodingException {
		System.out.println(URLDecoder.decode("%E6%8F%90%E4%BA%A4", "UTF-8"));

		HttpClient httpClient = new HttpClient();
		HttpDocument httpDocument = new HttpDocument(httpClient, "UTF-8");
		new BlogCupLogin(httpDocument).login("blogmover", "blogmover");
	}
}
