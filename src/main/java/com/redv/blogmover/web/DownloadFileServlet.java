/**
 *  Created on 2006-6-26 13:02:43
 */
package com.redv.blogmover.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class DownloadFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6310997986394657405L;

	private final Log log = LogFactory.getLog(this.getClass());

	public static final Pattern TEMP_FILE_NAME_PATTERN = Pattern
			.compile("^com/redv/blogmover/[a-zA-Z0-9]+/[0-9]+$");

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("filename");
		String attachmentFilename = request.getParameter("attachmentFilename");
		String contentType = request.getParameter("contentType");
		if (StringUtils.isEmpty(contentType)) {
			contentType = "application/oct-stream";
		}

		log.debug("filename: " + filename);
		if (!TEMP_FILE_NAME_PATTERN.matcher(filename).matches()) {
			throw new ServletException("Parameter filename is not matched "
					+ TEMP_FILE_NAME_PATTERN.pattern() + ".");
		}

		File tmpdir = new File(SystemUtils.JAVA_IO_TMPDIR);
		File downloadFile = new File(tmpdir, filename);
		log.debug("donwloadFile: " + downloadFile.getAbsolutePath());
		if (!downloadFile.exists()) {
			throw new ServletException("File not found.");
		}
		if (!downloadFile.isFile()) {
			throw new ServletException(
					"This is not a file(may be a directory).");
		}
		byte[] buffer = new byte[1024];
		int l;
		FileInputStream fis = new FileInputStream(downloadFile);
		ServletOutputStream sos = response.getOutputStream();
		
		if (contentType.equals("application/oct-stream")) {
			response.setContentType(contentType);
			response.addHeader("Content-Disposition", "attachment; filename="
					+ attachmentFilename);
		} else {
			response.setContentType(contentType + "; charset=UTF-8");	
		}
		
		response.setContentLength((int) downloadFile.length());
		try {
			while ((l = fis.read(buffer)) != -1) {
				sos.write(buffer, 0, l);
			}
		} finally {
			fis.close();
			sos.close();
		}
	}
}
