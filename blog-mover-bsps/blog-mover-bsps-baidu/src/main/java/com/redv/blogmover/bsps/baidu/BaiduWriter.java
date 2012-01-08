/**
 *  Created on 2006-7-18 22:32:22
 */
package com.redv.blogmover.bsps.baidu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 *
 */
public class BaiduWriter extends AbstractBlogWriter {

	private HttpDocument httpDocument;

	private String username;

	private String password;

	private String blogHandle;

	private static final Pattern messagePattern = Pattern
			.compile(".*writestr\\(\"(.*)\"\\);.*");

	/**
	 *
	 */
	public BaiduWriter() {
		super();

		HttpClient httpClient = new HttpClient();
		httpDocument = new HttpDocument(httpClient, "GB2312");
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#start()
	 */
	@Override
	protected void begin() throws BlogMoverException {
		new BaiduLogin(httpDocument).login(username, password);
		blogHandle = BaiduUtils.findBlogHandle(httpDocument);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#writeBlog(com.redv.blogremover.WebLog,
	 *      java.util.Map)
	 */
	@Override
	protected void writeBlog(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogMoverException {
		String action = "http://hi.baidu.com/" + blogHandle + "/commit";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		NameValuePair parameter = new NameValuePair("ct", "1");
		parameters.add(parameter);

		parameter = new NameValuePair("cm", "1");
		parameters.add(parameter);

		parameter = new NameValuePair("spBlogTitle", webLog.getTitle());
		parameters.add(parameter);

		parameter = new NameValuePair("spBlogText", webLog.getBody());
		parameters.add(parameter);

		parameter = new NameValuePair("spBlogCatName", "默认分类");
		parameters.add(parameter);

		parameter = new NameValuePair("spIsCmtAllow", "1");
		parameters.add(parameter);

		parameter = new NameValuePair("spBlogPower", "0");
		parameters.add(parameter);

		parameter = new NameValuePair("tj", " 发表文章 ");
		parameters.add(parameter);

		HeaderGroup hg = new HeaderGroup();
		hg.addHeader(new Header("Content-Type",
				"application/x-www-form-urlencoded; charset=GB2312"));
		Document document = httpDocument.post(action, parameters, hg);
		NodeList scripts = document.getElementsByTagName("script");
		String message = null;
		String expectMessage = "恭喜,您的文章已经发表成功。";
		boolean ok = false;
		for (int i = 0; i < scripts.getLength(); i++) {
			Element script = (Element) scripts.item(i);
			if (log.isDebugEnabled()) {
				try {
					log.debug("node: " + DomNodeUtils.getXmlAsString(script));
				} catch (TransformerException e) {
					log.debug(e);
				}
			}
			Node firstChild = script.getFirstChild();
			if (firstChild != null) {
				String actualMessage = firstChild.getNodeValue();
				log.debug("actualMessage: " + actualMessage);
				Matcher matcher = messagePattern.matcher(actualMessage);
				if (matcher.find()) {
					log.debug("found");
					message = matcher.group(1);
				}
				if (actualMessage != null
						&& actualMessage.indexOf(expectMessage) != -1) {
					ok = true;
				}
			}
		}
		if (!ok) {
			throw new BlogMoverException("写入错误，百度的提示为： "
					+ StringUtils.defaultString(message, ""));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#writeComment(com.redv.blogremover.Comment)
	 */
	@Override
	protected void writeComment(Comment comment) throws BlogMoverException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#writeAttachment(com.redv.blogremover.Attachment)
	 */
	@Override
	protected String writeAttachment(Attachment att) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws BlogMoverException {
		BaiduWriter bw = new BaiduWriter();
		bw.setUsername("blogmover1");
		bw.setPassword("blogmover1");
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("title");
		// webLog.setBody("body");
		webLogs.add(webLog);
		bw.write(webLogs);
	}
}
