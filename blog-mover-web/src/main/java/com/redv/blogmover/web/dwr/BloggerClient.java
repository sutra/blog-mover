/**
 * Created on 2006-9-29 下午09:02:33
 */
package com.redv.blogmover.web.dwr;

import java.io.Serializable;
import java.net.MalformedURLException;

import com.redv.bloggerapi.client.Blog;
import com.redv.bloggerapi.client.BloggerImpl;
import com.redv.bloggerapi.client.Fault;

/**
 * @author Shutra
 * 
 */
public class BloggerClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -322308697597217098L;

	public Blog[] getUsersBlogs(String serverURL, String username,
			String password) throws MalformedURLException, Fault {
		return new BloggerImpl(serverURL).getUsersBlogs("dummy", username,
				password);
	}

}
