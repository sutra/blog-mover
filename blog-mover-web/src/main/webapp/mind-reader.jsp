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
		<title>:::读心术—心理测试:::</title>
		<meta http-equiv="Content-Type"
			content="application/xhtml-xml; charset=UTF-8" />
		<meta name="robots" content="all" />
		<meta name="keywords" content="读心术, 心理测试, 吉普赛人" />
		<meta name="description" content="吉普赛人祖传的神奇读心术.它能测算出你的内心感应" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="styles/nicetabs.css" />
		<style type="text/css">
		@import url("styles/blog-remover.css");
		</style>
	</head>
	<body>
		<div>
				<h1>
					:::读心术—心理测试:::
				</h1>
				“吉普赛人祖传的神奇读心术.它能测算出你的内心感应” 提示：
				<p>
					任意选择一个两位数（或者说，从10~99之间任意选择一个数），把这个数的十位与个位相加，再把任意选择的数减去这个和。例如：你选的数是23，然后2+3=5，然后23-5=18。
				</p>
				<p>
					在图表中找出与最后得出的数所相应的图形，并把这个图形牢记心中，然后点击水晶球。你会发现，水晶球所显示出来的图形就是你刚刚心里记下的那个图形。
				</p>
				<p>
					如果你把这个读心术的网址复制给10个或更多的朋友，也许你就会明白一切，因为水晶球会让你神奇的感应到它是如何来读你的心！
				</p>
			<div style="margin: 10px;">
				<object
					codeBase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0
					height=500 width=698 align=absMiddle
					classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000>
					<PARAM NAME="movie" VALUE="mind-reader.swf">
					<PARAM NAME="quality" VALUE="high">
					<embed src="mind-reader.swf" quality=high
						pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"
						type="application/x-shockwave-flash" width="698" height="500"
						align="absmiddle"> </embed>
				</object>
			</div>
			<jsp:directive.include file="includes/analytics.jsp" />
			<jsp:directive.include file="includes/footer.jsp" />
		</div>
	</body>
</html>
