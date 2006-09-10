<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page
	import="java.io.File,java.util.*,org.apache.commons.lang.*,com.redv.blogmover.util.LocaleComparator"%>
<jsp:directive.page import="com.redv.blogmover.util.LocaleComparator;" />
<table>
	<tr>
		<td>
			Feed Type:
		</td>
		<td>
			<select name="feedType">
				<option value="rss_0.9">
					RSS 0.90
				</option>
				<option value="rss_0.91N">
					RSS 0.91 Netscape
				</option>
				<option value="rss_0.91U">
					RSS 0.91 Userland
				</option>
				<option value="rss_0.92">
					RSS 0.92
				</option>
				<option value="rss_0.93">
					RSS 0.93
				</option>
				<option value="rss_0.94">
					RSS 0.94
				</option>
				<option value="rss_1.0">
					RSS 1.0
				</option>
				<option value="rss_2.0">
					RSS 2.0
				</option>
				<option value="atom_0.3">
					Atom 0.3
				</option>
				<option value="atom_1.0" selected="selected">
					Atom 1.0
				</option>
			</select>
			注意：格式 RSS 0.90, RSS 0.91 Netscape, RSS 0.91 Userland 的日志数量只能在1～15之间。
		</td>
	</tr>
	<tr>
		<td>
			Title:
		</td>
		<td>
			<input type="text" name="title" />
		</td>
	</tr>
	<tr>
		<td>
			Link:
		</td>
		<td>
			<input type="text" name="link" />
		</td>
	</tr>
	<tr>
		<td>
			Description:
		</td>
		<td>
			<input type="text" name="description" />
		</td>
	<tr>
		<td>
			Language:
		</td>
		<td>
			<select name="language">
				<%
						String availableLocalesOptionsString;
						availableLocalesOptionsString = (String) application.getAttribute("availableLocalesOptionsString");
						
						if (availableLocalesOptionsString == null) {
							Locale[] availableLocales = Locale.getAvailableLocales();
							// Sorting.
							List<Locale> locales = new ArrayList<Locale>();
							for (Locale locale : availableLocales) {
								locales.add(locale);
							}
							Collections.sort(locales, new LocaleComparator());
							application.setAttribute("availableLocales", locales);
						
							StringBuffer sb = new StringBuffer();
							for (Locale locale : locales) {
								sb.append("<option");
								if (locale.toString().equals("zh_CN")) {
							sb.append(" selected='selected'");
								}
								sb.append(" value='");
								sb.append(locale.toString()).append("'>");
								sb.append(StringUtils.replace(StringUtils.rightPad(locale.toString(), 5, " "), " ", "&nbsp;"));
								sb.append(" ");
								sb.append(locale.getDisplayName());
								sb.append("</option>");
							}
							availableLocalesOptionsString = sb.toString();
							application.setAttribute("availableLocalesOptionsString", availableLocalesOptionsString);
						}
						out.println(availableLocalesOptionsString);
				%>
			
		</td>
	</tr>
	<tr>
		<td>
			Copyright:
		</td>
		<td>
			<input type="text" name="copyright" />
		</td>
	</tr>
	<tr>
		<td>
			Author:
		</td>
		<td>
			<input type="text" name="author" />
		</td>
	</tr>
</table>
<input type="hidden" name="filename" value="<%=session.getId()%>" />
<a
	href="download-file?filename=<%=session.getId()%>&attachmentFilename=feed.rss-atom.xml">当写入结束后，单击这里下载
	Atom/RSS 文件。</a>
