/**
 * Created on 2006-12-2 上午01:02:31
 */
package com.redv.blogmover.bsps.sohu;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author shutra
 * 
 */
public class SohuBlogWriterTest {
	private SohuBlogWriter sohuBlogWriter;

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
		this.sohuBlogWriter = new SohuBlogWriter();
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
	 * {@link com.redv.blogmover.impl.AbstractBlogWriter#write(java.util.List)}.
	 * 
	 * @throws BlogMoverException
	 */
	@Test
	public void testWrite() throws BlogMoverException {
		String username = "blogmover";
		String passwd = "blogmover";
		String maildomain = "@sohu.com";

		this.sohuBlogReader.setUsername(username);
		this.sohuBlogReader.setPasswd(passwd);
		this.sohuBlogReader.setMaildomain(maildomain);
		List<WebLog> old = this.sohuBlogReader.read();

		List<WebLog> webLogs = new ArrayList<WebLog>(1);
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("测试：标题。");
		webLog.setExcerpt("测试：摘要。");
		webLog.setBody("测试：内容。");
		webLogs.add(webLog);

		this.sohuBlogWriter.setUsername(username);
		this.sohuBlogWriter.setPasswd(passwd);
		this.sohuBlogWriter.setMaildomain(maildomain);
		this.sohuBlogWriter.write(webLogs);

		List<WebLog> _new = this.sohuBlogReader.read();

		assertEquals(1, _new.size() - old.size());
	}

}
