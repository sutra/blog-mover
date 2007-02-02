/**
 * Created on 2007-2-2 下午11:58:22
 */
package com.redv.blogmover.bsps.cn.com.blog;

import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader;

/**
 * @author shutra
 * 
 */
public class BlogReader extends AbstractOBlogReader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#buildListUrl(int)
	 */
	@Override
	protected String buildListUrl(int currentPageNumber) {
		return "http://www.blog.com.cn/user_blogmanage.asp?t=0&page="
				+ currentPageNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#buildModifyUrl(java.lang.String)
	 */
	@Override
	protected String buildModifyUrl(String webLogId) {
		return "http://www.blog.com.cn/user_post.asp?logid=" + webLogId
				+ "&t=0";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#buildPermalink(java.lang.String)
	 */
	@Override
	protected String buildPermalink(String urlParsedFromListPage) {
		return urlParsedFromListPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#login()
	 */
	@Override
	protected void login() throws LoginFailedException {
		new BlogLogin(httpDocument).login(username, password);
	}
}
