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
 * @version 3.0
 */
public abstract class AbstractBlogReader implements BlogReader {
	protected Log log = LogFactory.getLog(this.getClass());

	protected BSP bsp;

	protected Status status;

	/**
	 * 固定操作间隔。
	 */
	protected long operationInterval;

	/**
	 * HTTP 请求随机间隔—最小值。
	 */
	protected long minimumInterval;

	/**
	 * HTTP 请求随机间隔—最大值。
	 */
	protected long maximumInterval;

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

	/**
	 * {@inheritDoc}
	 */
	public BSP getBsp() {
		return bsp;
	}

	/**
	 * @param operationInterval
	 *            the operationInterval to set
	 */
	public void setOperationInterval(final long operationInterval) {
		this.operationInterval = operationInterval;
	}

	/**
	 * @param minimumInterval the minimumInterval to set
	 */
	public void setMinimumInterval(final long minimumInterval) {
		this.minimumInterval = minimumInterval;
	}

	/**
	 * @param maximumInterval the maximumInterval to set
	 */
	public void setMaximumInterval(final long maximumInterval) {
		this.maximumInterval = maximumInterval;
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract List<WebLog> read() throws BlogMoverException;

	/**
	 * {@inheritDoc}
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * {@inheritDoc}
	 */
	public void check() throws BlogMoverException {

	}

	/**
	 * @param filterIdAndArg
	 */
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

	/**
	 * {@inheritDoc}
	 */
	public void setBlogFilter(BlogFilter filter) {
		m_filter = filter;
	}

	/**
	 * @return
	 */
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

	/**
	 * @return
	 */
	protected List<WebLog> getWebLogs() {
		return m_webLogs;
	}
}
