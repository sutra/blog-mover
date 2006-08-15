/**
 *  Created on 2006-7-12 21:10:07
 */
package com.redv.blogremover.bsps.hexun;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogremover.BlogRemoverRuntimeException;
import com.redv.blogremover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class HexunLogin {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(HexunLogin.class);

	private HttpDocument httpDocument;

	HexunLogin(HttpDocument httpDocument) {
		this.httpDocument = httpDocument;
	}

	void login(String username, String password, String gourl) {
		String url = "http://blog.hexun.com/group/inc/login.aspx";
		Document document = httpDocument.get(url);
		NodeList forms = document.getElementsByTagName("form");
		if (forms.getLength() == 0) {
			throw new BlogRemoverRuntimeException("登录表单获取失败，和讯 Blog 登录服务器故障。");
		}
		Element form = (Element) forms.item(0);
		String action = form.getAttribute("action");
		NodeList inputs = document.getElementsByTagName("input");
		int len = inputs.getLength();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(len);
		for (int i = 0; i < len; i++) {
			NameValuePair parameter = new NameValuePair();
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			parameter.setName(name);
			String value = input.getAttribute("value");
			if ("username".equals(name)) {
				parameter.setValue(username);
			} else if ("password".equals(name)) {
				parameter.setValue(password);
			} else if ("gourl".equals(name)) {
				parameter.setValue(url);
			} else {
				parameter.setValue(value);
			}
			parameters.add(parameter);
		}
		parameters.add(new NameValuePair("x", "17"));
		parameters.add(new NameValuePair("y", "25"));

		document = httpDocument.post(action, parameters);

		NodeList scripts = document.getElementsByTagName("script");
		for (int i = 0; i < scripts.getLength(); i++) {
			String alert = scripts.item(i).getFirstChild().getNodeValue();
			if (alert.startsWith("alert")) {
				throw new BlogRemoverRuntimeException("用户名或密码错误。");
			}
		}
	}
}
