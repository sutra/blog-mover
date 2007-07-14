<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="scripts/prototype.js" charset="UTF-8"></script>
<script type="text/javascript" src="scripts/cookie.js" charset="UTF-8"></script>
<script type="text/javascript" src="dwr/interface/SyncUser.js"></script>
<script type="text/javascript" src="dwr/interface/BloggerClient.js"></script>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="scripts/blog-mover.jsp"></script>
<script type="text/javascript" src="scripts/sync/reader.jsp"></script>
<script type="text/javascript" src="scripts/sync/writer.jsp"></script>
<script type="text/javascript" src="scripts/sync/pick-web-log.jsp"></script>
<script type="text/javascript" src="scripts/metaWeblog.js"></script>
<script type="text/javascript" src="scripts/blogger.js"></script>
<script type="text/javascript">
<!--
// DWREngine.setReverseAjax(false);

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
	var t = "───────────────────────\r\n您确定要离开 Blog Mover 吗？\r\n确定后未完成的操作你将无法控制。\r\n───────────────────────";
	return t;
}

//if (window.addEventListener) {
//	window.addEventListener("unload", doLogout, false);
//}
//else if (window.attachEvent) {
//	window.attachEvent("onunload", doLogout);
//}
//else {
	window.onunload = doLogout;
//}
// 发出退出指令。
function doLogout() {
	var myAjax = new Ajax.Request(
		'logout.html',
		{method: 'get', parameters: ''}
		);
}

//-->
</script>


<script type="text/javascript">
<!--
var metaWeblogReader = new MetaWeblog("readers");
var metaWeblogWriter = new MetaWeblog("writers");
var blogger = new Blogger("writers");
//-->
</script>