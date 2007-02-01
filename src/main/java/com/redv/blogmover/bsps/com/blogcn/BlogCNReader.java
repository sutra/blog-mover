/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.HttpClientUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author shutrazh
 * 
 */
public class BlogCNReader extends AbstractBlogReader {
	private static final String LIST_URL_FORMAT = "http://login.blogcn.com/blue_modfile.asp?action=&s=&page={0}&cndok=GO%A3%A1";

	private static final String MODIFY_URL_PREFIX = "http://login.blogcn.com";

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private String username;

	private String password;

	private List<WebLog> webLogs;

	/**
	 * 
	 */
	public BlogCNReader() {
		super();

		httpClient = new HttpClient();

		// 如果没有设定cookie模式将会有警告：Cookie rejected
		this.httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);

		httpDocument = new HttpDocument(httpClient, HttpClientUtils
				.buildInternetExplorerHeader("zh-cn"), true, "GB2312");
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

		new BlogCNLogin(httpDocument).login(username, password);

		ListEntryHtmlParser listParser = new ListEntryHtmlParser();

		int currentPageNumber = 1;
		int lastPageNumber = 0;

		do {
			String url = StringUtils.replace(LIST_URL_FORMAT, "{0}", String
					.valueOf(currentPageNumber));
			Document document = httpDocument.get(url);
			listParser.setDocument(document);
			listParser.parse();

			this.status.setTotalCount(listParser.getTotalEntries());
			lastPageNumber = listParser.getTotalPages();

			detail(listParser.getModifyLinks(), listParser.getPermalinks());

		} while (++currentPageNumber <= lastPageNumber);

		return null;
	}

	private void detail(List<String> modifyLinks, List<String> permalinks) {
		int i = 0;
		for (String modifyLink : modifyLinks) {
			WebLog webLog = detail(modifyLink, permalinks.get(i++));
			this.webLogs.add(webLog);
			this.status.setCurrentWebLog(webLog);
			this.status.setCurrentCount(this.webLogs.size());
		}
	}

	private WebLog detail(String modifyLink, String permalink) {
		ModifyEntryHtmlParser parser = new ModifyEntryHtmlParser();
		String modifyUrl = MODIFY_URL_PREFIX + modifyLink;
		Document document = httpDocument.get(modifyUrl);
		parser.setDocument(document);
		parser.parse();
		WebLog webLog = parser.getWebLog();
		webLog.setUrl(permalink);
		return webLog;
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			BlogMoverException {
		System.out.println(URLDecoder.decode("GO%A3%A1", "GB2312"));
		System.out
				.println(URLDecoder
						.decode(
								"%B3%AC%B3%F6%B0%B2%C8%AB%B9%E6%B6%A8%CA%B1%BC%E4%A3%AC%C7%EB%C4%FA%CF%C8%B5%C7%C2%BC%D4%D9%B2%D9%D7%F7%A1%A3",
								"GB2312"));

		BlogCNReader r = new BlogCNReader();
		r.setUsername("blogmoverdev");
		r.setPassword("blogmoverdev");
		r.read();
	}
}
