/**
 * Created on 2006-6-30 13:41:29
 */
package com.redv.blogmover.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogFilter;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogReader;
import com.redv.blogmover.ReadFilterResult;
import com.redv.blogmover.Status;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.logging.BSP;

/**
 * @author Joe
 * @version 1.0
 * @version 2.0
 */
public abstract class AbstractBlogReader implements BlogReader {
	protected Log log = LogFactory.getLog(this.getClass());

	protected BSP bsp;

	protected Status status;

	protected long operationInterval;

	protected BlogFilter m_filter = BlogFilter.NONE;

	private List<WebLog> m_webLogs = new ArrayList<WebLog>();

	/**
	 * 
	 */
	public AbstractBlogReader() {
		this.bsp = new BSP();
		this.bsp.setId(this.getClass().getPackage().getName());

		this.status = new StatusImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.BlogReader#getBsp()
	 */
	public BSP getBsp() {
		return bsp;
	}

	/**
	 * @param operationInterval
	 *            the operationInterval to set
	 */
	public void setOperationInterval(long operationInterval) {
		this.operationInterval = operationInterval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogReader#read()
	 */
	public abstract List<WebLog> read() throws BlogMoverException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogReader#getStatus()
	 */
	public Status getStatus() {
		return status;
	}

	public void setBlogFilterId(String filterIdAndArg) {
		String[] args = filterIdAndArg.split(":");
		if ("BlogFilterNone".equals(args[0])) {
			setBlogFilter(BlogFilter.NONE);
		} else if ("BlogFilterByCount".equals(args[0])) {
			setBlogFilter(new BlogFilterByCount(Integer.parseInt(args[1])));
		} else if ("BlogFilterByPubDate".equals(args[0])) {
			Date now = new Date();
			final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
			Date fromPubDate = new Date(now.getTime()
					- Integer.parseInt(args[1]) * MILLSECS_PER_DAY);
			setBlogFilter(new BlogFilterByPubDate(fromPubDate));
		}
	}

	public void setBlogFilter(BlogFilter filter) {
		m_filter = filter;
	}

	protected BlogFilter getBlogFilter() {
		return m_filter;
	}

	/**
	 * @param webLog
	 * @return true, if more to read; false, otherwise
	 */
	protected boolean processNewBlog(WebLog webLog) {
		ReadFilterResult res = getBlogFilter().run(webLog);

		if (res.accepted()) {
			m_webLogs.add(webLog);
			status.setCurrentWebLog(webLog);
			status.setCurrentCount(m_webLogs.size());
			log.debug("status: " + status.toString());
		}

		return res.readMore();
	}

	protected List<WebLog> getWebLogs() {
		return m_webLogs;
	}
}
