/**
 * Created on 2006-7-6 14:45:20
 */
package com.redv.blogmover.bsps.bokee;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
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

import com.redv.blogmover.BlogMoverException;
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
		String action = "http://reg.bokee.com/account/LoginCtrl.b";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new NameValuePair("username", username));
		parameters.add(new NameValuePair("password", password));
		parameters.add(new NameValuePair("save", "0"));
		parameters.add(new NameValuePair("action", "1"));
		parameters.add(new NameValuePair("url", ""));
		parameters.add(new NameValuePair("send", ""));
		Document document = httpDocument.post(action, parameters);

		NodeList forms = document.getElementsByTagName("form");
		for (int i = 0; i < forms.getLength(); i++) {
			Element form = (Element) forms.item(i);
			action = form.getAttribute("action");
		}
		parameters.clear();
		NodeList inputs = document.getElementsByTagName("input");
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			parameters.add(new NameValuePair(name, value));
		}
		document = httpDocument.post(action, parameters);
	}

}
