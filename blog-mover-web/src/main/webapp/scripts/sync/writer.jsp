<%@ page contentType="text/javascript; charset=UTF-8"%>
// write
var writerBusy = false;
var writing = false;
function setWriter(className) {
	$("syncDestReadButton").disabled = true;
	setStatus("设置写入器……");
	debug("设置写入器：" + className);
	SyncUser.setSyncDest(
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
			$('syncDestReadButton').disabled = false;
		},
		onFailure: reportError, 
		evalScripts: true, 
		asynchronous:true}
		);
}
function blogSyncDestInitAndRead() {
	if (writerBusy) {
		alert("正在写入，请勿读取。");
	} else {
		$("syncDestReadButton").disabled = true;
		// DWREngine.setErrorHandler(readErrorHandler);
		setStatus("初始化读取器……");
		initSyncDest();
	}
}
function blogSyncDestWrite() {
	if (readerBusy) {
		alert("正在读取，请勿写入。");
	} else {
		$("syncDestWriteButton").disabled = true;
		DWREngine.setErrorHandler(writeErrorHandler);
		setStatus("正在写入……");
		writing = true;
		setTimeout("writeStatus()", 1000 * parseInt($F("statusRefreshInterval")));
		SyncUser.write({callback:writeReply,errorHandler:writeErrorHandler});
	}
}

function initSyncDest() {
	writerBusy = true;
	setStatus("正在初始化写入器……");

	// inputs
	var inputs = $("selectedWriterDiv").getElementsByTagName("input");
	var properties1 = new Array();
	var values1 = new Array();
	var index1 = 0;
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].name.length != 0) {
			properties1[index1] = inputs[i].name;
			values1[index1] = inputs[i].value;
			debug("parameter: " + properties1[index1] + "=" + values1[index1]);
			index1++;
		}
	}

	// selects
	var selects = $("selectedWriterDiv").getElementsByTagName("select");
	debug("selects.length: " + selects.length);
	var properties2 = new Array();
	var values2 = new Array();
	var index2 = 0;
	for (var i = 0; i < selects.length; i++) {
		debug("selects[" + i + "].name: " + selects[i].name);
		debug("selects[" + i + "].name.length: " + selects[i].name.length);
		if (selects[i].name.length != 0) {
			properties2[index2] = selects[i].name;
			values2[index2] = DWRUtil.getValue(selects[i]);
			debug("parameter: " + properties2[index2] + "=" + values2[index2]);
			index2++;
		}
	}

	//
	var properties = properties1.concat(properties2);
	var values = values1.concat(values2);
	
	debug("parameters count: " + properties.length);

	//
	SyncUser.setSyncDestProperties(
		properties,
		values,
//		{callback:initWriterReply, errorHandler:initSyncDestErrorHandler}
		{callback:initSyncDestReply, errorHandler:initSyncDestErrorHandler}
	);
}

function initSyncDestReply() {
	setStatus("正在读取……");
	reading = true;
	setTimeout("readStatus()", 1000 * parseInt($F("statusRefreshInterval")));
	SyncUser.read({callback:readReply, errorHandler:readErrorHandler});
}

function initSyncDestErrorHandler(errorString, exception) {
	setStatus("初始化写入器失败：" + errorString);
	alert("初始化写入器失败：\r\n" + errorString);
	writerBusy = false;
	$("syncDestReadButton").disabled = false;
}
function writeReply() {
	writing = false;
	writerBusy = false;
	setStatus("写入完毕。");
	$("syncDestWriteButton").disabled = false;
}
function writeErrorHandler(msg, exception) {
	writing = false;
	writerBusy = false;
	setStatus("写入时发生如下错误：" + msg);
	alert("写入时发生如下错误：\r\n" + msg);
	$("syncDestWriteButton").disabled = false;
}
function writeStatus() {
	debug("调用写入状态函数。");
	if (writing) {
		SyncUser.getSyncDestWriterStatus(
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