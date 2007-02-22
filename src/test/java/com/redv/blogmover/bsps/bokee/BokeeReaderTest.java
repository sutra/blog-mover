package com.redv.blogmover.bsps.bokee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Status;
import com.redv.blogmover.bsps.bokee.BokeeReader;

import junit.framework.TestCase;

public class BokeeReaderTest extends TestCase {
	private static final Log log = LogFactory.getLog(BokeeReaderTest.class);

	private BokeeReader reader;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reader = new BokeeReader();
	}
	
	public void test() {
		assertTrue(true);
	}

	/*
	 * Test method for 'com.redv.blogremover.bsps.bokee.BokeeReader.read()'
	 */
	public void _testRead() throws BlogMoverException {
		reader.read();
	}

	/*
	 * Test method for
	 * 'com.redv.blogremover.impl.AbstractBlogReader.getStatus()'
	 */
	public void _testGetStatus() {
		Status status = reader.getStatus();
		log.debug("totalCount: " + status.getTotalCount());
	}

}
