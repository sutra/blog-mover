/**
 *  Created on 2006-7-2 5:31:58
 */
package com.redv.blogmover.bsps.com.sohu.blog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class SohuBlogLogin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2738863475426731485L;

	private static final Log log = LogFactory.getLog(SohuBlogLogin.class);

	private HttpDocument httpDocument;

	/**
	 * The form action of the login form.
	 */
	private final String action = "http://blog.sohu.com/loginProxy";

	/**
	 * 
	 */
	public SohuBlogLogin(HttpClient httpClient) {
		HeaderGroup requestHeaderGroup = new HeaderGroup();
		requestHeaderGroup.addHeader(new Header("User-Agent",
				ManageUrlConstants.USER_AGENT));
		this.httpDocument = new HttpDocument(httpClient, requestHeaderGroup,
				"GBK");
	}

	public void login(String username, String mailDomain, String password)
			throws LoginFailedException {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new NameValuePair("loginid", username + mailDomain));
		parameters.add(new NameValuePair("username", username));
		parameters.add(new NameValuePair("maildomain", mailDomain));
		parameters.add(new NameValuePair("passwd", password));
		parameters.add(new NameValuePair("Submit", ""));
		Document document = this.httpDocument.post(action, parameters);
		NodeList titles = document.getElementsByTagName("title");
		if (titles.getLength() != 0
				&& titles.item(0).getFirstChild().getNodeValue().equals("错误")) {
			// System error returned.
			throw new BlogMoverRuntimeException("请求失败");
		} else {
			NodeList forms = document.getElementsByTagName("form");
			if (forms.getLength() == 0) {
				throw new BlogMoverRuntimeException(
						"No redirecting form found.");
			}
			// The ssl chain of sohu passport is not correct, so we use the http
			// instead of https to access the passport.
			// Element form = (Element) forms.item(0);
			// String action =
			// StringUtils.trimToNull(form.getAttribute("action"));
			String action = "http://passport.sohu.com/sso/login_js.jsp";
			NodeList inputs = document.getElementsByTagName("input");
			parameters = new ArrayList<NameValuePair>(inputs.getLength());
			for (int i = 0; i < inputs.getLength(); i++) {
				Element input = (Element) inputs.item(i);
				String name = input.getAttribute("name");
				String value = input.getAttribute("value");
				parameters.add(new NameValuePair(name, value));
			}
			document = this.httpDocument.post(action, parameters);
			DomNodeUtils.debug(log, document);
			Element blogUrlElementDiv = document.getElementById("blogUrl");
			if (blogUrlElementDiv == null) {
				throw new LoginFailedException("登录失败，用户名或者密码输入有误。");
			}
			DomNodeUtils.debug(log, blogUrlElementDiv);
			log.debug("blogUrlElement className: "
					+ blogUrlElementDiv.getClass().getName());
			NodeList childNodes = blogUrlElementDiv.getChildNodes();
			Node blogUrlElementA = null;
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				log.debug("node className: " + node.getClass().getName());
				if ("a".equalsIgnoreCase(node.getNodeName())) {
					blogUrlElementA = node;
					break;
				}
			}
			String blogUrl = blogUrlElementA.getAttributes().getNamedItem(
					"href").getNodeValue();
			log.debug("blogUrl: " + blogUrl);
			Pattern p = Pattern.compile("http://([^.]+).blog.sohu.com/");
			Matcher m = p.matcher(blogUrl);
			if (m.find()) {
				String blogDomain = m.group(1);
				log.debug("blogDomain: " + blogDomain);
			} else {
				throw new BlogMoverRuntimeException(
						"No blog domain group(group 1) found.");
			}
		}
	}

	public static void main(String[] args) throws LoginFailedException {
		new SohuBlogLogin(new HttpClient()).login("zhoushuqun2000",
				"@chinaren.com", "wangjing");
	}
}
