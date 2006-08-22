/**
 *  Created on 2006-7-31 11:14:10
 */
package com.redv.blogmover.bsps.csdn;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class CSDNBlogReader extends AbstractBlogReader {
	private HttpClient httpClient;

	private HttpDocument httpDocument;

	CSDNLogin csdnLogin;

	private boolean loggedIn = false;

	private String username;

	private String password;

	private String identifyingCode;

	private List<WebLog> webLogs = new ArrayList<WebLog>();

	/**
	 * 
	 */
	public CSDNBlogReader() {
		super();
		this.httpClient = new HttpClient();
		// 如果没有设定cookie模式将会有警告：Cookie rejected
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);

		HeaderGroup hg = new HeaderGroup();
		hg
				.addHeader(new Header(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*"));
		hg.addHeader(new Header("Accept-Language", "zh-cn"));
		hg.addHeader(new Header("UA-CPU", "x86"));
		hg
				.addHeader(new Header("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322)"));
		hg.addHeader(new Header("Accept-Encoding", "gzip, deflate"));
		hg.addHeader(new Header("Host", "passport.csdn.net"));
		hg.addHeader(new Header("Connection", "Keep-Alive"));

		this.httpDocument = new HttpDocument(httpClient, true);

		csdnLogin = new CSDNLogin(httpClient);
	}

	/**
	 * @param identifyingCode
	 *            The identifyingCode to set.
	 */
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	private void checkLogin() throws BlogMoverException {
		if (!loggedIn) {
			csdnLogin.login(username, password, identifyingCode);
		}
	}

	@Override
	public List<WebLog> read() throws BlogMoverException {
		checkLogin();
		String urlPrefix = "http://writeblog.csdn.net/PostList.aspx?pg=";
		String firstPage = urlPrefix + 1;
		Document document = httpDocument.get(firstPage);
		// Parse page.
		int totalPage = 0;
		NodeList divs = document.getElementsByTagName("div");
		for (int i = 0; i < divs.getLength(); i++) {
			Element div = (Element) divs.item(i);
			if ("Pager".equals(div.getAttribute("class"))) {
				NodeList children = div.getChildNodes();
				for (int j = 0; j < children.getLength(); j++) {
					Element child = (Element) children.item(j);
					if ("a".equalsIgnoreCase(child.getNodeName())) {
						if ("最后".equals(DomNodeUtils.getTextContent(child))) {
							String href = child.getAttribute("href");
							Pattern pattern = Pattern
									.compile("PostList.aspx?pg=([0-9]+)");
							Matcher matcher = pattern.matcher(href);
							boolean rs = matcher.find();
							if (rs) {
								totalPage = NumberUtils.toInt(matcher.group(1));
							}
						}
					}
				}
			}
		}
		this.status.setTotalCount(10 * totalPage);
		parse(document);
		for (int i = 2; i <= totalPage; i++) {
			parse(httpDocument.get(urlPrefix + i));
		}
		return webLogs;
	}

	private void parse(Document document) {
		NodeList as = document.getElementsByTagName("a");
		Pattern pattern = Pattern.compile("Referrers.aspx?EntryID=([0-9]+)");

		for (int i = 0; i < as.getLength(); i++) {
			Element a = (Element) as.item(i);
			String href = a.getAttribute("href");
			Matcher matcher = pattern.matcher(href);
			if (matcher.find()) {
				String id = matcher.group(1);
				WebLog webLog = detail(id);
				this.status.setCurrentCount(this.webLogs.size());
				this.webLogs.add(webLog);
				this.status.setCurrentWebLog(webLog);
			}
		}
	}

	private WebLog detail(String id) {
		WebLog webLog = new WebLogImpl();
		String url = "http://writeblog.csdn.net/PostEdit.aspx?entryId=" + id;
		Document document = httpDocument.get(url);
		// Title.
		Element element = document
				.getElementById("ctl00_ContentPlaceHolder1_EntryEditor1_txbTitle");
		webLog.setTitle(element.getAttribute("value"));
		// Body.
		element = document
				.getElementById("ctl00_ContentPlaceHolder1_EntryEditor1_FCKEditor");
		webLog.setBody(element.getAttribute("value"));
		// Excerpt.
		element = document
				.getElementById("ctl00_ContentPlaceHolder1_EntryEditor1_txbExcerpt");
		webLog.setExcerpt(DomNodeUtils.getTextContent(element));
		// Tags.
		element = document
				.getElementById("ctl00_ContentPlaceHolder1_EntryEditor1_txbTags");
		String[] tags = element.getAttribute("value").split(",");
		List<String> tagList = new ArrayList<String>();
		for (int i = 0; i < tags.length; i++) {
			String tag = tags[i].trim();
			if (tag.length() > 0) {
				tagList.add(tag);
			}
		}
		String[] t = new String[tagList.size()];
		tagList.toArray(t);
		webLog.setTags(t);

		return webLog;
	}

	public byte[] getIdentifyingCodeImage() throws BlogMoverException {
		return csdnLogin.getIdentifyingCodeImage();
	}
}
