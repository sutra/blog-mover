/**
 *  Created on 2006-7-31 11:16:24
 */
package com.redv.blogmover.bsps.csdn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpClientUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author <a href="zhoushuqun@gmail.com">Shutra Zhou</a>
 * @version 1.0
 * @see 如何获得浏览器在登录CSDN时POST的参数呢，使用Winsock Expert可以轻松跟踪。
 */
public class CSDNLogin {
	private final Log log = LogFactory.getLog(CSDNLogin.class);

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private String loginPageUrl = "http://passport.csdn.net/UserLogin.aspx?from=http%3a%2f%2fwriteblog.csdn.net%2fLogin.aspx%3fReturnUrl%3d%2fDefault.aspx";

	private Document loginPage;

	/**
	 * 
	 */
	public CSDNLogin(HttpClient httpClient) {
		this.httpClient = httpClient;

		HeaderGroup hg = new HeaderGroup();
		hg
				.addHeader(new Header(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-CN; rv:1.8.0.6) Gecko/20060728 Firefox/1.5.0.6"));
		hg
				.addHeader(new Header(
						"Accept",
						"text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5"));
		hg.addHeader(new Header("Accept-Language",
				"zh-cn,zh-tw;q=0.7,en-us;q=0.3"));
		hg.addHeader(new Header("Accept-Encoding", "gzip, deflate"));
		hg
				.addHeader(new Header("Accept-Charset",
						"gb2312,utf-8;q=0.7,*;q=0.7"));
		// hg.addHeader(new Header("Host", "passport.csdn.net"));
		hg.addHeader(new Header("Keep-Alive", "300"));
		hg.addHeader(new Header("Connection", "Keep-Alive"));

		this.httpDocument = new HttpDocument(httpClient, hg, true, "UTF-8");
	}

	private Document getLoginPage() {
		String url = loginPageUrl;
		if (this.loginPage == null) {
			this.loginPage = httpDocument.get(url);
			// httpDocument.get("http://www.csdn.net/LoginPageSideColumns.aspx");
		}
		return loginPage;
	}

	public void login(String username, String password, String identifyingCode)
			throws BlogMoverException {
		Document loginPageDocument = this.getLoginPage();

		// Parse form.
		Element form = loginPageDocument.getElementById("Form1");
		String action = "http://passport.csdn.net/"
				+ form.getAttribute("action");
		log.debug("action=" + action);
		String __VIEWSTATE = loginPageDocument.getElementById("__VIEWSTATE")
				.getAttribute("value");
		log.debug("__VIEWSTATE: " + __VIEWSTATE);
		String clientKey = null;
		NodeList inputs = loginPageDocument.getElementsByTagName("input");
		for (int i = 0; i < inputs.getLength(); i++) {
			Element e = (Element) inputs.item(i);
			if (e.getAttribute("name").equals("ClientKey")) {
				clientKey = e.getAttribute("value");
			}
		}
		log.debug("clientKey=" + clientKey);
		String from = loginPageDocument.getElementById("from").getAttribute(
				"value");
		log.debug("from=" + from);
		/*
		String __EVENTVALIDATION = loginPageDocument.getElementById(
				"__EVENTVALIDATION").getAttribute("value");
		log.debug("__EVENTVALIDATION: " + __EVENTVALIDATION);
		*/

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new NameValuePair("__EVENTTARGET", ""));
		parameters.add(new NameValuePair("__EVENTARGUMENT", ""));
		parameters.add(new NameValuePair("__VIEWSTATE", __VIEWSTATE));
		parameters
				.add(new NameValuePair("CSDNUserLogin$tb_UserName", username));
		parameters
				.add(new NameValuePair("CSDNUserLogin$tb_Password", password));
		parameters.add(new NameValuePair("ClientKey", clientKey));
		parameters.add(new NameValuePair("CSDNUserLogin$tb_ExPwd",
				identifyingCode));
		parameters
				.add(new NameValuePair("from",
						"http://writeblog.csdn.net/Login.aspx?ReturnUrl=%2fDefault.aspx"));
		parameters.add(new NameValuePair("CSDNUserLogin$Image_Login.x", "24"));
		parameters.add(new NameValuePair("CSDNUserLogin$Image_Login.y", "0"));
		/*
		parameters
				.add(new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION));
		*/
		if (log.isDebugEnabled()) {
			log.debug("parameters......");
			for (NameValuePair nvp : parameters) {
				log.debug(nvp.getName() + ": " + nvp.getValue());
			}
			log.debug("......parameters");
		}

		// Submit login form.
		HeaderGroup requestHeaderGroup = new HeaderGroup();
		requestHeaderGroup.addHeader(new Header("Referer", loginPageUrl));
		requestHeaderGroup.addHeader(new Header("Content-type",
				"application/x-www-form-urlencoded; charset=gb2312"));
		Document document = httpDocument.post(action, parameters,
				requestHeaderGroup);
		log.debug("---------------------- login response -----------------");
		DomNodeUtils.debug(log, document);

		// httpDocument.get("http://www.csdn.net/LoginPageSideColumns.aspx");

		Cookie[] cookies = httpDocument.getHttpClient().getState().getCookies();
		log.debug("cookies...");
		for (Cookie cookie : cookies) {
			log.debug(cookie.getName() + ": " + cookie.getValue());
		}
		log.debug("...cookies");

		NodeList scripts = document.getElementsByTagName("script");
		for (int i = 0; i < scripts.getLength(); i++) {
			Element script = (Element) scripts.item(i);
			if (script.hasChildNodes()) {
				String v = script.getFirstChild().getNodeValue();
				log.debug("script v: " + v);

				if (v != null && v.indexOf("附加码不正确") != -1) {
					throw new BlogMoverException(
							"登录失败。验证码不正确，请检查你的用户名和密码或者重新获取验证码");
				}
			}
		}

		Element element = document.getElementById("CSDNUserLoginOK_lb_Name");
		if (element == null) {
			throw new BlogMoverException("Login failed.(CSDN Passport)");
		} else {
			String v = element.getFirstChild().getNodeValue();
			log.debug("v: " + v);
			if (v.indexOf(username) == -1) {
				throw new BlogMoverException(
						"登录失败。用户名、密码或者验证码不正确，请检查你的用户名和密码或者重新获取验证码");
			}
		}
	}

	public byte[] getIdentifyingCodeImage() throws BlogMoverException {
		String showExPwdUrl = "http://passport.csdn.net/ShowExPwd.aspx?temp=f8x70ory";
		/*
		String showExPwdUrl = "http://passport.csdn.net/"
				+ getShowExPwdUrl(this.getLoginPage());
		showExPwdUrl = showExPwdUrl.replace(" ", "%20");
		*/
		log.debug("showExPwdUrl: " + showExPwdUrl);
		try {
			return this.showExPwd(showExPwdUrl);
		} catch (HttpException e) {
			throw new BlogMoverException(e);
		} catch (IOException e) {
			throw new BlogMoverException(e);
		}
	}

	private byte[] showExPwd(String showExPwdUrl) throws HttpException,
			IOException {
		GetMethod showExPwdMethod = new GetMethod(showExPwdUrl);
		if (httpDocument.isManualCookie()) {
			HttpClientUtils.addCookies(showExPwdMethod, httpClient.getState()
					.getCookies());
		}

		httpClient.executeMethod(showExPwdMethod);

		byte[] bytes = showExPwdMethod.getResponseBody();
		try {
			return bytes;
		} finally {
			showExPwdMethod.releaseConnection();
		}
	}

	@SuppressWarnings("unused")
	private String getShowExPwdUrl(Document loginPageDocument) {
		// check code
		NodeList scripts = loginPageDocument.getElementsByTagName("script");
		String showExPwdUrl = null;
		for (int i = 0; i < scripts.getLength(); i++) {
			Element e = (Element) scripts.item(i);
			if (e.hasChildNodes()) {
				String value = DomNodeUtils.getTextContent(e.getChildNodes()
						.item(0));
				if (value != null
						&& value
								.startsWith("document.write(\"<img src='ShowExPwd.aspx?DateTime=")) {
					value = value.substring(26);
					int idx = value.indexOf("\"");
					value = value.substring(0, idx);
					showExPwdUrl = value + Math.random();
					log.debug("ShowExPwd url getted: " + showExPwdUrl);
				}
			}
		}
		return showExPwdUrl;
	}

	public static void main(String[] args) throws BlogMoverException,
			IOException {
		System.out.println(java.net.URLDecoder.decode("%24", "UTF-8"));
		HttpClient httpClient = new HttpClient();
		// 如果没有设定cookie模式将会有警告：Cookie rejected
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);

		CSDNLogin login = new CSDNLogin(httpClient);
		byte[] bytes = login.getIdentifyingCodeImage();
		FileOutputStream fos = new FileOutputStream("/tmp/csdn-login.png");
		fos.write(bytes);
		fos.close();
		System.out.print("Enter the verify code: ");
		int ch;
		StringBuffer verifyCode = new StringBuffer();
		while ((ch = System.in.read()) != '\n' && ch != '\r') {
			verifyCode.append((char) ch);
		}
		System.out.println("verifyCode: " + ch);
		login.login("redv", "wangjing", verifyCode.toString());
	}
}
