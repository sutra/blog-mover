<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:bundle basename="com.redv.blogmover.bsps.messages-writers">
<img src="images/bsps/sina.gif" />
<table>
	<tr>
		<th colspan="2" style="text-align:left;">
			请填写将数据写入 Sina Blog 时必须的信息
		</th>
	</tr>
	<tr>
		<td>
			Sina Blog 帐号：
		</td>
		<td>
			<input type="text" name="username" tabindex="100" />
			（在这里填写你的 Sina Blog 帐号。）
		</td>
	</tr>
	<tr>
		<td>
			Sina Blog 密码：
		</td>
		<td>
			<input type="password" name="password" tabindex="101" />
			（在这里填写 Sina Blog 密码，我们不会记录您的密码，在搬家完成后就删除这些个人信息。）
		</td>
	</tr>
	<tr>
		<td style="vertical-align:top;">
			验证码：
		</td>
		<td>
			<input type="text" name="identifyingCode" tabindex="102" />
			（请在这里填写后面这张图片上的文字，这个验证码是给搬家程序登录 Sina Blog
			用的。如果你看不清下面的图片，请单击图片重新获取一张。）
			<br />
			<img src="identifying-code-image?type=writer&now=sina"
				alt="验证码图片，单击重新获取新的验证码。" style="border:0px;"
				onclick="this.src='identifying-code-image?type=writer&now=' + new Date().getTime()" />
		</td>
	</tr>
</table>
<div class="attention">
	仅支持导入日志、日期，不支持导入评论、标签、多媒体。
</div>
