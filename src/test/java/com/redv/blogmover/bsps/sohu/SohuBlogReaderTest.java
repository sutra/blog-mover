/**
 * Created on 2006-12-1 下午09:43:18
 */
package com.redv.blogmover.bsps.sohu;

import junit.framework.TestCase;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.bsps.com.sohu.blog.SohuBlogReader;

/**
 * @author shutra
 * 
 */
public class SohuBlogReaderTest extends TestCase {
	private SohuBlogReader sohuBlogReader;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	public void test() {
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.sohu.blog.SohuBlogReader#read()}.
	 * 
	 * @throws BlogMoverException
	 */
	public void _testRead() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@sohu.com");
		this.sohuBlogReader.read();
	}

	public void _testReadChinaren() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@chinaren.com");
		this.sohuBlogReader.read();
	}

	public void _testReadSogou() throws BlogMoverException {
		this.sohuBlogReader = new SohuBlogReader();
		this.sohuBlogReader.setUsername("blogmover");
		this.sohuBlogReader.setPasswd("blogmover");
		this.sohuBlogReader.setMaildomain("@sogou.com");
		this.sohuBlogReader.read();
	}

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
