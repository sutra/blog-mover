/**
 * Created on 2006-7-6 14:45:20
 */
package com.redv.blogmover.bsps.bokee;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.redv.blogmover.BlogRemoverException;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 */
public class BokeeLogin {
	private static final Log log = LogFactory.getLog(BokeeLogin.class);

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	public BokeeLogin(HttpClient httpClient, HttpDocument httpDocument) {
		this.httpClient = httpClient;
		this.httpDocument = httpDocument;
	}
	
	public void login(String username, String password) {
		
	}

	static void login(HttpClient httpClient, String username, String password)
			throws HttpException, IOException, SAXException,
			BlogRemoverException {
		PostMethod method = new PostMethod(
				"http://reg.bokee.com/account/LoginCtrl.b");
		method.addParameter("username", username);
		method.addParameter("password", password);
		method.addParameter("save", "0");
		method.addParameter("url", "");
		method.addParameter("action", "");
		httpClient.executeMethod(method);
		log.debug(method.getResponseBodyAsString());
		InputStream inputStream = method.getResponseBodyAsStream();
		DOMParser domParser = new DOMParser();
		try {
			InputSource inputSource = new InputSource(inputStream);
			domParser.parse(inputSource);
		} finally {
			inputStream.close();
			method.releaseConnection();
		}
		Document document = domParser.getDocument();
		NodeList forms = document.getElementsByTagName("form");
		if (forms.getLength() == 0) {
			throw new BlogRemoverException("Login failed.");
		}
		Element form = (Element) forms.item(0);
		String action = null, bokie = null, url = null;
		action = form.getAttribute("action");
		NodeList inputs = document.getElementsByTagName("input");
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			if (StringUtils.equals(name, "bokie")) {
				bokie = input.getAttribute("value");
			} else if (StringUtils.equals(name, "url")) {
				url = input.getAttribute("url");
			}
		}
		method = new PostMethod(action);
		if (bokie == null || url == null) {
			throw new BlogRemoverException("Login failed.");
		}
		method.addParameter("bokie", bokie);
		method.addParameter("url", url);
		httpClient.executeMethod(method);
		log.debug(method.getResponseBodyAsString());
	}
}
