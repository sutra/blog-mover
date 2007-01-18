/**
 * Created on 2007-1-17 下午04:09:26
 */
package com.redv.blogmover.web.tags;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
		out
				.println("<table style=\"width: 100%;border: 1px solid #333;background-color: #eee;\">");
		out.println("<thead>");
		out.println("<tr>");
		out.println("<th>");
		out.println("搬迁统计");
		out.println("</th>");
		out.println("</tr>");
		out.println("</thead>");
		out.println("<tbody>");
		this.renderLines();
		out.println("</tbody>");
		out.println("<tfoot>");
		out.println("<tr>");
		out.println("<td>");
		out
				.println("<div class=\"movedIn\" style=\"width: 40px;display: inline;\">");
		out.println("&nbsp;");
		out.println("</div>");
		out.println("搬入次数");
		out
				.println("<div class=\"movedOut\" style=\"width: 40px;display: inline;\">");
		out.println("&nbsp;");
		out.println("</div>");
		out.println("搬出次数");
		out.println("</td>");
		out.println("</tr>");
		out.println(String.format("<tr><td>报表生成时间：%1$s</td></tr>", DateFormat
				.getDateTimeInstance().format(new Date())));
		out.println("</tfoot>");
		out.println("</table>");
	}

	private void renderLines() throws IOException {
		JspWriter out = this.pageContext.getOut();
		ReportBuilder reportBuilder = (ReportBuilder) WebApplicationContextUtils
				.getWebApplicationContext(this.pageContext.getServletContext())
				.getBean("reportBuilder");
		Map<BSP, long[]> statistic = reportBuilder.buildStatistic();
		long[] max = this.getMax(statistic);
		for (Iterator<BSP> iter = statistic.keySet().iterator(); iter.hasNext();) {
			BSP bsp = iter.next();
			long[] movedInOut = statistic.get(bsp);
			float w0 = (float) movedInOut[0] / (float) max[0] * 100;
			float w1 = (float) movedInOut[1] / (float) max[1] * 100;
			out.print("<tr>");
			out.print("<td style='width: 10%;'>");
			out.print(StringUtils.abbreviate(BSPNameMessages.getString(bsp
					.getId()), 40));
			out.print("</td>");
			out.print("<td>");
			out.print("<div class='movedIn' style='width:" + w0
					+ "%;'>&nbsp;</div>");
			out.print("<div class='movedOut' style='width:" + w1
					+ "%;'>&nbsp;</div>");
			out.print("</td>");
			out.print("<td>" + movedInOut[0] + "<br />" + movedInOut[1]
					+ "</td>");
			out.print("</tr>\n");
		}

	}

	private long[] getMax(Map<BSP, long[]> statistic) {
		long[] max = new long[2];
		for (Iterator<long[]> iter = statistic.values().iterator(); iter
				.hasNext();) {
			long[] movedInOut = iter.next();
			if (movedInOut[0] > max[0]) {
				max[0] = movedInOut[0];
			}
			if (movedInOut[1] > max[1]) {
				max[1] = movedInOut[1];
			}
		}
		return max;
	}
}
