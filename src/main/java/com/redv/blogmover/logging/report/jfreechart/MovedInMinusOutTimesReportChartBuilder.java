/**
 * Created on 2007-2-5 下午10:54:10
 */
package com.redv.blogmover.logging.report.jfreechart;

import java.util.Date;

import org.jfree.chart.JFreeChart;

import com.redv.blogmover.logging.ReportBuilder;

/**
 * @author shutra
 * 
 */
public class MovedInMinusOutTimesReportChartBuilder implements
		JFreeChartBuilder {
	private ReportBuilder reportBuilder;

	/**
	 * @param reportBuilder
	 *            the reportBuilder to set
	 */
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.report.JFreeChartBuilder#build(java.util.Date,
	 *      java.util.Date)
	 */
	public JFreeChart build(Date beginDate, Date endDate) {
		JFreeChart chart = JFreeChartBuilderUtils.createChart(reportBuilder
				.buildMovedInMinusOutDateset(beginDate, endDate),
				"Moved in-out times", "Date", "Times");

		return chart;
	}

}
