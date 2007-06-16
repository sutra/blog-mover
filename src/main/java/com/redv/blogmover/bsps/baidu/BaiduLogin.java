/**
 *  Created on 2006-7-18 21:46:30
 */
package com.redv.blogmover.bsps.baidu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

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
		parameters[3] = new NameValuePair("submit", "登录");
		HeaderGroup hg = new HeaderGroup();
		hg.addHeader(new Header("Content-Type",
				"application/x-www-form-urlencoded; charset=GB2312"));
		Document document = httpDocument.post(action, parameters, hg);
//		NodeList anchors = document.getElementsByTagName("a");
		boolean ok = false;
//		for (int i = 0; i < anchors.getLength(); i++) {
//			String s = DomNodeUtils.getTextContent(anchors.item(i));
//			if (s != null && s.equalsIgnoreCase(username)) {
//				ok = true;
//				break;
//			}
//		}
		
		NodeList scripts = document.getElementsByTagName("script");
		if (scripts.getLength() > 0) {
			try {
				String s = DomNodeUtils.getXmlAsString(scripts.item(0));
				if (s.indexOf("location.href=\"http://hi.baidu.com/\"") != -1) {
					ok = true;
				}
			} catch (TransformerException e) {
				log.error(e);
			}
		}

		if (!ok) {
			String errorString = "登录失败，用户名或者密码错误。（Blog mover 无法获知更多错误信息。）";
			if (scripts.getLength() >= 4) {
				try {
					String s = DomNodeUtils.getXmlAsString(scripts.item(4));
					Pattern pattern = Pattern.compile("switch\\(([\\d])+\\)");
					Matcher matcher = pattern.matcher(s);
					log.debug("s: " + s);
					if (matcher.find()) {
						int errorCode = Integer.parseInt(matcher.group(1));
						errorString = getErrorString(errorCode, username);
					}
				} catch (TransformerException e) {
					log.error(e);
					errorString = "登录失败，用户名或者密码错误。（Blog mover 无法获知更多错误信息。）";
				}
			}
			throw new LoginFailedException(errorString);
		}
	}
	
	private String getErrorString(int errorCode, String username) {
		String err_str;
		switch(errorCode)
		{
		        case 1: 
		                err_str = "用户名格式错误，请重新输入";
		                break;
		        case 2:
		                err_str = "用户“" + username + "”不存在";
		                break;
		        case 3:
		                err_str = "";
		                break;
		        case 4:
		                err_str = "登录密码错误，请重新输入";
		                break;
		        case 5:
		                err_str = "今日登录次数过多";
		                break;
		        case 6:
		                err_str = "验证码不匹配，请重新输入验证码";
		                break;
		        case 7:
		                err_str = "登录时发生未知错误，请重新输入";
		                break;
		        case 8:
		                err_str = "登录时发生未知错误，请重新输入";
		                break;
		        case 16:
		                err_str = "对不起，您现在无法登录";
		                break;
		        case 256:
		                err_str = "";
		                break;
		        default:
		                err_str = "登录时发生未知错误，请重新输入";
		                break;
		}
		return err_str;
	}
}
