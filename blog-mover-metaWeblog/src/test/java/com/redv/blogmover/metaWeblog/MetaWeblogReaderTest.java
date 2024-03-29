/**
 * Created on 2006-9-28 19:09:22
 */
package com.redv.blogmover.metaWeblog;

import java.net.MalformedURLException;
import java.util.List;

import junit.framework.TestCase;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;

/**
 * @author Shutra
 * 
 */
public class MetaWeblogReaderTest extends TestCase {
	private MetaWeblogReader reader;

	public MetaWeblogReaderTest() throws MalformedURLException {
		reader = new MetaWeblogReader();
		reader.setServerURL("http://www.bokeland.com/xmlrpc.php");
		reader.setBlogid("722");
		reader.setUsername("blogmover");
		reader.setPassword("xpert.cn");
	}

	public void test() {
		assertTrue(true);
	}

	public void _testRead() throws BlogMoverException {
		List<WebLog> webLogs = reader.read();
		assertNotSame(0, webLogs.size());
		// WebLog webLog = webLogs.get(0);
		// assertEquals(webLog.getTitle(), "test");
		// assertEquals(webLog.getBody(), "test");
	}
}
