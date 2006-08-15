<%@page contentType="text/html; charset=UTF-8"%>
请填写必要信息，用于 Blog Remover 像 Sohu Blog 写入日志：
<br />
用户名：
<input type="text" name="username" />
<select name="maildomain">
	<option value="@sohu.com">
		@sohu.com
	</option>
	<option value="@chinaren.com">
		@chinaren.com
	</option>
	<option value="@vip.sohu.com">
		@vip.sohu.com
	</option>
	<option value="@sms.sohu.com">
		@sms.sohu.com
	</option>
	<option value="@sol.sohu.com">
		@sol.sohu.com
	</option>
	<option value="@sogou.com">
		@sogou.com
	</option>
</select>
<br />
密 码：
<input type="password" name="passwd" />
（我们不会恶意记录您的密码，该密码用于 Blog Remover 登录您的 Sohu Blog 后获取日志信息）
<div class="attention">
	将写入内容，不写入评论、图片、多媒体、日期等。
</div>
