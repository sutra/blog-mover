/**
 * Created on 2006-9-11 下午10:06:17
 */
package com.redv.blogmover.web;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.util.BlogMoverUtils;

/**
 * @author Shutra
 * 
 */
public class BlogMoverServletContextListener implements ServletContextListener {
	private final Log log = LogFactory.getLog(this.getClass());

	private File dir;

	public static final File TMPDIR = new File(new File(
			SystemUtils.JAVA_IO_TMPDIR), "com/redv/blogmover");

	public BlogMoverServletContextListener() {
		dir = TMPDIR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		boolean deleted = BlogMoverUtils.deleteDir(dir);
		if (!deleted) {
			log.warn("Delete dir: " + dir.getAbsolutePath() + " failed.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		boolean made = dir.mkdirs();
		if (!made) {
			log.fatal("Made tmpdir: " + TMPDIR.getAbsolutePath() + " failed.");
		}
	}

}
