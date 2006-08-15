<%@page contentType="text/html; charset=UTF-8"%>
<div id="debugContainer">
	Javascript debug （下面是诊断信息，如果你不清楚，不必在意。）：
	<a href="#" onclick="javascript:$('debug').value = '';return false;">
		清空
	</a>
	<textarea id="debug" rows="10" cols="5" style="width:90%; height:400px"></textarea>
</div>
