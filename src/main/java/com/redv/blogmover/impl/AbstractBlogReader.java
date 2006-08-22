/**
 * Created on 2006-6-30 13:41:29
 */
package com.redv.blogmover.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogReader;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Status;
import com.redv.blogmover.WebLog;

/**
 * @author Joe
 * @version 1.0
 */
public abstract class AbstractBlogReader implements BlogReader {
	protected Log log = LogFactory.getLog(this.getClass());

	protected Status status;

	/**
	 * 
	 */
	public AbstractBlogReader() {
		super();
		this.status = new StatusImpl();
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

}
