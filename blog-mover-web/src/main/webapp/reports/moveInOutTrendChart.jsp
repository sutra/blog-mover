<jsp:directive.page contentType="text/html; charset=UTF-8"
	session="false" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<fmt:bundle basename="com.redv.blogmover.messages">
<html>
	<head>
		<title><fmt:message key="blogmover.moveInOutTrendChart" /></title>
	</head>
	<body>
		<div>
			<div>
				<fmt:message key="blogmover.moveInOutTrendChart.description" />
			</div>
			<div>
				<img
					src="${ctx}/chart?jFreeChartBuilder=movedInMinusOutTimesReportChartBuilder"
					alt="Moved in-out times" />
			</div>
		</div>
	</body>
</html>
</fmt:bundle>