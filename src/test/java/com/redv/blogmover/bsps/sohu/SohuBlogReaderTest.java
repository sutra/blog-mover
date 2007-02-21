/**
 * Created on 2006-12-1 下午09:43:18
 */
package com.redv.blogmover.bsps.sohu;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.bsps.com.sohu.blog.SohuBlogReader;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.sohu.blog.SohuBlogReader#read()}.
	 * 
	 * @throws BlogMoverException
	 */
	// @Test
	public void _testRead() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@sohu.com");
		this.sohuBlogReader.read();
	}

	// @Test
	public void _testReadChinaren() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@chinaren.com");
		this.sohuBlogReader.read();
	}

	// @Test
	public void _testReadSogou() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@sogou.com");
		this.sohuBlogReader.read();
	}

	// @Test
	public void _testMailDomainNotCorrect() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@error.com");
		try {
			this.sohuBlogReader.read();
			fail("A LoginFailedException should be throwed.");
		} catch (LoginFailedException ex) {
			// Good.
		}
	}

	// @Test
	public void _testPasswordNotCorrect() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("errorpassword");
		this.sohuBlogReader.setMaildomain("@sogou.com");
		try {
			this.sohuBlogReader.read();
			fail("Username password not correct, should throw a exception.");
		} catch (LoginFailedException e) {
			// Good.
		}
	}

}
