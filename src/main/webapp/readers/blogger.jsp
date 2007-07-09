<%@ page contentType="text/html; charset=UTF-8"%>
<div>
	Blogger
</div>
<div>
<a href="http://www.blogger.com/">http://www.blogger.com/</a>
</div>
<div>
	<input type="hidden" id="writers.blogger.serverURL" value="http://www.blogger.com/api" />
	<table>
		<tr>
			<td>
				username:
			</td>
			<td>
				<input type="text" id="writers.blogger.username" name="username" tabindex="101" />
				登录你的 Blog 的帐号。
			</td>
		</tr>
		<tr>
			<td>
				password:
			</td>
			<td>
				<input type="password" id="writers.blogger.password" name="password" tabindex="102" />
				登录你的 Blog 的密码。
			</td>
		</tr>
		<tr>
			<td>
				blogid:
			</td>
			<td>
				<select name="blogid" id="writers.blogger.blogid" tabindex="103"></select>
				<a href="#" onclick="blogger.getUsersBlogs()" class="mockButton"
					onfocus="this.blur();"
					onmouseover="this.className='mockButtonOver'"
					onmousedown="this.className='mockButtonDown'"
					onmouseout="this.className='mockButton'">获取博客列表 </a>
			</td>
		</tr>
	</table>
</div>
<div class="attention">
	仅支持导入日志、日期，不支持导入评论、标签、多媒体。
</div>