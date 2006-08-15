/**
 *  Created on 2006-7-18 21:46:30
 */
package com.redv.blogremover.bsps.baidu;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.redv.blogremover.BlogRemoverException;
import com.redv.blogremover.util.DomNodeUtils;
import com.redv.blogremover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class BaiduLogin {
	@SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(this.getClass());

	private HttpDocument httpDocument;

	/**
	 * 
	 */
	public BaiduLogin(HttpDocument httpDocument) {
		this.httpDocument = httpDocument;
	}

	public void login(String username, String password)
			throws BlogRemoverException {
		String action = "http://passport.baidu.com/?login&tpl=sp&tpl_reg=sp&u=http://hi.baidu.com/";
		String u = "http://hi.baidu.com/";
		NameValuePair[] parameters = new NameValuePair[4];
		parameters[0] = new NameValuePair("u", u);
		parameters[1] = new NameValuePair("username", username);
		parameters[2] = new NameValuePair("password", password);
		parameters[3] = new NameValuePair("submit", "登录");
		HeaderGroup hg = new HeaderGroup();
		hg.addHeader(new Header("Content-Type",
				"application/x-www-form-urlencoded; charset=GB2312"));
		Document document = httpDocument.post(action, parameters, hg);
		NodeList anchors = document.getElementsByTagName("a");
		boolean ok = false;
		for (int i = 0; i < anchors.getLength(); i++) {
			String s = DomNodeUtils.getTextContent(anchors.item(i));
			if (s.equalsIgnoreCase(username)) {
				ok = true;
				break;
			}
		}
		if (!ok) {
			throw new BlogRemoverException("登录失败，用户名或者密码错误。");
		}
	}
}
