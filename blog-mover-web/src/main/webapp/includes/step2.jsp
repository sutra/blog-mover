<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="step2">
	<script type="text/javascript" src="niftycube.js"></script>
	<h3>
		请在下面的列表中删除不需要导入目标 Blog 的日志：
	</h3>
	<div id="webLogs">
		<div>
			<input type="button" id="deleteButton" value="&#x2715;"
				onclick="deleteWebLogs()" title="删除" />
			<input type="button" id="clearButton" value="&#x2716;"
				onclick="clearWebLogs()" title="清空" />
			&nbsp;&nbsp; 页大/总数：
			<input type="text" id="pageSize" value="10"
				style="width: 20px;text-align: right;" />
			/
			<span id="totalCount"> NA </span>
			<input type="button" id="firstButton" value="&#x00AB;"
				onclick="showWebLogs(-2)" title="首页" disabled="disabled" />
			<input type="button" id="previousButton" value="&#x2039;"
				onclick="showWebLogs(-1)" title="上一页" disabled="disabled" />
			<input type="button" id="refreshButton" value="&#x25CC;"
				onclick="showWebLogs(0)" title="刷新" />
			<input type="button" id="nextButton" value="&#x203A;"
				onclick="showWebLogs(1)" title="下一页" disabled="disabled" />
			<input type="button" id="lastButton" value="&#x00BB;"
				onclick="showWebLogs(2)" title="末页" disabled="disabled" />
		</div>
		<div id="webLogsBox">
			<fmt:bundle basename="com.redv.blogmover.messages">
				<table border="0" style="width: 100%;">
					<thead>
						<tr>
							<th style="text-align:left;">
								<input type="checkbox" id="index"
									onclick="checkAll(this.checked)" />
								<label for='index'>
									&nbsp;
									<fmt:message key="No." />
								</label>
							</th>
							<th>
								<fmt:message key="title" />
							</th>
							<th>
								<fmt:message key="publishedDate" />
							</th>
						</tr>
					</thead>
					<tbody id="webLogsTbody">
						<tr>
							<td colspan="3">
								……
							</td>
						</tr>
					</tbody>
				</table>
			</fmt:bundle>
		</div>
		<script type="text/javascript">
		<!--
		Nifty("div#webLogsBox", "small");
		//-->
		</script>
	</div>
	<span style="color: #f00;font:bold 16px/18px Tahoma;"> &#x2020;
	</span> 本页列出了将要导入的日志，在本页所做的删除操作不会影响源 Blog。
</div>
