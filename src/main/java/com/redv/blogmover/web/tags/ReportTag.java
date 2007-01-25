/**
 * Created on 2007-1-17 下午04:09:26
 */
package com.redv.blogmover.web.tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.redv.blogmover.bsps.BSPNameMessages;
import com.redv.blogmover.logging.BSP;
import com.redv.blogmover.logging.ReportBuilder;

/**
 * @author shutra
 * 
 */
public class ReportTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 744369341150491624L;

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			render();
		} catch (IOException e) {
			throw new JspException(e);
		}
		return super.doEndTag();
	}

	private void render() throws IOException {
		JspWriter out = this.pageContext.getOut();
		out.println("<table cellpadding='0' cellspacing='0' class='report'>");
		out.print("<thead>");
		out.print("<tr>");
		out.print("<th colspan='2'>");
		out.print("搬迁统计");
		out.print("</th>");
		out.print("</tr>");
		out.println("</thead>");
		out.print("<tbody>");
		this.renderLines();
		out.println("</tbody>");
		out.print("<tfoot>");
		out.print("<tr>");
		out.print("<td>");
		out.print("<div class=\"movedIn\" style='width: 60%;'>");
		out.print("搬入次数");
		out.print("</div>");
		out.print("<div class=\"movedOut\" style='width: 60%;'>");
		out.print("搬出次数");
		out.print("</div></td>");
		out.print("<td style='text-align: right;'>");
		out.print(String
				.format("报表生成时间：%1$s</td></tr>", sdf.format(new Date())));
		out.println("</tfoot>");
		out.println("</table>");
	}

	private void renderLines() throws IOException {
		JspWriter out = this.pageContext.getOut();
		ReportBuilder reportBuilder = (ReportBuilder) WebApplicationContextUtils
				.getWebApplicationContext(this.pageContext.getServletContext())
				.getBean("reportBuilder");
		Map<BSP, long[]> statistic = reportBuilder.buildStatistic();
		long max = this.getMax(statistic);
		for (Iterator<BSP> iter = statistic.keySet().iterator(); iter.hasNext();) {
			BSP bsp = iter.next();
			long[] movedInOut = statistic.get(bsp);
			float w0 = (float) movedInOut[0] / (float) max * 100;
			float w1 = (float) movedInOut[1] / (float) max * 100;
			out.print("<tr>");
			out.print("<td style='width: 10%;text-align: right;'>");
			out.print(StringUtils.abbreviate(BSPNameMessages.getString(bsp
					.getId()), 20));
			out.print("</td>");
			out.print("<td style='width: 85%;'>");
			out.print("<div class='movedIn' style='width:" + w0 + "%;'>"
					+ movedInOut[0] + "</div>");
			out.print("<div class='movedOut' style='width:" + w1 + "%;'>"
					+ movedInOut[1] + "</div>");
			out.print("</td>");
			out.println("</tr>");
		}

	}

	private long getMax(Map<BSP, long[]> statistic) {
		long max = 0;
		for (Iterator<long[]> iter = statistic.values().iterator(); iter
				.hasNext();) {
			long[] movedInOut = iter.next();
			if (movedInOut[0] > max) {
				max = movedInOut[0];
			}
			if (movedInOut[1] > max) {
				max = movedInOut[1];
			}
		}
		return max;
	}
}
