/**
 * Created on 2006-12-1 下午09:43:18
 */
package com.redv.blogmover.bsps.sohu;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redv.blogmover.BlogMoverException;

/**
 * @author shutra
 * 
 */
public class SohuBlogReaderTest {
	private SohuBlogReader sohuBlogReader;

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
		this.sohuBlogReader = new SohuBlogReader();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.sohu.SohuBlogReader#read()}.
	 * 
	 * @throws BlogMoverException
	 */
	@Test
	public void testRead() throws BlogMoverException {
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@sohu.com");
		this.sohuBlogReader.read();
	}

	@Test
	public void testReadChinaren() throws BlogMoverException {
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@chinaren.com");
		this.sohuBlogReader.read();
	}

	@Test
	public void testReadSogou() throws BlogMoverException {
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@sogou.com");
		this.sohuBlogReader.read();
	}

}
