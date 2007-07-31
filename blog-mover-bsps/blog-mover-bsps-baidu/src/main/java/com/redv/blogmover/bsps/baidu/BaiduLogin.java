/**
 *  Created on 2006-7-18 21:46:30
 */
package com.redv.blogmover.bsps.baidu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
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
 * password: blogmover1
 * url: http://hi.baidu.com/blogmover1
 * </pre>
 * 
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
			throws BlogMoverException {
		String action = "http://passport.baidu.com/?login&tpl=sp&tpl_reg=sp&u=http://hi.baidu.com/";
		String u = "http://hi.baidu.com/";
		NameValuePair[] parameters = new NameValuePair[4];
		parameters[0] = new NameValuePair("u", u);
		parameters[1] = new NameValuePair("username", username);
		parameters[2] = new NameValuePair("password", password);
		parameters[3] = new NameValuePair("submit", " 登录 ");
		HeaderGroup hg = new HeaderGroup();
		hg.addHeader(new Header("Content-Type",
				"application/x-www-form-urlencoded; charset=GB2312"));
		Document document = httpDocument.post(action, parameters, hg);
		NodeList scripts = document.getElementsByTagName("script");
		int err_code = -1; // unknown error
		String err_msg = "未知错误";
		boolean ok = true;
		for (int i = 0; i < scripts.getLength(); i++) {
			String s = DomNodeUtils.getTextContent(scripts.item(i));
			if (s == null)
				continue;
			Pattern p = Pattern.compile("switch\\((\\d+)\\)");
			Matcher m = p.matcher(s);
			if (m.find()) {
				err_code = Integer.parseInt(m.group(1));
				p = Pattern.compile("case.*?" + err_code
						+ "[^\\d].*?err_str.*?\"(.*?)\"", Pattern.DOTALL);
				m = p.matcher(s);
				if (m.find())
					err_msg = m.group(1);
				ok = false;
				break;
			}
		}
		if (!ok) {
			throw new LoginFailedException("登录失败。错误信息:" + err_msg + "， 错误代码:"
					+ err_code);
		}
	}

}
