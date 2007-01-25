/**
 * Created on 2007-1-14 上午02:43:59
 */
package com.redv.blogmover.logging;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

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

	public JFreeChart createChart(XYDataset xydataset, String title,
			String timeAxisLabel, String valueAxisLabel) {
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,
				timeAxisLabel, valueAxisLabel, xydataset, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot
				.getRenderer();
		if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyitemrenderer;
			xylineandshaperenderer.setShapesVisible(true);
			xylineandshaperenderer.setShapesFilled(true);
			xylineandshaperenderer.setItemLabelsVisible(true);
		}
		PeriodAxis periodaxis = new PeriodAxis("Date");
		periodaxis.setTimeZone(TimeZone.getDefault());
		periodaxis.setAutoRangeTimePeriodClass(org.jfree.data.time.Month.class);
		periodaxis.setMajorTickTimePeriodClass(org.jfree.data.time.Month.class);
		PeriodAxisLabelInfo aperiodaxislabelinfo[] = new PeriodAxisLabelInfo[2];
		aperiodaxislabelinfo[0] = new PeriodAxisLabelInfo(
				org.jfree.data.time.Month.class, new SimpleDateFormat("MMM"),
				new RectangleInsets(2D, 2D, 2D, 2D), new Font("SimSun", 1, 10),
				Color.blue, false, new BasicStroke(0.0F), Color.lightGray);// "SansSerif"
		aperiodaxislabelinfo[1] = new PeriodAxisLabelInfo(
				org.jfree.data.time.Year.class, new SimpleDateFormat("yyyy"));
		periodaxis.setLabelInfo(aperiodaxislabelinfo);
		xyplot.setDomainAxis(periodaxis);
		return jfreechart;
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