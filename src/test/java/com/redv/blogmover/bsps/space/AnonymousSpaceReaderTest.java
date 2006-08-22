/**
 *  Created on 2006-6-20 21:42:25
 */
package com.redv.blogmover.bsps.space;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.bsps.space.AnonymousSpaceReader;

/**
 * 
 * @author Joe
 * @version 1.0
 */
public class AnonymousSpaceReaderTest extends TestCase {
	private static final Log log = LogFactory
			.getLog(AnonymousSpaceReaderTest.class);

	private AnonymousSpaceReader bsp;

	public AnonymousSpaceReaderTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bsp = new AnonymousSpaceReader();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'com.redv.blogremover.space.SpaceBSP.read()'
	 */
	public void testRead() {
		try {
			read("jf8264");
		} catch (BlogMoverException e) {
			log.debug(e.getMessage());
		}
	}

	private void read(String spaceName) throws BlogMoverException {
		bsp.setSpaceName(spaceName);
		List<WebLog> webLogs = bsp.read();
		for (WebLog webLog : webLogs) {
			log.info(webLog.getUrl());
			log.info(webLog.getPublishedDate());
			log.info(webLog.getTitle());
		}
	}

}
