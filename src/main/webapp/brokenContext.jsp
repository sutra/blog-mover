<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${param['hl'] != null}">
	<fmt:setLocale value="${param['hl']}" scope="session" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
	<head>
		<title>Blog Mover （博客“搬家公司”、博客搬家通用工具、博客搬家服务、博客备份通用工具）</title>
		<meta http-equiv="Content-Type"
			content="application/xhtml-xml; charset=UTF-8" />
		<meta name="robots" content="all" />
		<meta name="keywords"
			content="blog, weblog, 博客, mover, remover, 搬家, 备份, 博客搬家, 博客搬家通用工具, 博客搬家服务, 博客搬家软件" />
		<meta name="description"
			content="Blog Mover （博客搬家通用工具）可以帮你的博客搬家（备份），可以在任意 BSP 之前自由地搬迁。" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="styles/nicetabs.css" />
		<style type="text/css">
		@import url("styles/blog-remover.css");
		</style>
		<jsp:directive.include file="includes/scripts.jsp" />
	</head>
	<body id="nicetabs">
		<div id="welcome">
			<h2>
				Blog Mover is Unavailable
			</h2>

			<p>
				There was a fatal error initializing the Blog Mover application
				context. This is almost always because of an error in the Spring
				bean configuration files. Are the files valid XML? Do the beans they
				refer to all exist?
				<br />
				<br />
				Before placing Blog Mover in production, you should change this page
				to present a UI appropriate for the case where the Blog Mover web
				application is fundamentally broken. Perhaps "Sorry, Blog Mover is
				currently unavailable." with some links to your user support
				information.
			</p>

			<c:if
				test="${not empty applicationScope.exceptionCaughtByServlet and empty applicationScope.exceptionCaughtByListener}">
				<p>
					The Throwable representing the fatal error has been logged by the
					<em>SafeDispatcherServlet</em> via Commons Logging, via
					ServletContext logging, and to System.err.
				</p>
			</c:if>

			<c:if
				test="${empty applicationScope.exceptionCaughtByServlet and not empty applicationScope.exceptionCaughtByListener}">
				<p>
					The Throwable representing the fatal error has been logged by the
					SafeContextLoaderListener via Commons Logging, via ServletContext
					logging, and to System.err.
				</p>
			</c:if>

			<!-- Render information about the throwables themselves -->

			<c:if test="${not empty applicationScope.exceptionCaughtByListener}">
				<p>
					The Throwable encountered at context listener initialization was:
					<br />
					<br />
					<c:out value="${applicationScope.exceptionCaughtByListener}" />
				</p>
			</c:if>

			<c:if test="${not empty applicationScope.exceptionCaughtByServlet}">
				<p>
					The Throwable encountered at dispatcher servlet initialization was:
					<br />
					<br />
					<c:out value="${applicationScope.exceptionCaughtByServlet}" />
				</p>
			</c:if>
		</div>
	</body>
</html>

