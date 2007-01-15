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
import com.redv.blogmover.bsps.BSPIDUtils;
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

	/**
	 * 
	 */
	public AbstractBlogReader() {
		this.bsp = new BSP();
		this.bsp.setId(BSPIDUtils.getId(this.getClass()));

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
