/**
 * Created on 2006-12-1 下午11:11:39
 */
package com.redv.blogmover.bsps.baidu;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author shutra
 * 
 */
public class BaiduWriterTest extends TestCase {
	private static final Log log = LogFactory.getLog(BaiduWriterTest.class);

	private BaiduWriter baiduWriter;

	private BaiduReader baiduReader;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		this.baiduWriter = new BaiduWriter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}
	
	public void test() {
		assertTrue(true);
	}

	private List<WebLog> generateWebLogs() {
		List<WebLog> webLogs = new ArrayList<WebLog>(1);
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("测试：标题。");
		webLog.setExcerpt("测试：摘要。");
		webLog.setBody("测试：内容。");
		webLogs.add(webLog);
		return webLogs;
	}

	private void testWrite(String username, String password)
			throws BlogMoverException {
		this.baiduWriter = new BaiduWriter();
		this.baiduReader = new BaiduReader();
		this.baiduReader.setUsername(username);
		this.baiduReader.setPassword(password);
		List<WebLog> old = this.baiduReader.read();
		log.debug("old.size(): " + old.size());

		this.baiduWriter.setUsername(username);
		this.baiduWriter.setPassword(password);
		this.baiduWriter.write(this.generateWebLogs());

		List<WebLog> _new = this.baiduReader.read();
		log.debug("_new.size(): " + _new.size());
		assertEquals(1, _new.size() - old.size());
	}

	public void _testGetBsp() {
		assertEquals("com.redv.blogmover.bsps.baidu.BaiduWriter",
				this.baiduWriter.getBsp().getId());
	}

	public void _testWriteUsernameEqualsBlogHandle() throws BlogMoverException {
		String username = "blogremover";// blogHandle: blogremover
		String password = "wangjing";
		this.testWrite(username, password);
	}

	public void _testWriteUsernameEqualsBlogHandle1() throws BlogMoverException {
		String username = "blogmover1";// blogHandle: blogmover1
		String password = "blogmover1";
		this.testWrite(username, password);
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.impl.AbstractBlogWriter#write(java.util.List)}.
	 * 
	 * @throws BlogMoverException
	 */
	public void _testWriteUsernameNotEqualsBlogHandle()
			throws BlogMoverException {
		String username = "blogmover2";// blogHandler: blogmover3
		String password = "blogmover2";
		this.testWrite(username, password);
	}

	public void _testWritePasswordError() throws BlogMoverException {
		this.baiduWriter = new BaiduWriter();
		String username = "blogmover2";// blogHandler: blogmover3
		String errorPassword = "anerrorpassword";
		this.baiduWriter.setUsername(username);
		this.baiduWriter.setPassword(errorPassword);
		try {
			this.baiduWriter.write(this.generateWebLogs());
			fail("A LoginFailedException should be throwed.");
		} catch (LoginFailedException ex) {
			// Good.
		}
	}
}
