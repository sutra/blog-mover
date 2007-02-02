/**
 * Created on 2007-2-3 上午12:02:43
 */
package com.redv.blogmover.bsps.cn.com.blog;

import org.w3c.dom.Document;

import com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogLogin;
import com.redv.blogmover.util.HttpDocument;

/**
 * @author shutra
 * 
 */
class BlogLogin extends AbstractOBlogLogin {

	public BlogLogin(HttpDocument httpDocument) {
		super(httpDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.OBlogLogin#buildLoginActionUrl()
	 */
	@Override
	protected String buildLoginActionUrl() {
		return "http://www.blog.com.cn/login.asp?action=showindexlogin&chk=1";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.OBlogLogin#checkLoginSuccess(org.w3c.dom.Document)
	 */
	@Override
	protected boolean checkLoginSuccess(Document document) {
		return new LoginResponseParser().checkLoginSuccess(document);
	}

}
