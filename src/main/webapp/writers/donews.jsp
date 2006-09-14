<%@ page contentType="text/html; charset=UTF-8"%>
<img src="images/bsps/donews.gif" />
<table>
	<tr>
		<th colspan="2" style="text-align:left;">
			请填写将数据写入 DoNews 时必须的信息
		</th>
	</tr>
	<tr>
		<td>
			DoNews 帐号：
		</td>
		<td>
			<input type="text" name="username" onchange="$('writer_donews_username_preview').href='http://blog.donews.com/' + this.value;" />
			（在这里填写你的 DoNews 帐号。） 你可以点击
			<a id="writer_donews_username_preview" href="http://blog.donews.com/">
				这里
			</a>
			访问你在 DoNews 的 Blog。
		</td>
	</tr>
	<tr>
		<td>
			DoNews 密码：
		</td>
		<td>
			<input type="password" name="password" />
			（在这里填写 DoNews 密码，我们不会记录您的密码，在搬家完成后就删除这些个人信息。）
		</td>
	</tr>
	<tr>
		<td style="vertical-align:top;">
			验证码：
		</td>
		<td>
			<input type="text" name="identifyingCode" />
			（请在这里填写后面这张图片上的文字，这个验证码是给搬家程序登录 DoNews Blog 用的。如果你看不清下面的图片，请单击图片重新获取一张。）
			<br />
			<img src="identifying-code-image?type=writer&now=donews" alt="验证码图片，单击重新获取新的验证码。" style="border:0px;" onclick="this.src='identifying-code-image?type=writer&now=' + new Date().getTime()" />
		</td>
	</tr>
</table>
<div class="attention">
	仅支持导入日志，不支持导入日期、评论、标签、多媒体。
</div>
