/**
 * Created on 2006-9-28 下午10:22:52
 */
package com.redv.blogmover.feed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.SystemUtils;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author Shutra
 * 
 */
public class RomeWriterTest extends TestCase {

	private RomeWriter writer;

	private File outputFile;

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
	 * {@link com.redv.blogmover.impl.AbstractBlogWriter#write(java.util.List)}.
	 * 
	 * @throws BlogMoverException
	 * 
	 */
	public void _testWrite() throws BlogMoverException {
		outputFile = new File(SystemUtils.getJavaIoTmpDir(),
				"RomeWriterTest.xml");

		writer = new RomeWriter();
		writer.setAuthor("Shutra");
		writer.setCopyright("Copyright (c) Redv Soft");
		writer.setDescription("描述");
		writer.setFeedType("atom_1.0");
		writer.setOutputFile(outputFile);
		writer.setLanguage("zh-CN");
		writer.setLink("http://shutra.xpert.cn");
		writer.setTitle("Shutra's");

		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("标题");
		webLog.setBody("内容");
		webLog.setUrl("permaLink");
		webLogs.add(webLog);
		writer.write(webLogs);

		if (!this.outputFile.delete()) {
			this.outputFile.deleteOnExit();
		}
	}

}
