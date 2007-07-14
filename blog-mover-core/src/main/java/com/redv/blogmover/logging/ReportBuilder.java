/**
 * Created on 2007-1-14 上午02:43:59
 */
package com.redv.blogmover.logging;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * @author shutra
 * 
 */
public class ReportBuilder {
	private static final Log log = LogFactory.getLog(ReportBuilder.class);

	private LoggingService loggingService;

	/**
	 * @param loggingService
	 *            the loggingService to set
	 */
	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	public Map<BSP, long[]> buildStatistic() {
		List<BSP> bsps = loggingService.getBsps();
		Map<BSP, long[]> statistic = new HashMap<BSP, long[]>(bsps.size());
		for (int i = 0; i < bsps.size(); i++) {
			long[] movedInOut = new long[2];
			BSP bsp = bsps.get(i);
			movedInOut[0] = loggingService.getMovedInCount(bsp.getId());
			movedInOut[1] = loggingService.getMovedOutCount(bsp.getId());
			statistic.put(bsp, movedInOut);
		}
		return statistic;
	}

	/**
	 * Build a dataset for jfreechart.
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public XYDataset buildMovedInDataset(Date beginDate, Date endDate) {
		return this.buildDataset(beginDate, endDate, new MovedInDataReader(
				this.loggingService));
	}

	/**
	 * Build a dataset for jfreechart.
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public XYDataset buildMovedOutDataset(Date beginDate, Date endDate) {
		return this.buildDataset(beginDate, endDate, new MovedOutDataReader(
				this.loggingService));
	}

	public XYDataset buildMovedInMinusOutDateset(Date beginDate, Date endDate) {
		return this.buildDataset(beginDate, endDate,
				new MovedInMinusOutDataReader(this.loggingService));
	}

	private XYDataset buildDataset(Date beginDate, Date endDate,
			DataReader dataReader) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
		List<BSP> bsps = loggingService.getBsps();
		Month firstMonth = new Month(beginDate);
		Month lastMonth = new Month(endDate);

		log.debug(firstMonth);
		log.debug(lastMonth);

		for (BSP bsp : bsps) {
			TimeSeries timeseries = new TimeSeries(bsp.getId(), Month.class);
			for (Month currentMonth = firstMonth; currentMonth
					.compareTo(lastMonth) <= 0; currentMonth = (Month) currentMonth
					.next()) {
				timeseries.add(currentMonth, dataReader.getCount(bsp.getId(),
						currentMonth.getStart(), currentMonth.getEnd()));
			}
			timeSeriesCollection.addSeries(timeseries);
		}

		return timeSeriesCollection;
	}

}

interface DataReader {
	long getCount(String bspId, Date beginDate, Date endDate);
}

class MovedInDataReader implements DataReader {
	private LoggingService loggingService;

	public MovedInDataReader(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.DataReader#getCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getCount(String bspId, Date beginDate, Date endDate) {
		return this.loggingService.getMovedInCount(bspId, beginDate, endDate);
	}

}

class MovedOutDataReader implements DataReader {
	private LoggingService loggingService;

	public MovedOutDataReader(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.DataReader#getCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getCount(String bspId, Date beginDate, Date endDate) {
		return this.loggingService.getMovedOutCount(bspId, beginDate, endDate);
	}
}

class MovedInMinusOutDataReader implements DataReader {
	private LoggingService loggingService;

	public MovedInMinusOutDataReader(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.DataReader#getCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getCount(String bspId, Date beginDate, Date endDate) {
		return this.loggingService.getMovedInCount(bspId, beginDate, endDate)
				- this.loggingService.getMovedOutCount(bspId, beginDate,
						endDate);
	}
}