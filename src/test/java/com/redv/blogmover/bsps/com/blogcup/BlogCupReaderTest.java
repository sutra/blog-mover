package com.redv.blogmover.bsps.com.blogcup;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author shutrazh
 * 
 */
public class BlogCupReaderTest {
	private BlogCupReader reader;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		reader = new BlogCupReader();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcup.BlogCupReader#BlogCupReader()}.
	 */
	@Test
	public void testBlogChineseReader() {
		assertEquals("blogcup.com", reader.getBsp().getId());
	}

}
