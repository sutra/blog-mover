/**
 *  Created on 2006-7-28 13:44:26
 */
package com.redv.blogmover.bsps.sina;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.ho.yaml.Yaml;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.BlogSettings;
import com.redv.blogmover.Category;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.CategoryImpl;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DocumentToString;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;
import com.redv.blogmover.util.IntUtil;
import com.redv.blogmover.util.StringUtil;

/**
 * @author Richard Zhu
 * @version 1.0
 * 
 */
public class SinaReader extends AbstractBlogReader {

	/**
	 * 配置文件路径
	 */
	public static String configFilePath = System.getProperty("user.dir")
			+ "\\settings.yaml";

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private boolean loggedIn = false;

	private String username;

	private String password;

	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public static BlogSettings config = null;

	public static BlogSettings getConfig() {
		if(null == SinaReader.config) {
			SinaReader.config = SinaReader.initConfig();
			return config;
		}
		return SinaReader.config;
	}


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
	
	public static BlogSettings initConfig()
	{
		String filePath = SinaReader.configFilePath;
		File rulesFile = new File(filePath);
		
		try {
			BlogSettings config = Yaml.loadType(rulesFile, BlogSettings.class);
			return config;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
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
		String url = "http://blog.sina.com.cn/s/articlelist_" + this.getUid()
				+ "_0_%s.html";

		String acateUrl = "http://blogcnf.sinajs.cn/acate?jv=x&"
				+ this.getUid();

		int totalArticleCount = 0;
		int pages = 0;
		Document document = httpDocument.get(acateUrl);

		// 获取文章总数和页码总数
		String p = "\"total\":([0-9]+)";
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(document.getFirstChild()
				.getTextContent());
		boolean rs = matcher.find();
		if (rs) {
			totalArticleCount = NumberUtils.toInt(matcher.group(1));
			this.status.setApproximative(true);
			this.status.setTotalCount(totalArticleCount);
		}
		pages = IntUtil.GetPageCount(totalArticleCount);
		log.debug("---- 日志页数 ----：" + String.valueOf(pages));

		String listPageUrl = "";
		for (int i = 1; i <= pages; i++) {
			listPageUrl = String.format(url, i);
			document = httpDocument.get(listPageUrl);
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
		String doc = DocumentToString.toString(document);

		String pattern = "http://blog.sina.com.cn/s/blog_([0-9a-z]+).html";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(doc);
		while (m.find()) {
			String id = m.group(1);
			if (ids.add(id)) {
				detailWebLog(id);
			}
		}
	}

	/**
	 * 将某个日志读取完毕。
	 * 
	 * @param id
	 *            日志编号
	 */
	private void detailWebLog(String id) {
		String postUrl = "http://blog.sina.com.cn/s/blog_" + id + ".html";
		String url = "http://blog.sina.com.cn/u/" + this.getUid();
		WebLog webLog = new WebLogImpl();
		webLog.setUrl(url);

		Document document = httpDocument.get(postUrl);
		document.normalize();
		String doc = DocumentToString.toString(document);

		// 日志标题
		String pattern = "<b\\s*id=\"t_" + id + "\">(.*?)</b>";
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE | Pattern.MULTILINE | Pattern.COMMENTS);
		Matcher m = p.matcher(doc);
		boolean rs = m.find();
		if (rs) {
			webLog.setTitle(m.group(1));
			log.debug("title: " + webLog.getTitle());
		} else {
			throw new BlogMoverRuntimeException("新浪日志页码标题编号已变更，请稍后重试。");
		}

		// 发表时间
		pattern = "class=\"time\">\\((.*?)\\)</span>";
		p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE | Pattern.MULTILINE | Pattern.COMMENTS);
		m = p.matcher(doc);
		rs = m.find();
		if (rs) {
			webLog.setPublishedDate(StringUtil.strToDate(m.group(1)));
			log.debug("publishedDate: " + webLog.getPublishedDate());
		}

		// tags 标签
		pattern = "var\\s*\\$tag='(.*?)';";
		p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE | Pattern.MULTILINE | Pattern.COMMENTS);
		m = p.matcher(doc);
		rs = m.find();
		if (rs) {
			String tags = m.group(1);
			webLog.setTags(tags.split(","));
			log.debug("tags: " + tags);
		}

		// 日志所属分类
		pattern = "分类：<a\\shref=\"(.*?)\">(.*?)</a>";
		p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE | Pattern.MULTILINE | Pattern.COMMENTS);
		m = p.matcher(doc);
		rs = m.find();
		if (rs) {
			String strCategory = m.group(2);
			CategoryImpl category = new CategoryImpl();
			category.setName(strCategory);
			Category[] categories = new CategoryImpl[] { category };
			webLog.setCategories(categories);
			log.debug("category: " + strCategory);
		}

		// 日志内容（含图片）
		NodeList bodyNodeList = document.getElementById("articleBody").getChildNodes();
		StringBuilder sb = new StringBuilder();
		BlogSettings config = SinaReader.getConfig();
		for (int i = 0; i < bodyNodeList.getLength(); i++) {
			sb.append(DomNodeUtils.TextExtractor(bodyNodeList.item(i), config));
		}
		webLog.setBody(StringEscapeUtils.unescapeJavaScript(sb.toString()));

		// 评论信息
		readComments(webLog, id);

		this.status.setCurrentWebLog(webLog);
		webLogs.add(webLog);
		this.status.setCurrentCount(webLogs.size());
	}

	/**
	 * @param webLog
	 */
	private void readComments(WebLog webLog, String postId) {
		String commentsPageUrl = "http://blog.sina.com.cn/s/comment_" + postId
				+ "_%s.html";
		String uid = postId.substring(0, 8);
		String aids = postId.substring(10);

		// 获取评论总数，也可以获得阅读、收藏次数
		int totalCommentsCount = 0;
		String commentsCnfUrl = "http://blogcnf.sinajs.cn/num?uid=" + uid
				+ "&aids=" + aids + "&requestId=scriptId_0.942631857192015";
		Document cnfDoc = this.httpDocument.get(commentsCnfUrl);
		String p = "\"" + aids + "\":\\{c:(\\d+),";
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(cnfDoc.getFirstChild()
				.getTextContent());
		boolean rs = matcher.find();
		if (rs) {
			totalCommentsCount = NumberUtils.toInt(matcher.group(1));
		} else {
			return; // 如果找不到评论总数，则退出
		}

		int pages = IntUtil.GetPageCount(totalCommentsCount);

		CommentPageParser commentPageParser;
		List<Comment> comments = null;
		String url = "";
		for (int i = 1; i <= pages; i++) {
			url = String.format(commentsPageUrl, i);
			log.debug(url);

			commentPageParser = new CommentPageParser(this.httpDocument
					.getHtml(url));
			comments = commentPageParser.getComments();
			webLog.getComments().addAll(comments);
		}
	}

	public byte[] getIdentifyingCodeImage() throws HttpException, IOException {
		return SinaLogin.getIdentifyingCodeImage(httpClient);
	}

	public static void main(String[] args) throws HttpException, IOException,
			BlogMoverException {

		SinaReader sr = new SinaReader();

		sr.setUid("1258516351");
		/*
		 * LineNumberReader lnr = new LineNumberReader(new InputStreamReader(
		 * System.in)); System.out.print("Please enter your username: ");
		 * sr.setUsername(lnr.readLine());
		 * System.out.print("Please enter your password: ");
		 * sr.setPassword(lnr.readLine());
		 * 
		 * String identifyingCode = "";
		 * 
		 * sr.setIdentifyingCode(identifyingCode);
		 */
		List<WebLog> entries = sr.read();
		int i = 0;
		for (WebLog entry : entries) {
			System.out.print(++i);
			System.out.print('\t');
			System.out.println(entry.getTitle());
			System.out.println(entry.getUrl());
		}
	}
}
