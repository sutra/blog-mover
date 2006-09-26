/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		BlogcnLogin.java
 * Module:			blog-mover
 * Class:			BlogcnLogin
 * Date:			2006-9-26 下午06:38:05
 * Author:			<a href="mailto:rory.cn@gmail.com">somebody</a>
 * Description:		
 *
 *
 * ======================================================================
 * Change History Log
 * ----------------------------------------------------------------------
 * Mod. No.	| Date		| Name			| Reason			| Change Req.
 * ----------------------------------------------------------------------
 * 			| 2006-9-26   | Rory Ye	    | Created			|
 *
 * </pre>
 **/
package com.redv.blogmover.bsps.blogcn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author <a href="mailto:rory.cn@gmail.com">somebody</a>
 * @since 2006-9-26 下午06:38:05
 * @version $Id BlogcnLogin.java$
 */
public class BlogcnLogin {
	private static final Log log = LogFactory.getLog(BlogcnLogin.class);
	
	private HttpDocument httpDocument;
	
	public BlogcnLogin(HttpDocument httpDocument){
		this.httpDocument = httpDocument;
	}
	
	public void login(String username, String password) throws BlogMoverException{
		String action = "http://www.blog.com.cn/login.asp?fromurl=";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		NameValuePair param = new NameValuePair("username",username);
		params.add(param);
		
		param = new NameValuePair("password", password);
		params.add(param);
		
		param = new NameValuePair("CookieDate","0");
		params.add(param);
		Document document = httpDocument.post(action, params);
		boolean ok = false;
		if (!ok) {
			throw new BlogMoverException("用户名密码或者验证码有误，请重新输入");
		}
	}
	
}
