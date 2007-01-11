/**
 * Created on 2007-1-11 下午11:12:45
 */
package com.redv.blogmover.logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.test.TestUtils;

/**
 * @author shutra
 * 
 */
public class LoggingServiceTest extends
		AbstractTransactionalDataSourceSpringContextTests {
	private LoggingService loggingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	 */
	@Override
	protected String[] getConfigLocations() {
		return TestUtils.getConfigLocations();
	}

	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.logging.LoggingServiceImpl#log(java.util.List, com.redv.blogmover.logging.BSP)}.
	 */
	@Test
	public void testLog() {
		List<WebLog> webLogs = new ArrayList<WebLog>();
		WebLog webLog = new WebLogImpl();
		webLog.setTitle("test");
		webLog.setBody("test");
		webLog.setExcerpt("test");
		webLog.setPublishedDate(new Date());
		webLog.setUrl("http://xpert.cn/shutra/entry/test");
		webLogs.add(webLog);

		BSP toBsp = new BSP();
		toBsp.setId("test");
		toBsp.setName("test");
		toBsp.setServerURL("http://xpert.cn/shutra/");
		loggingService.log(webLogs, toBsp);

		this.transactionManager.commit(this.transactionStatus);

		assertEquals(1, this.countRowsInTable("bsp"));
		assertEquals(1, this.countRowsInTable("moving"));
		assertEquals(1, this.countRowsInTable("moving_entry"));
		assertEquals(1, this.countRowsInTable("moving_log"));

		this.deleteFromTables(new String[] { "moving_log", "moving",
				"moving_entry", "bsp" });
	}

}
