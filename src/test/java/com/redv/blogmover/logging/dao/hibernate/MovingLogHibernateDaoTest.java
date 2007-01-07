/**
 * Created on 2006-12-25 下午11:36:19
 */
package com.redv.blogmover.logging.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.redv.blogmover.logging.BSP;
import com.redv.blogmover.logging.Moving;
import com.redv.blogmover.logging.MovingLog;
import com.redv.blogmover.logging.dao.MovingLogDao;
import com.redv.blogmover.test.TestUtils;

/**
 * @author shutra
 * 
 */
public class MovingLogHibernateDaoTest extends
		AbstractTransactionalDataSourceSpringContextTests {

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
}
