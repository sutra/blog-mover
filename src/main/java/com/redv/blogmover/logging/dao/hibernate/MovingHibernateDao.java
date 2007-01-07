/**
 * Created on 2006-12-25 上午12:35:23
 */
package com.redv.blogmover.logging.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.redv.blogmover.logging.BSP;
import com.redv.blogmover.logging.Moving;
import com.redv.blogmover.logging.MovingEntry;
import com.redv.blogmover.logging.MovingLog;
import com.redv.blogmover.logging.dao.MovingLogDao;

/**
 * @author shutra
 * 
 */
public class MovingHibernateDao extends HibernateDaoSupport implements
		MovingLogDao {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(MovingHibernateDao.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getBsp(java.lang.String)
	 */
	public BSP getBsp(String id) {
		return (BSP) this.getHibernateTemplate().get(BSP.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getMovingEntry(java.lang.String)
	 */
	public MovingEntry getMovingEntry(String id) {
		return (MovingEntry) this.getHibernateTemplate().get(MovingEntry.class,
				id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getMovingLog(java.lang.String)
	 */
	public MovingLog getMovingLog(String id) {
		return (MovingLog) this.getHibernateTemplate().get(MovingLog.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getMoving(java.lang.String)
	 */
	public Moving getMoving(String id) {
		return (Moving) this.getHibernateTemplate().get(Moving.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#insertMoving(com.redv.blogmover.logging.Moving)
	 */
	public String insertMoving(Moving moving) {
		String id = (String) this.getHibernateTemplate().save(moving);

		List<MovingLog> movingLogs = moving.getMovingLogs();
		if (movingLogs != null) {
			for (MovingLog movingLog : movingLogs) {
				addBsp(movingLog.getToBsp());
				if (movingLog.getFrom() != null) {
					addBsp(movingLog.getFrom().getBsp());
				}
				this.getHibernateTemplate().save(movingLog.getFrom());
				this.getHibernateTemplate().save(movingLog);
			}
		}

		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#insertBsp(com.redv.blogmover.logging.BSP)
	 */
	public String insertBsp(BSP bsp) {
		return (String) this.getHibernateTemplate().save(bsp);
	}

	private void addBsp(BSP bsp) {
		if (bsp != null && this.getBsp(bsp.getId()) == null) {
			this.insertBsp(bsp);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getFromStatistic()
	 */
	public Map<BSP, Long> getFromStatistic() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getToStatistic()
	 */
	@SuppressWarnings("unchecked")
	public Map<BSP, Long> getToStatistic() {
		String hql = "select toBsp.id, count(toBsp) from MovingLog group by toBsp";
		Map<BSP, Long> ret = new HashMap<BSP, Long>();
		List<Object[]> counts = (List<Object[]>) this.getHibernateTemplate()
				.find(hql);
		for (Object[] count : counts) {
			ret.put(this.getBsp((String) count[0]), (Long) count[1]);
		}
		return ret;
	}
}
