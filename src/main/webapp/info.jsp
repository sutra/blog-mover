Current path: <%=new java.io.File("./").getAbsolutePath()%>
<%=request.getRealPath(".")%>
<xmp>
<%
System.getProperties().list(new java.io.PrintWriter(out));
%>
</xmp>