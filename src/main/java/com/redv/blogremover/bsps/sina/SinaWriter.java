/**
 *  Created on 2006-7-19 23:11:39
 */
package com.redv.blogremover.bsps.sina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogremover.Attachment;
import com.redv.blogremover.BlogRemoverException;
import com.redv.blogremover.Comment;
import com.redv.blogremover.WebLog;
import com.redv.blogremover.impl.AbstractBlogWriter;
import com.redv.blogremover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class SinaWriter extends AbstractBlogWriter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7633022188758496941L;

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private boolean loggedIn = false;

	private String username;

	private String password;

	private String identifyingCode;

	/**
	 * 
	 */
	public SinaWriter() {
		super();
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, true, "GB2312");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#start()
	 */
	@Override
	protected void begin() throws BlogRemoverException {
		if (!loggedIn) {
			new SinaLogin(httpDocument).login(username, password,
					identifyingCode);
			loggedIn = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogRemoverException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#writeBlog(com.redv.blogremover.WebLog,
	 *      java.util.Map)
	 */
	@Override
	protected void writeBlog(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogRemoverException {
		String action = "http://blog.sina.com.cn/control/writing/scriber/article_post.php";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		Document document = httpDocument
				.get("http://blog.sina.com.cn/control/writing/scriber/article_add.php?quick1");
		NodeList inputs = document.getElementsByTagName("input");
		NodeList selects = document.getElementsByTagName("select");
		NodeList textareas = document.getElementsByTagName("textarea");

		NameValuePair parameter;
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			parameter = new NameValuePair();
			parameter.setName(name);
			log.debug("设置" + name + "的值。");
			if ("blog_title".equals(name)) {
				parameter.setValue(webLog.getTitle());
			} else if ("prview".equals(name)) {
				continue;
			} else if ("draft".equals(name)) {
				continue;
			} else {
				parameter.setValue(value);
			}
			parameters.add(parameter);
		}

		Calendar cal = new GregorianCalendar();
		cal.setTime(webLog.getPublishedDate());
		for (int i = 0; i < selects.getLength(); i++) {
			Element select = (Element) selects.item(i);
			String name = select.getAttribute("name");
			String value = select.getAttribute("value");
			parameter = new NameValuePair();
			parameter.setName(name);
			log.debug("设置" + name + "的值。");
			if ("year".equals(name)) {
				parameter.setValue(Integer.toString(cal.get(Calendar.YEAR)));
			} else if ("month".equals(name)) {
				parameter.setValue(Integer.toString(cal.get(Calendar.MONTH)));
			} else if ("day".equals(name)) {
				parameter.setValue(Integer.toString(cal.get(Calendar.DATE)));
			} else if ("hours".equals(name)) {
				parameter.setValue(Integer.toString(cal.get(Calendar.HOUR)));
			} else if ("minutes".equals(name)) {
				parameter.setValue(Integer.toString(cal.get(Calendar.MINUTE)));
			} else if ("seconds".equals(name)) {
				parameter.setValue(Integer.toString(cal.get(Calendar.SECOND)));
			} else if ("blog_class".equals(name)) {
				parameter.setValue("0");
			} else {
				parameter.setValue(value);
			}
			parameters.add(parameter);
		}

		log.debug("textarea length: " + textareas.getLength());
		for (int i = 0; i < textareas.getLength(); i++) {
			Element textarea = (Element) textareas.item(i);
			String name = textarea.getAttribute("name");
			String value = textarea.getAttribute("value");
			parameter = new NameValuePair();
			parameter.setName(name);
			log.debug("设置" + name + "的值。");
			if ("blog_body".equals(name)) {
				parameter.setValue(webLog.getBody());
			} else {
				parameter.setValue(value);
			}
			parameters.add(parameter);
		}

		parameter = new NameValuePair("blog_body", webLog.getBody());
		parameters.add(parameter);

		HeaderGroup hg = new HeaderGroup();
		hg.addHeader(new Header("Content-Type",
				"application/x-www-form-urlencoded; charset=GB2312"));
		httpDocument.post(action, parameters, hg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#writeComment(com.redv.blogremover.Comment)
	 */
	@Override
	protected void writeComment(Comment comment) throws BlogRemoverException {
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

	public byte[] getIdentifyingCodeImage() throws HttpException, IOException {
		return SinaLogin.getIdentifyingCodeImage(httpClient);
	}

}
