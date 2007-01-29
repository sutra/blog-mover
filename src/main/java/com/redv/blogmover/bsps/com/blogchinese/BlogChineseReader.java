/**
 * Created on 2007-1-27 上午03:46:59
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author shutra
 * 
 */
public class BlogChineseReader extends AbstractBlogReader {
	private static final String listUrlFormat = "http://www.blogchinese.com/user_blogmanage.asp?t=0&page=%1$s";

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private String username;

	private String password;

	private List<WebLog> webLogs;

	public BlogChineseReader() {
		super();
		httpClient = new HttpClient();
		httpDocument = new HttpDocument(httpClient, "GB2312");
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		new BlogChineseLogin(httpDocument).login(username, password);

		ListWebLogHtmlPageParser listParser = new ListWebLogHtmlPageParser();
		String url = String.format(listUrlFormat, 1);
		Document document = httpDocument.get(url);
		listParser.parse(document);
		this.status.setTotalCount(listParser.getTotalCount());
		int lastPageNumber = listParser.getLastPageNumber();

		detail(listParser.getWebLogIds());

		for (int i = 2; i <= lastPageNumber; i++) {
			url = String.format(listUrlFormat, i);
			document = httpDocument.get(url);
			listParser.parse(document);
			detail(listParser.getWebLogIds());

		}

		return webLogs;
	}

	/**
	 * Build the web logs from their modify page.
	 * 
	 * @param webLogIds
	 */
	private void detail(List<String> webLogIds) {
		for (String webLogId : webLogIds) {
			WebLog webLog = detail(webLogId);
			this.webLogs.add(webLog);
			this.status.setCurrentWebLog(webLog);
			this.status.setCurrentCount(this.webLogs.size());
		}
	}

	private WebLog detail(String webLogId) {
		ModifyWebLogHtmlPageParser parser = new ModifyWebLogHtmlPageParser();
		String url = String.format(
				"http://www.blogchinese.com/user_post.asp?logid=%1$s&t=0",
				webLogId);
		Document document = httpDocument.get(url);
		parser.setDocument(document);
		WebLog webLog = parser.getWebLog();
		return webLog;
	}

}
