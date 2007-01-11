/**
 * Created on 2006-12-25 下午11:36:19
 */
package com.redv.blogmover.logging.dao.hibernate;

import java.util.Date;
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
	@SuppressWarnings("unused")
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
	 * {@link com.redv.blogmover.logging.dao.hibernate.MovingHibernateDao#InsertBsp(com.redv.blogmover.logging.BSP)}.
	 */
	public void testInsertBsp() {
		BSP bsp = new BSP();
		bsp.setId(UUID.randomUUID().toString());
		bsp.setName("xpert");
		bsp.setDescription("xpert.cn is a good BSP.");
		bsp.setServerURL("http://xpert.cn");

		String id = this.movingLogDao.insertBsp(bsp);

		this.transactionManager.commit(this.transactionStatus);

		BSP dbBsp = this.movingLogDao.getBsp(id);
		assertEquals(bsp, dbBsp);

		assertEquals(1, this.countRowsInTable("bsp"));

		this.deleteFromTables(new String[] { "bsp" });
	}

	public void testDuplicateInsert() {
		BSP bsp = new BSP();
		bsp.setId("test");
		bsp.setName("xpert");
		bsp.setDescription("xpert.cn is a good BSP.");
		bsp.setServerURL("http://xpert.cn");

		this.movingLogDao.insertBsp(bsp);

		this.transactionManager.commit(this.transactionStatus);

		try {
			this.movingLogDao.insertBsp(bsp);
			fail("Should catch a exception.");
		} catch (Exception ex) {
			// Do nothing.
		}

		assertEquals(1, this.countRowsInTable("bsp"));

		this.deleteFromTables(new String[] { "bsp" });
	}

	public void testInsertMovingEntry() {
		BSP bsp = new BSP();
		bsp.setId(UUID.randomUUID().toString());
		bsp.setName("xpert");
		bsp.setDescription("xpert.cn is a good BSP.");
		bsp.setServerURL("http://xpert.cn");
		this.movingLogDao.insertBsp(bsp);

		MovingEntry movingEntry = new MovingEntry();
		movingEntry.setBsp(bsp);
		movingEntry
				.setPermalink("http://xpert.cn/shutra/entry/my-test-entry);");
		movingEntry.setTitle("My test entry");
		String id = this.movingLogDao.insertMovingEntry(movingEntry);

		this.transactionManager.commit(this.transactionStatus);

		MovingEntry dbMovingEntry = this.movingLogDao.getMovingEntry(id);
		assertEquals(movingEntry, dbMovingEntry);

		assertEquals(1, this.countRowsInTable("moving_entry"));
		assertEquals(1, this.countRowsInTable("bsp"));

		this.deleteFromTables(new String[] { "moving_entry", "bsp" });
	}

	public void testInsertMovingLog() {
		BSP bsp = new BSP();
		bsp.setId(UUID.randomUUID().toString());
		bsp.setName("xpert");
		bsp.setDescription("xpert.cn is a good BSP.");
		bsp.setServerURL("http://xpert.cn");
		this.movingLogDao.insertBsp(bsp);

		MovingEntry movingEntry = new MovingEntry();
		movingEntry.setBsp(bsp);
		movingEntry
				.setPermalink("http://xpert.cn/shutra/entry/my-test-entry);");
		movingEntry.setTitle("My test entry");
		this.movingLogDao.insertMovingEntry(movingEntry);

		BSP anotherBsp = new BSP();
		anotherBsp.setId(UUID.randomUUID().toString());
		anotherBsp.setName("xpert");
		anotherBsp.setDescription("xpert.cn is a good BSP.");
		anotherBsp.setServerURL("http://xpert.cn");
		this.movingLogDao.insertBsp(anotherBsp);

		MovingLog movingLog = new MovingLog();
		movingLog.setFromEntry(movingEntry);
		movingLog.setToBsp(anotherBsp);

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
		this.movingLogDao.insertBsp(fromBsp);

		// To BSP.
		BSP toBsp = new BSP();
		toBsp.setId("to-bsp");
		this.movingLogDao.insertBsp(toBsp);

		// Moving entry.
		MovingEntry movingEntry = new MovingEntry();
		movingEntry.setBsp(fromBsp);
		movingEntry.setPermalink("http://xpert.cn/shutra/entry/my-test-entry");
		movingEntry.setTitle("My test entry");
		this.movingLogDao.insertMovingEntry(movingEntry);

		// Moving log.
		MovingLog movingLog = new MovingLog();
		movingLog.setFromEntry(movingEntry);
		movingLog.setToBsp(toBsp);
		this.movingLogDao.insertMovingLog(movingLog);

		// Moving logs.
		Set<MovingLog> movingLogs = new HashSet<MovingLog>();
		movingLogs.add(movingLog);

		// Moving.
		Moving moving = new Moving();
		moving.setDate(new Date());
		moving.setMovingLogs(movingLogs);

		assertNull(moving.getId());
		assertEquals(1, moving.getMovingLogs().size());
		assertEquals(moving.getMovingLogs(), moving.getMovingLogs());

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

		assertTrue(moving.getMovingLogs() == moving.getMovingLogs());

		Set set1 = moving.getMovingLogs();
		Set set2 = moving.getMovingLogs();

		assertEquals(1, set1.size());
		assertEquals(1, set2.size());

		MovingLog ml1 = (MovingLog) set1.iterator().next();
		MovingLog ml2 = (MovingLog) set2.iterator().next();
		assertEquals(ml1, ml2);
		assertEquals(ml1.hashCode(), ml2.hashCode());
		assertEquals(ml1.hashCode(), ml1.hashCode());

		assertTrue(set1.contains(ml1));
		assertTrue(set1.contains(ml2));

		assertEquals(moving.getMovingLogs(), moving.getMovingLogs());
		assertEquals(moving.getMovingLogs(), dbMoving.getMovingLogs());
		assertEquals(moving, dbMoving);
	}

	public void testGetFromCount() {
		assertEquals(0, this.movingLogDao.getFromCount("test"));

	}

	public void testGetToCount() {
		assertEquals(0, this.movingLogDao.getToCount("test"));
	}

}
