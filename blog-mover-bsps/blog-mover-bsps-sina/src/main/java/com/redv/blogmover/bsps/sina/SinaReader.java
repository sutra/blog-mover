/**
 *  Created on 2006-7-28 13:44:26
 */
package com.redv.blogmover.bsps.sina;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.Comment;
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
public class SinaReader extends AbstractBlogReader {
	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private boolean loggedIn = false;

	private String username;

	private String password;

	private String identifyingCode;

	private Set<String> ids = new HashSet<String>();

	private List<WebLog> webLogs = new ArrayList<WebLog>();

	public SinaReader() {
		super();

		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, true, "GB2312");
		httpDocument.setRequestCharSet("GB2312");
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

	@Override
	public void check() throws BlogMoverException {
		if (!loggedIn) {
			new SinaLogin(httpDocument).login(username, password,
					identifyingCode);
			loggedIn = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogReader#read()
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		if (!loggedIn) {
			new SinaLogin(httpDocument).login(username, password,
					identifyingCode);
			loggedIn = true;
		}

		String url = "http://blog.sina.com.cn/control/writing/explorer/article_list_sort.php";
		int pages = 0;
		Document document = httpDocument.get(url);
		NodeList tds = document.getElementsByTagName("td");
		log.debug("tds.getLength(): " + tds.getLength());
		for (int i = 0; i < tds.getLength(); i++) {
			Element td = (Element) tds.item(i);
			if ("page_bar".equals(td.getAttribute("class"))) {
				String pageStr = DomNodeUtils.getTextContent(td);
				log.debug("pageStr: " + pageStr);
				String p = "共([0-9]+)页";
				Pattern pattern = Pattern.compile(p);
				Matcher matcher = pattern.matcher(pageStr);
				boolean rs = matcher.find();
				if (rs) {
					pages = NumberUtils.toInt(matcher.group(1));
					this.status.setTotalCount(20 * pages);
				}
				break;
			}
		}
		parse(document);
		for (int i = 2; i <= pages; i++) {
			document = httpDocument.get(url + "?p=" + i);
			parse(document);
		}
		return this.webLogs;
	}

	/**
	 * 分析日志列表。
	 * 
	 * @param document
	 */
	private void parse(Document document) {
		NodeList nodeList = document.getChildNodes();
		if (nodeList.getLength() <= 0) {
			throw new BlogMoverRuntimeException("获取的数据不是预期的格式，请稍后重试。");
		}
		nodeList = nodeList.item(0).getChildNodes();
		if (nodeList.getLength() <= 1) {
			throw new BlogMoverRuntimeException("获取的数据不是预期的格式，请稍后重试。");
		}
		nodeList = nodeList.item(1).getChildNodes();
		if (nodeList.getLength() <= 19) {
			throw new BlogMoverRuntimeException("获取的数据不是预期的格式，请稍后重试。");
		}
		nodeList = nodeList.item(19).getChildNodes();
		if (nodeList.getLength() <= 0) {
			throw new BlogMoverRuntimeException("获取的数据不是预期的格式，请稍后重试。");
		}
		Node node = nodeList.item(0);
		String str = node.getNodeValue();
		parse(str);
	}

	/**
	 * 分析日志列表。
	 * 
	 * @param str
	 */
	void parse(String str) {
		String[] strs = str.split("data: \\[");
		for (String s : strs) {
			parseOne(s);
		}
	}

	/**
	 * 分析日志列表中的某一个日志。
	 * 
	 * @param str
	 */
	private void parseOne(String str) {
		log.debug("str: " + str);
		String pattern = "/writing/scriber/article_edit\\.php\\?blog_id\\=([0-9a-z]+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		boolean rs = m.find();
		log.debug("rs: " + rs);
		log.debug("groupCount: " + m.groupCount());
		if (rs) {
			for (int i = 1; i <= m.groupCount(); i++) {
				String id = m.group(i);
				if (ids.add(id)) {
					detailWebLog(id);
				}
			}
		}
	}

	/**
	 * 将某个日志读取完毕。
	 * 
	 * @param id
	 */
	private void detailWebLog(String id) {
		String editUrl = "http://blog.sina.com.cn/control/writing/scriber/article_edit.php?blog_id="
				+ id;
		String url = "http://blog.sina.com.cn/u/" + id;
		WebLog webLog = new WebLogImpl();
		webLog.setUrl(url);

		Document document = httpDocument.get(editUrl);
		log.debug("---- document ----");
		DomNodeUtils.debug(this.log, document);
		Element element = document.getElementById("blog_title");
		if (element == null) {
			throw new BlogMoverRuntimeException("获取的数据不是预期的格式，请稍后重试。");
		}
		webLog.setTitle(element.getAttribute("value"));
		log.debug("title: " + webLog.getTitle());

		webLog.setPublishedDate(this.findDate(document));
		log.debug("publishedDate: " + webLog.getPublishedDate());

		// body
		webLog.setBody(this.findBody(document));

		readComments(webLog);

		this.status.setCurrentWebLog(webLog);
		webLogs.add(webLog);
		this.status.setCurrentCount(webLogs.size());
	}

	/**
	 * 从日志详细页面中找到日期。
	 * 
	 * @param document
	 * @return
	 */
	private Date findDate(Document document) {
		int year = 0, month = 0, day = 0, hours = 0, minutes = 0, seconds = 0;
		// date
		NodeList nodeList = document.getElementsByTagName("script");
		Node node = nodeList.item(14);
		String str = node.getFirstChild().getNodeValue();
		log.debug("node value: " + str);

		// year
		String pattern = "setSelect\\(\"year\",		'([0-9]{4})'\\);";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		boolean rs = m.find();
		log.debug("rs: " + rs);
		log.debug("groupCount: " + m.groupCount());
		if (rs) {
			for (int i = 1; i <= m.groupCount(); i++) {
				year = NumberUtils.toInt(m.group(i));
				log.debug("year: " + year);
			}
		}
		// month
		pattern = "setSelect\\(\"month\",		'([0-9]{1,2})'\\);";
		p = Pattern.compile(pattern);
		m = p.matcher(str);
		rs = m.find();
		if (rs) {
			for (int i = 1; i <= m.groupCount(); i++) {
				month = NumberUtils.toInt(m.group(i));
				log.debug("month: " + month);
			}
		}
		// day
		pattern = "setSelect\\(\"day\",		'([0-9]{1,2})'\\);";
		p = Pattern.compile(pattern);
		m = p.matcher(str);
		rs = m.find();
		if (rs) {
			for (int i = 1; i <= m.groupCount(); i++) {
				day = NumberUtils.toInt(m.group(i));
				log.debug("day: " + day);
			}
		}
		// hours
		pattern = "setSelect\\(\"hours\",		'([0-9]{1,2})'\\);";
		p = Pattern.compile(pattern);
		m = p.matcher(str);
		rs = m.find();
		if (rs) {
			for (int i = 1; i <= m.groupCount(); i++) {
				hours = NumberUtils.toInt(m.group(i));
				log.debug("hours: " + hours);
			}
		}
		// minutes
		pattern = "setSelect\\(\"minutes\",	'([0-9]{1,2})'\\);";
		p = Pattern.compile(pattern);
		m = p.matcher(str);
		rs = m.find();
		if (rs) {
			for (int i = 1; i <= m.groupCount(); i++) {
				minutes = NumberUtils.toInt(m.group(i));
				log.debug("minutes: " + minutes);
			}
		}
		// seconds
		pattern = "setSelect\\(\"seconds\",	'([0-9]{1,2})'\\);";
		p = Pattern.compile(pattern);
		m = p.matcher(str);
		rs = m.find();
		if (rs) {
			for (int i = 1; i <= m.groupCount(); i++) {
				seconds = NumberUtils.toInt(m.group(i));
				log.debug("seconds: " + seconds);
			}
		}

		Calendar cal = new GregorianCalendar(year, month, day, hours, minutes,
				seconds);
		return cal.getTime();
	}

	/**
	 * 从日志详细页面中找到日志内容。
	 * 
	 * @param document
	 * @return
	 */
	private String findBody(Document document) {
		String body = null;
		NodeList nodeList = document.getElementsByTagName("script");
		boolean notFound = true;
		int index = 0;
		while (index < nodeList.getLength() && notFound) {
			Node node = nodeList.item(index);
			index++;
			if (node.getFirstChild() == null) {
				continue;
			}
			String str = node.getFirstChild().getNodeValue();
			log.debug("node value: " + str);

			String pattern = "if\\(sState == \"iframe\"\\)\\{\\s*SinaEditor\\.initialize\\(\"SinaEditor\", \"blog_body\", true, \"(.*)\"\\);";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			boolean rs = m.find();
			if (rs) {
				notFound = false;
				for (int i = 1; i <= m.groupCount(); i++) {
					body = m.group(i);
					body = body.substring(0, body.length() - 3);
					body = StringEscapeUtils.unescapeJavaScript(body);
					log.debug("body: " + body);
				}
			}
		}
		return body;
	}

	/**
	 * @param webLog
	 */
	private void readComments(WebLog webLog) {
		String url = webLog.getUrl();
		String commentsPageUrl = new ViewBlogPageParser(this.httpDocument
				.get(url)).getCommentsFirstPageUrl();
		if (commentsPageUrl == null) {
			return;
		}

		String commentsPageUrlPattern = commentsPageUrl.substring(0,
				commentsPageUrl.length() - 6)
				+ "%1$s.html";
		log.debug("commentsPageUrlPattern: " + commentsPageUrlPattern);
		CommentPageParser commentPageParser;
		List<Comment> comments = null;
		int i = 1;
		do {
			commentsPageUrl = String.format(commentsPageUrlPattern, i);
			commentPageParser = new CommentPageParser(this.httpDocument
					.get(commentsPageUrl));
			comments = commentPageParser.getComments();
			webLog.getComments().addAll(comments);
			i++;
		} while (comments.size() != 0);
	}

	public byte[] getIdentifyingCodeImage() throws HttpException, IOException {
		return SinaLogin.getIdentifyingCodeImage(httpClient);
	}

	public static void main(String[] args) throws HttpException, IOException,
			BlogMoverException {
		SinaReader sr = new SinaReader();
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(
				System.in));
		System.out.print("Please enter your username: ");
		sr.setUsername(lnr.readLine());
		System.out.print("Please enter your password: ");
		sr.setPassword(lnr.readLine());
		byte[] image = sr.getIdentifyingCodeImage();
		File file = new File(SystemUtils.JAVA_IO_TMPDIR, SinaReader.class
				.getName());
		FileUtils.writeByteArrayToFile(file, image);
		System.out.print(String.format(
				"Please enter the code on the image(%1$s): ", file.getPath()));
		String identifyingCode = lnr.readLine();
		System.out.println("Your entered code is: " + identifyingCode);
		if (!file.delete()) {
			file.deleteOnExit();
		}
		sr.setIdentifyingCode(identifyingCode.toString().trim());
		List<WebLog> entries = sr.read();
		for (WebLog entry : entries) {
			System.out.println(entry.getTitle());
			System.out.println(entry.getUrl());
		}
	}
}
