/**
 *  Created on 2006-6-26 1:46:29
 */
package com.redv.blogremover.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogremover.Status;
import com.redv.blogremover.WebLog;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class StatusImpl implements Status {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2200075780168734557L;

	private static final Log log = LogFactory.getLog(StatusImpl.class);

	private WebLog currentWebLog;

	private int currentCount;

	private int totalCount;

	/**
	 * 
	 */
	public StatusImpl() {
		super();
		this.currentWebLog = new WebLogImpl();
		this.currentCount = 0;
		this.totalCount = -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Status#getCurrentWebLog()
	 */
	public WebLog getCurrentWebLog() {
		return this.currentWebLog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Status#setCurrentWebLog(com.redv.blogremover.WebLog)
	 */
	public void setCurrentWebLog(WebLog webLog) {
		log
				.debug("setCurrentWebLog(com.redv.blogremover.WebLog) called. webLog="
						+ ((webLog == null) ? "null" : webLog.getTitle()));
		this.currentWebLog = webLog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Status#getCurrentCount()
	 */
	public int getCurrentCount() {
		return this.currentCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Status#setCurrentCount(int)
	 */
	public void setCurrentCount(int currentCount) {

		this.currentCount = currentCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Status#getTotalCount()
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Status#setTotalCount(int)
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
