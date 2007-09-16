<%@ page contentType="text/html; charset=UTF-8"%>
<div>
	<img src="images/bsps/baidu-hi.gif" />
</div>
<div>
	请填写将数据写入百度空间时必须的信息
</div>
<div>
	<table>
		<tr>
			<td class="label">
				百度帐号：
			</td>
			<td>
				<input type="text" name="username" tabindex="100" />
			</td>
		</tr>
		<tr>
			<td class="label">
				百度密码：
			</td>
			<td>
				<input type="password" name="password" tabindex="101" />
				（在这里填写您的百度帐号的密码，我们不会记录您的密码，在搬家完成后就删除这些个人信息。）
			</td>
		</tr>
		<tr>
			<td class="label">
				操作间隔：
			</td>
			<td>
				<input type="text" name="operationInterval" value="1000"
					tabindex="102" />
				毫秒。
			</td>
		</tr>
	</table>
</div>
<div class="attention">
	仅支持读入日志、日期，不支持读入评论、标签、多媒体。
</div>
