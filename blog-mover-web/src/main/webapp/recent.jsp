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
		<title>最近搬迁的日志 - Blog Mover
			（博客“搬家公司”、博客搬家通用工具、博客搬家服务、博客备份通用工具）</title>
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
		@import url("styles/blog-mover.css");
		</style>
	</head>
	<body id="nicetabs">
		<div>
			<h1>
				最近搬迁的日志
			</h1>
			<a href="./">返回</a>
			<jsp:useBean id="cache" class="com.redv.blogmover.RecentWebLogsCache"
				scope="page"></jsp:useBean>
			<ol>
				<c:forEach items="${cache.all}" var="webLog">
					<li>
						<a href="${webLog.url}">${webLog.title}</a>
					</li>
				</c:forEach>
			</ol>
		</div>
	</body>
</html>
