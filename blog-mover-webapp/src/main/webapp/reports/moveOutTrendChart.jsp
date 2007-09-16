<jsp:directive.page contentType="text/html; charset=UTF-8"
	session="false" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<fmt:bundle basename="com.redv.blogmover.messages">
<html>
	<head>
		<title><fmt:message key="blogmover.moveOutTrendChart" /></title>
	</head>
	<body>
		<div>
			<img src="${ctx}/chart?jFreeChartBuilder=movedOutTimesReportChartBuilder"
				alt="Moved out times" />
		</div>
	</body>
</html>
</fmt:bundle>