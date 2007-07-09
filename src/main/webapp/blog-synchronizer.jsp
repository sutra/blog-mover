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
			content="blog, weblog, 博客, mover, remover, 搬家, 备份, 博客搬家, 博客搬家通用工具, 博客搬家服务, 博客搬家软件, metaWeblog, metaWeblogApi" />
		<meta name="description"
			content="Blog Mover （博客搬家通用工具）可以帮你的博客搬家（备份），可以在任意 BSP 之前自由地搬迁。" />
		<%-- FavIcon from Pics: http://www.chami.com/html-kit/services/favicon/ --%>
		<link rel="shortcut icon" href="<c:url value="/favicon.ico" />"
			type="image/x-icon" />
		<link rel="icon" href="<c:url value="/favicon.ico" />"
			type="image/x-icon" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="styles/nicetabs.css" />
		<style type="text/css">
		@import url("styles/blog-mover.css");
		</style>
		<jsp:directive.include file="includes/sync/scripts.jsp" />
	</head>
	<body id="nicetabs">
		<div>
			<noscript>
				<div class="noscriptError">
					应启用 JavaScript，才能使用 Blog Mover。然而，JavaScript 似乎已被禁用，要么就是您的浏览器不支持
					JavaScript。要使用 Blog Mover，请更改您的浏览器选项以启用 JavaScript，然后重试。
				</div>
			</noscript>
			<div id="blogMoverHeader">
				<h1>
					Blog Synchronizer （博客搬家同步工具）
				</h1>
			</div>
			<jsp:directive.include file="includes/sync/menu.jsp" />
			<div style="margin: 10px;">
				<jsp:directive.include file="includes/sync/step1.jsp" />
				<jsp:directive.include file="includes/sync/step2.jsp" />
				<jsp:directive.include file="includes/sync/step3.jsp" />
				<jsp:directive.include file="includes/option.jsp" />
				<jsp:directive.include file="includes/about.jsp" />
			</div>
			<script type="text/javascript">
			<!--
			var selectedTab = getCookie("selectedTab");
			if (selectedTab != null && selectedTab != '') {
				selectTab(selectedTab);
			} else {
				selectTab('about');
			}
			//-->
			</script>
			<jsp:directive.include file="includes/status-bar.jsp" />
			<jsp:directive.include file="includes/debug.jsp" />
			<jsp:directive.include file="includes/analytics.jsp" />
			<jsp:directive.include file="includes/footer.jsp" />
			<jsp:directive.include file="includes/ads.jsp" />
		</div>
	</body>
</html>