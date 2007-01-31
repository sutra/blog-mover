/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author shutrazh
 * 
 */
public class BlogCupReader extends AbstractBlogReader {
	private static final String LIST_URL_FORMAT = "http://blogcup.com/user_blogmanage.asp?t=0&page=%1$s";

	private static final String MODIFY_URL_FORMAT = "http://blogcup.com/user_post.asp?logid=%1$s&t=0";

	private static final String WEB_LOG_URL_FORMAT = "http://blogccup.com/%1%s";

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private List<WebLog> webLogs;

	private String username;

	private String password;

	/**
	 * 
	 */
	public BlogCupReader() {
		super();
		httpClient = new HttpClient();
		httpDocument = new HttpDocument(httpClient, "UTF-8");
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

		new BlogCupLogin(httpDocument).login(username, password);

		ListWebLogHtmlParser listParser = new ListWebLogHtmlParser();

		int currentPageNumber = 1;
		int lastPageNumber = 0;

		do {
			String url = String.format(LIST_URL_FORMAT, currentPageNumber);
			Document document = httpDocument.get(url);
			listParser.setDocument(document);
			listParser.parse();

			this.status.setTotalCount(listParser.getTotalCount());
			lastPageNumber = listParser.getTotalPage();

			detail(listParser.getIds(), listParser.getLinks());

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
		ModifyWebLogHtmlParser parser = new ModifyWebLogHtmlParser();
		String modifyUrl = String.format(MODIFY_URL_FORMAT, webLogId);
		Document document = httpDocument.get(modifyUrl);
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		webLog.setUrl(String.format(WEB_LOG_URL_FORMAT, url));
		return webLog;
	}
}
