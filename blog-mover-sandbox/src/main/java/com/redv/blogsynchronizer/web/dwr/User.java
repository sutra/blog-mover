package com.redv.blogsynchronizer.web.dwr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogReader;
import com.redv.blogmover.BlogWriter;
import com.redv.blogmover.Status;
import com.redv.blogsynchronizer.BlogSyncDest;
import com.redv.blogsynchronizer.UserFacade;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.logging.LoggingService;

/**
 * DWR 用户接口。
 * 
 * @author Joe
 * @version 1.0
 * @version 2.0
 * 
 */
public class User implements Serializable {
	/**
   * 
   */
  private static final long serialVersionUID = -7395364219564026322L;

  private static final Log log = LogFactory.getLog(User.class);

	public static final String SESSION_NAME_USER_FACADE = UserDelegate.SESSION_NAME_USER_FACADE;

	private UserDelegate userDelegate;

	public User() {
		this.userDelegate = new UserDelegate();
	}

	public void setSyncSrc(String className) {
		try {
			this.userDelegate.setSyncSrc(className);
		} catch (InstantiationException e) {
			log.error("An error occured in setReader.", e);
		} catch (IllegalAccessException e) {
			log.error("An error occured in setReader.", e);
		} catch (ClassNotFoundException e) {
			log.error("An error occured in setReader.", e);
		} catch (RuntimeException e) {
			log.error("An error occured in setReader.", e);
		}
	}

	public void setSyncDest(String className) {
		try {
			this.userDelegate.setSyncDest(className);
		} catch (InstantiationException e) {
			log.error("An error occured in setWriter.", e);
		} catch (IllegalAccessException e) {
			log.error("An error occured in setWriter.", e);
		} catch (ClassNotFoundException e) {
			log.error("An error occured in setWriter.", e);
		} catch (RuntimeException e) {
			log.error("An error occured in setWriter.", e);
		}
	}

	public Object[] getWebLogs(int firstResult, int maxResults) {
		try {
			return this.userDelegate.getWebLogs(firstResult, maxResults);
		} catch (RuntimeException e) {
			log.error("An error occured in getWebLogs.", e);
			return null;
		}
	}

	/**
	 * @deprecated
	 * @param property
	 * @param value
	 * @throws BlogMoverException
	 */
	public void setSyncSrcProperty(String property, String value)
			throws BlogMoverException {
		try {
			this.userDelegate.setSyncSrcProperty(property, value);
		} catch (RuntimeException e) {
			log.error("An error occured in setReaderProperty.", e);
		}
	}

	public void setSyncSrcProperties(String[] properties, String[] values)
			throws BlogMoverException {
		try {
			this.userDelegate.setSyncSrcProperties(properties, values);
		} catch (RuntimeException e) {
			log.error("An error occured in setReaderProperties.", e);
		}
	}

	/**
	 * @deprecated
	 * @param property
	 * @param value
	 * @throws BlogMoverException
	 */
	public void setSyncDestProperty(String property, String value)
			throws BlogMoverException {
		try {
			this.userDelegate.setSyncDestProperty(property, value);
		} catch (RuntimeException e) {
			log.error("An error occured in setWriterProperty.", e);
		}
	}

	public void setSyncDestProperties(String[] properties, String[] values)
			throws BlogMoverException {
		try {
			this.userDelegate.setSyncDestProperties(properties, values);
		} catch (RuntimeException e) {
			log.error("An error occured in setWriterProperties.", e);
		}
	}

	public Status getSyncSrcReaderStatus() {
		try {
			return this.userDelegate.getSyncSrcReaderStatus();
		} catch (RuntimeException e) {
			log.error("An error occured in getReaderStatus.", e);
			return null;
		}
	}

	public Status getSyncDestReaderStatus() {
    try {
      return this.userDelegate.getSyncDestReaderStatus();
    } catch (RuntimeException e) {
      log.error("An error occured in getWriterStatus.", e);
      return null;
    }
	}
	
	public Status getSyncDestWriterStatus() {
		try {
			return this.userDelegate.getSyncDestWriterStatus();
		} catch (RuntimeException e) {
			log.error("An error occured in getWriterStatus.", e);
			return null;
		}
	}

	public void deleteWebLogs(int[] indices) {
		try {
			this.userDelegate.deleteWebLogs(indices);
		} catch (RuntimeException e) {
			log.error("An error occured in deleteWebLogs.", e);
		}
	}

	public void clearWebLogs() {
		try {
			this.userDelegate.clearWebLogs();
		} catch (RuntimeException e) {
			log.error("An error occured in clearWebLogs.", e);
		}
	}

	public void write() throws BlogMoverException {
		try {
			this.userDelegate.write();
		} catch (BlogMoverException ex) {
			throw ex;
		} catch (RuntimeException e) {
			log.error("An error occured in write.", e);
			throw e;
		}
	}

	public void read() throws BlogMoverException {
		try {
			this.userDelegate.read();
		} catch (BlogMoverException ex) {
			throw ex;
		} catch (RuntimeException e) {
			log.error("An error occured in read.", e);
			throw e;
		}
	}

	class UserDelegate implements Serializable {
		/**
     * 
     */
    private static final long serialVersionUID = 4000064494406330649L;

    private final Log log = LogFactory.getLog(UserDelegate.class);

		public static final String SESSION_NAME_USER_FACADE = "com.redv.blogsynchronizer.web.dwr.User.userFacade";

		/**
		 * 
		 */
		public UserDelegate() {
			log.debug("UserDelegate constructor called.");
		}

		/**
		 * 获取用户门面。 如果会话中没有门面对象，将生成一个放入会话中。
		 * 
		 * @return 用户门面。
		 */
		private UserFacade getUserFacade() {
			WebContext webContext = WebContextFactory.get();
			HttpSession session = webContext.getSession(true);
			// return (UserFacade) WebUtils.getOrCreateSessionAttribute(session,
			// SESSION_NAME_USER_FACADE, UserFacade.class);
			UserFacade ret = (UserFacade) session
					.getAttribute(SESSION_NAME_USER_FACADE);
			if (ret == null) {
				ret = new UserFacade();
				ret.setToken(session.getId());
				session.setAttribute(SESSION_NAME_USER_FACADE, ret);

				LoggingService loggingService = (LoggingService) WebApplicationContextUtils
						.getWebApplicationContext(
								WebContextFactory.get().getServletContext())
						.getBean("loggingService");

				ret.setLoggingService(loggingService);
			}
			return ret;
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
		private BlogReader newSyncSource(String className)
				throws InstantiationException, IllegalAccessException,
				ClassNotFoundException {
			BlogReader reader = (BlogReader) Class.forName(className)
					.newInstance();
			return reader;
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
		private BlogSyncDest newSyncDest(String className)
				throws InstantiationException, IllegalAccessException,
				ClassNotFoundException {
		  if (!className.endsWith("Writer"))
		    throw new ClassNotFoundException("ERROR");
		  
      BlogReader reader = (BlogReader) Class.forName(className.replace("Writer", "Reader"))
        .newInstance();
			BlogWriter writer = (BlogWriter) Class.forName(className)
					.newInstance();
			return new BlogSyncDest(reader, writer);
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
		 * @throws ClassNotFoundException
		 * @throws IllegalAccessException
		 * @throws InstantiationException
		 */
		public void setSyncSrc(String className) throws InstantiationException,
				IllegalAccessException, ClassNotFoundException {
			log.debug("setSyncSource called. className=" + className);
			this.getUserFacade().setSyncSrc(newSyncSource(className));
		}

		/**
		 * 设置写入器。
		 * 
		 * @param className
		 * @throws ClassNotFoundException
		 * @throws IllegalAccessException
		 * @throws InstantiationException
		 */
		public void setSyncDest(String className) throws InstantiationException,
				IllegalAccessException, ClassNotFoundException {
			log.debug("setSyncDest called. className=" + className);
			this.getUserFacade().setSyncDest(newSyncDest(className));
		}

		/**
		 * 设置读取器属性。
		 * 
		 * @param property
		 * @param value
		 * @throws BlogMoverException
		 */
		public void setSyncSrcProperty(String property, String value)
				throws BlogMoverException {
			UserFacade facade = this.getUserFacade();
			facade.setSyncSrcProperty(property, value);
		}

		public void setSyncSrcProperties(String[] properties, String[] values)
				throws BlogMoverException {
			UserFacade facade = this.getUserFacade();
			for (int i = 0; i < properties.length; i++) {
				log.debug("Setting reader property: " + properties[i]
						+ ", value: " + values[i]);
				if (properties[i] != null) {
					facade.setSyncSrcProperty(properties[i], values[i]);
				}
			}
		}

		/**
		 * 设置写入器属性。
		 * 
		 * @param property
		 * @param value
		 * @throws BlogMoverException
		 */
		public void setSyncDestProperty(String property, String value)
				throws BlogMoverException {
			UserFacade facade = this.getUserFacade();
			facade.setSyncDestProperty(property, value);
		}

		public void setSyncDestProperties(String[] properties, String[] values)
				throws BlogMoverException {
			UserFacade facade = this.getUserFacade();
			for (int i = 0; i < properties.length; i++) {
				log.debug("Setting writer property: " + properties[i]
						+ ", value: " + values[i]);
				if (properties[i] != null) {
					facade.setSyncDestProperty(properties[i], values[i]);
				}
			}
		}

		/**
		 * 读取。
		 * 
		 * @throws BlogMoverException
		 */
		public void read() throws BlogMoverException {
			log.debug("read() called.");
			this.getUserFacade().read();
		}

		/**
		 * 写入。
		 * 
		 * @throws BlogMoverException
		 */
		public void write() throws BlogMoverException {
			log.debug("write() called.");
			this.getUserFacade().write();
		}

		/**
		 * 获取读取器状态。
		 * 
		 * @return
		 */
		public Status getSyncSrcReaderStatus() {
			return this.getUserFacade().getReader().getStatus();
		}

		/**
		 * 获取写入器状态。
		 * 
		 * @return
		 */
    public Status getSyncDestReaderStatus() {
      return this.getUserFacade().getSyncDest().getReader().getStatus();
    }
		public Status getSyncDestWriterStatus() {
			return this.getUserFacade().getSyncDest().getWriter().getStatus();
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
}