/**
 *  Created on 2006-7-12 21:09:14
 */
package com.redv.blogmover.bsps.hexun;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogRemoverException;
import com.redv.blogmover.BlogRemoverRuntimeException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class HexunReader extends AbstractBlogReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4008375112695687185L;

	private HttpClient httpClient;

	private HeaderGroup requestHeaderGroup;

	private HttpDocument httpDocument;

	private String username;

	private String password;

	/**
	 * 
	 */
	public HexunReader() {
		super();
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		requestHeaderGroup = new HeaderGroup();
		// requestHeaderGroup
		// .addHeader(new Header("User-Agent",
		// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR
		// 1.1.4322)"));
		// requestHeaderGroup.addHeader(new Header("Accept-Language", "zh-cn"));
		// requestHeaderGroup.addHeader(new Header("Accept", "*/*"));
		// requestHeaderGroup.addHeader(new Header("Referer",
		// "http://blog.hexun.com/group/inc/login.aspx"));
		httpDocument = new HttpDocument(httpClient, requestHeaderGroup, true,
				"GBK");
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogRemoverException {
		new HexunLogin(httpDocument).login(username, password,
				"http://blog.hexun.com/group/inc/login.aspx");
		// String url = "http://hexun.com/admin_index.aspx";
		// String url = "http://blog.hexun.com/myblogadmin.aspx";
		// BlogRemoverUtils.getDocument(httpClient, null, url, true);
		// String url = "http://blogremover.blog.hexun.com/adminarticle.aspx";
		int page = 0;
		List<WebLog> webLogs = new ArrayList<WebLog>();
		while (true) {
			String url = "http://blogremover.blog.hexun.com/adminarticle.aspx?articlecategoryid=0&page="
					+ (++page);
			Document document = httpDocument.get(url);

			List<WebLog> webLogsPage = parseList(document);
			if (webLogsPage.size() == 0) {
				break;
			} else {
				webLogs.addAll(webLogsPage);
			}
		}
		return webLogs;
	}

	private List<WebLog> parseList(Document document) {
		List<WebLog> webLogs = new ArrayList<WebLog>(20); // 和讯的博客文章编辑页面每页默认最多20个日志。
		NodeList tables = document.getElementsByTagName("table");
		Element table = null;
		for (int i = 0; i < tables.getLength(); i++) {
			table = (Element) tables.item(i);
			String styleClass = table.getAttribute("class");
			if ("gridbox".equals(styleClass)) {
				break;
			}
		}
		if (table == null) {
			throw new BlogRemoverRuntimeException("没有找到文章列表表格。");
		}
		NodeList trs = table.getChildNodes();
		Node tr;
		for (int i = 0; i < trs.getLength(); i++) {
			tr = trs.item(i);
			if (StringUtils.equalsIgnoreCase(tr.getNodeName(), "tr")) {
				WebLog webLog = parseTr(tr);
				if (webLog != null) {
					webLogs.add(webLog);
					// Status.
					this.status.setCurrentCount(webLogs.size());
					this.status.setCurrentWebLog(webLog);
				}
			}
		}
		return webLogs;
	}

	private WebLog parseTr(Node tr) {
		HexunWebLog webLog = null;
		NodeList tds = tr.getChildNodes();
		Node tdUrl = tds.item(1);
		Node a = tdUrl.getChildNodes().item(0);
		if (log.isDebugEnabled()) {
			log
					.debug("ChildNodes.length: "
							+ tdUrl.getChildNodes().getLength());
		}
		if (a != null && StringUtils.equalsIgnoreCase(a.getNodeName(), "a")) {
			log.debug(a.getNodeName());
			Node n = a.getAttributes().getNamedItem("href");
			if (n != null) {
				String href = n.getNodeValue();
				if (href != null) {
					webLog = new HexunWebLog();
					webLog.setUrl(href); // url
					log.debug("url: " + href);
					webLog.setTitle(DomNodeUtils.getTextContent(a)); // t

					Node tdEditUrl = tds.item(7);
					webLog.setEditUrl(((Element) tdEditUrl.getFirstChild())
							.getAttribute("href")); // edit url
					Node tdDeleteUrl = tds.item(9);
					webLog.setDeleteUrl(((Element) tdDeleteUrl.getFirstChild())
							.getAttribute("href")); // delete url

					// Detail web log.
					parseDetail(webLog);
				}
			}
		}
		return webLog;
	}

	private void parseDetail(HexunWebLog webLog) {
		Document document = httpDocument.get(webLog.getEditUrl());
		// title
		Element titleElement = document
				.getElementById("EditArticle1_TitleTextbox");
		webLog.setTitle(titleElement.getAttribute("value"));
		// excerpt
		Element excerptElement = document
				.getElementById("EditArticle1_BriefTextbox");
		webLog.setExcerpt(excerptElement.getAttribute("value"));
		// tags
		Element tagsElement = document
				.getElementById("EditArticle1_TagTextbox");
		webLog.setTags(parseTags(tagsElement.getAttribute("value")));

		NodeList textareas = document.getElementsByTagName("textarea");
		for (int i = 0; i < textareas.getLength(); i++) {
			Element textarea = (Element) textareas.item(i);
			String name = textarea.getAttribute("name");
			if ("EditArticle1:ContentSpaw".equals(name)) {
				// body
				webLog.setBody(DomNodeUtils.getTextContent(textarea));
				if (log.isDebugEnabled()) {
					log.debug(webLog.getBody());
				}
			}
		}
	}

	String[] parseTags(String t) {
		List<String> tags = new ArrayList<String>();
		char[] chars = t.toCharArray();
		boolean quote = false;
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (c == '"') {
				quote = !quote;
			} else if (c == ' ') {
				if (quote) {
					sb.append(c);
				} else {
					if (sb.toString().trim().length() > 0) {
						tags.add(sb.toString());
					}
					sb = new StringBuffer();
				}
			} else if (c == ',') {
				if (quote) {
					sb.append(c);
				} else {
					if (sb.toString().trim().length() > 0) {
						tags.add(sb.toString());
					}
					sb = new StringBuffer();
				}
			} else {
				sb.append(c);
			}
		}
		if (sb.toString().trim().length() > 0) {
			tags.add(sb.toString());
		}
		String[] ret = new String[tags.size()];
		tags.toArray(ret);
		return ret;
	}
}
