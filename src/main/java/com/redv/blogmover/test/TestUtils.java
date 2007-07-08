/**
 * Created on 2006-12-29 下午10:56:40
 */
package com.redv.blogmover.test;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.commons.lang.SystemUtils;

/**
 * @author shutra
 * 
 */
public class TestUtils {
	public static String[] getConfigLocations() {
		File userDir = new File(SystemUtils.getUserDir(),
				"src/main/webapp/WEB-INF/");

		String[] paths;
		try {
			paths = new String[] {
					new File(userDir, "applicationContext.xml").toURI().toURL()
							.toExternalForm(),
					new File(userDir, "dataAccessContext-hibernate.xml")
							.toURI().toURL().toExternalForm(),
					new File(userDir, "blog-mover-servlet.xml").toURI().toURL()
							.toExternalForm() };
			return paths;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
