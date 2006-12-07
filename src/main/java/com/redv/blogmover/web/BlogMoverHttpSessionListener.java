/**
 * Created on 2006-9-11 下午07:53:39
 */
package com.redv.blogmover.web;

import java.io.File;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.util.BlogMoverUtils;

/**
 * @author Shutra
 * 
 */
public class BlogMoverHttpSessionListener implements HttpSessionListener {
	private final Log log = LogFactory.getLog(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent se) {
		final HttpSession session = se.getSession();
		if (session != null) {
			File tmpdir = BlogMoverServletContextListener.TMPDIR;
			File file = new File(tmpdir, session.getId());
			boolean made = file.mkdir();
			log.debug("SessionId: " + session.getId() + ". Dir: "
					+ file.getAbsolutePath() + " made.");
			if (!made) {
				log.warn("SessionId: " + session.getId() + ". Make dir: "
						+ file.getAbsolutePath() + " failed.");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		final HttpSession session = se.getSession();
		if (session != null) {
			File tmpdir = BlogMoverServletContextListener.TMPDIR;
			File file = new File(tmpdir, session.getId());
			if (file.exists()) {
				boolean deleted = BlogMoverUtils.deleteDir(file);
				log.debug("SessionId: " + session.getId() + ". Delete dir: "
						+ file.getAbsolutePath());
				if (!deleted) {
					log.warn("SessionId: " + session.getId()
							+ ". Delete dir failed: " + file.getAbsolutePath());
				}
			}
		}
	}

}
