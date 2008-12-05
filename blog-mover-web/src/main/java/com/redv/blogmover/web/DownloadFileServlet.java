/**
 *  Created on 2006-6-26 13:02:43
 */
package com.redv.blogmover.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.Constants;

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

	public static final Pattern TEMP_FILE_NAME_PATTERN = Constants
			.getDownloadFileTempFileNamePattern();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			executeDownload(request, response);
		} catch (IllegalArgumentException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (FileNotFoundException ex) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void executeDownload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String filename = request.getParameter("filename");

		log.debug("filename: " + filename);
		if (!TEMP_FILE_NAME_PATTERN.matcher(filename).matches()) {
			throw new IllegalArgumentException(
					"Parameter filename is not matched "
							+ TEMP_FILE_NAME_PATTERN.pattern() + ".");
		}

		File tmpdir = new File(SystemUtils.JAVA_IO_TMPDIR);
		File downloadFile = new File(tmpdir, filename);
		log.debug("donwloadFile: " + downloadFile.getAbsolutePath());
		if (!downloadFile.exists()) {
			throw new FileNotFoundException("File not found.");
		}
		if (!downloadFile.isFile()) {
			throw new FileNotFoundException(
					"This is not a file(may be a directory).");
		}

		String contentType = request.getParameter("contentType");
		if (StringUtils.isEmpty(contentType)) {
			contentType = "application/oct-stream";
		}

		String attachmentFilename = request.getParameter("attachmentFilename");

		if (contentType.equals("application/oct-stream")) {
			response.setContentType(contentType);
			response.addHeader("Content-Disposition", "attachment; filename="
					+ attachmentFilename);
		} else {
			response.setContentType(contentType + "; charset=UTF-8");
		}

		int length = (int) downloadFile.length();
		response.setContentLength(length);

		ServletOutputStream sos = response.getOutputStream();
		try {
			writeFile(downloadFile, sos);
		} finally {
			IOUtils.closeQuietly(sos);
		}
	}

	/**
	 * Write file into output stream.
	 * 
	 * @param downloadFile
	 *            the file to write
	 * @param sos
	 *            output stream
	 * @throws IOException
	 *             the file not found, read failed or write to output stream
	 *             failed
	 */
	private void writeFile(File downloadFile, ServletOutputStream sos)
			throws IOException {
		FileInputStream fis = new FileInputStream(downloadFile);
		try {
			IOUtils.copy(fis, sos);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}
}
