/**
 *  Created on 2006-7-19 22:30:48
 */
package com.redv.blogmover.bsps.sina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.HttpDocument;

/**
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
		String action = "http://blog.sina.com.cn/login.php?url=%2Fcontrol%2F";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		NameValuePair parameter = new NameValuePair("loginname", loginname);
		parameters.add(parameter);

		parameter = new NameValuePair("passwd", passwd);
		parameters.add(parameter);

		parameter = new NameValuePair("checkwd", checkwd);
		parameters.add(parameter);

		Document document = httpDocument.post(action, parameters);

		NodeList scripts = document.getElementsByTagName("script");
		boolean ok = false;
		for (int i = 0; i < scripts.getLength(); i++) {
			Element input = (Element) scripts.item(i);
			if (input != null) {
				Node node = input.getFirstChild();
				if (node != null) {
					String s = node.getNodeValue();
					log.debug("s: " + s);
					if (" top.window.location.href = \"http://blog.sina.com.cn/control/\"; "
							.equals(s)) {
						ok = true;
						break;
					}
				}
			}
		}
		if (!ok) {
			throw new LoginFailedException("用户名密码或者验证码有误，请重新输入，或者重新获取验证码。");
		}
	}

	public static byte[] getIdentifyingCodeImage(HttpClient httpClient)
			throws HttpException, IOException {
		String url = "http://blog.sina.com.cn/myblog/checkwd_image.php";
		GetMethod method = new GetMethod(url);
		httpClient.executeMethod(method);
		return method.getResponseBody();
	}

}
