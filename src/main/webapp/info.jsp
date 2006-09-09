<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
	<head>
		<title>Server Info</title>
	</head>
	<body>
		<%@ page import="java.util.*"%>
		<table border="1">
			<tr>
				<td align="right">
					new java.io.File("./").getAbsolutePath():
				</td>
				<td>
					<%=new java.io.File("./").getAbsolutePath()%>
				</td>
			</tr>
			<tr>
				<td align="right">
					request.getRealPath("."):
				</td>
				<td>
					<%=request.getRealPath(".")%>
				</td>
			</tr>

			<%
				java.util.Properties props = System.getProperties();
				java.util.Set keySet = props.keySet();
				for (Iterator iter = keySet.iterator(); iter.hasNext();) {
					String key = iter.next().toString();
					out.print("<tr><td align='right'>");
					out.print(key);
					out.print("</td><td>");
					out.print(props.get(key));
					out.println("</td></tr>");
				}
			%>
		</table>
	</body>
</html>
