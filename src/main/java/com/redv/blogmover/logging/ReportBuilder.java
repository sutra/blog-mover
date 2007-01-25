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

import com.redv.blogmover.bsps.BSPNameMessages;

/**
 * @author shutra
 * 
 */
public class ReportBuilder {
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
	public XYDataset buildDateset(Date beginDate, Date endDate) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
		List<BSP> bsps = loggingService.getBsps();
		Month firstMonth = new Month(beginDate);
		Month lastMonth = new Month(endDate);

		for (BSP bsp : bsps) {
			TimeSeries timeseries = new TimeSeries(BSPNameMessages
					.getString(bsp.getId()), Month.class);
			for (Month currentMonth = firstMonth; currentMonth
					.compareTo(lastMonth) <= 0; currentMonth = (Month) currentMonth
					.next()) {
				timeseries.add(currentMonth, this.loggingService
						.getMovedInCount(bsp.getId(), currentMonth.getStart(),
								currentMonth.getEnd()));
				timeSeriesCollection.addSeries(timeseries);
			}
		}

		return timeSeriesCollection;
	}

	public JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
				"Moving times", "Date", "Times", xydataset, true, true, false);
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
				new RectangleInsets(2D, 2D, 2D, 2D), new Font("SansSerif", 1,
						10), Color.blue, false, new BasicStroke(0.0F),
				Color.lightGray);
		aperiodaxislabelinfo[1] = new PeriodAxisLabelInfo(
				org.jfree.data.time.Year.class, new SimpleDateFormat("yyyy"));
		periodaxis.setLabelInfo(aperiodaxislabelinfo);
		xyplot.setDomainAxis(periodaxis);
		return jfreechart;
	}
}
