<%@page contentType="text/html; charset=UTF-8"%>
<div id="header">
	<ul class="menuList">
		<li id="menu_step1">
			<a title="Step 1: 从源 Blog 读取日志" onclick="return selectTab('step1')"
				accesskey="S"> <span class="accesskey">S</span>tep 1: 从源 Blog
				读取日志 </a>
		</li>
		<li id="menu_step2">
			<a title="Step 2: 去除不需要导入的日志" onclick="return selectTab('step2')"
				accesskey="T"> S<span class="accesskey">t</span>ep 2: 去除不需要导入的日志
			</a>
		</li>
		<li id="menu_step3">
			<a title="Step 3: 导入目标 Blog" onclick="return selectTab('step3')"
				accesskey="E"> St<span class="accesskey">e</span>p 3: 导入目标 Blog
			</a>
		</li>
		<li id="menu_option">
			<a title="选项" onclick="return selectTab('option')" accesskey="O">
				选项(<span class="accesskey">O</span>) </a>
		</li>
		<li id="menu_about">
			<a title="关于 Blog Mover" onclick="return selectTab('about')"
				accesskey="A"> 关于 Blog Mover(<span class="accesskey">A</span>) </a>
		</li>
		<%--
		<li>
			<a href="help" title="帮助" accesskey="H">帮助(<span
				class="accesskey">H</span>)</a>
		</li>
		--%>
	</ul>
	<div id="blogMoverTopRight">
		<a href="reports/moving-stat.jsp">搬迁报告</a>
		<a href="help/"
			class="blogMoverTopRightLast">帮助</a>
	</div>
</div>
