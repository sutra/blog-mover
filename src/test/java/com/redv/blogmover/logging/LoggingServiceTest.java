/**
 * Created on 2007-1-11 下午11:12:45
 */
package com.redv.blogmover.logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
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
	
	public void test() {
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.logging.LoggingServiceImpl#log(java.util.List, com.redv.blogmover.logging.BSP)}.
	 */
	public void _testLog() {
		loggingService.log(this.buildWebLogs(this.buildBsp("fromBsp"), 1), this
				.buildBsp("toBsp"));

		this.transactionManager.commit(this.transactionStatus);

		assertEquals(2, this.countRowsInTable("bsp"));
		assertEquals(1, this.countRowsInTable("moving"));
		assertEquals(1, this.countRowsInTable("moving_entry"));
		assertEquals(1, this.countRowsInTable("moving_log"));

		this.deleteFromTables(new String[] { "moving_log", "moving",
				"moving_entry", "bsp" });
	}

	public void _testGetFromCountAndGetToCount1() {
		testGetFromCountAndGetToCount(1);
	}

	public void _testGetFromCountAndGetToCount2() {
		testGetFromCountAndGetToCount(2);
	}

	private void testGetFromCountAndGetToCount(int times) {
		BSP fromBsp = this.buildBsp("fromBsp");
		BSP toBsp = this.buildBsp("toBsp");

		int total = 0;
		for (int i = 0; i < times; i++) {
			int n = RandomUtils.nextInt(100);
			total += n;
			loggingService.log(this.buildWebLogs(fromBsp, n), toBsp);
		}

		this.transactionManager.commit(this.transactionStatus);

		assertEquals(times, this.loggingService.getMovedOutCount(fromBsp
				.getId()));
		assertEquals(times, this.loggingService.getMovedInCount(toBsp.getId()));

		assertEquals(total, this.countRowsInTable("moving_log"));

		this.deleteFromTables(new String[] { "moving_log", "moving",
				"moving_entry", "bsp" });
	}

	private List<WebLog> buildWebLogs(BSP fromBsp, int size) {
		List<WebLog> webLogs = new ArrayList<WebLog>(size);
		for (int i = 0; i < size; i++) {
			WebLog webLog = new WebLogImpl();
			webLog.setBsp(fromBsp);
			webLog.setTitle("test");
			webLog.setBody("test");
			webLog.setExcerpt("test");
			webLog.setPublishedDate(new Date());
			webLog.setUrl("http://xpert.cn/shutra/entry/test");
			webLogs.add(webLog);
		}
		return webLogs;
	}

	private BSP buildBsp(String bspId) {
		BSP toBsp = new BSP();
		toBsp.setId(bspId);
		return toBsp;
	}

}
