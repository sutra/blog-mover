<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
<!--
var metaWeblog = new MetaWeblog();
//-->
</script>
<div>
	MetaWeblog
	<a href="http://www.xmlrpc.com/metaWeblogApi">RFC: MetaWeblog API</a>
</div>
<div>
	一切支持
	<a href="http://www.xmlrpc.com/metaWeblogApi">RFC: MetaWeblog API</a> 的
	Blog 均可使用该读取器。
</div>
<div>
	<table>
		<tr>
			<td>
				ServerURL:
			</td>
			<td>
				<input type="text" id="readers.metaWeblog.serverURL"
					name="serverURL" tabindex="100" style="width: 200px;" />
				这里填写你的博客服务器的 MetaWeblog 接口的 XML-RPC 地址。比如：
				<a href="http://xpert.cn">专家博客</a>的接口地址为：
				<a href="#"
					onclick="$('readers.metaWeblog.serverURL').value='http://xpert.cn/xmlrpc';return false;"
					title="单击填入">http://xpert.cn/xmlrpc</a>
			</td>
		</tr>
		<tr>
			<td>
				username:
			</td>
			<td>
				<input type="text" id="readers.metaWeblog.username" name="username"
					tabindex="101" />
				登录你的 Blog 的帐号。
			</td>
		</tr>
		<tr>
			<td>
				password:
			</td>
			<td>
				<input type="password" id="readers.metaWeblog.password"
					name="password" tabindex="102" />
				登录你的 Blog 的密码。
			</td>
		</tr>
		<tr>
			<td>
				blogid:
			</td>
			<td>
				<!--
				<input type="text" name="blogid" tabindex="103" onfocus="" />
				-->
				<select name="blogid" id="readers.metaWeblog.blogid" tabindex="103"></select>
				<a href="#" onclick="metaWeblog.getUsersBlogs()" class="mockButton"
					onfocus="this.blur();"
					onmouseover="this.className='mockButtonOver'"
					onmousedown="this.className='mockButtonDown'"
					onmouseout="this.className='mockButton'">获取博客列表 </a> 你的博客在该 BSP
				上的唯一标识。 比如：你使用的是
				<a href="http://xpert.cn">专家博客</a>的博客服务，那么这里应该填写的是 URL 中的一部分：http://
				<span style="color:red">blogid</span>.xpert.cn。
			</td>
		</tr>
	</table>
</div>
<div class="attention">
	仅支持读入日志、日期，不支持导入评论、标签、多媒体。
</div>
