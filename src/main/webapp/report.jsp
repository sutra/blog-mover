<jsp:directive.page contentType="text/html; charset=UTF-8" />
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
	}
	</style>
	</head>
	<body>
		<blog-mover:report />
	</body>
</html>
