/**
 *  Created on 2006-7-18 21:46:30
 */
package com.redv.blogmover.bsps.baidu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
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
		this.httpDocument.get("http://www.baidu.com/");
		this.httpDocument
				.get("http://passport.baidu.com/?login&tpl=mn&u=http%3A//www.baidu.com/");
		String action = "http://passport.baidu.com/?login";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new NameValuePair("tpl_ok", StringUtils.EMPTY));
		parameters.add(new NameValuePair("next_target", StringUtils.EMPTY));
		parameters.add(new NameValuePair("tpl", "mn"));
		parameters.add(new NameValuePair("skip_ok", StringUtils.EMPTY));
		parameters.add(new NameValuePair("aid", StringUtils.EMPTY));
		parameters.add(new NameValuePair("need_pay", StringUtils.EMPTY));
		parameters.add(new NameValuePair("need_coin", StringUtils.EMPTY));
		parameters.add(new NameValuePair("u", "http://www.baidu.com/"));
		parameters.add(new NameValuePair("return_method", "get"));
		parameters.add(new NameValuePair("psp_tt", "0"));
		parameters.add(new NameValuePair("username", username));
		parameters.add(new NameValuePair("password", password));
		parameters.add(new NameValuePair("mem_pass", "on"));
		// parameters.add(new NameValuePair(StringUtils.EMPTY, "登录"));
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
		this.httpDocument.get("http://www.baidu.com/");
	}

}
