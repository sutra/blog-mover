/**
 * Created on 2006-6-30 17:37:51
 */
package com.redv.blogmover.bsps.sohu;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * http://blog.sohu.com
 * <p>
 * e.g. http://blog-remover.blog.sohu.com <br />
 * Username: zhoushuqun2000@chianren.com
 * </p>
 * 
 * @author Joe
 * @version 1.0
 */
public class SohuBlogReader extends AbstractBlogReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8738863775456733485L;

	private static final SimpleDateFormat sdfWithTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private HeaderGroup requestHeaderGroup;

	private boolean loggedIn = false;

	private String maildomain;

	private String passwd;

	private String username;

	private int currentCount = 0;

	/**
	 * 
	 */
	public SohuBlogReader() {
		super();
		// System.setProperty("apache.commons.httpclient.cookiespec",
		// "COMPATIBILITY");
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		this.requestHeaderGroup = new HeaderGroup();
		this.requestHeaderGroup.addHeader(new Header("User-Agent",
				ManageUrlConstants.USER_AGENT));
		// httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(
		// 10000);
		// HttpClientParams params = new HttpClientParams();
		// httpClient.setParams(params);
		// httpClient.getParams().setCookiePolicy(
		// CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, requestHeaderGroup, false,
				null);
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

	/**
	 * @return Returns the maildomain.
	 */
	public String getMaildomain() {
		return maildomain;
	}

	/**
	 * @param maildomain
	 *            The maildomain to set.
	 */
	public void setMaildomain(String maildomain) {
		this.maildomain = maildomain;
	}

	/**
	 * @return Returns the passwd.
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            The passwd to set.
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 检查是否已经登录，如果没有登录，执行登录。
	 * 
	 * @throws BlogMoverException
	 */
	private void checkLogin() throws BlogMoverException {
		if (!loggedIn) {
			try {
				boolean b = new SohuBlogLogin(this.httpClient).login(username,
						maildomain, passwd);
				if (!b) {
					throw new BlogMoverException("Login failed.");
				}
			} catch (HttpException e) {
				throw new BlogMoverException(e);
			} catch (IOException e) {
				throw new BlogMoverException(e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogReader#read(java.util.List)
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		List<WebLog> webLogs = new ArrayList<WebLog>();
		this.currentCount = 0;
		log.debug("username: " + this.getUsername());
		log.debug("passwd: " + this.getPasswd());
		log.debug("maildomain: " + this.getMaildomain());
		checkLogin();
		Setting setting = null;
		try {
			setting = this.parseSetting();
			if (log.isDebugEnabled()) {
				log.debug("name: " + setting.getName());
				log.debug("desc: " + setting.getDesc());
				log.debug("entryShowMode: " + setting.getEntryShowMode());
				log.debug("entryPerPage: " + setting.getEntryPerPage());
				log.debug("defaultAllowComment: "
						+ setting.getDefaultAllowComment());
			}
			this.parseWebLogManagementListPage(webLogs, setting);
		} catch (IOException e) {
			throw new BlogMoverException(e);
		} catch (SAXException e) {
			throw new BlogMoverException(e);
		} catch (BlogMoverException e) {
			throw e;
		}
		return webLogs;
	}

	/**
	 * 从设定页面获取个人设定。
	 * 
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws BlogMoverException
	 */
	private Setting parseSetting() throws IOException, SAXException,
			BlogMoverException {
		if (log.isDebugEnabled()) {
			Cookie[] cookies = httpClient.getState().getCookies();
			for (Cookie cookie : cookies) {
				log.debug(cookie.getName() + ": " + cookie.getValue());
			}
		}

		Document doc = httpDocument.get(ManageUrlConstants.SETTING);
		NodeList forms = doc.getElementsByTagName("form");
		Setting setting = new Setting();
		Node node = null;
		for (int i = 0; i < forms.getLength(); i++) {
			node = forms.item(i);
			if (node.getAttributes().getNamedItem("name").getNodeValue()
					.equals("blogForm")) {
				break;
			}
		}
		if (node == null) {
			throw new BlogMoverException("No setting form found.");
		}
		NodeList inputs = doc.getElementsByTagName("input");
		for (int i = 0; i < inputs.getLength(); i++) {
			node = inputs.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				String name = element.getAttribute("name");
				String value = element.getAttribute("value");
				boolean checked = (element.getAttribute("checked") != null);
				if (StringUtils.equals(name, "name")) {
					setting.setName(value);
				} else if (StringUtils.equals(name, "desc")) {
					setting.setDesc(value);
				} else if (StringUtils.equals(name, "entryShowMode")) {
					if (element.getAttribute("checked") != null) {
						setting.setEntryShowMode(NumberUtils.toInt(value));
					}
				} else if (StringUtils.equals(name, "entryPerPage")) {
					if (checked) {
						setting.setEntryPerPage(NumberUtils.toInt(value));
					}
				} else if (StringUtils.equals(name, "defaultAllowComment")) {
					if (checked) {
						setting
								.setDefaultAllowComment(NumberUtils
										.toInt(value));
					}
				}
			}
		}
		NodeList selects = doc.getElementsByTagName("select");
		for (int i = 0; i < selects.getLength(); i++) {
			node = selects.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				String name = element.getAttribute("name");
				if (StringUtils.equals(name, "entryPerPage")) {
					NodeList options = element.getElementsByTagName("option");
					for (int j = 0; j < options.getLength(); j++) {
						Node option = options.item(i);
						if (option instanceof Element) {
							Element optionElement = (Element) option;
							boolean selected = (optionElement
									.getAttribute("selected") != null);
							if (selected) {
								String value = optionElement
										.getAttribute("value");
								setting.setEntryPerPage(NumberUtils
										.toInt(value));
							}
						}
					}
				}
			}
		}
		return setting;
	}

	/**
	 * 日志列表的首页。
	 * 
	 * @param setting
	 * @throws IOException
	 * @throws SAXException
	 * @throws BlogMoverException
	 */
	private void parseWebLogManagementListPage(final List<WebLog> webLogs,
			final Setting setting) throws IOException, SAXException,
			BlogMoverException {
		Document doc = httpDocument.get(ManageUrlConstants.ENTRY);
		// 查找页码总数。
		int pages = 0;
		NodeList divs = doc.getElementsByTagName("div");
		Element infoElement = null;
		for (int i = 0; i < divs.getLength(); i++) {
			if (divs.item(i) instanceof Element) {
				Element div = (Element) divs.item(i);
				if (StringUtils.equals(div.getAttribute("class"), "item-info")) {
					infoElement = div;
					break;
				}
			}
		}

		if (infoElement != null) {
			Node node = infoElement.getFirstChild();
			if (node != null) {
				String s = node.getNodeValue();
				if (s != null) {
					String regEx = "共([0-9]+)页";
					Pattern p = Pattern.compile(regEx);
					Matcher m = p.matcher(s);
					boolean rs = m.find();
					String pagesString = null;
					if (rs) {
						pagesString = m.group();
						log.debug("pagesString: " + pagesString);
					}

					regEx = "[0-9]+";
					p = Pattern.compile(regEx);
					m = p.matcher(pagesString);
					rs = m.find();
					if (rs) {
						pages = NumberUtils.toInt(m.group());
					}
				}
			}
		}
		log.debug("pages: " + pages);
		// End of finding pages.
		status.setTotalCount(setting.getEntryPerPage() * pages);

		// 处理第一页
		parseEntryList(webLogs, doc);
		// 处理后续页面
		for (int i = 2; i <= pages; i++) {
			Document d = httpDocument
					.get("http://blog.sohu.com/manage/entry.do?pg=" + i);
			parseEntryList(webLogs, d);
		}
	}

	private void parseEntryList(List<WebLog> webLogs, Document doc)
			throws IOException, SAXException, BlogMoverException {
		Element entryList = doc.getElementById("entryList");
		log.debug(entryList.getChildNodes().getLength());
		Node item = entryList.getChildNodes().item(5);
		Node itemContent = item.getChildNodes().item(3);
		Node table = itemContent.getChildNodes().item(3);
		if (log.isDebugEnabled()) {
			log.debug("NodeName： " + table.getNodeName());
			log.debug("class: "
					+ table.getAttributes().getNamedItem("class")
							.getNodeValue());
		}

		Node tbody = table.getChildNodes().item(3);
		NodeList trs = tbody.getChildNodes();
		for (int i = 0; i < trs.getLength(); i++) {
			Node tr = trs.item(i);
			if (StringUtils.equalsIgnoreCase(tr.getNodeName(), "tr")) {
				SohuWebLog webLog = new SohuWebLog();
				log.debug(tr.getNodeName());
				NodeList tds = tr.getChildNodes();
				// date
				String dateString = DomNodeUtils.getTextContent(tds.item(1));
				webLog.setDateString(StringUtils.trim(dateString));
				// title node
				Node titleNode = tds.item(3);
				if (titleNode == null) {
					// No web log yet.
					break;
				}
				NodeList titleChildNodes = titleNode.getChildNodes();
				Node aNode = titleChildNodes.item(1);
				// uri
				webLog.setUrl(aNode.getAttributes().getNamedItem("href")
						.getNodeValue());
				// title
				String titleString = DomNodeUtils
						.getTextContent(titleChildNodes.item(1));
				webLog.setTitle(StringUtils.trim(titleString));
				// modify
				Node modifyNode = tds.item(5);
				String modifyUrl = modifyNode.getChildNodes().item(1)
						.getAttributes().getNamedItem("href").getNodeValue();
				webLog.setModifyUrl("http://blog.sohu.com" + modifyUrl);
				// delete
				Node deleteNode = tds.item(7);
				String deleteUrl = deleteNode.getChildNodes().item(1)
						.getAttributes().getNamedItem("href").getNodeValue();
				webLog.setDeleteUrl("http://blog.sohu.com" + deleteUrl);

				// Parse detail info of a weblog.
				parse(webLog);

				webLogs.add(webLog);
				status.setCurrentCount(++currentCount);
				status.setCurrentWebLog(webLog);
			}
		}
	}

	private void parse(SohuWebLog webLog) throws IOException, SAXException,
			BlogMoverException {
		// 因为列表只能得到weblog的发布日志，所以从查看weblog页面得到具体时间。
		parseView(webLog);
		// 从修改页面获得weblog的详细信息。
		parseModify(webLog);
	}

	/**
	 * 解析查看日志的页面。从中可以获取具体的发表时间。
	 * 
	 * @param webLog
	 * @throws IOException
	 * @throws SAXException
	 */
	private void parseView(SohuWebLog webLog) throws IOException, SAXException {
		Document doc = httpDocument.get(webLog.getUrl());
		Element entry = doc.getElementById("entry");
		String s = DomNodeUtils.getTextContent(entry.getChildNodes().item(3)
				.getChildNodes().item(3).getChildNodes().item(2));
		s = StringUtils.trim(s);
		s = s.substring(s.length() - 5, s.length());

		Date publishedDate;
		try {
			publishedDate = sdfWithTime.parse(webLog.getDateString() + " " + s);
			webLog.setPublishedDate(publishedDate);
		} catch (ParseException e) {
			log.error(e);
		}
	}

	/**
	 * 解析修改日志的页面。从中可以获取标题、内容、摘要、关键字等详细信息。
	 * 
	 * @param webLog
	 * @throws IOException
	 * @throws SAXException
	 * @throws BlogMoverException
	 */
	private void parseModify(SohuWebLog webLog) throws IOException,
			SAXException, BlogMoverException {
		Document doc = httpDocument.get(webLog.getModifyUrl());

		String title = doc.getElementById("entrytitle").getAttribute("value");
		webLog.setTitle(title);

		String body = DomNodeUtils.getTextContent(doc
				.getElementById("entrycontent"));
		webLog.setBody(body);

		String keywords = doc.getElementById("keywords").getAttribute("value");
		webLog.setKeywords(keywords);

		String excerpt = doc.getElementById("excerpt").getAttribute("value");
		webLog.setExcerpt(excerpt);
	}

}