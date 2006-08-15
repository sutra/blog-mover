/**
 *  Created on 2006-6-30 23:53:50
 */
package com.redv.blogremover.web;

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
public class ReadersController implements Controller {
	private static final Log log = LogFactory.getLog(ReadersController.class);

	private String[][] readers;

	public void setReaders(Map<String, String> readersMap) {
		log.debug("setReaders(Map<String, String>) called.");
		readers = new String[readersMap.keySet().size()][2];
		int i = 0;
		for (Iterator<String> iter = readersMap.keySet().iterator(); iter
				.hasNext();) {
			String id = iter.next();
			String className = readersMap.get(id);
			readers[i++] = new String[] { id, className };
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
		return new ModelAndView("readers", "readers", this.readers);
	}

}
