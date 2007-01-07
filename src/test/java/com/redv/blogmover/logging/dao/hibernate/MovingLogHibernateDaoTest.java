/**
 * Created on 2006-12-25 下午11:36:19
 */
package com.redv.blogmover.logging.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.redv.blogmover.logging.BSP;
import com.redv.blogmover.logging.Moving;
import com.redv.blogmover.logging.MovingEntry;
import com.redv.blogmover.logging.MovingLog;
import com.redv.blogmover.logging.dao.MovingLogDao;
import com.redv.blogmover.test.TestUtils;

/**
 * @author shutra
 * 
 */
public class MovingLogHibernateDaoTest extends
		AbstractTransactionalDataSourceSpringContextTests {
	private static final Log log = LogFactory
			.getLog(MovingLogHibernateDaoTest.class);

	private MovingLogDao movingLogDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	 */
	@Override
	protected String[] getConfigLocations() {
		return TestUtils.getConfigLocations();
	}

	public void setMovingLogDao(MovingLogDao movingLogDao) {
		this.movingLogDao = movingLogDao;
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.logging.dao.hibernate.MovingHibernateDao#insertMoving(com.redv.blogmover.logging.Moving)}.
	 */
	public void testInsertMoving() {
		Moving moving = new Moving();
		moving.setDate(new Date());
		moving.setMovingLogs(new ArrayList<MovingLog>());

		assertNull(moving.getId());
		this.movingLogDao.insertMoving(moving);
		assertNotNull(moving.getId());
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.logging.dao.hibernate.MovingHibernateDao#InsertBsp(com.redv.blogmover.logging.BSP)}.
	 */
	public void testInsertBsp() {
		BSP bsp = new BSP();
		bsp.setId("test");
		bsp.setName("xpert");
		bsp.setDescription("xpert.cn is a good BSP.");
		bsp.setServerURL("http://xpert.cn");

		assertEquals("test", bsp.getId());
		String ret = this.movingLogDao.insertBsp(bsp);
		assertEquals("test", ret);
		assertEquals("test", bsp.getId());
	}

	private BSP buildBsp(String id) {
		BSP bsp = new BSP();
		bsp.setId(id);
		bsp.setName(id + "-name");
		return bsp;
	}

	private Moving buildMoving(String fromBsp, String toBsp) {
		BSP bspFrom = this.buildBsp(fromBsp);
		BSP bspTo = this.buildBsp(toBsp);

		Moving moving = new Moving();
		moving.setDate(new Date());
		List<MovingLog> movingLogs = new ArrayList<MovingLog>();
		MovingLog movingLog = new MovingLog();

		MovingEntry movingEntry = new MovingEntry();
		movingEntry.setBsp(bspFrom);
		movingEntry.setPermalink("http://example.com");
		movingEntry.setTitle("A test blog");

		movingLog.setFrom(movingEntry);
		movingLog.setToBsp(bspTo);

		movingLogs.add(movingLog);
		moving.setMovingLogs(movingLogs);
		return moving;
	}

	public void testGetToCount() {

		log.debug("start to insert");
		Moving moving = this.buildMoving("from-test-1", "to-test-1");
		this.movingLogDao.insertMoving(moving);

		moving = this.buildMoving("from-test-2", "to-test-2");
		this.movingLogDao.insertMoving(moving);

		moving = this.buildMoving("from-test-3", "to-test-1");
		this.movingLogDao.insertMoving(moving);

		this.transactionManager.commit(this.transactionStatus);

		Map<BSP, Long> fromStatistic = this.movingLogDao.getFromStatistic();
		assertEquals(3, fromStatistic.size());
		assertEquals(1, (long) fromStatistic.get(this.buildBsp("from-test-1")));
		assertEquals(1, (long) fromStatistic.get(this.buildBsp("from-test-2")));
		assertEquals(1, (long) fromStatistic.get(this.buildBsp("from-test-3")));

		Map<BSP, Long> toStatistic = this.movingLogDao.getToStatistic();
		// TODO: replace the fllowing with junit addon.
		assertEquals(2, toStatistic.size());
		assertEquals(2, (long) toStatistic.get(this.buildBsp("to-test-1")));
		assertEquals(1, (long) toStatistic.get(this.buildBsp("to-test-2")));
	}
}
