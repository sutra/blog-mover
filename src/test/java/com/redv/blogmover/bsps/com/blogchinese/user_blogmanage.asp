<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<title>blogmover-用户管理后台</title>
<link title="Oblog_Green" href="OblogStyle/OblogStyleAdminMainFrame.css" rel="stylesheet" media="all" type="text/css" rev="alternate" />
<script src="inc/function.js" type="text/javascript"></script>
</head>

<body onUnload="chkcopy();">

<div class="none"><p>用户管理界面（无样式纯文字版，适合手机及PDA，您可以点样式切换回其他界面）</p></div>

<div id="menu">
	<div class="menu_content">
		<div class="menu_navimg"></div>
		<div id="nav">
			<ul id="div1">
				<li class="w100p"><a href="user_index.asp" >管理首页</a>
					
          <ul>
            <li><a href="user_index.asp">管理首页</a></li>

            <li><a href="user_help.asp">用户管理后台帮助</a></li>
          </ul>
				</li>
				<li><a href="user_blogmanage.asp">日志管理</a>
					
          <ul>
            <li><a href="user_post.asp">添加日志</a></li>
            <li><a href="user_blogmanage.asp?usersearch=5">草稿箱</a></li>

            <li><a href="user_blogmanage.asp">所有日志</a></li>
            <li><a href="user_blogmanage.asp?action=downlog">备份日志</a></li>
            <li><a href="user_subject.asp">日志分类管理</a></li>
          </ul>
				</li>
				
				<li><a href="user_photomanage.asp">相册管理</a>
					
          <ul>

            <li><a href="user_post.asp?t=1">添加相片</a></li>
            <li><a href="user_blogmanage.asp?t=1">所有相片</a></li>
            <li><a href="user_subject.asp?t=1">相册分类管理</a></li>
          </ul>
				</li>
				
				<li  class="w100p"><a href="user_comments.asp">评论留言</a>
					
          <ul>

            <li><a href="user_comments.asp">评论管理</a></li>
            <li><a href="user_messages.asp">留言管理</a></li>
          </ul>
				</li>
				<li><a href="user_template.asp?action=showconfig">模版设置</a>
					
          <ul>
            <li><a href="user_template.asp?action=showconfig">选择模板</a></li>

            <li><a href="user_template.asp?action=modiconfig&amp;editm=1">修改主模板</a></li>
            <li><a href="user_template.asp?action=modiviceconfig&amp;editm=1">修改Blog内容模板</a></li>
            <li><a href="user_template.asp?action=bakskin">备份我当前的模板</a></li>
            
           		<li><a href="user_template.asp?action=good">推荐共享我的模板</a></li>
           	
            <li>&nbsp;</li>
          </ul>
				</li>

				<li><a href="user_setting.asp">控制面板</a>
					
          <ul>
            <li><a href="user_setting.asp">站点设置</a></li>
            <li><a href="user_setting.asp?action=placard">修改公告</a></li>
            <li><a href="user_setting.asp?action=links">我的友情连接</a></li>
            <li><a href="user_setting.asp?action=userinfo">我的个人资料</a></li>

            <li><a href="user_setting.asp?action=userpassword">修改我的密码</a></li>
            <li><a href="user_setting.asp?action=blogpassword">加密整站</a></li>
            <li><a href="user_setting.asp?action=blogstar">申请博客之星</a></li>
          </ul>
				</li>
				<li><a href="user_team.asp">团队好友</a>
					
          <ul>

            <li><a href="user_friends.asp?action=add">添加好友</a></li>
            <li><a href="user_friends.asp">好友管理</a></li>
            <li><a href="user_friends.asp?action=add&amp;type=black">添加黑名单</a></li>
            <li><a href="user_friends.asp?usersearch=1">管理黑名单</a></li>
            <li><a href="user_team.asp">博客团队</a></li>
            <li>&nbsp;</li>

          </ul>
				</li>
				<li><a href="user_files.asp">文件管理</a>
					
          <ul>
            <li><a href="user_files.asp">所有文件</a></li>
            <li><a href="user_files.asp?usersearch=1">图片文件</a></li>
            <li><a href="user_files.asp?usersearch=2">压缩文件</a></li>

            <li><a href="user_files.asp?usersearch=3">文档文件</a></li>
            <li style="display:none"><a href="user_files.asp?usersearch=4">媒体文件</a></li>
          </ul>
				</li>
			</ul>
		</div>
	</div>
</div>

<div id="top">

<div class="logo"></div>
  <div class="top_button">
	<a href="user_post.asp"><div class="none">发表Blog </div><img src="OblogStyle/OblogStyleAdminImages/space.gif" alt="发表Blog" border="0" class="ico_5" /></a>
		<a href="user_blogmanage.asp">
	<div class="none">管理Blog </div><img class="ico_4" src="OblogStyle/OblogStyleAdminImages/space.gif" alt="管理Blog" /></a>
		
		<a href="user_photomanage.asp"><div class="none">我的相册</div><img class="ico_7" src="OblogStyle/OblogStyleAdminImages/space.gif" alt="我的相册" /></a>

		
		<a href="user_messages.asp"><div class="none">留言管理</div><img class="ico_3" src="OblogStyle/OblogStyleAdminImages/space.gif" alt="留言管理"/></a>
		<a href="user_comments.asp"><div class="none">评论管理</div><img  class="ico_2" src="OblogStyle/OblogStyleAdminImages/space.gif" alt="评论管理" /></a>
		<a href="user_update.asp"><div class="none">重建首页</div><img class="ico_1" src="OblogStyle/OblogStyleAdminImages/space.gif" alt="重建首页" /></a>
		<a  href="user_index.asp?t=logout"><div class="none">退出登陆</div><img class="ico_6" src="OblogStyle/OblogStyleAdminImages/space.gif" alt="退出登陆" /></a>
  </div>
  <div class="top_menu">
  <a href="http://blogmover.blogchinese.com" target="_blank">blogmover.blogchinese.com</a> | <a href="user_pmmanage.asp">短消息(0)</a> | <a href="index.asp" target="_blank">站点首页</a> | <a href="http://club.blogchinese.com" target=_blank>工作平台</a></div>

</div>

<div id="main">
  <div class="submenu">
    <div class="side_c1 side11"></div>
    <div class="side_c2 side21"></div>
    <div class="submenu_content">
        &#8226; <a href="user_blogmanage.asp?t=0">所有日志</a>
        
            &#8226; <a href="user_blogmanage.asp?usersearch=5&t=0">草稿箱</a>

			&#8226; <a href="user_blogmanage.asp?action=downlog&t=0">备份日志</a>
		
		&#8226; <a href="user_subject.asp?t=0">日志分类管理</a>
    </div>
  </div>
  <div class="content">
    <div class="content_top">
            <div class="side_d1 side11"></div>

            <div class="side_d2 side21"></div>
            日志管理
    </div>
    
    <div class="content_body">

<div id="list">
    <h2>
    <form name="form1" action="user_blogmanage.asp" method="get">
    	<input type="hidden" name="t" value="0">
快速查找:
        <select size=1 name="usersearch" onChange="javascript:submit()">

          <option value="10" selected>请选择查找类型</option>
          <option value="0">列出所有日志</option>
          <option value="1">未通过审核的日志</option>
          <option value="2">已通过审核的日志</option>
          <option value="3">推荐日志</option>
           <option value="5">草稿箱</option>

          
        </select> &nbsp;
      按专题查找:&nbsp;
        <select name="selectsub" id="selectsub" onChange="javascript:submit()">
<option value=''>请选择专题</option><option value=0>未分类</option>
        </select>&nbsp;
搜索:
        <select name="Field" id="Field">
            
            <option value="id">日志ID号</option>

            <option value="topic" selected>日志标题</option>
            <option value="tag">标签(TAG)</option>
         </select>
         <input name="Keyword" type="text" id="Keyword" size="20" maxlength="30">
         <input type="submit" name="Submit2" value=" 搜索 ">
  </form>
    </h2>

<style type="text/css">
<!--
    #list ul{ width: 95%;padding:0;}
    #list ul li.t1 { width: 50px}
    #list ul li.t2 { width: 90px}
    #list ul li.t3 { width: 360px}
    #list ul li.t4 { width: 90px;text-align:center}
    #list ul li.t5 { width: 100px}
    #list ul li.t6 { width: 60px}
    #list ul li.t7 { width: 50px}
    #list ul li.t8 { width: 60px}
    #list ul li.t9 { width: 90px}
-->
</style>

<h1>当前选择：&nbsp;&gt;&gt;&nbsp;所有日志 (共有21篇日志)</h1>
    <form name="myform" method="Post" action="user_blogmanage.asp?t=0" onSubmit="return confirm('确定要执行选定的操作吗？');">
    <ul class="list_top">
    <li class="t1">选中</li>
    <li class="t2">分类</li>
    <li class="t3">日志标题</li>

    <li class="t4">作者</li>
    <li class="t5">发表时间</li>
    <li class="t6">点/评</li>
    <li class="t7">状态</li>
    <li class="t9">操作</li>
    </ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939711' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127212020.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939711&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939711&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939711&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939712' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127212644.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939712&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939712&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939712&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939713' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127212710.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939713&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939713&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939713&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939714' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127212720.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939714&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939714&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939714&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939718' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/200712721289.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939718&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939718&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939718&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939722' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127212830.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939722&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939722&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939722&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939724' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127212839.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939724&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939724&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939724&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939727' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/200712721299.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939727&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939727&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939727&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939730' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127212934.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939730&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939730&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939730&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939736' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/200712721303.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939736&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939736&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939736&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939739' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213010.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939739&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939739&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939739&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939741' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213028.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939741&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939741&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939741&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939742' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213056.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939742&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939742&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939742&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939743' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/200712721312.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939743&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939743&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939743&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939745' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213123.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939745&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939745&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939745&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939751' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213137.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939751&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939751&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939751&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939753' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213234.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939753&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939753&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939753&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939755' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213255.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939755&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939755&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939755&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939756' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127213320.shtml target=_blank>a </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939756&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939756&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939756&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
    <li class="t1"><input name='id' type='checkbox' onClick="unselectall()" id="id" value='939703' /></li>
    <li class="t2">未分类</li>
    <li class="t3">
<a href=06091/260430/archives/2007/2007127211931.shtml target=_blank>test </a> </li>
    <li class="t4">blogmover</li>

    <li class="t5" title="2007-1-27 21:19:00">2007-1-27 </li>
    <li class="t6">0/0</li>
    <li class="t7">发布</li>
    <li class="t9">
<a href='user_blogmanage.asp?action=updatelog&id=939703&t=0'>发布</a>&nbsp;<a href='user_post.asp?logid=939703&t=0'>修改</a>&nbsp;<a href='user_blogmanage.asp?action=del&id=939703&t=0' onClick='return confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
</ul>

    <ul class="list_bot">
    <input name="chkAll" type="checkbox" id="chkAll" onclick=CheckAll(this.form) value="checkbox" />
              全选
操作:
    <input name="action" type="radio" value="del" checked onClick="document.myform.subject.disabled=true" />删除&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="action" type="radio" value="Move" onClick="document.myform.subject.disabled=false" />移动到专题
    <select name="subject" id="subject" disabled>
    
    </select>&nbsp;&nbsp;&nbsp;
    
    <input type="submit" name="Submit" value=" 执行 ">
    </ul>

    </form>
</div>
<div id="showpage">共21篇日志&nbsp;&nbsp;首页 上一页&nbsp;<a href='user_blogmanage.asp?t=0&page=2'>下一页</a>&nbsp;<a href='user_blogmanage.asp?t=0&page=2'>尾页</a>&nbsp;页次：1/2</strong>页 &nbsp;20篇日志/页&nbsp;转到：<select name='page' size='1' onchange="javascript:window.location='user_blogmanage.asp?t=0&page='+this.options[this.selectedIndex].value;"><option value='1' selected >1</option><option value='2'>2</option></select></div>
    </div>
    
    <div class="content_bot">

            <div class="side_e1 side12"></div>
            <div class="side_e2 side22"></div>
    </div>
    
  </div>
</div>
  
  <div id="bottom"><center><a href="http://www.blogchinese.com" target=_blank>站点首页</a> | 
<a href="http://www.blogchinese.com/lianxi.htm" target=_blank>联系我们</a> | <a href="http://www.blogchinese.com/reg.asp" target=_blank>博客注册</a> | <a href="http://www.blogchinese.com/user_login.asp" target=_blank>博客登陆</a> | 

<a class="hint9pt" onclick="this.style.behavior='url(#default#homepage)';this.setHomePage('http://www.blogchinese.com'); return false;" href="#">设为首页</a> | <a class="hint9pt" onclick="window.external.AddFavorite('http://www.blogchinese.com', '博客中国人 Blogchinese - 让每个中国人拥有博客！'); return false;" href="#">加入收藏夹</a><br>
&copy; 2004-2006 <a href="http://www.blogchinese.com">博客中国人 - 让每个中国人拥有博客！</a>, All rights reserved.<a class="hint9pt" target="_blank" href="http://www.blogchinese.com/reg.asp?action=protocol">博客中国人服务协议</a>&nbsp;<a target="_blank" href="http://www.blogchinese.com/lianxi.htm">与Blogchinese合作</a> <a href="http://www.miibeian.gov.cn" target=_blank>鄂ICP备05000932号</a></div><div id="powered"><a href="http://www.blogchinese.com" target="_blank"><img src="images\blogchinese_powered.gif" border="0" alt="Powered by Blogchinese." /></a></div>

</body>
</html>

