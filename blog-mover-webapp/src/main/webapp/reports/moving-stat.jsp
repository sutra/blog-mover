<jsp:directive.page contentType="text/html; charset=UTF-8"
	session="false" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache" %>
<%@ taglib prefix="blog-mover" uri="http://blogmover.redv.com/"%>
<fmt:bundle basename="com.redv.blogmover.messages">
<html>
	<head>
		<title><fmt:message key="blogmover.movingStat" /></title>
	</head>
	<body>
		<div>
			<%-- 2592000 = 30天 * 24小时 * 60分 * 60秒 --%>
			<cache:cache key="reports.moving-stat.jsp.report" time="2592000">
				<blog-mover:report />
			</cache:cache>
		</div>
	</body>
</html>
</fmt:bundle>