/**
 *  Created on 2006-6-24 17:24:22
 */
package com.redv.blogmover.bsps.csdn;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class CSDNBlogWriterUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(CSDNBlogWriterUtil.class);

	private CSDNBlogWriter writer;

	/**
	 * 
	 */
	CSDNBlogWriterUtil(CSDNBlogWriter writer) {
		super();
		this.writer = writer;
	}

	void login() throws HttpException, IOException, SAXException,
			BlogMoverException {
		log.debug("Start of login...");
		loginPassport();

		String url = "http://writeblog.csdn.net/Login.aspx?ReturnUrl=%2fDefault.aspx";
		GetMethod loginMethod = new GetMethod(url);
		log.debug("Cookie=" + this.cookieToHeaderString(writer.cookies));
		loginMethod.addRequestHeader("Cookie", this
				.cookieToHeaderString(writer.cookies));
		writer.httpClient.executeMethod(loginMethod);
		String s = loginMethod.getResponseBodyAsString();
		if (s.indexOf("已登录为") == -1) {
			log.debug("---- Login response start ----");
			log.error("登录失败： " + s);
			log.debug("---- Login response end ----");
			throw new BlogMoverException("Login failed.(CSDN Blog)");
		}
		log.debug("End of login.");
	}

	private void setCookie(String key, String value) {
		writer.cookies.remove(key);
		writer.cookies.put(key, value);
	}

	void setCookie(Header[] headers) {
		for (Header header : headers) {
			String[] ss = header.getValue().split(";");
			ss = ss[0].split("=");
			if (ss.length < 2) {
				this.setCookie(ss[0], "");
			} else {
				this.setCookie(ss[0], ss[1]);
			}
		}
	}

	String cookieToHeaderString(Map<String, String> cookies) {
		StringBuffer ret = new StringBuffer();
		for (Iterator<String> iter = cookies.keySet().iterator(); iter
				.hasNext();) {
			String key = iter.next();
			ret.append(key).append("=").append(cookies.get(key)).append("; ");
		}
		return ret.toString();
	}

	void loginPassport() throws HttpException, IOException, SAXException,
			BlogMoverException {
		Document loginPageDocument = writer.loginPageDocument;

		// Parse form.
		Element form = loginPageDocument.getElementById("Form1");
		String action = "http://passport.csdn.net/"
				+ form.getAttribute("action");
		log.debug("action=" + action);
		String __VIEWSTATE = loginPageDocument.getElementById("__VIEWSTATE")
				.getAttribute("value");
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
		String __EVENTVALIDATION = loginPageDocument.getElementById(
				"__EVENTVALIDATION").getAttribute("value");

		// Build login PostMethod.
		PostMethod loginMethod = new PostMethod(action);
		loginMethod.addRequestHeader("Cookie", this
				.cookieToHeaderString(writer.cookies));

		loginMethod.addParameter("__VIEWSTATE", __VIEWSTATE);
		loginMethod.addParameter("CSDNUserLogin:tb_UserName", writer.username);
		loginMethod.addParameter("CSDNUserLogin:tb_Password", writer.password);
		loginMethod.addParameter("ClientKey", clientKey);
		loginMethod.addParameter("CSDNUserLogin:tb_ExPwd",
				writer.identifyingCode);
		// method.addParameter("from", from);
		loginMethod
				.addParameter("from",
						"http://writeblog.csdn.net/Login.aspx?ReturnUrl=%2fDefault.aspx");
		loginMethod.addParameter("__EVENTVALIDATION", __EVENTVALIDATION);
		loginMethod.addParameter("CSDNUserLogin:Image_Login.x", "47");
		loginMethod.addParameter("CSDNUserLogin:Image_Login.y", "8");

		log.debug("---- RequestHeaders start ----");
		Header[] headers = loginMethod.getRequestHeaders();
		for (Header header : headers) {
			log.debug(header.getName() + ": " + header.getValue());
		}
		log.debug("---- RequestHeaders end ----");

		// Submit login form.
		writer.httpClient.executeMethod(loginMethod);

		log.debug("---- ResponseHeaders start ----");
		headers = loginMethod.getResponseHeaders();
		for (Header header : headers) {
			log.debug(header.getName() + ": " + header.getValue());
		}
		log.debug("---- ResponseHeaders end ----");

		this.setCookie(loginMethod
				.getResponseHeaders(CSDNBlogWriter.SET_COOKIE_HEADER_NAME));

		String s = loginMethod.getResponseBodyAsString();
		if (s.indexOf("您好，您已经成功登录。") == -1) {
			log.debug("---- Login passport response start ----");
			log.debug(s);
			log.debug("---- Login passport response end ----");
			throw new BlogMoverException("Login failed.(CSDN Passport)");
		}
	}

	Document getLoginPage() throws HttpException, IOException, SAXException {
		GetMethod getLoginPageMethod = new GetMethod(
				"http://passport.csdn.net/UserLogin.aspx?from=http%3a%2f%2fwriteblog.csdn.net%2fLogin.aspx%3fReturnUrl%3d%2fDefault.aspx");

		getLoginPageMethod
				.addRequestHeader(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
		getLoginPageMethod.addRequestHeader("Accept-Language", "zh-cn");
		getLoginPageMethod.addRequestHeader("UA-CPU", "x86");
		getLoginPageMethod
				.addRequestHeader("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322)");
		getLoginPageMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
		getLoginPageMethod.addRequestHeader("Host", "passport.csdn.net");
		getLoginPageMethod.addRequestHeader("Connection", "Keep-Alive");

		writer.httpClient.executeMethod(getLoginPageMethod);

		log.debug("---- Login page ResponseHeaders start ----");
		Header[] headers = getLoginPageMethod.getResponseHeaders();
		for (Header header : headers) {
			log.debug(header.getName() + ": " + header.getValue());
		}
		log.debug("---- Login page ResponseHeaders end ----");

		setCookie(getLoginPageMethod
				.getResponseHeaders(CSDNBlogWriter.SET_COOKIE_HEADER_NAME));

		InputStream is = getLoginPageMethod.getResponseBodyAsStream();
		InputSource inputSource = new InputSource(is);
		DOMParser parser = new DOMParser();
		parser.parse(inputSource);
		Document doc = parser.getDocument();
		return doc;
	}

	String getShowExPwdUrl(Document loginPageDocument) {
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

	void write(WebLog webLog) throws HttpException, IOException, SAXException {
		log.debug("write called. webLog.title=" + webLog.getTitle());
		webLog.setExcerpt("原文：" + webLog.getUrl() + ", 发表时间："
				+ webLog.getPublishedDate());

		GetMethod postEditMethod = new GetMethod(
				"http://writeblog.csdn.net/PostEdit.aspx");
		writer.httpClient.executeMethod(postEditMethod);
		// log.debug(postEditMethod.getResponseBodyAsString());
		setCookie(postEditMethod
				.getResponseHeaders(CSDNBlogWriter.SET_COOKIE_HEADER_NAME));
		InputStream is = postEditMethod.getResponseBodyAsStream();
		InputSource inputSource = new InputSource(is);
		DOMParser parser = new DOMParser();
		parser.parse(inputSource);
		Document doc = parser.getDocument();
		Element form = doc.getElementById("aspnetForm");
		String action = "http://writeblog.csdn.net/"
				+ form.getAttribute("action");
		String __VIEWSTATE = doc.getElementById("__VIEWSTATE").getAttribute(
				"value");

		PostMethod writeMethod = new PostMethod(action);
		writeMethod.addRequestHeader("Cookie",
				cookieToHeaderString(writer.cookies));
		// http://www.blogjava.net/lostfire/archive/2006/06/15/52871.html
		writeMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		writeMethod.addParameter("__VIEWSTATE", __VIEWSTATE);
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$txbTitle", webLog
						.getTitle() == null ? "" : webLog.getTitle());
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$FCKEditor", webLog
						.getBody() == null ? "" : webLog.getBody());
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$txbExcerpt", webLog
						.getExcerpt() == null ? webLog.getTitle() : webLog
						.getExcerpt());
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$SaveButton", "发表");
		writeMethod.addParameter("__EVENTVALIDATION", doc.getElementById(
				"__EVENTVALIDATION").getAttribute("value"));

		// 原创
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$rblOri", "ori");
		// 不发表到CSDN技术中心
		writeMethod
				.addParameter(
						"ctl00$ContentPlaceHolder1$EntryEditor1$GlobalCategoryList",
						"");
		// 发布到网页
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$ckbPublished", "on");
		// 允许评论
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$chkComments", "on");
		// 在首页显示
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$chkDisplayHomePage",
				"on");
		// 允许客户端订阅
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$chkMainSyndication",
				"on");
		// 仅在索引页显示摘要
		writeMethod
				.addParameter(
						"ctl00$ContentPlaceHolder1$EntryEditor1$chkSyndicateDescriptionOnly",
						"on");
		// 允许聚合站点显示
		writeMethod.addParameter(
				"ctl00$ContentPlaceHolder1$EntryEditor1$chkIsAggregated", "on");

		writer.httpClient.executeMethod(writeMethod);

		log.debug(writeMethod.getResponseBodyAsString());
	}

	/**
	 * 检查是否已经登录。如果没有登录，尝试登录。
	 */
	void checkLogin() throws BlogMoverException {
		if (!writer.loggedIn) {
			try {
				login();
			} catch (HttpException e) {
				throw new BlogMoverException(e);
			} catch (IOException e) {
				throw new BlogMoverException(e);
			} catch (SAXException e) {
				throw new BlogMoverException(e);
			}
			writer.loggedIn = true;
		}
	}
}
