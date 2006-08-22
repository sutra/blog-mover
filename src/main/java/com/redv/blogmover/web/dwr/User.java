/**
 *  Created on 2006-6-24 11:58:40
 */
package com.redv.blogmover.web.dwr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContextFactory;
import org.springframework.web.util.WebUtils;

import com.redv.blogmover.BlogReader;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogWriter;
import com.redv.blogmover.Status;
import com.redv.blogmover.UserFacade;
import com.redv.blogmover.WebLog;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3343291565699533910L;

	private static final Log log = LogFactory.getLog(User.class);

	public static final String SESSION_NAME_USER_FACADE = "com.redv.blogremover.web.dwr.User.userFacade";

	/**
	 * 
	 */
	public User() {
		super();
		log.debug("User constructor called.");
	}

	/**
	 * 获取用户门面。
	 * 
	 * @return
	 */
	private UserFacade getUserFacade() {
		HttpSession session = WebContextFactory.get().getSession(true);
		return (UserFacade) WebUtils.getOrCreateSessionAttribute(session,
				SESSION_NAME_USER_FACADE, UserFacade.class);
	}

	/**
	 * 获取日志。
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return Object[] = {totalCount, fromIndex, List<WebLog>}
	 */
	public Object[] getWebLogs(int firstResult, int maxResults) {
		Object[] ret = new Object[3];
		List<WebLog> webLogs = this.getUserFacade().getWebLogs();
		if (webLogs == null) {
			ret[0] = 0; // totalCount
			ret[1] = 0; // fromIndex
			ret[2] = new ArrayList<WebLog>(0);
		} else {
			int fromIndex = firstResult;
			if (fromIndex < 0 || fromIndex >= webLogs.size()) {
				fromIndex = 0;
			}
			int toIndex = fromIndex + maxResults;
			if (toIndex > webLogs.size()) {
				toIndex = webLogs.size();
			}
			List<WebLog> list = webLogs.subList(fromIndex, toIndex);
			ret[0] = webLogs.size(); // totalCount
			ret[1] = fromIndex; // fromIndex
			ret[2] = list;
		}
		return ret;
	}

	/**
	 * 设置读取器。
	 * 
	 * @param className
	 */
	public void setReader(String className) {
		log.debug("setReader called. className=" + className);
		try {
			this.getUserFacade().setReader(newReader(className));
		} catch (InstantiationException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (ClassNotFoundException e) {
			log.error(e);
		}
	}

	/**
	 * 新建一个读取器实例。
	 * 
	 * @param className
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private BlogReader newReader(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		BlogReader reader = (BlogReader) Class.forName(className).newInstance();
		return reader;
	}

	/**
	 * 设置写入器。
	 * 
	 * @param className
	 */
	public void setWriter(String className) {
		log.debug("setWriter called. className=" + className);
		try {
			this.getUserFacade().setWriter(newWriter(className));
		} catch (InstantiationException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (ClassNotFoundException e) {
			log.error(e);
		}
	}

	/**
	 * 新建一个写入器实例。
	 * 
	 * @param className
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private BlogWriter newWriter(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		BlogWriter writer = (BlogWriter) Class.forName(className).newInstance();
		return writer;
	}

	/**
	 * 设置读取器属性。
	 * 
	 * @param property
	 * @param value
	 * @throws BlogMoverException
	 */
	public void setReaderProperty(String property, String value)
			throws BlogMoverException {
		UserFacade facade = this.getUserFacade();
		try {
			facade.setReaderProperty(property, value);
		} catch (BlogMoverException e) {
			log.error(e);
			throw e;
		}
	}

	public void setReaderProperties(String[] properties, String[] values)
			throws BlogMoverException {
		UserFacade facade = this.getUserFacade();
		for (int i = 0; i < properties.length; i++) {
			log.debug("Setting reader property: " + properties[i] + ", value: "
					+ values[i]);
			facade.setReaderProperty(properties[i], values[i]);
		}
	}

	/**
	 * 设置写入器属性。
	 * 
	 * @param property
	 * @param value
	 * @throws BlogMoverException
	 */
	public void setWriterProperty(String property, String value)
			throws BlogMoverException {
		UserFacade facade = this.getUserFacade();
		try {
			facade.setWriterProperty(property, value);
		} catch (BlogMoverException e) {
			log.error(e);
			throw e;
		}
	}

	public void setWriterProperties(String[] properties, String[] values)
			throws BlogMoverException {
		UserFacade facade = this.getUserFacade();
		for (int i = 0; i < properties.length; i++) {
			log.debug("Setting writer property: " + properties[i] + ", value: "
					+ values[i]);
			facade.setWriterProperty(properties[i], values[i]);
		}
	}

	/**
	 * 读取。
	 * 
	 * @throws BlogMoverException
	 */
	public void read() throws BlogMoverException {
		log.debug("read() called.");
		try {
			this.getUserFacade().read();
		} catch (BlogMoverException e) {
			log.error(e);
			throw e;
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 写入。
	 * 
	 * @throws BlogMoverException
	 */
	@SuppressWarnings("unchecked")
	public void write() throws BlogMoverException {
		log.debug("write() called.");
		try {
			this.getUserFacade().write();
		} catch (BlogMoverException e) {
			log.error(e);
			throw e;
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 获取读取器状态。
	 * 
	 * @return
	 */
	public Status getReaderStatus() {
		return this.getUserFacade().getReader().getStatus();
	}

	/**
	 * 获取写入器状态。
	 * 
	 * @return
	 */
	public Status getWriterStatus() {
		return this.getUserFacade().getWriter().getStatus();
	}

	/**
	 * 删除指定索引处的日志。
	 * 
	 * @param indices
	 */
	public void deleteWebLogs(int[] indices) {
		List<WebLog> webLogs = this.getUserFacade().getWebLogs();
		List<WebLog> toDelete = new ArrayList<WebLog>(indices.length);
		for (int index : indices) {
			toDelete.add(webLogs.get(index));
		}
		webLogs.removeAll(toDelete);
	}

	/**
	 * 清空日志。
	 * 
	 */
	public void clearWebLogs() {
		List<WebLog> webLogs = this.getUserFacade().getWebLogs();
		webLogs.clear();
	}

}
