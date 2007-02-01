/**
 * Created on 2007-1-27 上午03:46:59
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.blogengines.cn.oblog.ListWebLogHtmlPageParser;
import com.redv.blogmover.blogengines.cn.oblog.ModifyWebLogHtmlPageParser;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author shutra
 * 
 */
public class BlogChineseReader extends AbstractBlogReader {
	private static final String LIST_URL_FORMAT = "http://www.blogchinese.com/user_blogmanage.asp?t=0&page=%1$s";

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
		this.webLogs = new ArrayList<WebLog>();

		new BlogChineseLogin(httpDocument).login(username, password);

		ListWebLogHtmlPageParser listParser = new ListWebLogHtmlPageParser();

		int currentPageNumber = 1;
		int lastPageNumber = 0;

		do {
			String url = String.format(LIST_URL_FORMAT, currentPageNumber);
			Document document = httpDocument.get(url);
			listParser.setCurrentPageNumber(currentPageNumber);
			listParser.setDocument(document);
			listParser.parse();

			this.status.setTotalCount(listParser.getTotalCount());
			lastPageNumber = listParser.getLastPageNumber();

			detail(listParser.getWebLogIds(), listParser.getUrls());

		} while (++currentPageNumber <= lastPageNumber);

		log.debug("webLogs.size(): " + webLogs.size());
		return webLogs;
	}

	/**
	 * Build the web logs from their modify page.
	 * 
	 * @param webLogIds
	 */
	private void detail(List<String> webLogIds, List<String> urls) {
		int i = 0;
		for (String webLogId : webLogIds) {
			WebLog webLog = detail(webLogId, urls.get(i++));
			this.webLogs.add(webLog);
			this.status.setCurrentWebLog(webLog);
			this.status.setCurrentCount(this.webLogs.size());
		}
	}

	private WebLog detail(String webLogId, String url) {
		ModifyWebLogHtmlPageParser parser = new ModifyWebLogHtmlPageParser();
		String modifyUrl = String.format(
				"http://www.blogchinese.com/user_post.asp?logid=%1$s&t=0",
				webLogId);
		Document document = httpDocument.get(modifyUrl);
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		webLog.setUrl("http://www.blogchinese.com/" + url);
		return webLog;
	}

}
