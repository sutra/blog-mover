/**
 * Created on 2006-8-5 下午01:18:22
 */
package com.redv.blogmover.bsps.baidu;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.bsps.baidu.BaiduReader;

import junit.framework.TestCase;

/**
 * @author Shutra
 * 
 */
public class BaiduReaderTest extends TestCase {
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link com.redv.blogmover.bsps.baidu.BaiduReader#read()}.
	 * 
	 * @throws BlogMoverException
	 */
	public void _testReadUsernameEqualsBlogHandlerWithBlogEntities()
			throws BlogMoverException {
		BaiduReader baiduReader = new BaiduReader();
		baiduReader.setUsername("blogremover");// blogHandle: blogremover
		baiduReader.setPassword("wangjing");
		baiduReader.read();
	}

	public void _testReadUsernameEqualsBlogHandleWithoutBlogEntities()
			throws BlogMoverException {
		BaiduReader baiduReader = new BaiduReader();
		baiduReader.setUsername("blogmover1");// blogHandle: blogmover1
		baiduReader.setPassword("blogmover1");
		baiduReader.read();
	}

	public void _testReadUsernameNotEqualsBlogHandleWithoutBlogEnties()
			throws BlogMoverException {
		BaiduReader baiduReader = new BaiduReader();
		baiduReader.setUsername("blogmover2");// blogHandler: blogmover3
		baiduReader.setPassword("blogmover2");
		assertTrue(0 < baiduReader.read().size());
	}

}
