/**
 * Created on 2007-2-2 下午11:08:08
 */
package com.redv.blogmover.blogengines.cn.oblog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.HttpDocument;

/**
 * The reader for oblog(http://www.oblog.cn/).
 * 
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public abstract class AbstractOBlogReader extends AbstractBlogReader {
	protected final Log log = LogFactory.getLog(getClass());

	protected HttpClient httpClient;

	protected HttpDocument httpDocument;

	protected String username;

	protected String password;

	protected List<WebLog> webLogs;

	public AbstractOBlogReader() {
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

		login();

		ListWebLogHtmlPageParser listParser = new ListWebLogHtmlPageParser();

		int currentPageNumber = 1;
		int lastPageNumber = 0;

		do {
			String url = this.buildListUrl(currentPageNumber);
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
		String modifyUrl = buildModifyUrl(webLogId);
		Document document = httpDocument.get(modifyUrl);
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		webLog.setUrl(this.buildPermalink(url));
		return webLog;
	}

	protected abstract void login() throws LoginFailedException;

	protected abstract String buildListUrl(int currentPageNumber);

	protected abstract String buildModifyUrl(String webLogId);

	protected abstract String buildPermalink(String urlParsedFromListPage);
}
