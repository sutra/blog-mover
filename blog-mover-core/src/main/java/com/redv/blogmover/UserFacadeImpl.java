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
public class UserFacadeImpl implements Serializable, UserFacade {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4355547205744489604L;

	private static final transient Log log = LogFactory
			.getLog(UserFacadeImpl.class);

	private transient RecentWebLogsCache history = new RecentWebLogsCache();

	private transient String token;

	private transient final ReadWriteLock readerLock = new ReentrantReadWriteLock();

	private transient final ReadWriteLock writerLock = new ReentrantReadWriteLock();

	private transient BlogReader reader;

	private transient BlogWriter writer;

	private transient List<WebLog> webLogs;

	private transient LoggingService loggingService;

	public UserFacadeImpl() {
		webLogs = new Vector<WebLog>();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#setToken(java.lang.String)
	 */
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

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#getReader()
	 */
	public BlogReader getReader() {
		this.readerLock.readLock().lock();
		try {
			return reader;
		} finally {
			this.readerLock.readLock().unlock();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#getWriter()
	 */
	public BlogWriter getWriter() {
		this.writerLock.readLock().lock();
		try {
			return writer;
		} finally {
			this.writerLock.readLock().unlock();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#setReader(com.redv.blogmover.BlogReader)
	 */
	public void setReader(BlogReader reader) {
		this.readerLock.writeLock().lock();
		try {
			this.reader = reader;
		} finally {
			this.readerLock.writeLock().unlock();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#setWriter(com.redv.blogmover.BlogWriter)
	 */
	public void setWriter(BlogWriter writer) {
		this.writerLock.writeLock().lock();
		try {
			this.writer = writer;
		} finally {
			this.writerLock.writeLock().unlock();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#getWebLogs()
	 */
	public List<WebLog> getWebLogs() {
		return webLogs;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#setReaderProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public void setReaderProperty(String property, String value)
			throws ReaderNotSettedException {
		log.debug("setReaderProperty called. property=" + property + ", value="
				+ value);
		this.readerLock.writeLock().lock();
		try {
			if (this.reader == null) {
				throw new ReaderNotSettedException("读取器为空。请设置读取器。");
			}
			new PropertySetter(this.reader).setProperty(property, value);
		} finally {
			this.readerLock.writeLock().unlock();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#setWriterProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public void setWriterProperty(String property, String value)
			throws WriterNotSettedException {
		log.debug("setReaderProperty called. property=" + property + ", value="
				+ value);
		this.writerLock.writeLock().lock();
		try {
			if (this.writer == null) {
				throw new WriterNotSettedException("写入器为空。请设置写入器。");
			}
			new PropertySetter(this.writer).setProperty(property, value);
		} finally {
			this.writerLock.writeLock().unlock();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#read()
	 */
	public void read() throws BlogMoverException {
		if (reader == null) {
			throw new BlogMoverException("尚未设置读取器。");
		}
		log.debug("read() called." + reader.getClass() + " will be called.");
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

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.UserFacade#write()
	 */
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
