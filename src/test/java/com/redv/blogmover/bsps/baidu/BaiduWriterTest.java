/**
 * Created on 2006-12-1 下午11:11:39
 */
package com.redv.blogmover.bsps.baidu;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.LoginFailedException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author shutra
 * 
 */
public class BaiduWriterTest {
	private static final Log log = LogFactory.getLog(BaiduWriterTest.class);

	private BaiduWriter baiduWriter;

	private BaiduReader baiduReader;

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
		this.baiduWriter = new BaiduWriter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
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

	@Test
	public void testGetBsp() {
		assertEquals("com.redv.blogmover.bsps.baidu.BaiduWriter",
				this.baiduWriter.getBsp().getId());
	}

	@Test
	public void testWriteUsernameEqualsBlogHandle() throws BlogMoverException {
		String username = "blogremover";// blogHandle: blogremover
		String password = "wangjing";
		this.testWrite(username, password);
	}

	@Test
	public void testWriteUsernameEqualsBlogHandle1() throws BlogMoverException {
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
	@Test
	public void testWriteUsernameNotEqualsBlogHandle()
			throws BlogMoverException {
		String username = "blogmover2";// blogHandler: blogmover3
		String password = "blogmover2";
		this.testWrite(username, password);
	}

	@Test
	public void testWritePasswordError() throws BlogMoverException {
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
