/**
 * Created on 2006-8-5 12:10:27
 */
package com.redv.blogmover.bsps.baidu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Shutra
 * 
 */
public class BaiduReader extends AbstractBlogReader {
	private HttpDocument httpDocument;

	private BaiduLogin baiduLogin;

	private boolean loggedIn = false;

	private List<WebLog> webLogs;

	@SuppressWarnings("unused")
	private int dispNum;

	private SimpleDateFormat dateMod;

	private int timeMod;

	private String username;

	private String password;

	/**
	 * the handle of user's blog.<br />
	 * http://hi.baidu.com/BLOGHANDLE/
	 */
	private String blogHandle;

	public BaiduReader() {
		super();

		HttpClient httpClient = new HttpClient();
		httpDocument = new HttpDocument(httpClient, "GB2312");
		baiduLogin = new BaiduLogin(httpDocument);
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
	 * @see com.redv.blogmover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		webLogs = new ArrayList<WebLog>();
		checkLogin();
		blogHandle = BaiduUtils.findBlogHandle(this.httpDocument);
		parseSetting(blogHandle);
		String url = "http://hi.baidu.com/" + blogHandle + "/blog/index/";
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String u = url + i;
			log.debug("u: " + u);
			Document document = httpDocument.get(u);
			parse(document);
			// Page.
			if (!hasNextPage(document)) {
				break;
			}
		}
		return webLogs;
	}

	private void checkLogin() throws BlogMoverException {
		if (!loggedIn) {
			baiduLogin.login(username, password);
		}
	}

	private boolean hasNextPage(Document document) {
		boolean hasNextPage = false;
		Element page = document.getElementById("page");
		if (page != null) {
			NodeList pageChildren = page.getChildNodes();
			for (int j = 0; j < pageChildren.getLength(); j++) {
				Node child = pageChildren.item(j);
				if (StringUtils.equalsIgnoreCase(child.getNodeName(), "a")) {
					if (StringUtils.equals(DomNodeUtils.getTextContent(child),
							"[下一页]")) {
						hasNextPage = true;
					}
				}
			}
		}
		return hasNextPage;
	}

	private void parseSetting(String blogHandle) throws BlogMoverException {
		String settingUrl = "http://hi.baidu.com/" + blogHandle
				+ "/modify/spbasic/0";
		Document document = httpDocument.get(settingUrl);
		// DispNum.
		Element spConfigDispNum = document.getElementById("spConfigDispNum");
		if (spConfigDispNum == null) {
			throw new BlogMoverException("没有找到设置页面，请重试F。");
		}
		Node nextSibling = spConfigDispNum.getNextSibling();
		log.debug("nextSibling" + nextSibling.getNodeName());
		String textContent = DomNodeUtils.getTextContent(nextSibling);
		Pattern pattern = Pattern
				.compile("getselect\\(\"spConfigDispNum\",([0-9]+)\\);");
		Matcher matcher = pattern.matcher(textContent);
		if (matcher.find()) {
			int index = NumberUtils.toInt(matcher.group(1));
			log.debug("index: " + index);
			switch (index) {
			case 0:
				dispNum = 5;
				break;
			case 1:
				dispNum = 10;
				break;
			case 2:
				dispNum = 15;
				break;
			default:
				dispNum = 10;
			}
		}
		// DateMod.
		Element spDateMod = document.getElementById("spDateMod");
		nextSibling = spDateMod.getNextSibling();
		log.debug("nextSibling: " + nextSibling.getNodeName());
		textContent = DomNodeUtils.getTextContent(nextSibling);
		pattern = Pattern.compile("getselect\\(\"spDateMod\",([0-9]+)\\);");
		matcher = pattern.matcher(textContent);
		if (matcher.find()) {
			int index = NumberUtils.toInt(matcher.group(1));
			log.debug("index: " + index);
			switch (index) {
			case 0:
				dateMod = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 1:
				dateMod = new SimpleDateFormat("yyyy/MM/dd");
				break;
			case 2:
				dateMod = new SimpleDateFormat("yyyy年MM月dd日");
				break;
			case 3:
				dateMod = new SimpleDateFormat("yyyy年MM月dd日 EEEE");
				break;
			default:
				dateMod = new SimpleDateFormat("yyyy-MM-dd");
			}
		}
		// TimeMod.
		Element spTimeMod = document.getElementById("spTimeMod");
		nextSibling = spTimeMod.getNextSibling();
		log.debug("nextSibling: " + nextSibling.getNodeName());
		textContent = DomNodeUtils.getTextContent(nextSibling);
		pattern = Pattern.compile("getselect\\(\"spTimeMod\",([0-9]+)\\);");
		matcher = pattern.matcher(textContent);
		if (matcher.find()) {
			int index = NumberUtils.toInt(matcher.group(1));
			log.debug("index: " + index);
			timeMod = index;
		}
	}

	private void parse(Document document) {
		NodeList divs = document.getElementsByTagName("div");
		for (int i = 0; i < divs.getLength(); i++) {
			Element div = (Element) divs.item(i);
			String styleClass = div.getAttribute("class");
			if (StringUtils.equals(styleClass, "tit")) {
				Element a = (Element) div.getFirstChild();
				String href = a.getAttribute("href");
				log.debug("href: " + href);
				if (href.startsWith("/" + this.blogHandle + "/blog/item/")) {
					String id = href.substring(
							("/" + this.blogHandle + "/blog/item/").length(),
							href.length() - ".html".length());
					log.debug("id: " + id);
					WebLog webLog = detail(id);
					log.debug("webLog.title: " + webLog.getTitle());
					log.debug("webLog.publishedDate: "
							+ webLog.getPublishedDate());
					log.debug("webLog.body: " + webLog.getBody());
					this.webLogs.add(webLog);
					this.status.setCurrentWebLog(webLog);
					this.status.setCurrentCount(webLogs.size());
				}
			}
		}
	}

	private WebLog detail(String id) {
		WebLog webLog = new WebLogImpl();
		Document document;
		document = httpDocument.get("http://hiup.baidu.com/" + this.blogHandle
				+ "/modify/blog/" + id);
		// Title.
		webLog.setTitle(document.getElementById("spBlogTitle").getAttribute(
				"value"));
		// Body.
		webLog.setBody(DomNodeUtils.getTextContent(document
				.getElementById("midstatus")));

		String url = "http://hi.baidu.com/" + this.blogHandle + "/blog/item/"
				+ id + ".html";
		webLog.setUrl(url);

		document = httpDocument.get(url);
		NodeList divs = document.getElementsByTagName("div");
		boolean publishedDateSetted = false;
		for (int i = 0; i < divs.getLength(); i++) {
			Element div = (Element) divs.item(i);
			String styleClass = div.getAttribute("class");
			String textContent = DomNodeUtils.getTextContent(div);
			log.debug("styleClass: " + styleClass + ", textContent: "
					+ textContent);
			if (StringUtils.equals(styleClass, "tit")) { // Title.
				// webLog.setTitle(textContent);
			} else if (StringUtils.equals(styleClass, "date")) { // PublishedDate.
				if (!publishedDateSetted) {
					webLog.setPublishedDate(parseDate(textContent));
					publishedDateSetted = true;
				}
			} else if (StringUtils.equals(styleClass, "cnt")) { // Body.
				// webLog.setBody(textContent);
			}
		}

		return webLog;
	}

	private Date parseDate(String s) {
		String[] ss = s.split("  ");
		Calendar cal = Calendar.getInstance();
		try {
			Date date = this.dateMod.parse(ss[0]);
			cal.setTime(date);
		} catch (ParseException e) {
			throw new BlogMoverRuntimeException(e);
		}
		String t = ss[1];
		switch (timeMod) {
		case 0: // 18:50
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			try {
				Date time = sdf.parse(t);
				Calendar calTime = Calendar.getInstance();
				calTime.setTime(time);
				cal
						.set(Calendar.HOUR_OF_DAY, calTime
								.get(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
			} catch (ParseException e) {
				log.error(e);
			}
			break;
		case 1: // 6:50 P.M.
			String s1 = t.substring(0, t.length() - " A.M.".length());
			String[] s1s = s1.split(":");
			int hour = NumberUtils.toInt(s1s[0]);
			int minute = NumberUtils.toInt(s1s[1]);
			if (t.endsWith("P.M.")) {
				hour += 12;
			}
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			break;
		case 2: // 下午 6:50
			s1 = t.substring("上午 ".length(), t.length());
			s1s = s1.split(":");
			hour = NumberUtils.toInt(s1s[0]);
			minute = NumberUtils.toInt(s1s[1]);
			if (t.startsWith("下午")) {
				hour += 12;
			}
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			break;
		}
		return cal.getTime();
	}

}
