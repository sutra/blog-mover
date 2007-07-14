package com.redv.blogmover.bsps.com.blogchinese;

import junit.framework.TestCase;

/**
 * @author shutrazh
 * 
 */
public class BlogChineseReaderTest extends TestCase {
	private BlogChineseReader reader;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		reader = new BlogChineseReader();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogchinese.BlogChineseReader#BlogChineseReader()}.
	 */
	public void testBlogChineseReader() {
		assertEquals("com.redv.blogmover.bsps.com.blogchinese", reader.getBsp().getId());
	}

}
