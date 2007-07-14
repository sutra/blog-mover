<%@page contentType="text/html; charset=UTF-8"%>
<div>
	<img src="images/bsps/sohublog.gif" alt="Sohu Blog" />
</div>
<div>
	请填写必要信息，用于 Blog Remover 从 Sohu Blog 读取日志：
</div>
<div>
	<table>
		<tr>
			<td class="label">
				用户名：
			</td>
			<td>
				<input type="text" name="username" tabindex="100" />
				<select name="maildomain" tabindex="101">
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
			</td>
		</tr>
		<tr>
			<td class="label">
				密码：
			</td>
			<td>
				<input type="password" name="passwd" tabindex="102" />
				（我们不会恶意记录您的密码，该密码用于 Blog Mover 登录您的 Sohu Blog 后获取日志信息）
			</td>
		</tr>
	</table>
</div>
<div class="attention">
	将读取日期、内容，不读取评论、图片、多媒体等。
</div>
