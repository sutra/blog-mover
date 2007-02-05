/**
 * Created on 2007-2-5 下午11:27:36
 */
package com.redv.blogmover.logging.report.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class JFreeChartBuilderUtils {
	public static JFreeChart createChart(XYDataset xydataset, String title,
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
