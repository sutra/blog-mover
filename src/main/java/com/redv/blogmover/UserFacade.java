/**
 *  Created on 2006-6-14 21:03:09
 */
package com.redv.blogmover;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.logging.LoggingService;
import com.redv.blogmover.util.PropertySetter;

/**
 * Blog Remover User Facade.
 * <p>
 * 线程安全的。
 * </p>
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public class UserFacade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4355547205744489604L;

	private static final transient Log log = LogFactory
			.getLog(UserFacade.class);

	private transient RecentWebLogsCache history = new RecentWebLogsCache();

	private transient String token;

	private transient final ReadWriteLock readerLock = new ReentrantReadWriteLock();

	private transient final ReadWriteLock writerLock = new ReentrantReadWriteLock();

	private transient BlogReader reader;

	private transient BlogWriter writer;

	private transient List<WebLog> webLogs;

	private transient LoggingService loggingService;

	public UserFacade() {
		webLogs = new Vector<WebLog>();
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param loggingService
	 *            the loggingService to set
	 */
	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	/**
	 * @return Returns the reader.
	 */
	public BlogReader getReader() {
		this.readerLock.readLock().lock();
		try {
			return reader;
		} finally {
			this.readerLock.readLock().unlock();
		}
	}

	/**
	 * @return Returns the writer.
	 */
	public BlogWriter getWriter() {
		this.writerLock.readLock().lock();
		try {
			return writer;
		} finally {
			this.writerLock.readLock().unlock();
		}
	}

	/**
	 * @param reader
	 *            The reader to set.
	 */
	public void setReader(BlogReader reader) {
		this.readerLock.writeLock().lock();
		try {
			this.reader = reader;
		} finally {
			this.readerLock.writeLock().unlock();
		}
	}

	/**
	 * @param writer
	 *            The writer to set.
	 */
	public void setWriter(BlogWriter writer) {
		this.writerLock.writeLock().lock();
		try {
			this.writer = writer;
		} finally {
			this.writerLock.writeLock().unlock();
		}
	}

	/**
	 * @return Returns the webLogs.
	 */
	public List<WebLog> getWebLogs() {
		return webLogs;
	}

	/**
	 * 
	 * @param property
	 *            The property to set.
	 * @param value
	 *            The value to set.
	 * @throws BlogMoverException
	 */
	public void setReaderProperty(String property, String value)
			throws BlogMoverException {
		log.debug("setReaderProperty called. property=" + property + ", value="
				+ value);
		this.readerLock.writeLock().lock();
		try {
			if (this.reader == null) {
				throw new BlogMoverException("读取器为空。请设置读取器。");
			}
			new PropertySetter(this.reader).setProperty(property, value);
		} finally {
			this.readerLock.writeLock().unlock();
		}
	}

	/**
	 * 
	 * @param property
	 *            The property to set.
	 * @param value
	 *            The value to set.
	 * @throws BlogMoverException
	 */
	public void setWriterProperty(String property, String value)
			throws BlogMoverException {
		log.debug("setReaderProperty called. property=" + property + ", value="
				+ value);
		this.writerLock.writeLock().lock();
		try {
			if (this.writer == null) {
				throw new BlogMoverException("写入器为空。请设置写入器。");
			}
			new PropertySetter(this.writer).setProperty(property, value);
		} finally {
			this.writerLock.writeLock().unlock();
		}
	}

	public void read() throws BlogMoverException {
		if (reader == null) {
			throw new BlogMoverException("尚未设置读取器。");
		}
		this.readerLock.readLock().lock();
		try {
			List<WebLog> webLogs = reader.read();
			if (webLogs != null) {
				// Set the WebLog's BSP.
				for (WebLog webLog : webLogs) {
					webLog.setBsp(reader.getBsp());
				}

				this.webLogs.addAll(webLogs);
			}
		} finally {
			this.readerLock.readLock().unlock();
		}
	}

	public void write() throws BlogMoverException {
		if (writer == null) {
			throw new BlogMoverException("写入器尚未设置。");
		}
		this.writerLock.readLock().lock();
		try {
			List<WebLog> unmodifiableList = Collections
					.unmodifiableList(this.webLogs);
			List<WebLog> webLogsCopy = new ArrayList<WebLog>(unmodifiableList
					.size());
			webLogsCopy.addAll(unmodifiableList);

			writer.write(webLogsCopy);

			history.put(token, webLogsCopy);

			this.loggingService.log(webLogsCopy, this.writer.getBsp());
		} finally {
			this.writerLock.readLock().unlock();
		}
	}
}
