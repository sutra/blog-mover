/**
 * Created on 2007-1-27 上午03:46:59
 */
package com.redv.blogmover.bsps.com.blogchinese;

import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader;

/**
 * @author shutra
 * 
 */
public class BlogChineseReader extends AbstractOBlogReader {
	private static final String LIST_URL_FORMAT = "http://www.blogchinese.com/user_blogmanage.asp?t=0&page=%1$s";

	public BlogChineseReader() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#login()
	 */
	@Override
	protected void login() throws LoginFailedException {
		new BlogChineseLogin(httpDocument).login(username, password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#buildListUrl(int)
	 */
	@Override
	protected String buildListUrl(int currentPageNumber) {
		return String.format(LIST_URL_FORMAT, currentPageNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#buildModifyUrl(java.lang.String)
	 */
	@Override
	protected String buildModifyUrl(String webLogId) {
		return String.format(
				"http://www.blogchinese.com/user_post.asp?logid=%1$s&t=0",
				webLogId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.blogengines.cn.oblog.AbstractOBlogReader#buildPermalink(java.lang.String)
	 */
	@Override
	protected String buildPermalink(String urlParsedFromListPage) {
		return "http://www.blogchinese.com/" + urlParsedFromListPage;
	}

}
