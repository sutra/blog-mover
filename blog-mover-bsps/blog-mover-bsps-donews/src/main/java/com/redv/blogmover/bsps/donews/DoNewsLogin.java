/**
 *  Created on 2006-7-16 2:41:03
 */
package com.redv.blogmover.bsps.donews;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class DoNewsLogin implements Serializable {
	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = -6557665616746697405L;

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	/**
	 * 
	 */
	public DoNewsLogin(HttpClient httpClient, HttpDocument httpDocument) {
		super();
		this.httpClient = httpClient;
		this.httpDocument = httpDocument;
	}

	public void login(String username, String password, String validationKey)
			throws DOMException, BlogMoverException {
		String loginUrl = "http://writeblog.donews.com/login.aspx?ReturnUrl=/editposts.aspx";
		log.debug("获取登录页面，url：" + loginUrl);
		Document document = httpDocument.get(loginUrl);
		String action = document.getElementById("frmLogin").getAttribute(
				"action");
		if (!action.startsWith("http://")) {
			if (!action.startsWith("/")) {
				action = "/" + action;
			}
			URI uri = null;
			try {
				uri = new URI(loginUrl);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
			action = "http://" + uri.getHost() + action;
		}
		NodeList inputs = document.getElementsByTagName("input");
		NameValuePair[] parameters = new NameValuePair[inputs.getLength()];
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			parameters[i] = new NameValuePair();
			parameters[i].setName(name);
			if (StringUtils.equals(name, "tbUserName")) {
				parameters[i].setValue(username);
			} else if (StringUtils.equals(name, "tbPassword")) {
				parameters[i].setValue(password);
			} else if (StringUtils.equals(name, "ValidationKey")) {
				parameters[i].setValue(validationKey);
			} else if (StringUtils.equals(name, "__EVENTTARGET")) { // __doPostBack('lblLogin','')
				parameters[i].setValue("lblLogin");
			} else {

				parameters[i].setValue(value);
			}
		}
		log.debug("执行登录 HttpPost。");
		log.debug("Action: " + action);
		Document loginResultDocument = httpDocument.post(action, parameters);
		Element vKValidator = loginResultDocument.getElementById("VKValidator");
		Element message = loginResultDocument.getElementById("Message");
		if (vKValidator != null) {
			throw new LoginFailedException("验证码输入有误："
					+ vKValidator.getNodeValue());
		} else if (message != null
				&& StringUtils.isNotEmpty(message.getNodeValue())) {
			throw new LoginFailedException("登录失败：" + message.getNodeValue());
		}
	}

	public byte[] getIdentifyingCodeImage() throws HttpException, IOException {
		String url = "http://writeblog.donews.com/VKImage.aspx";
		GetMethod method = new GetMethod(url);
		httpClient.executeMethod(method);
		return method.getResponseBody();
	}

}
