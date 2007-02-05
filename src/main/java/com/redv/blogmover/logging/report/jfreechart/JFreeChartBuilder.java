/**
 * Created on 2007-2-5 下午10:49:56
 */
package com.redv.blogmover.logging.report.jfreechart;

import java.util.Date;

import org.jfree.chart.JFreeChart;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public interface JFreeChartBuilder {
	JFreeChart build(Date beginDate, Date endDate);
}
