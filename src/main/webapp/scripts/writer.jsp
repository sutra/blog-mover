<%@ page contentType="text/javascript; charset=UTF-8"%>
// write
var writerBusy = false;
var writing = false;
function setWriter(className) {
	$("writeButton").disabled = true;
	setStatus("设置写入器……");
	debug("设置写入器：" + className);
	User.setWriter(
		className, 
		{
			callback:setWriterReply,
			errorHandler:function(errorString, exception)
			{
				setStatus("设置写入器失败：" + errorString);
				alert("设置写入器失败：\r\n" + errorString);
			}
		}
		);
}
function setWriterReply(data) {
	var writerId = g_writerId;
	var url = 'writers/' + writerId + '.jsp?now=' + new Date().getTime();
		var myAjax = new Ajax.Updater(
		'selectedWriterDiv',
		url,
		{method: 'get', 
		parameters: '', 
		onLoading: function(request) {
			placeholder.innerHtml = "...";
		},
		onComplete: function(request) {
			setStatus("写入器设置完毕。");
			$('writeButton').disabled = false;
		},
		onFailure: reportError, 
		evalScripts: true, 
		asynchronous:true}
		);
}
function blogWrite() {
	if (readerBusy) {
		alert("正在读取，请勿写入。");
	} else {
		$("writeButton").disabled = true;
		DWREngine.setErrorHandler(writeErrorHandler);
		setStatus("初始化写入器……");
		initWriter();
	}
}
function initWriter() {
	writerBusy = true;
	setStatus("正在初始化写入器……");

	// inputs
	var inputs = $("selectedWriterDiv").getElementsByTagName("input");
	var properties1 = new Array();
	var values1 = new Array();
	for (var i = 0; i < inputs.length; i++) {
		properties1[i] = inputs[i].name;
		values1[i] = inputs[i].value;
	}

	// selects
	var selects = $("selectedWriterDiv").getElementsByTagName("select");
	var properties2 = new Array();
	var values2 = new Array();
	for (var i = 0; i < selects.length; i++) {
		properties2[i] = selects[i].name;
		values2[i] = DWRUtil.getValue(selects[i]);
	}

	//
	var properties = properties1.concat(properties2);
	var values = values1.concat(values2);

	//
	User.setWriterProperties(
		properties,
		values,
		{callback:initWriterReply, errorHandler:initWriterErrorHandler}
	);
}

function initWriterReply(data) {
	setStatus("正在写入……");
	writing = true;
	setTimeout("writeStatus()", 1000 * parseInt($F("statusRefreshInterval")));
	User.write({callback:writeReply,errorHandler:writeErrorHandler});
}
function initWriterErrorHandler(errorString, exception) {
	setStatus("初始化写入器失败：" + errorString);
	alert("初始化写入器失败：\r\n" + errorString);
	writerBusy = false;
	$("writeButton").disabled = false;
}
function writeReply() {
	writing = false;
	writerBusy = false;
	setStatus("写入完毕。");
	$("writeButton").disabled = false;
}
function writeErrorHandler(msg, exception) {
	writing = false;
	writerBusy = false;
	setStatus("写入时发生如下错误：" + msg);
	alert("写入时发生如下错误：\r\n" + msg);
	$("writeButton").disabled = false;
}
function writeStatus() {
	debug("调用写入状态函数。");
	if (writing) {
		User.getWriterStatus(
			{callback:writeStatusReply, errorHandler:writeStatusErrorHandler}
			);
	}
}
function writeStatusReply(data) {
	// 写入尚未结束
	if (writing) {
		var s = "正在写入…… 当前写入数目/总数：" + data.currentCount + "/" 
			+ (data.totalCount == -1 ? "未知" : data.totalCount) + "，当前正在处理的日志：" 
			+ (data.currentWebLog.title === null ? "" : data.currentWebLog.title)
			+ "(" + data.currentWebLog.publishedDate + ")。";
		setStatus(s);
		debug(s);
		// 继续查询写入状态。
		setTimeout("writeStatus()", 1000 * parseInt($F("statusRefreshInterval")));
	}
}
function writeStatusErrorHandler(errorString, exception) {
	if (writing) {
		setStatus("获取写入状态失败：" + errorString);
		debug("获取写入状态失败：" + errorString);
		// 继续查询写入状态。
		setTimeout("writeStatus()", 1000 * parseInt($F("statusRefreshInterval")));		
	}
}