<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="scripts/prototype.js" charset="UTF-8"></script>
<script type="text/javascript" src="dwr/interface/User.js"></script>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="scripts/blog-remover.jsp"></script>
<script type="text/javascript" src="scripts/reader.jsp"></script>
<script type="text/javascript" src="scripts/writer.jsp"></script>
<script type="text/javascript" src="scripts/pick-web-log.jsp"></script>
<script type="text/javascript">
<!--
DWREngine.setReverseAjax(false);

//if (window.addEventListener) {
//	window.addEventListener("beforeunload", doClose, false);
//}
//else if (window.attachEvent) {
//	window.attachEvent("onbeforeunload", doClose);
//}
//else {
	window.onbeforeunload = doClose;
//}
function doClose() {
	var t = "───────────────────────\r\n您确定要离开 Blog Remover 吗？\r\n确定后未完成的操作你将无法控制。\r\n───────────────────────";
	return t;
}

//if (window.addEventListener) {
//	window.addEventListener("unload", doLogout, false);
//}
//else if (window.attachEvent) {
//	window.attachEvent("onunload", doLogout);
//}
//else {
//	window.onunload = doLogout;
//}
// 发出退出指令。
function doLogout() {
	var myAjax = new Ajax.Request(
		'logout.jhtml',
		{method: 'get', parameters: ''}
		);
}

//-->
</script>
