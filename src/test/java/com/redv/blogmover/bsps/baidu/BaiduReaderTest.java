/**
 * Created on 2006-8-5 下午01:18:22
 */
package com.redv.blogmover.bsps.baidu;

import com.redv.blogmover.BlogRemoverException;
import com.redv.blogmover.bsps.baidu.BaiduReader;

import junit.framework.TestCase;

/**
 * @author Shutra
 *
 */
public class BaiduReaderTest extends TestCase {
	private BaiduReader baiduReader;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		baiduReader = new BaiduReader();
		baiduReader.setUsername("blogremover");
		baiduReader.setPassword("wangjing");
	}

	/**
	 * Test method for {@link com.redv.blogmover.bsps.baidu.BaiduReader#read()}.
	 * @throws BlogRemoverException 
	 */
	public void testRead() throws BlogRemoverException {
		baiduReader.read();
	}

}
