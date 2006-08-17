/**
 * Created on 2006-8-4 下午07:41:50
 */
package com.redv.blogmover.bsps.donews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogRemoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Shutra
 * 
 */
public class DoNewsBlogReader extends AbstractBlogReader {
	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private DoNewsLogin doNewsLogin;

	private boolean loggedIn = false;

	private List<WebLog> webLogs;

	private String username;

	private String password;

	private String identifyingCode;

	public DoNewsBlogReader() {
		super();
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, true, "UTF-8");
		doNewsLogin = new DoNewsLogin(httpClient, httpDocument);
	}

	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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
		webLogs = new ArrayList<WebLog>();
		checkLogin();
		String url = "http://writeblog.donews.com/EditPosts.aspx?pg=";
		Document document = httpDocument.get(url + "1");
		NodeList divs = document.getElementsByTagName("table");
		Element pager = null;
		for (int i = 0; i < divs.getLength(); i++) {
			Element div = (Element) divs.item(i);
			String styleClass = div.getAttribute("class");
			if (StringUtils.equals(styleClass, "Pager")) {
				pager = div;
				break;
			}
		}
		if (pager == null) {
			throw new BlogRemoverException("没有找到分页部分代码，无法继续。");
		}
		NodeList children = pager.getChildNodes().item(0).getChildNodes().item(
				0).getChildNodes();
		Node last = null;
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getFirstChild() == null) {
				continue;
			}
			log.debug("Node name: " + node.getNodeName() + ", node value: "
					+ node.getFirstChild().getNodeValue());
			if (StringUtils.equals(node.getFirstChild().getNodeValue(), "最后")) {
				last = node;
			}
		}
		if (last == null) {
			throw new BlogRemoverException("没有找到最后一页标识，无法继续。");
		}
		String href = last.getAttributes().getNamedItem("href").getNodeValue();
		String pageString = href.substring("EditPosts.aspx?pg=".length(), href
				.length());
		log.debug("pageString: " + pageString);
		int totalPage = NumberUtils.toInt(pageString);
		this.status.setTotalCount(10 * totalPage);

		parse(document);

		for (int i = 2; i <= totalPage; i++) {
			log.debug("Parse page " + i);
			parse(httpDocument.get(url + i));
		}
		return this.webLogs;
	}

	private void checkLogin() throws DOMException, BlogRemoverException {
		if (!loggedIn) {
			doNewsLogin.login(username, password, identifyingCode);
			loggedIn = true;
		}
	}

	private void parse(Document document) {
		Element listingTable = document.getElementById("Listing");
		NodeList trs = listingTable.getChildNodes();
		for (int i = 1; i < trs.getLength(); i++) {
			NodeList tds = trs.item(i).getChildNodes();
			log.debug("tds.getLength(): " + tds.getLength());
			if (tds.getLength() >= 9) {
				Node td = tds.item(9);
				NodeList as = td.getChildNodes();
				log.debug("as.getLength(): " + as.getLength());
				if (as.getLength() == 3) {
					String href = as.item(1).getAttributes().getNamedItem(
							"href").getNodeValue();
					log.debug("href: " + href);
					String id = href.substring("Referrers.aspx?EntryID="
							.length(), href.length());
					log.debug("id: " + id);
					WebLog webLog = detail(id);
					this.status.setCurrentWebLog(webLog);
					webLogs.add(webLog);
					this.status.setCurrentCount(webLogs.size());
				}
			}
		}
	}

	private WebLog detail(String id) {
		String url = "http://writeblog.donews.com/editposts.aspx?EntryID=" + id;
		Document document = httpDocument.get(url);
		WebLog webLog = new WebLogImpl();

		// Title.
		webLog.setTitle(document.getElementById("Editor_Edit_txbTitle")
				.getAttribute("value"));

		// Body.
		NodeList textareas = document.getElementsByTagName("textarea");
		for (int i = 0; i < textareas.getLength(); i++) {
			Element textarea = (Element) textareas.item(i);
			if (StringUtils.equals(textarea.getAttribute("name"),
					"Editor:Edit:FCKEditor")) {
				webLog.setBody(DomNodeUtils.getTextContent(textarea));
			}
		}

		// Excerpt.
		Element element = document.getElementById("Editor_Edit_txbExcerpt");
		webLog.setExcerpt(DomNodeUtils.getTextContent(element));

		// URL.

		// PublishedDate.

		return webLog;
	}

	public byte[] getIdentifyingCodeImage() throws HttpException, IOException {
		return doNewsLogin.getIdentifyingCodeImage();
	}

}
