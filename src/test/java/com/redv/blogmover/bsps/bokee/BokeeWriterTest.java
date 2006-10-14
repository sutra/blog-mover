/**
 * Created on 2006-8-22 下午09:33:34
 */
package com.redv.blogmover.bsps.bokee;

import java.util.ArrayList;
import java.util.List;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

import junit.framework.TestCase;

/**
 * @author Shutra
 * 
 */
public class BokeeWriterTest extends TestCase {
	private BokeeWriter bokeeWriter;

	/**
	 * @param name
	 */
	public BokeeWriterTest(String name) {
		super(name);
		bokeeWriter = new BokeeWriter();
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.bokee.BokeeWriter#writeBlog(com.redv.blogmover.WebLog, java.util.Map)}.
	 * 
	 * @throws BlogMoverException
	 */
	public void testWriteBlog() throws BlogMoverException {
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("test");
		webLog.setBody("测试");
		webLogs.add(webLog);
		bokeeWriter.setUsername("blogremover");
		bokeeWriter.setPassword("xpert.cn");
		// bokeeWriter.write(webLogs);
	}

}
