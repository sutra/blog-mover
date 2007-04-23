/**
 *  Created on 2006-6-30 23:53:50
 */
package com.redv.blogmover.web;

import java.util.Iterator;
import java.util.Map;

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
public class WritersController implements Controller {
	private static final Log log = LogFactory.getLog(WritersController.class);

	private String[][] writers;

	public void setWriters(Map<String, String> writersMap) {
		log.debug("setWriters(Map<String, String>) called.");
		writers = new String[writersMap.keySet().size()][2];
		int i = 0;
		for (Iterator<String> iter = writersMap.keySet().iterator(); iter
				.hasNext();) {
			String id = iter.next();
			String className = writersMap.get(id);
			writers[i++] = new String[] { id, className };
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("WritersController.handleRequest called, writers.length: "
				+ this.writers.length);
		return new ModelAndView("writers", "writers", this.writers);
	}

}
