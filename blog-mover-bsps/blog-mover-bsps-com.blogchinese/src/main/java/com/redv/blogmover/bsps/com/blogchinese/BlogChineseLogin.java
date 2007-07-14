/**
 * Created on 2007-1-27 下午12:26:56
 */
package com.redv.blogmover.bsps.com.blogchinese;

import org.w3c.dom.Document;

import com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogLogin;
import com.redv.blogmover.util.HttpDocument;

/**
 * To login to http://www.blogchinse.com with http client.
 * <p>
 * Test account: <br />
 * username: blogmover, <br />
 * password: blogmover
 * </p>
 * 
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
class BlogChineseLogin extends AbstractOBlogLogin {
	private static final String loginActionUrl = "http://www.blogchinese.com/login.asp";

	public BlogChineseLogin(HttpDocument httpDocument) {
		super(httpDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.OBlogLogin#buildLoginActionUrl()
	 */
	@Override
	protected String buildLoginActionUrl() {
		return loginActionUrl;
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
