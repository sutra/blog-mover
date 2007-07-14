package com.redv.blogmover.bsps.com.blogcup;

import junit.framework.TestCase;

/**
 * @author shutrazh
 * 
 */
public class BlogCupReaderTest extends TestCase {
	private BlogCupReader reader;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		reader = new BlogCupReader();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcup.BlogCupReader#BlogCupReader()}.
	 */
	public void testBlogChineseReader() {
		assertEquals("blogcup.com", reader.getBsp().getId());
	}

}
