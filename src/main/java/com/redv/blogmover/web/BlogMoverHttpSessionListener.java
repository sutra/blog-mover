/**
 * Created on 2006-9-11 下午07:53:39
 */
package com.redv.blogmover.web;

import java.io.File;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
			File tmpdir = new File(SystemUtils.JAVA_IO_TMPDIR);
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
			File tmpdir = new File(SystemUtils.JAVA_IO_TMPDIR);
			File file = new File(tmpdir, session.getId());
			boolean deleted = deleteDir(file);
			log.debug("SessionId: " + session.getId() + ". Delete dir: "
					+ file.getAbsolutePath());
			if (!deleted) {
				log.warn("SessionId: " + session.getId()
						+ ". Delete dir failed: " + file.getAbsolutePath());
			}
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

}
