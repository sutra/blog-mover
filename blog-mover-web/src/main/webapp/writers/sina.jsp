<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:bundle basename="com.redv.blogmover.bsps.sina.messages">
<img src="images/bsps/sina.gif" />
<table>
	<tr>
		<th colspan="2" style="text-align:left;">
			<fmt:message key="header" />
		</th>
	</tr>
	<tr>
		<td>
			<fmt:message key="username" />
		</td>
		<td>
			<input type="text" name="username" tabindex="100" />
			<fmt:message key="username.comment" />
		</td>
	</tr>
	<tr>
		<td>
			<fmt:message key="password" />
		</td>
		<td>
			<input type="password" name="password" tabindex="101" />
			<fmt:message key="password.comment" />
		</td>
	</tr>
	<tr>
		<td style="vertical-align:top;">
			<fmt:message key="identifyingCode" />
		</td>
		<td>
			<input type="text" name="identifyingCode" tabindex="102" />
			<fmt:message key="identifyingCode.comment" />
			<br />
			<img src="identifying-code-image?type=writer&now=sina"
				alt="<fmt:message key="identifying-code-image.alt" />" style="border:0px;"
				onclick="this.src='identifying-code-image?type=writer&now=' + new Date().getTime()" />
		</td>
	</tr>
</table>
<div class="attention">
	<fmt:message key="attention" />
</div>
</fmt:bundle>