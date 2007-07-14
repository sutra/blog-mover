<%@ page contentType="text/html; charset=UTF-8"%>
<img src="images/bsps/baidu-hi.gif" />
<table>
	<tr>
		<th colspan="2" style="text-align:left;">
			请填写将数据写入百度空间时必须的信息
		</th>
	</tr>
	<tr>
		<td>
			百度帐号：
		</td>
		<td>
			<input type="text" name="username" tabindex="100" />
		</td>
	</tr>
	<tr>
		<td>
			百度密码：
		</td>
		<td>
			<input type="password" name="password" tabindex="101" />
			（在这里填写您的百度帐号的密码，我们不会记录您的密码，在搬家完成后就删除这些个人信息。）
		</td>
	</tr>
	<tr>
		<td>
			操作间隔：
		</td>
		<td>
			<input type="text" name="operationInterval" value="1000"
				tabindex="102" />
			毫秒。（每隔多长时间发送一次HTTP请求，由于百度空间不允许频繁提交，如果你发现提交过程失败，请调整该参数。）
		</td>
	</tr>
</table>
<div class="attention">
	仅支持导入日志，不支持导入日期、评论、标签、多媒体。
</div>
