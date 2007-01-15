<jsp:directive.page contentType="text/html; charset=UTF-8" />
<jsp:directive.page
	import="org.springframework.web.context.support.WebApplicationContextUtils" />
<jsp:directive.page import="com.redv.blogmover.logging.ReportBuilder" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="com.redv.blogmover.logging.BSP" />
<jsp:directive.page import="org.apache.commons.lang.StringUtils" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
	<head>
		<title>搬迁报告 - Blog Mover</title>
		<style>
	.movedIn {
	background-color:green;
	}
	.movedOut {
	background-color:red;
	}
	.movedIn, .movedOut {
	}
	</style>
	</head>
	<body>
		<table style="border: 1px solid #333;background-color: #eee;">
			<thead>
				<tr>
					<th>
						搬迁统计
					</th>
				</tr>
			</thead>
			<tbody>
			<fmt:bundle basename="com.redv.blogmover.bsps.messages-bsp-names">
				<%
					ReportBuilder reportBuilder = (ReportBuilder) WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("reportBuilder");
					Map<BSP, long[]> statistic = reportBuilder.buildStatistic();
					for (Iterator<BSP> iter = statistic.keySet().iterator(); iter.hasNext(); ) {
						BSP bsp = iter.next();
						request.setAttribute("bsp", bsp);
						long[] movedInOut = statistic.get(bsp);
						long w0 = movedInOut[0];
						long w1 = movedInOut[1];
						out.print("<tr>");
						out.print("<td>");
						%>
						<fmt:message key="com.redv.blogmover.bsps.${bsp.id}" />
						<%
						out.print("</td>");
						out.print("<td>");
						out.print("<div class='movedIn' style='width:" + w0  + "px;'>&nbsp;</div>");
						out.print("<div class='movedOut' style='width:" + w1 + "px;'>&nbsp;</div>");
						out.print("</td>");
						out.print("<td>" + w0 + "<br />" + w1 + "</td>");
						out.print("</tr>\n");
						}
				%>
				</fmt:bundle>
			</tbody>
			<tfoot>
				<tr>
					<td>
						<div class="movedIn" style="width: 40px;display: inline;">
							&nbsp;
						</div>
						搬入次数
						<div class="movedOut" style="width: 40px;display: inline;">
							&nbsp;
						</div>
						搬出次数
					</td>
				</tr>
			</tfoot>
		</table>
	</body>
</html>
