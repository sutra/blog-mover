/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		BlogcnWriter.java
 * Module:			blog-mover
 * Class:			BlogcnWriter
 * Date:			2006-9-27 上午08:42:39
 * Author:			<a href="mailto:rory.cn@gmail.com">somebody</a>
 * Description:		
 *
 *
 * ======================================================================
 * Change History Log
 * ----------------------------------------------------------------------
 * Mod. No.	| Date		| Name			| Reason			| Change Req.
 * ----------------------------------------------------------------------
 * 			| 2006-9-27   | Rory Ye	    | Created			|
 *
 * </pre>
 **/
package com.redv.blogmover.bsps.blogcn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
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
 * @author <a href="mailto:rory.cn@gmail.com">somebody</a>
 * @since 2006-9-27 上午08:42:39
 * @version $Id BlogcnWriter.java$
 */
public class BlogcnWriter extends AbstractBlogWriter {
	
	private HttpDocument httpDocument;
	
	private String username;
	
	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 
	 */
	public BlogcnWriter() {
		super();
		HttpClient httpClient  = new HttpClient();
		httpDocument = new HttpDocument(httpClient, true, "GBK");
	}

	/* (non-Javadoc)
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#begin()
	 */
	@Override
	protected void begin() throws BlogMoverException {
		new BlogcnLogin(httpDocument).login(username, password);
	}

	/* (non-Javadoc)
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {
		
	}

	/* (non-Javadoc)
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeAttachment(com.redv.blogmover.Attachment)
	 */
	@Override
	protected String writeAttachment(Attachment att) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeBlog(com.redv.blogmover.WebLog, java.util.Map)
	 */
	@Override
	protected void writeBlog(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogMoverException {
		Document document = httpDocument.get("http://www.blog.com.cn/user_post.asp");
		String postchknum = null;
		NodeList inputs = document.getElementsByTagName("input");
		for (int i=0; i<inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			String name = input.getAttribute("name");
			String value = input.getAttribute("value");
			if (name.equals("postchknum")) {
				postchknum = value;
			}
		}
		
		String action = "http://www.blog.com.cn/user_post.asp?action=savelog&t=0";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		/**
		 * 设置一个默认类别,或许应该提供用户选择
		 * <code>
		 * <option value='1'>情感私语</option>
		 * </code>
		 */
		NameValuePair parameter = new NameValuePair("classid","1");
		parameters.add(parameter);
		
		/**
		 * 个人分类默认不选择
		 * <code>
		 * <select name="subjectid" id="subjectid">
		 *    <option value="0">不选择</option>
		 * </select>
		 * </code>
		 */
		parameter = new NameValuePair("subjectid", "0");
		parameters.add(parameter);
		
		/**
		 * 这个东西好像是随机生成的。还要做一些处理才行
		 * <code>
		 * <input type="hidden" name="postchknum" value="1492" />
		 * </code>
		 */
		if (log.isDebugEnabled()) {
			log.debug("postchknum:" + postchknum);
		}
		parameter = new NameValuePair("postchknum", postchknum);
		
		
		parameter = new NameValuePair("topic", webLog.getTitle());
		parameters.add(parameter);
		
		parameter = new NameValuePair("logtags",StringUtils.join(webLog.getTags(), ","));
		parameters.add(parameter);
		
		parameter = new NameValuePair("edit", "");
		parameters.add(parameter);
		
		parameter = new NameValuePair("isubb", "0");
		parameters.add(parameter);
		
		parameter = new NameValuePair("isdraft", "0");
		parameters.add(parameter);
		
		parameter = new NameValuePair("isencomment","1");
		parameters.add(parameter);
		
		parameter = new NameValuePair("action","savelog");
		parameters.add(parameter);
		
		/**
		 * 设置发布时间
		 */
		Calendar cal = new GregorianCalendar();
		cal.setTime(webLog.getPublishedDate());
		
		if (log.isDebugEnabled()) {
			log.debug("设置发送时间:" + webLog.getPublishedDate());
		}
		parameter = new NameValuePair("selecty",String.valueOf(cal.get(Calendar.YEAR)));
		parameters.add(parameter);
		
		parameter = new NameValuePair("selectm",String.valueOf(cal.get(Calendar.MONTH)));
		parameters.add(parameter);
		
		parameter = new NameValuePair("selectd",String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		parameters.add(parameter);
		
		parameter = new NameValuePair("selecth",String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
		parameters.add(parameter);
		
		parameter = new NameValuePair("selectmi",String.valueOf(cal.get(Calendar.MINUTE)));
		parameters.add(parameter);
		
		parameter = new NameValuePair("selects",String.valueOf(cal.get(Calendar.SECOND)));
		parameters.add(parameter);
		
		parameter = new NameValuePair("logid","0");
		parameters.add(parameter);
		
		parameter = new NameValuePair("oldisdraft","0");
		parameters.add(parameter);
		
		HeaderGroup hg = new HeaderGroup();
		hg.addHeader(new Header("Content-Type",
				"application/x-www-form-urlencoded; charset=GB2312"));
		hg.addHeader(new Header("Referer", "http://www.blog.com.cn/user_post.asp"));
		httpDocument.post(action, parameters, hg);
		
	}

	/* (non-Javadoc)
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#writeComment(com.redv.blogmover.Comment)
	 */
	@Override
	protected void writeComment(Comment comment) throws BlogMoverException {
		// TODO Auto-generated method stub

	}

}
