/**
 * Created on 2006-12-25 上午12:35:23
 */
package com.redv.blogmover.logging.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
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
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getBsps()
	 */
	@SuppressWarnings("unchecked")
	public List<BSP> getBsps() {
		return this.getHibernateTemplate().find("from BSP");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#insertBsp(com.redv.blogmover.logging.BSP)
	 */
	public String insertBsp(BSP bsp) {
		return (String) this.getHibernateTemplate().save(bsp);
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
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#insertMovingEntry(com.redv.blogmover.logging.MovingEntry)
	 */
	public String insertMovingEntry(MovingEntry movingEntry) {
		return (String) this.getHibernateTemplate().save(movingEntry);
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
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#insertMovingLog(com.redv.blogmover.logging.MovingLog)
	 */
	public String insertMovingLog(MovingLog movingLog) {
		return (String) this.getHibernateTemplate().save(movingLog);
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
		return (String) this.getHibernateTemplate().save(moving);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getFromCount(java.lang.String)
	 */
	public long getFromCount(String id) {
		String hql = "select count(*) from Moving where id in(select moving.id from MovingLog where fromEntry.bsp.id = :id)";
		Query query = this.getSession().createQuery(hql);
		query.setString("id", id);
		return (Long) query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getFromCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getFromCount(String id, Date beginDate, Date endDate) {
		String hql = "select count(*) from Moving where id in(select moving.id from MovingLog where fromEntry.bsp.id = :id) and date between :beginDate and :endDate";
		Query query = this.getSession().createQuery(hql);
		query.setString("id", id);
		return (Long) query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getToCount(java.lang.String)
	 */
	public long getToCount(String id) {
		String hql = "select count(*) from Moving where id in (select moving.id from MovingLog where toBsp.id = :id)";
		Query query = this.getSession().createQuery(hql);
		query.setString("id", id);
		return (Long) query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.dao.MovingLogDao#getToCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getToCount(String id, Date beginDate, Date endDate) {
		String hql = "select count(*) from Moving where id in(select moving.id from MovingLog where toBsp.id = :id) and date between :beginDate and :endDate";
		Query query = this.getSession().createQuery(hql);
		query.setString("id", id);
		query.setDate("beginDate", beginDate);
		query.setDate("endDate", endDate);
		return (Long) query.uniqueResult();
	}

}
