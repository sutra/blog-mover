/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		BlogcnLoginTest.java
 * Module:			blog-mover
 * Class:			BlogcnLoginTest
 * Date:			2006-9-26 下午07:00:57
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

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;

import com.redv.blogmover.bsps.cn.com.blog.BlogcnLogin;
import com.redv.blogmover.util.HttpDocument;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:rory.cn@gmail.com">somebody</a>
 * @since 2006-9-26 下午07:00:57
 * @version $Id BlogcnLoginTest.java$
 */
public class BlogcnLoginTest extends TestCase {
	private HttpClient httpClient;
	
	private HttpDocument httpDocument;
	
	private BlogcnLogin blogcnLogin;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		httpDocument = new HttpDocument(httpClient, true, "GBK");
		blogcnLogin = new BlogcnLogin(httpDocument);
	}
	
	public void testLogin() throws Exception{
		blogcnLogin.login("blog-mover", "jdkcn.com");
	}
}
