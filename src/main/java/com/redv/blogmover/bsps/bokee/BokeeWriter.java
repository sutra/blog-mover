/**
 * Created on 2006-8-22 下午09:29:14
 */
package com.redv.blogmover.bsps.bokee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author Shutra
 * 
 */
public class BokeeWriter extends AbstractBlogWriter {
	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private BokeeLogin bokeeLogin;

	private String username;

	private String password;

	public BokeeWriter() {
		bsp.setName("博客网");
		bsp.setServerURL("http://www.bokee.com/");

		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, "GBK");
		bokeeLogin = new BokeeLogin(httpClient, httpDocument);
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#begin()
	 */
	@Override
	protected void begin() throws BlogMoverException {
		bokeeLogin.login(username, password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeAttachment(com.redv.blogmover.Attachment)
	 */
	@Override
	protected String writeAttachment(Attachment att) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeBlog(com.redv.blogmover.WebLog,
	 *      java.util.Map)
	 */
	@Override
	protected void writeBlog(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogMoverException {
		String url = "http://" + username
				+ ".bokee.com/control/diary/postDiary.b";
		Document document = httpDocument.get(url);
		String action = url;
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		NodeList inputs = document.getElementsByTagName("input");
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			if (name.equals("diary.diaryTitle")) {
				parameters.add(new NameValuePair(name, webLog.getTitle()));
			} else if (name.equals("diary.diaryContent")) {
				parameters.add(new NameValuePair(name, webLog.getBody()));
			} else {
				parameters.add(new NameValuePair(name, value));
			}
		}
		parameters.add(new NameValuePair("diary.categoryId", "0"));
		httpDocument.post(action, parameters);

		throw new UnsupportedOperationException("不支持搬迁到 bokee 网。");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeComment(com.redv.blogmover.Comment)
	 */
	@Override
	protected void writeComment(Comment comment) throws BlogMoverException {
		// TODO Auto-generated method stub

	}

}
