<jsp:directive.page contentType="text/html; charset=UTF-8"
	session="false" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="blog-mover" uri="http://blogmover.redv.com/"%>
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
	display: block;
	text-align: right;
	}
	.report {
	margin: 0px;
	padding: 0px;
	width: 100%;
	border: 1px solid #333;
	background-color: #eee;
	}
	.report th, td{
	margin: 0px;
	padding: 0px;
	border-bottom: 1px solid #333;
	}
	</style>
	</head>
	<body>
		<div>
			<h2>
				搬迁统计
			</h2>
			<blog-mover:report />
			<hr />
			<h2>
				搬入趋势图
			</h2>
			<div>
				<img src="chart?jFreeChartBuilder=movedInTimesReportChartBuilder"
					alt="Moved in times" />
			</div>
			<hr />
			<h2>
				搬出趋势图
			</h2>
			<div>
				<img src="chart?jFreeChartBuilder=movedOutTimesReportChartBuilder"
					alt="Moved out times" />
			</div>
			<hr />
			<h2>
				搬入搬出趋势图
			</h2>
			<div>
				搬入次数减去搬出次数得出的结果画出的趋势图
			</div>
			<div>
				<img
					src="chart?jFreeChartBuilder=movedInMinusOutTimesReportChartBuilder"
					alt="Moved in-out times" />
			</div>
		</div>
	</body>
</html>
