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
			<blog-mover:report />
			<hr />
			<div>
				<img
					src="chart?jFreeChartBuilder=movedInTimesReportChartBuilder"
					alt="Moved in times" />
			</div>
			<hr />
			<div>
				<img src="chart?jFreeChartBuilder=movedOutTimesReportChartBuilder"
					alt="Moved out times" />
			</div>
			<hr />
			<div>
				<img
					src="chart?jFreeChartBuilder=movedInMinusOutTimesReportChartBuilder"
					alt="Moved in-out times" />
			</div>
		</div>
	</body>
</html>
