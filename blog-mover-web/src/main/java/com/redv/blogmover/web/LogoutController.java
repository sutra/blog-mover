/**
 *  Created on 2006-7-2 2:43:20
 */
package com.redv.blogmover.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class LogoutController implements Controller {
	private static final Log log = LogFactory.getLog(LogoutController.class);

	/**
	 * 
	 */
	public LogoutController() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Logout Controller called.");
		request.getSession().invalidate();
		return null;
	}

}
