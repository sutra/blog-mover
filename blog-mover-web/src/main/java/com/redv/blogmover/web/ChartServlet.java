/**
 * 
 */
package com.redv.blogmover.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.redv.blogmover.logging.report.jfreechart.JFreeChartBuilder;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class ChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8262682118227561745L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

	private static final Log log = LogFactory.getLog(ChartServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String chartBuilder = StringUtils.defaultIfEmpty(request
				.getParameter("jFreeChartBuilder"),
				"movedInMinusOutTimesReportChartBuilder");
		String beginDateString = request.getParameter("beginDate");
		String endDateString = request.getParameter("endDate");
		Date beginDate = null, endDate = null;
		if (beginDateString != null) {
			try {
				beginDate = sdf.parse(beginDateString);
			} catch (ParseException e) {
			}
		}
		if (endDateString != null) {
			try {
				endDate = sdf.parse(endDateString);
			} catch (ParseException e) {
			}
		}
		if (beginDate == null && endDate == null) {
			Calendar cal = Calendar.getInstance();
			endDate = cal.getTime();
			cal.add(Calendar.MONTH, -24);
			beginDate = cal.getTime();
		} else if (beginDate == null && endDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.MONTH, -24);
			beginDate = cal.getTime();
		} else if (beginDate != null && endDate == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			cal.add(Calendar.MONTH, 24);
			endDate = cal.getTime();
		}

		log.debug("startDate: " + beginDate);
		log.debug("endDate: " + endDate);

		JFreeChartBuilder jFreeChartBuilder = (JFreeChartBuilder) WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext()).getBean(
						chartBuilder);
		JFreeChart chart = jFreeChartBuilder.build(beginDate, endDate);

		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 800,
				600);
	}
}
