/**
 * Created on 2006-8-4 下午07:41:50
 */
package com.redv.blogmover.bsps.donews;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;
import com.redv.blogmover.util.IntervallicHttpClient;

/**
 * @author Sutra
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 */
public class DoNewsBlogReader extends AbstractBlogReader {
	private static final String URL_BASE = "http://blog.donews.com";
	private static final String URL_FORMAT = URL_BASE
			+ "/%1$s/archive/%2$s/%3$s.aspx";
	private static final DateFormat URL_DATE_FORMAT = new SimpleDateFormat(
			"yyyy/MM/dd", Locale.ENGLISH);
	private static final String DUMMY_DATE_STRING = "2000/01/01";
	private static final DateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat(
			"yyyy年MM月dd日 h:mm a", Locale.ENGLISH);
	private static final String DISPLAY_DATE_PARSE_FLAG = "发表于";
	private static final int DISPLAY_DATE_PARSE_FLAG_LENGTH = DISPLAY_DATE_PARSE_FLAG
			.length();

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

		httpClient = new IntervallicHttpClient();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<WebLog> read() throws BlogMoverException {
		this.httpClient.getParams().setLongParameter(
				IntervallicHttpClient.MINIMUM_INTERVAL, this.minimumInterval);
		this.httpClient.getParams().setLongParameter(
				IntervallicHttpClient.MAXIMUM_INTERVAL, this.maximumInterval);

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

		// 解析页数。
		int totalPage;
		if (pager != null) {
			Node last = getLastPageNode(pager);
			String href = last.getAttributes().getNamedItem("href").getNodeValue();
			String pageString = href.substring("EditPosts.aspx?pg=".length(), href
					.length());
			log.debug("pageString: " + pageString);
			totalPage = NumberUtils.toInt(pageString);
		} else {
			totalPage = 1;
			this.status.setApproximative(true);
		}
		this.status.setTotalCount(10 * totalPage);

		// 解析当前页的 blog entry 列表。
		parse(document);

		// 当前页也就是第一页上面已经解析过了，这里从第二页开始。
		for (int i = 2; i <= totalPage; i++) {
			log.debug("Parse page " + i);
			parse(httpDocument.get(url + i));
		}
		return this.webLogs;
	}

	/**
	 * 获取最后一页按钮的节点元素。
	 * 
	 * @param pager
	 *            翻页部分的HTML节点
	 * @return 最后一页按钮的HTML节点
	 */
	private Node getLastPageNode(Element pager) {
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
			/**
			 * bug fix
			 * 
			 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
			 *         修改如果用户日志不超过9页的时候不会出现最后文字。
			 */
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				if (node.getFirstChild() == null) {
					continue;
				}
				last = node;
			}
		}
		return last;
	}

	private void checkLogin() throws DOMException, BlogMoverException {
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

		// PublishedDate.
		Date publishedDate = getPublishedDate(id);
		webLog.setPublishedDate(publishedDate);

		// URL.
		webLog.setUrl(getUrl(id, publishedDate));

		return webLog;
	}

	public byte[] getIdentifyingCodeImage() throws HttpException, IOException {
		return doNewsLogin.getIdentifyingCodeImage();
	}

	public Date getPublishedDate(String id) {
		String url = String.format(URL_FORMAT, username, DUMMY_DATE_STRING, id);
		Document document = httpDocument.get(url);
		List<Node> nodes = DomNodeUtils.getElementsByCssClass(document, "div",
				"postFoot");
		Node postFoot = nodes.get(0);
		String content = DomNodeUtils.getTextContent(postFoot);
		String dateString = content.substring(content
				.indexOf(DISPLAY_DATE_PARSE_FLAG)
				+ DISPLAY_DATE_PARSE_FLAG_LENGTH);
		try {
			return DISPLAY_DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			throw new BlogMoverRuntimeException(e);
		}
	}

	public String getUrl(String id, Date publishedDate) {
		String dateString = URL_DATE_FORMAT.format(publishedDate);
		return String.format(URL_FORMAT, this.username, dateString, id);
	}

	public static void main(String[] args) throws HttpException, IOException,
			BlogMoverException {
		LineNumberReader in = new LineNumberReader(new InputStreamReader(
				System.in));
		PrintStream out = System.out;
		DoNewsBlogReader dnbr = new DoNewsBlogReader();
		byte[] image = dnbr.getIdentifyingCodeImage();
		File tmpImageFile = new File(System.getProperty("java.io.tmpdir"),
				DoNewsBlogReader.class.getName() + ".jpg");
		tmpImageFile.deleteOnExit();
		OutputStream os = new FileOutputStream(tmpImageFile);
		try {
			os.write(image);
		} finally {
			os.close();
		}
		out.print("Please enter the code(" + tmpImageFile.getPath() + "): ");
		String code = in.readLine();
		out.print("Please enter your username: ");
		String username = in.readLine();
		out.print("Please enter your password: ");
		String password = in.readLine();
		dnbr.setUsername(username);
		dnbr.setPassword(password);
		dnbr.setIdentifyingCode(code);
		List<WebLog> entries = dnbr.read();
		out.println("entries: " + entries.size());
	}
}
