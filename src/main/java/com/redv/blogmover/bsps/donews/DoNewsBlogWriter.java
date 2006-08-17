/**
 *  Created on 2006-7-16 3:37:19
 */
package com.redv.blogmover.bsps.donews;

import java.io.IOException;
import java.util.ArrayList;
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

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogRemoverException;
import com.redv.blogmover.BlogRemoverRuntimeException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class DoNewsBlogWriter extends AbstractBlogWriter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7682529181037898575L;

	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private DoNewsLogin doNewsLogin;

	private boolean loggedIn = false;

	private String username;

	private String password;

	private String identifyingCode;

	/**
	 * 
	 */
	public DoNewsBlogWriter() {
		super();
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, true);
		this.doNewsLogin = new DoNewsLogin(httpClient, httpDocument);
	}

	/**
	 * @return Returns the identifyingCode.
	 */
	public String getIdentifyingCode() {
		return identifyingCode;
	}

	/**
	 * @param identifyingCode
	 *            The identifyingCode to set.
	 */
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
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
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#start()
	 */
	@Override
	protected void begin() throws BlogRemoverException {
		if (!loggedIn) {
			log.debug("尚未登录，开始登录。");
			doNewsLogin.login(username, password, identifyingCode);
			loggedIn = true;
			log.debug("登录成功。");
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
		Document document = this.getPostForm();
		Element titleElement = document.getElementById("Editor_Edit_txbTitle");
		if (titleElement == null) {
			throw new BlogRemoverRuntimeException("没有找到发表文章的表单。");
		}

		List<NameValuePair> parameters = this.buildPostParameters(document,
				webLog);
		String url = "http://writeblog.donews.com/EditPosts.aspx";
		log.debug("提交发表表单");

		HeaderGroup hg = new HeaderGroup();
		hg.addHeader(new Header("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8"));
		httpDocument.post(url, parameters, hg);
	}

	private Document getPostForm() {
		String url = "http://writeblog.donews.com/EditPosts.aspx";
		log.debug("获取编辑页面");
		Document document = httpDocument.get(url);
		log.debug("按下“写文章”链接，获取写文章表单");
		// 按下“写文章”链接：javascript:__doPostBack('_ctl0','')。
		NodeList inputs = document.getElementsByTagName("input");
		NameValuePair[] parameters = new NameValuePair[inputs.getLength()];
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			parameters[i] = new NameValuePair();
			parameters[i].setName(name);
			if ("__EVENTTARGET".equals(name)) {
				parameters[i].setValue("_ctl0");
			} else if ("__EVENTARGUMENT".equals(name)) {
				parameters[i].setValue("");
			} else {
				parameters[i].setValue(value);
			}
		}
		// 写文章的表单。
		document = httpDocument.post(url, parameters);
		return document;
	}

	private List<NameValuePair> buildPostParameters(Document document,
			WebLog webLog) {
		// 填充表单。
		NodeList inputs = document.getElementsByTagName("input");
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			NameValuePair parameter = new NameValuePair();
			log.debug("正在填充：" + name);
			parameter.setName(name);
			if ("Editor:Edit:txbTitle".equals(name)) {
				parameter.setValue(webLog.getTitle());
			} else if ("Editor:Edit:rblOri".equals(name)) {
				parameter.setValue("ori");
			} else if ("Editor:Edit:FCKEditor".equals(name)) {
				// ....
			} else if ("Editor:Edit:Advanced:ckbPublished".equals(name)
					|| "Editor:Edit:Advanced:chkComments".equals(name)
					|| "Editor:Edit:Advanced:chkDisplayHomePage".equals(name)
					|| "Editor:Edit:Advanced:chkMainSyndication".equals(name)
					|| "Editor:Edit:Advanced:chkIsAggregated".equals(name)) {
				parameter.setValue("on");
			} else if ("__EVENTTARGET".equals(name)) {// __doPostBack('Editor$Edit$lkbPost','')}
				parameter.setValue("Editor:Edit:lkbPost");
			} else {
				parameter.setValue(value);
			}
			parameters.add(parameter);
		}
		NodeList textareas = document.getElementsByTagName("textarea");
		for (int i = 0; i < textareas.getLength(); i++) {
			Element textarea = (Element) textareas.item(i);
			String name = textarea.getAttribute("name");
			String value = textarea.getAttribute("value");
			NameValuePair parameter = new NameValuePair();
			parameter.setName(name);
			if ("Editor:Edit:txbExcerpt".equals(name)) {
				parameter.setValue(webLog.getExcerpt());
			} else {
				parameter.setValue(value);
			}
		}
		NameValuePair parameter = new NameValuePair();
		parameter.setName("Editor:Edit:FCKEditor");
		parameter.setValue(webLog.getBody());
		parameters.add(parameter);

		return parameters;
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
		return this.doNewsLogin.getIdentifyingCodeImage();
	}

}
