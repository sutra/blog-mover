/**
 * Created on 2006-12-29 下午11:50:39
 */
package com.redv.blogmover.web;

import java.io.File;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.commons.lang.SystemUtils;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @author shutra
 * 
 */
public abstract class AbstractControllerTest extends TestCase {
	protected XmlWebApplicationContext ctx;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		String[] paths = getConfigLocations();
		ctx = new XmlWebApplicationContext();
		ctx.setConfigLocations(paths);
		ctx.setServletContext(new MockServletContext(""));
		ctx.refresh();
	}
}
