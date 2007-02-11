/**
 * Created on 2006-8-11 上午03:14:32
 */
package com.redv.blogmover.bsps.com.sohu.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringUtils;
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
public class SohuBlogWriter extends AbstractBlogWriter {
	private HttpClient httpClient;

	private HttpDocument httpDocument;

	private HeaderGroup requestHeaderGroup;

	private SohuBlogLogin sohuBlogLogin;

	private String maildomain;

	private String passwd;

	private String username;

	public SohuBlogWriter() {
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		this.requestHeaderGroup = new HeaderGroup();
		this.requestHeaderGroup.addHeader(new Header("User-Agent",
				ManageUrlConstants.USER_AGENT));
		httpDocument = new HttpDocument(httpClient, requestHeaderGroup, false,
				"GBK");
		sohuBlogLogin = new SohuBlogLogin(this.httpClient);
	}

	public void setMaildomain(String maildomain) {
		this.maildomain = maildomain;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#begin()
	 */
	@Override
	protected void begin() throws BlogMoverException {
		sohuBlogLogin.login(username, maildomain, passwd);
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
	 * @see com.redv.blogremover.impl.AbstractBlogWriter#writeAttachment(com.redv.blogremover.Attachment)
	 */
	@Override
	protected String writeAttachment(Attachment att) {
		return null;
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
		String url = "http://blog.sohu.com/manage/entry.do?m=add";
		Document document = httpDocument.get(url);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		NodeList inputs = document.getElementsByTagName("input");
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			if (StringUtils.equals(name, "aid")) {
				parameters.add(new NameValuePair("aid", value));
			}
		}
		String action = "http://blog.sohu.com/manage/entry.do";

		parameters.add(new NameValuePair("oper", "art_ok"));
		parameters.add(new NameValuePair("m", "save"));
		parameters.add(new NameValuePair("shortcutFlag", "true"));
		parameters.add(new NameValuePair("entrytitle", webLog.getTitle()));
		parameters.add(new NameValuePair("categoryId", "0"));
		parameters.add(new NameValuePair("entrycontent", webLog.getBody()));
		parameters.add(new NameValuePair("swapView", "0"));
		parameters.add(new NameValuePair("newGategory", ""));
		parameters.add(new NameValuePair("keywords", ""));
		parameters.add(new NameValuePair("excerpt", webLog.getExcerpt()));
		parameters.add(new NameValuePair("allowComment", "0"));
		parameters.add(new NameValuePair("perm", "0"));
		parameters.add(new NameValuePair("save", "发表日志"));

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
	protected void writeComment(Comment comment) throws BlogMoverException {
		// TODO Auto-generated method stub

	}

}
