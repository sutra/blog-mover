/**
 *  Created on 2006-7-19 22:30:48
 */
package com.redv.blogmover.bsps.sina;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * Account for testing:
 * 
 * <pre>
 * username: blogmover1
 * password: blogmover
 * url: http://blog.sina.com.cn/blogmover
 * </pre>
 * <pre>
 * username: 博客搬家
 * password: blogmover
 * url: http://blog.sina.com.cn/blogmover1
 * </pre>
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public class SinaLogin {
	private final Log log = LogFactory.getLog(SinaLogin.class);

	private HttpDocument httpDocument;

	/**
	 * 
	 */
	public SinaLogin(HttpDocument httpDocument) {
		this.httpDocument = httpDocument;
	}

	public void login(String loginname, String passwd, String checkwd)
			throws BlogMoverException {
		// String action =
		// "http://blog.sina.com.cn/login.php?url=%2Fcontrol%2F";
		String action = "http://my.blog.sina.com.cn/login.php?url=%2F";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		NameValuePair parameter = new NameValuePair("loginname", loginname);
		parameters.add(parameter);

		parameter = new NameValuePair("passwd", passwd);
		parameters.add(parameter);

		parameter = new NameValuePair("checkwd", checkwd);
		parameters.add(parameter);

		Document document = httpDocument.post(action, parameters);
		DomNodeUtils.debug(log, document);

		NodeList scripts = document.getElementsByTagName("script");
		for (int i = 0; i < scripts.getLength(); i++) {
			Element input = (Element) scripts.item(i);
			if (input != null) {
				Node node = input.getFirstChild();
				if (node != null) {
					String s = node.getNodeValue();
					log.debug("s: " + s);
					if (s.indexOf("很抱歉，您输入的登录名不存在，请输入正确的登录名：") != -1) {
						throw new LoginFailedException(
								"很抱歉，您输入的登录名不存在，请输入正确的登录名。");
					} else if (s.indexOf("很抱歉，您输入的登录密码错误，请重新输入：") != -1) {
						throw new LoginFailedException("很抱歉，您输入的登录密码错误，请重新输入。");
					} else {
						break;
					}
				}
			}
		}
	}

	public static byte[] getIdentifyingCodeImage(HttpClient httpClient)
			throws HttpException, IOException {
		// String url = "http://blog.sina.com.cn/myblog/checkwd_image.php";
		String url = "http://my.blog.sina.com.cn/myblog/checkwd_image.php?"
				+ System.currentTimeMillis();
		GetMethod method = new GetMethod(url);
		httpClient.executeMethod(method);
		return method.getResponseBody();
	}

	public static void main(String[] args) throws HttpException, IOException,
			BlogMoverException {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpDocument httpDocument = new HttpDocument(httpClient);
		httpDocument.setRequestCharSet("GB2312");
		SinaLogin login = new SinaLogin(httpDocument);
		Reader r = new InputStreamReader(System.in);
		LineNumberReader lnr = new LineNumberReader(r);
		System.out.print("Please enter your username: ");
		String username = lnr.readLine();
		System.out.println("Your entered username is: " + username.toString());
		System.out.print("Please enter your password: ");
		String password = lnr.readLine();
		System.out.println("Your entered password is: " + password.toString());
		byte[] image = getIdentifyingCodeImage(httpClient);
		File file = new File(SystemUtils.JAVA_IO_TMPDIR, SinaReader.class
				.getName()
				+ ".png");
		FileUtils.writeByteArrayToFile(file, image);
		System.out.print(String.format(
				"Please enter the code on the image(%1$s): ", file.getPath()));
		String identifyingCode = lnr.readLine();
		System.out.println("Your entered code is: " + identifyingCode);
		if (!file.delete()) {
			file.deleteOnExit();
		}
		try {
			login.login(username.toString(), password.toString(),
					identifyingCode.toString());
			System.out.println("Login OK.");
		} catch (LoginFailedException ex) {
			System.out.println("Login failed.");
		}
	}
}
