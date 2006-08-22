/**
 *  Created on 2006-6-25 16:45:36
 */
package com.redv.blogmover.bsps.space;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class AnonymousSpaceReaderApp {
	private static final Log log = LogFactory
			.getLog(AnonymousSpaceReaderApp.class);

	/**
	 * 
	 */
	public AnonymousSpaceReaderApp() {
		super();
	}

	public static void main(String[] args) throws IOException,
			BlogMoverException {
		AnonymousSpaceReader bsp = new AnonymousSpaceReader();

		bsp.setSpaceName("jungleford");

		List<WebLog> webLogs = bsp.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss");
		for (WebLog wl : webLogs) {
			log.debug(wl.getPublishedDate());
			log.debug(wl.getTitle());
			log.debug(wl.getBody());

			FileOutputStream fos = new FileOutputStream(new File(SystemUtils
					.getUserHome(), sdf.format(wl.getPublishedDate())
					+ "."
					+ wl.getTitle().replace('/', '_').replace('\\', '_')
							.replace(':', '_').replace('*', '_').replace('?',
									'_').replace('\"', '_').replace('<', '_')
							.replace('>', '_').replace('|', '_') + ".html"));
			Writer w = new OutputStreamWriter(fos, "UTF-8");
			w.write(wl.getUrl());
			w.write("<hr />");
			w.write(wl.getTitle());
			w.write("<hr />");
			w.write(sdf.format(wl.getPublishedDate()));
			w.write("<hr />");
			w.write(wl.getBody());
			w.close();
			fos.close();
		}
	}
}
