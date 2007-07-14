/**
 * 
 */
package com.redv.blogmover;

import java.util.regex.Pattern;

/**
 * @author sutra
 * 
 */
public class Constants {
	private static Pattern DownloadFileServlet_TEMP_FILE_NAME_PATTERN = Pattern
			.compile("^com/redv/blogmover/[a-zA-Z0-9]+/[0-9]+$");

	public static Pattern getDownloadFileTempFileNamePattern() {
		return DownloadFileServlet_TEMP_FILE_NAME_PATTERN;
	}
}
