package com.redv.blogsynchronizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogReader;
import com.redv.blogmover.RecentWebLogsCache;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.BlogFilterByPubDate;
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

	private transient final ReadWriteLock syncDestLock = new ReentrantReadWriteLock();

	private transient BlogReader reader;

	private transient BlogSyncDest syncDest;

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
	public BlogSyncDest getSyncDest() {
		this.syncDestLock.readLock().lock();
		try {
			return syncDest;
		} finally {
			this.syncDestLock.readLock().unlock();
		}
	}

	/**
	 * @param reader
	 *            The reader to set.
	 */
	public void setSyncSrc(BlogReader reader) {
		this.readerLock.writeLock().lock();
		try {
			this.reader = reader;
		} finally {
			this.readerLock.writeLock().unlock();
		}
	}

	/**
	 * @param syncDest
	 *            The writer to set.
	 */
	public void setSyncDest(BlogSyncDest syncDest) {
		this.syncDestLock.writeLock().lock();
		try {
			this.syncDest = syncDest;
		} finally {
			this.syncDestLock.writeLock().unlock();
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
	public void setSyncSrcProperty(String property, String value)
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
	public void setSyncDestProperty(String property, String value)
			throws BlogMoverException {
		log.debug("setReaderProperty called. property=" + property + ", value="
				+ value);
		this.syncDestLock.writeLock().lock();
		try {
			if (this.syncDest == null) {
				throw new BlogMoverException("写入器为空。请设置写入器。");
			}
			new PropertySetter(this.syncDest.getReader()).setProperty(property, value);
	    new PropertySetter(this.syncDest.getWriter()).setProperty(property, value);
		} finally {
			this.syncDestLock.writeLock().unlock();
		}
	}

	public void read() throws BlogMoverException {
		if (reader == null) {
			throw new BlogMoverException("尚未设置源日志。");
		}
    if (syncDest == null) {
      throw new BlogMoverException("尚未设置目标日志。");
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
		
		if ((this.webLogs == null) || this.webLogs.isEmpty())
		  return;
		
		// get the pubdate for the last entry, which can be used to filter syncdest's reader
		Date fromPubDate = this.webLogs.get(this.webLogs.size() - 1).getPublishedDate();
		// using an one-hour threshold to avoid the inaccuracy in time computation
		fromPubDate.setTime(fromPubDate.getTime() - 60 * 60 * 1000);

		this.syncDestLock.readLock().lock();
    try {
      syncDest.getReader().setBlogFilter(new BlogFilterByPubDate(fromPubDate));
      
      List<WebLog> webLogs = syncDest.getReader().read();
      if (webLogs != null) {
        // Set the WebLog's BSP.
        for (WebLog webLog : webLogs) {
          webLog.setBsp(syncDest.getReader().getBsp());
          
          excludeIfExists(webLog);
        }
      }
    } finally {
      this.syncDestLock.readLock().unlock();
    }
	}

	private boolean bCompareTitleOnly = true;
	private void excludeIfExists(WebLog webLog) {
	  Iterator<WebLog> it = this.webLogs.iterator();
	  while (it.hasNext()) {
	    WebLog wl = it.next();
	    if (isSameBlogEntry(webLog, wl, bCompareTitleOnly)) {
	      it.remove();
	      break;
	    }
	  }
  }

  private boolean isSameBlogEntry(WebLog wl1, WebLog wl2, boolean bCompareTitleOnly) {
    if (!bCompareTitleOnly)
      return wl1.equals(wl2);
    else {
      if (wl1.getTitle() == null)
        return (wl2.getTitle() == null);
      else 
        return wl1.getTitle().equals(wl2.getTitle());
    }
  }

  public void write() throws BlogMoverException {
		if (syncDest == null) {
			throw new BlogMoverException("写入器尚未设置。");
		}
		this.syncDestLock.writeLock().lock();
		try {
			List<WebLog> unmodifiableList = Collections
					.unmodifiableList(this.webLogs);
			List<WebLog> webLogsCopy = new ArrayList<WebLog>(unmodifiableList
					.size());
			webLogsCopy.addAll(unmodifiableList);

			syncDest.getWriter().write(webLogsCopy);

			history.put(token, webLogsCopy);

			this.loggingService.log(webLogsCopy, this.syncDest.getWriter().getBsp());
		} finally {
			this.syncDestLock.writeLock().unlock();
		}
	}
}