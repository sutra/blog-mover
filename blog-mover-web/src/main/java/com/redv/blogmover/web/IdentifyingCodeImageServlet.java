/**
 *  Created on 2006-6-26 13:02:43
 */
package com.redv.blogmover.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.util.WebUtils;

import com.redv.blogmover.UserFacade;
import com.redv.blogmover.UserFacadeImpl;
import com.redv.blogmover.web.dwr.User;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class IdentifyingCodeImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6310997986394657405L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		HttpSession session = request.getSession(true);
		UserFacade userFacade = (UserFacade) WebUtils
				.getOrCreateSessionAttribute(session,
						User.SESSION_NAME_USER_FACADE, UserFacadeImpl.class);

		Object readerOrWriter = null;
		if (type.equals("reader")) {
			readerOrWriter = userFacade.getReader();
		} else {
			readerOrWriter = userFacade.getWriter();
		}
		if (readerOrWriter == null) {
			throw new ServletException("readerOrWriter is null.");
		}
		Method m = null;
		try {
			m = readerOrWriter.getClass().getMethod("getIdentifyingCodeImage");
		} catch (SecurityException e) {
			throw new ServletException(e);
		} catch (NoSuchMethodException e) {
			throw new ServletException(e);
		}
		Object o;
		try {
			o = m.invoke(readerOrWriter);
		} catch (IllegalArgumentException e) {
			throw new ServletException(e);
		} catch (IllegalAccessException e) {
			throw new ServletException(e);
		} catch (InvocationTargetException e) {
			throw new ServletException(e);
		}
		byte[] bytes = (byte[]) o;
		try {
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
			response.getOutputStream().write(bytes);
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}

}
