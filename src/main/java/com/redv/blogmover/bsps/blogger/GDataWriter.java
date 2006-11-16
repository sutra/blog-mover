/**
 * Created on 2006-11-11 下午12:22:08
 */
package com.redv.blogmover.bsps.blogger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TimeZone;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Entry;
import com.google.gdata.data.HtmlTextConstruct;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogWriter;

/**
 * @author Shutra
 * 
 */
public class GDataWriter extends AbstractBlogWriter {
	private URL postUrl;

	private String blogid;

	private String username;

	private String password;

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param postUrl
	 *            the postUrl to set
	 */
	public void setPostUrl(URL postUrl) {
		this.postUrl = postUrl;
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
		try {
			postUrl = new URL(String.format(
					"http://www.blogger.com/feeds/%1$s/posts/default", blogid));
		} catch (MalformedURLException e) {
			throw new BlogMoverException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.impl.AbstractBlogWriter#end()
	 */
	@Override
	protected void end() throws BlogMoverException {

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
		Entry entry = new Entry();
		entry.setTitle(new PlainTextConstruct(webLog.getTitle()));
		entry.setSummary(new HtmlTextConstruct(webLog.getExcerpt()));
		entry.setContent(new HtmlTextConstruct(webLog.getBody()));
		entry.setPublished(new DateTime(webLog.getPublishedDate(), TimeZone
				.getDefault()));

		GoogleService gs = new GoogleService("blogger", "blog-mover");
		try {
			gs.setUserCredentials(username, password);
			Entry insertedEntry = gs.insert(postUrl, entry);
			log.debug(insertedEntry.getId());
		} catch (AuthenticationException e) {
			throw new BlogMoverException(e);
		} catch (IOException e) {
			throw new BlogMoverException(e);
		} catch (ServiceException e) {
			throw new BlogMoverException(e);
		}
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
