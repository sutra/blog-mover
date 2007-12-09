/**
 *  Created on 2006-7-12 21:09:14
 */
package com.redv.blogmover.bsps.hexun;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.bsps.hexun.htmlparser.DetailParser;
import com.redv.blogmover.bsps.hexun.htmlparser.ListParser;
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
	public List<WebLog> read() throws BlogMoverException {
		new HexunLogin(httpDocument).login(username, password,
				"http://blog.hexun.com/group/inc/login.aspx");
		// String url = "http://hexun.com/admin_index.aspx";
		// String url = "http://blog.hexun.com/myblogadmin.aspx";
		// BlogRemoverUtils.getDocument(httpClient, null, url, true);
		// String url = "http://blogremover.blog.hexun.com/adminarticle.aspx";
		int page = 0;
		List<WebLog> webLogs = new ArrayList<WebLog>();
		while (true) {
			// String url =
			// "http://blogremover.blog.hexun.com/adminarticle.aspx?articlecategoryid=0&page="
			// + (++page);
			String url = "http://post.blog.hexun.com/inc/adminarticle.aspx?blogname="
					+ username + "&categoryid=0&page=" + (++page);
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
		DomNodeUtils.debug(log, document);
		ListParser lp = new ListParser(document);
		List<HexunWebLog> entries = lp.getEntries();
		List<WebLog> webLogs = new ArrayList<WebLog>(entries.size());
		for (HexunWebLog entry : entries) {
			parseDetail(entry);
			webLogs.add(entry);
			// Status.
			this.status.setCurrentCount(webLogs.size());
			this.status.setCurrentWebLog(entry);
		}
		return webLogs;
	}

	private void parseDetail(HexunWebLog webLog) {
		Document document = httpDocument.get(webLog.getEditUrl());
		new DetailParser(webLog, document);
	}

	public static void main(String[] args) throws IOException,
			BlogMoverException {
		LineNumberReader in = new LineNumberReader(new InputStreamReader(
				System.in));
		PrintStream out = System.out;
		out.print("Please enter your username: ");
		String username = in.readLine();
		out.print("Please enter your password: ");
		String password = in.readLine();
		HexunReader hr = new HexunReader();
		hr.setUsername(username);
		hr.setPassword(password);
		List<WebLog> entries = hr.read();
		out.println("entries: " + entries.size());
	}
}
