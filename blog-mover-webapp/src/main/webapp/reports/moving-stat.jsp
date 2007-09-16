<jsp:directive.page contentType="text/html; charset=UTF-8"
	session="false" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="blog-mover" uri="http://blogmover.redv.com/"%>
<fmt:bundle basename="com.redv.blogmover.messages">
<html>
	<head>
		<title><fmt:message key="blogmover.movingStat" /></title>
	</head>
	<body>
		<div>
			<blog-mover:report />
		</div>
	</body>
</html>
</fmt:bundle>