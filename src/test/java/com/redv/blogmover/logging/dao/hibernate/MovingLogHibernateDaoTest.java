/**
 * Created on 2006-12-25 下午11:36:19
 */
package com.redv.blogmover.logging.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

	private BSP buildSimpleBsp() {
		BSP bsp = new BSP();
		bsp.setId(UUID.randomUUID().toString());
		bsp.setName("xpert");
		bsp.setDescription("xpert.cn is a good BSP.");
		bsp.setServerURL("http://xpert.cn");

		return bsp;
	}

	private MovingEntry buildSimpleMovingEntry() {
		MovingEntry movingEntry = new MovingEntry();
		movingEntry.setBsp(this.buildSimpleBsp());
		movingEntry
				.setPermalink("http://xpert.cn/shutra/entry/my-test-entry);");
		movingEntry.setTitle("My test entry");
		return movingEntry;
	}

	private MovingLog buildSimpleMovingLog() {
		MovingLog movingLog = new MovingLog();
		movingLog.setFrom(this.buildSimpleMovingEntry());
		movingLog.setToBsp(this.buildSimpleBsp());
		return movingLog;
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.logging.dao.hibernate.MovingHibernateDao#InsertBsp(com.redv.blogmover.logging.BSP)}.
	 */
	public void testInsertBsp() {
		BSP bsp = this.buildSimpleBsp();
		String id = this.movingLogDao.insertBsp(bsp);

		this.transactionManager.commit(this.transactionStatus);

		BSP dbBsp = this.movingLogDao.getBsp(id);
		assertEquals(bsp, dbBsp);

		assertEquals(1, this.countRowsInTable("bsp"));

		this.deleteFromTables(new String[] { "bsp" });
	}

	public void testInsertMovingEntry() {
		MovingEntry movingEntry = buildSimpleMovingEntry();

		// this.movingLogDao.insertBsp(movingEntry.getBsp());

		String id = this.movingLogDao.insertMovingEntry(movingEntry);

		this.transactionManager.commit(this.transactionStatus);

		MovingEntry dbMovingEntry = this.movingLogDao.getMovingEntry(id);
		assertEquals(movingEntry, dbMovingEntry);

		assertEquals(1, this.countRowsInTable("moving_entry"));
		assertEquals(1, this.countRowsInTable("bsp"));

		this.deleteFromTables(new String[] { "moving_entry", "bsp" });
	}

	public void testInsertMovingLog() {
		MovingLog movingLog = this.buildSimpleMovingLog();

		// this.movingLogDao.insertBsp(movingLog.getFrom().getBsp());
		// this.movingLogDao.insertMovingEntry(movingLog.getFrom());
		// this.movingLogDao.insertBsp(movingLog.getToBsp());
		String id = this.movingLogDao.insertMovingLog(movingLog);

		this.transactionManager.commit(this.transactionStatus);

		MovingLog dbMovingLog = this.movingLogDao.getMovingLog(id);
		assertEquals(movingLog, dbMovingLog);

		assertEquals(1, this.countRowsInTable("moving_log"));
		assertEquals(1, this.countRowsInTable("moving_entry"));
		assertEquals(2, this.countRowsInTable("bsp"));

		deleteFromTables(new String[] { "moving_log", "moving_entry", "bsp" });
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.logging.dao.hibernate.MovingHibernateDao#insertMoving(com.redv.blogmover.logging.Moving)}.
	 */
	public void testInsertMoving() {
		//
		// Two BSPs.
		//

		// From BSP.
		BSP fromBsp = new BSP();
		fromBsp.setId("from-bsp");

		// To BSP.
		BSP toBsp = new BSP();
		toBsp.setId("to-bsp");

		// Moving entry.
		MovingEntry movingEntry = new MovingEntry();
		movingEntry.setBsp(fromBsp);
		movingEntry.setPermalink("http://xpert.cn/shutra/entry/my-test-entry");
		movingEntry.setTitle("My test entry");

		// Moving log.
		MovingLog movingLog = new MovingLog();
		movingLog.setFrom(movingEntry);
		movingLog.setToBsp(toBsp);

		// Moving logs.
		Set<MovingLog> movingLogs = new HashSet<MovingLog>();
		movingLogs.add(movingLog);

		// Moving.
		Moving moving = new Moving();
		moving.setDate(new Date());
		moving.setMovingLogs(movingLogs);

		assertNull(moving.getId());
		assertEquals(1, moving.getMovingLogs().size());

		// Insert moving.
		String id = this.movingLogDao.insertMoving(moving);
		// Commit.
		this.transactionManager.commit(this.transactionStatus);

		// Check insert OK.
		assertNotNull(moving.getId());

		// Check the object in database is equals to the original one.
		Moving dbMoving = this.movingLogDao.getMoving(id);

		assertEquals(moving.getId(), dbMoving.getId());
		assertEquals(moving.getDate(), dbMoving.getDate());
		assertEquals(moving.getMovingLogs().size(), dbMoving.getMovingLogs()
				.size());
		MovingLog ml = moving.getMovingLogs().iterator().next();
		MovingLog dbMl = dbMoving.getMovingLogs().iterator().next();
		assertEquals(ml, dbMl);
		
		MovingLog selfml = moving.getMovingLogs().iterator().next();
		MovingLog selfdbMl = moving.getMovingLogs().iterator().next();
		assertEquals(selfml, selfdbMl);
		assertEquals(moving.getMovingLogs(), moving.getMovingLogs());
		assertEquals(moving.getMovingLogs(), dbMoving.getMovingLogs());
		assertEquals(moving, dbMoving);
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
		Set<MovingLog> movingLogs = new HashSet<MovingLog>();
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

	public void _testGetFromCount() {

		log.debug("start to insert");
		Moving moving = this.buildMoving("from-test-1", "to-test-1");
		this.movingLogDao.insertMoving(moving);

		moving = this.buildMoving("from-test-2", "to-test-2");
		this.movingLogDao.insertMoving(moving);

		moving = this.buildMoving("from-test-3", "to-test-1");
		this.movingLogDao.insertMoving(moving);

		this.transactionManager.commit(this.transactionStatus);

		Date dateMin = new GregorianCalendar(1970, 1, 1).getTime();
		Calendar c = new GregorianCalendar();
		c.setTime(new Date());
		c.add(Calendar.DATE, 1);
		Date dateMax = c.getTime();

		//
		// Now check.
		//
		assertEquals(1, moving.getMovingLogs().size());

		// Get a moving from db, and check it.
		Moving dbMoving = this.movingLogDao.getMoving(moving.getId());
		assertEquals(1, dbMoving.getMovingLogs().size());

		assertNotNull(this.movingLogDao.getBsp("from-test-1"));
		assertNull(this.movingLogDao.getBsp("not aviable"));

		MovingLog movingLog = (MovingLog) dbMoving.getMovingLogs().toArray()[0];
		assertEquals("from-test-3", movingLog.getFrom().getBsp().getId());
		assertEquals("to-test-1", movingLog.getToBsp().getId());
		assertEquals(moving.getId(), movingLog.getMoving().getId());

		// 
		assertEquals(5, this.countRowsInTable("bsp"));
		assertEquals(3, this.countRowsInTable("moving"));
		assertEquals(3, this.countRowsInTable("movinglog"));
		assertEquals(3, this.countRowsInTable("movingentry"));
		long count = this.movingLogDao.getFromCount("from-test-1");
		assertEquals(1, count);

		// count = this.movingLogDao.getToCount("to-test-1", dateMin, dateMax);
		// assertEquals(2, count);
	}

	public void testSet() {
		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		String string1 = new String("string1");
		String string2 = new String("string2");
		set1.add(string1);
		set1.add(string2);
		set2.add(string2);
		set2.add(string1);

		assertEquals(set1, set2);
	}
}
