/**
 * Created on 2006-12-29 下午11:50:39
 */
package com.redv.blogmover.web;

import junit.framework.TestCase;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.redv.blogmover.test.TestUtils;

/**
 * @author shutra
 * 
 */
public class AbstractControllerTest extends TestCase {
	protected XmlWebApplicationContext ctx;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		String[] paths = TestUtils.getConfigLocations();
		ctx = new XmlWebApplicationContext();
		ctx.setConfigLocations(paths);
		ctx.setServletContext(new MockServletContext(""));
		ctx.refresh();
	}
}
