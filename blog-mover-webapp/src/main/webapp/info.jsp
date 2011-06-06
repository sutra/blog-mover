<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
	<head>
		<title>Server Info</title>
	</head>
	<body>
		<%@ page import="java.util.*, org.apache.commons.lang.ObjectUtils"%>
		Session:
		<table>
			<%
			Enumeration<String> attributeNames = session.getAttributeNames();
			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();
				Object attribute = session.getAttribute(attributeName);
				out.println(String.format("<tr><td>%1$s</td><td>%2$s</td></tr>",attributeName, ObjectUtils.toString(attribute)));
			}
			%>
		</table>
		Header:
		<table border="1">
			<%
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				out.println("<tr>");
				String headerName = headerNames.nextElement();
				out.println("<td>");
				out.println(headerName);
				System.out.println(headerName);
				out.println("</td>");
				out.println("<td>");
				Enumeration<String> headers = request.getHeaders(headerName);
				while (headers.hasMoreElements()) {
					String header = headers.nextElement();
					out.println(header);
					System.out.println(header);
					out.println("<br />");
				}
				out.println("</td>");
				out.println("</tr>");
			}
			%>
		</table>
		Env:
		<table border="1">
			<%
				Map env = System.getenv();
				Set envKeySet = env.keySet();
				for (Iterator iter = envKeySet.iterator(); iter.hasNext();) {
					String key = iter.next().toString();
					out.print("<tr><td align='right'>");
					out.print(key);
					out.print("</td><td>");
					out.print(env.get(key));
					out.println("</td></tr>");
				}
			%>
			<tr>
				<td colspan="2"><hr /></td>
			</tr>
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
