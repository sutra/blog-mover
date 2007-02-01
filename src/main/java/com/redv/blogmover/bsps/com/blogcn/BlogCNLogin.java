/**
 * Created on 2007-2-1 上午12:18:39
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.w3c.dom.Document;

import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.HttpDocument;

/**
 * Login for <a href="http://www.blogcn.com/">http://www.blogcn.com</a>.
 * <p>
 * Test account:<br />
 * Username: blogmoverdev<br />
 * Password: blogmoverdev<br />
 * </p>
 * 
 * @author shutra
 * 
 */
public class BlogCNLogin {
	private static final String LOGIN_ACTION_URL = "http://login.blogcn.com/cgi-bin/Login/Login.aspx";

	private HttpDocument httpDocument;

	public BlogCNLogin(HttpDocument httpDocument) {
		this.httpDocument = httpDocument;
	}

	public void login(String username, String password)
			throws LoginFailedException {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new NameValuePair("UserName", username));
		parameters.add(new NameValuePair("Password", password));
		parameters.add(new NameValuePair("RedirectUrl",
				"http://login.blogcn.com/mc/newfm.aspx"));
		Document document = httpDocument.post(LOGIN_ACTION_URL, parameters);

		LoginResponseParser parser = new LoginResponseParser();
		parser.setDocument(document);
		parser.parse();
		if (parser.getResult() == LoginResponseParser.RESULT_USERNAME_NOT_EXISTS) {
			throw new LoginFailedException("用户名不存在，请重新输入");
		} else if (parser.getResult() == LoginResponseParser.RESULT_PASSWORD_INCORRECT) {
			throw new LoginFailedException("密码不正确，请重新输入");
		}
	}

	public static void main(String[] args) throws LoginFailedException {
		HttpClient httpClient = new HttpClient();
		HttpDocument httpDocument = new HttpDocument(httpClient);
		// OK
		new BlogCNLogin(httpDocument).login("blogmoverdev", "blogmoverdev");
		// Password error.
		try {
			new BlogCNLogin(httpDocument).login("blogmoverdev",
					"blogmoverdeverror");
		} catch (LoginFailedException ex) {
		}
		// Username error.
		try {
			new BlogCNLogin(httpDocument).login("blogmoverdeverror",
					"blogmoverdev");
		} catch (LoginFailedException ex) {
		}
	}
}
