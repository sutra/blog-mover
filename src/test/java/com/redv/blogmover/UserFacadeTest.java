/**
 * Created on 2006-12-30 上午12:26:21
 */
package com.redv.blogmover;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.SystemUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.redv.blogmover.bsps.baidu.BaiduReader;
import com.redv.blogmover.feed.RomeWriter;
import com.redv.blogmover.logging.LoggingService;
import com.redv.blogmover.test.TestUtils;

/**
 * @author shutra
 * 
 */
public class UserFacadeTest extends TestCase {
	private XmlWebApplicationContext ctx;

	private UserFacade userFacade;

	private LoggingService loggingService;

	private MockHttpServletRequest request;

	private MockHttpSession session;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		String[] paths = TestUtils.getConfigLocations();
		ctx = new XmlWebApplicationContext();
		ctx.setConfigLocations(paths);
		ctx.setServletContext(new MockServletContext(""));
		ctx.refresh();

		loggingService = (LoggingService) ctx.getBean("loggingService");

		request = new MockHttpServletRequest();
		session = (MockHttpSession) request.getSession(true);

		userFacade = new UserFacade();
		userFacade.setToken(session.getId());
		userFacade.setLoggingService(loggingService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() throws BlogMoverException {
		userFacade.setReader(new BaiduReader());
		userFacade.setReaderProperty("username", "blogremover");
		userFacade.setReaderProperty("password", "wangjing");
		userFacade.read();

		List<WebLog> webLogs = userFacade.getWebLogs();

		assertTrue(webLogs.size() > 0);
		File outputFile = new File(SystemUtils.getJavaIoTmpDir(),
				"RomeWriterTest.xml");

		RomeWriter romeWriter = new RomeWriter();
		romeWriter.setOutputFile(outputFile);
		userFacade.setWriter(romeWriter);
		userFacade.setWriterProperty("author", "Sutra");
		userFacade.setWriterProperty("copyright", "Copyright (c) Redv Soft");
		userFacade.setWriterProperty("description", "描述");
		userFacade.setWriterProperty("feedType", "atom_1.0");
		userFacade.setWriterProperty("language", "zh-CN");
		userFacade.setWriterProperty("link", "http://redv.com");
		userFacade.setWriterProperty("title", "Sutra's");
		userFacade.write();

		if (!outputFile.delete()) {
			outputFile.deleteOnExit();
		}
	}

}
