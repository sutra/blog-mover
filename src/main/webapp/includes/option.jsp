<%@ page contentType="text/html; charset=UTF-8"%>
<div id="option">
	<h3>
		请设置 Blog Remover 运行时参数：
	</h3>
	<div id="options">
		<div style="display:none;">
			界面语言：
			<select id="hl" name="hl" onchange="if(this.value != '') {window.onunload=function(){};self.location='?hl=' + this.value}">
				<option value="">----</option>
				<option value="zh-CN">中文简体</option>
				<option value="en-US">English</option>
			</select>
		</div>
		<div>
			状态刷新间隔：
			<input type="text" value="5" id="statusRefreshInterval" style="width:20px;text-align:right;" maxlength="1" />
			秒
		</div>
		<div>
			<input id="debugSwitch" type="checkbox" onclick="setDebug(this.checked)" />
			<label for="debugSwitch">
				允许 Javascript 调试
			</label>
			<input id="debugDivShowSwitch" type="checkbox" onclick="showDebug(this.checked)" />
			<label for="debugDivShowSwitch">
				显示 Javascript 调试信息
			</label>
		</div>
	</div>
</div>
