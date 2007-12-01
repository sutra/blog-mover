package com.redv.blogmover.bsps.blogger;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Query;
import com.google.gdata.data.Entry;
import com.google.gdata.data.Feed;
import com.google.gdata.data.HtmlTextConstruct;
import com.google.gdata.data.TextContent;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.redv.blogmover.BlogFilter;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.AbstractBlogReader;
import com.redv.blogmover.impl.BlogFilterByCount;
import com.redv.blogmover.impl.WebLogImpl;

public class GDataReader extends AbstractBlogReader {
	private String blogid;

	private String username;

	private String password;

	public static final int MAX_READ_RESULTS = 1000;

	/**
	 * @param blogid
	 *            the blogid to set
	 */
	public void setBlogid(String blogid) {
		this.blogid = blogid;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public List<WebLog> read() throws BlogMoverException {

		GoogleService gs = new GoogleService("blogger", "blog-mover");
		try {
			gs.setUserCredentials(username, password);

			URL feedUrl = new URL("http://www.blogger.com/feeds/" + blogid
					+ "/posts/default");
			Query myQuery = new Query(feedUrl);
			myQuery.setMaxResults(getMaxReadResults());
			Feed resultFeed = gs.query(myQuery, Feed.class);

			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				Entry entry = resultFeed.getEntries().get(i);

				if (!parse(entry))
					break;
			}
		} catch (AuthenticationException e) {
			throw new BlogMoverException(e);
		} catch (IOException e) {
			throw new BlogMoverException(e);
		} catch (ServiceException e) {
			throw new BlogMoverException(e);
		}
		return getWebLogs();
	}

	private int getMaxReadResults() {
		BlogFilter filter = getBlogFilter();
		if (filter instanceof BlogFilterByCount) {
			return ((BlogFilterByCount) filter).getLimit();
		}
		return MAX_READ_RESULTS;
	}

	private boolean parse(Entry entry) {
		// create a webLog object from the entry
		WebLog webLog = new WebLogImpl();

		webLog.setTitle(entry.getTitle().getPlainText());
		TextContent tc = (TextContent) entry.getContent();
		HtmlTextConstruct htc = (HtmlTextConstruct) tc.getContent();
		webLog.setBody(htc.getHtml());
		webLog.setPublishedDate(new Date(entry.getPublished().getValue()));

		return processNewBlog(webLog);
	}
}