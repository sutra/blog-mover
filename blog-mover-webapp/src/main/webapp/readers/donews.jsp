<%@ page contentType="text/html; charset=UTF-8"%>
<div>
	<img src="images/bsps/donews.gif" />
</div>
<div>
	请填写从 DoNews 读取日志时必须的信息
</div>
<div>
	<table>
		<tr>
			<td class="label">
				帐号：
			</td>
			<td>
				<input type="text" name="username"
					onchange="$('writer_donews_username_preview').href='http://blog.donews.com/' + this.value;"
					tabindex="100" />
				（在这里填写你的 DoNews 帐号。） 你可以点击
				<a id="writer_donews_username_preview"
					href="http://blog.donews.com/"> 这里 </a> 访问你在 DoNews 的 Blog。
			</td>
		</tr>
		<tr>
			<td class="label">
				密码：
			</td>
			<td>
				<input type="password" name="password" tabindex="101" />
				（在这里填写 DoNews 密码，我们不会记录您的密码，在搬家完成后就删除这些个人信息。）
			</td>
		</tr>
		<tr>
			<td class="label" style="vertical-align:top;">
				验证码：
			</td>
			<td>
				<input type="text" name="identifyingCode" tabindex="102" />
				（请在这里填写后面这张图片上的文字，这个验证码是给搬家程序登录 DoNews Blog
				用的。如果你看不清下面的图片，请单击图片重新获取一张。）
				<br />
				<img src="identifying-code-image?type=reader&now=donews"
					alt="验证码图片，单击重新获取新的验证码。" style="border:0px;cursor:pointer"
					onclick="this.src='identifying-code-image?type=reader&now=' + new Date().getTime()" />
			</td>
		</tr>
		<tr>
			<td class="label">
				HTTP 请求随机间隔：
			</td>
			<td>
				<input type="text" name="minimumInterval" tabindex="103" value="1000" />
				~
				<input type="text" name="maximumInterval" tabindex="104" value="30000" />
				毫秒（1秒=1000毫秒）
				有些 BSP 限制了 HTTP 请求频度，因此用此间隔来模拟人类操作。
			</td>
		</tr>
	</table>
</div>
<div class="attention">
	仅支持读入日志内容、日期，不支持评论、标签、多媒体。
</div>
