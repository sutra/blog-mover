<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page
	import="java.io.File,java.util.*,com.redv.blogmover.util.*,org.apache.commons.lang.*,java.net.*"%>
<%!String filename = RandomStringUtils.randomNumeric(8);%>
<table>
	<tr>
		<td>
			Feed Type:
		</td>
		<td>
			<select id="feedType" name="feedType" tabindex="100"
				onchange='
				var href = "download-file?filename=com/redv/blogmover/<%=session.getId()%>/<%=filename%>&attachmentFilename=feed.rss-atom.xml&contentType=";
				var feedType = (this.options[this.options.selectedIndex].value);
				var contentType;
				if (feedType.substring(0, 3) == "rss") {
					contentType="<%=URLEncoder.encode("application/rss+xml", "UTF-8")%>";
				} else {
					contentType="<%=URLEncoder.encode("application/atom+xml", "UTF-8")%>";
				}
				document.getElementById("downloadFileAnchor").href = href + contentType;
				'>
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
			<input type="text" name="title" tabindex="101" />
		</td>
	</tr>
	<tr>
		<td>
			Link:
		</td>
		<td>
			<input type="text" name="link" tabindex="102" />
		</td>
	</tr>
	<tr>
		<td>
			Description:
		</td>
		<td>
			<input type="text" name="description" tabindex="103" />
		</td>
	</tr>
	<tr>
		<td>
			Language:
		</td>
		<td>
			<select name="language" tabindex="104">
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
								String value = StringUtils.replace(locale.toString(), "_", "-");
								sb.append("<option");
								if (value.equals("zh-CN")) {
							sb.append(" selected='selected'");
								}
								sb.append(" value='");
								sb.append(value);
								sb.append("'>");
								sb.append(StringUtils.replace(StringUtils.rightPad(value, 5, " "), " ", "&nbsp;"));
								sb.append(" ");
								sb.append(locale.getDisplayName());
								sb.append("</option>");
							}
							availableLocalesOptionsString = sb.toString();
							application.setAttribute("availableLocalesOptionsString", availableLocalesOptionsString);
						}
						out.println(availableLocalesOptionsString);
				%>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			Copyright:
		</td>
		<td>
			<input type="text" name="copyright" tabindex="105" />
		</td>
	</tr>
	<tr>
		<td>
			Author:
		</td>
		<td>
			<input type="text" name="author" tabindex="106" />
		</td>
	</tr>
</table>
<input type="hidden" name="filename" tabindex="107"
	value="com/redv/blogmover/<%=session.getId() + "/" + filename%>" />
当写入结束后，
<a id="downloadFileAnchor"
	href="download-file?filename=com/redv/blogmover/<%=session.getId()%>/<%=filename%>&attachmentFilename=feed.rss-atom.xml&contentType=<%=URLEncoder.encode("application/atom+xml", "UTF-8")%>"
	target="_blank" tabindex="108">单击这里查看 Atom/RSS 文件</a>
，
<a
	href="download-file?filename=com/redv/blogmover/<%=session.getId()%>/<%=filename%>&attachmentFilename=feed.rss-atom.xml&contentType=<%=URLEncoder.encode("application/oct-stream", "UTF-8")%>"
	target="_blank" tabindex="109">单击这里下载 Atom/RSS 文件</a>
。
<div>
	<img src="images/romepower04-anim-med.gif" />
</div>
